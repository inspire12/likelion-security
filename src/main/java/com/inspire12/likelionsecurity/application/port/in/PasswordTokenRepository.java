package com.inspire12.likelionsecurity.application.port.in;

import com.inspire12.likelionsecurity.domain.User;

public interface PasswordTokenRepository {
    void publishTokenAndSendMail(User user);


    String validateAndGetUsernameByToken(String token);
}
