# geek-spring-part-one-17

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/49533cd8756c4b8695c40bf535a2fea9)](https://app.codacy.com/gh/Kirikx/geek-spring-part-one-17?utm_source=github.com&utm_medium=referral&utm_content=Kirikx/geek-spring-part-one-17&utm_campaign=Badge_Grade)

# Tasks
App context: host:port/mvc-app/
Lesson 1
1. Попробовать настроить проект с использованием Spring и прописать различные настройки бинов. В качестве практического задания можете сдавать тестовый проект либо вопросы, которые возникли при изучении данной темы.

Lesson 2
1. Разобраться с примером проекта на Spring MVC.
2. Создать класс Товар (Product), с полями id, title, cost. Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары). Репозиторий должен уметь выдавать список всех товаров и товар по id.
3. Сделать форму для добавления товара в репозиторий и логику работы этой формы.
4. Сделать страницу, на которой отображаются все товары из репозитория

Lesson 3
1. В базе данных (MySQL) необходимо реализовать возможность хранить информацию о покупателях (id, имя) и товарах (id, название, стоимость). У каждого покупателя свой набор купленных товаров.
Задача: написать тестовое консольное приложение, которое позволит посмотреть, какие товары покупал клиент, какие клиенты купили определенный товар, и предоставит возможность удалять из базы товары/покупателей.
3. * Добавить детализацию по паре «покупатель — товар»: сколько стоил товар в момент покупки клиентом.
4. *В Spring приложение добавить валидацию данных вводимых пользователем

Lesson 4
1. Создать сущность «товар» (id, название, стоимость) и соответствующую таблицу в БД. Заполнить таблицу тестовыми данными (20 записей).
2. Сделать страницу, в которую будут выведены эти записи.
3. С помощью GET-запроса указывать фильтрацию по:
a. только минимальной,
b. только максимальной,
c. или минимальной и максимальной цене.
4. * Добавить постраничное отображение (по 5 записей на странице).

Lesson 5
1. Добавить пагинацию по страницам в таблице товаров. Обязательно добавить сортировку по Id или имени сущности.
2. Переписать фильтр при помощи Criteria API и Spring Data Specifications
3. Добавить возможность редактировать существующие товары (если еще не сделано)
4. * Реализовать возможность сортировки по разным колонкам таблицы.

Lesson 6
1. Перенести функциональность, реализованную на прошлых занятиях, на платформу Spring Boot.

Lesson 7
1. Добавить к проекту REST API для одной из сущностей.

Lesson 8
1. Добавить роли в схему данных приложения. Между пользователями и ролями должна быть связь многие ко многим. Добавить возможность редактировать список ролей пользователя в форме (если получится)
2. Создать свою собственную форму логина и настроить Spring Security для её использования
3. Создать страницу со списком товаров, на которой можно добавлять позиции и редактировать существующие. На эту страницу должны иметь доступ админы и менеджеры.
4. Создать страницу со списком всех пользователей, к которой имеют доступ только админы.
5. Добавить роль суперадмина и дать ему возможность создавать новых пользователей и указывать роли существующим (использовать дополнения к Thymleaf для Spring Security)*

### Use DB shema
![Alt text](md_content/db.png?raw=true "shema_db")

### Description UI
##### Welcome page (not auth)
![Alt text](md_content/0_welcome.png?raw=true "welcome_root")

##### Login page
![Alt text](md_content/form_login.png?raw=true "login_page")
##### Login page (invalid log/pass message)
![Alt text](md_content/form_login_invalid.png?raw=true "invalid_message")

##### Welcome page (auth user)
![Alt text](md_content/1_welcome_auth.png?raw=true "welcome_after_auth")

##### Logout page (logout message)
![Alt text](md_content/form_login_logout.png?raw=true "logout_message")

#### List user page (ROLE_ADMIN)
In memory user role "ROLE_ADMIN"
login: mem_user
password: password
![Alt text](md_content/user_list.png?raw=true "list_user")
##### Create user
![Alt text](md_content/user_create.png?raw=true "create_user")
##### Edit user
![Alt text](md_content/user_edit.png?raw=true "edit_user")
If you edit user if not exist, to be not fount page
![Alt text](md_content/not_found_form_0.png?raw=true "not_found")

#### List product page (ROLE_ADMIN, ROLE_MANAGER)
Create user role "ROLE_MANAGER" (use in memory user mem_user)
(For example uses user "man")
![Alt text](md_content/product_list.png?raw=true "list_product")
##### Create product
![Alt text](md_content/product_create.png?raw=true "create_product")
If you edit product if not exist, to be view "not fount page"
![Alt text](md_content/not_found_form_1.png?raw=true "not_found")

#### 403 page
If you not credential user, redirect 403 page
![Alt text](md_content/403_form.png?raw=true "403_page")

### Reference
For further reference:

 * [HOME](http://localhost:8080/mvc-app/)
 * [USERS](http://localhost:8080/mvc-app/user)
 * [PRODUCTS](http://localhost:8080/mvc-app/product)



