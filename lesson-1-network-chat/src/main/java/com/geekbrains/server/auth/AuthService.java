package com.geekbrains.server.auth;

import com.geekbrains.server.User;

public interface AuthService {

    boolean authUser(User user);
}
