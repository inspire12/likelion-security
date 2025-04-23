package com.inspire12.likelionsecurity.infrastructure.adapter;

import com.inspire12.likelionsecurity.application.port.in.PasswordTokenRepository;
import com.inspire12.likelionsecurity.domain.User;
import com.inspire12.likelionsecurity.infrastructure.entity.PasswordResetToken;
import com.inspire12.likelionsecurity.infrastructure.mail.CustomMailSenderService;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.PasswordResetTokenMemoryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class PasswordTokenAdapter implements PasswordTokenRepository {

    private String resetLinkPrefix =  "http://frontend-domain/reset-password?token=";

    private final PasswordResetTokenMemoryRepository tokenRepository;
    private final CustomMailSenderService mailSender;

    public PasswordTokenAdapter(PasswordResetTokenMemoryRepository passwordResetTokenMemoryRepository, CustomMailSenderService mailSender) {
        this.tokenRepository = passwordResetTokenMemoryRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void publishTokenAndSendMail(User user) {
        if (user == null) {
            tokenRepository.findByToken(user.getEmail()).ifPresent(tokenRepository::delete);
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken(
                token,
                user.getUsername(),
                LocalDateTime.now().plusHours(1)
        );
        tokenRepository.save(prt);

        String resetLink = resetLinkPrefix + token;
        mailSender.sendMail(user.getEmail(), resetLink);
    }

    @Override
    public String validateAndGetUsernameByToken(String token) {
        throw new UnsupportedOperationException();
    }
}
