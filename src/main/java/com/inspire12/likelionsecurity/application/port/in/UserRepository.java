package com.inspire12.likelionsecurity.application.port.in;

import com.inspire12.likelionsecurity.domain.User;

public interface UserRepository {
    User findByUsername(String email);

    void changePassword(String usernameByToken, String newPassword);
}
