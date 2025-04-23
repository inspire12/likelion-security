package com.inspire12.likelionsecurity.application.service;

import com.inspire12.likelionsecurity.application.port.in.PasswordTokenRepository;
import com.inspire12.likelionsecurity.application.port.in.UserRepository;
import com.inspire12.likelionsecurity.domain.User;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
    private final UserRepository userRepo;
    private final PasswordTokenRepository tokenRepo;

    public PasswordResetService(UserRepository userRepo,
                                PasswordTokenRepository tokenRepo
                                ) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }


    public void createPasswordResetToken(String email) {
        User user = userRepo.findByUsername(email);
        tokenRepo.publishTokenAndSendMail(user);

    }

    public void resetPassword(String token, String newPassword) {
        String usernameByToken = tokenRepo.validateAndGetUsernameByToken(token);
        userRepo.changePassword(usernameByToken, newPassword);
    }
} 