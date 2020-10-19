package com.geekbrains.controller;

import com.geekbrains.persist.entity.Role;
import com.geekbrains.persist.entity.User;
import com.geekbrains.persist.repo.RoleRepository;
import com.geekbrains.persist.repo.UserRepository;
import com.geekbrains.persist.repo.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10};
    private static final String INIT_SORT_FIELD = "id";

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String allUsers(Model model,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortBy") Optional<String> sortBy,
                           @RequestParam("sortDirection") Optional<Sort.Direction> sortDirection) {

        logger.info("Filtering by name {} email {} ", name, email);
        logger.info("Pagination: page {} size {} ", page, size);
        logger.info("Sorting: sortBy {} sortDirection {} ", sortBy, sortDirection);

        if (userRepository.count() != 0) {
            //
        } else {
            addToRepository();
        }

        Sort.Direction direction = sortDirection.orElse(Sort.Direction.ASC);

        PageRequest pageRequest = PageRequest.of(
                page.orElse(INITIAL_PAGE) - 1,
                size.orElse(INITIAL_PAGE_SIZE),
                direction,
                sortBy.orElse(INIT_SORT_FIELD));

        Specification<User> spec = UserSpecification.trueLiteral();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(UserSpecification.loginLike(name));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.emailLike(email));
        }

        model.addAttribute("usersPage", userRepository.findAll(spec, pageRequest));
        model.addAttribute("sortDirection", direction);
        model.addAttribute("sortReversDirection", direction.isAscending() ? "DESC" : "ASC");
        model.addAttribute("sortBy", sortBy.orElse(INIT_SORT_FIELD));
        model.addAttribute("pageSize", PAGE_SIZES);
        return "users";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        logger.info("Create user");
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User " + id + " not found", "User")
        );
        logger.info("Edit user {} ", user);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, @RequestParam Map<String, String> form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user";
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            bindingResult.rejectValue("matchingPassword", "password.no.match", "Введенные пароли не совпадают");
            return "user";
        }

        Set<String> roles = roleRepository.findAll().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(roleRepository.findByRoleName(key));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Update user {} ", user);
        userRepository.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        logger.info("Delete user by id {} ", id);
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    public void addToRepository() {
        for (int i = 0; i < 10; i++) {
            //below we are adding users to our repository for the sake of this example
            User user = new User();
            user.setLogin("Kirill" + i);
            user.setEmail("kirix" + i + "@mail.ru");
            user.setPassword(passwordEncoder.encode("123" + i));
            userRepository.save(user);
        }
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String notFoundException(NotFoundException exception) {
//        return "not_found";
//    }
}
