package com.inspire12.likelionsecurity.infrastructure.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CustomMailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    public CustomMailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String email, String resetLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();         // MIME 메시지 객체임
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(username); // 보내는 사람 주소임, smtp 설정과 맞아야함
            helper.setTo(email); // 받는 사람 주소임
            helper.setSubject("멋쟁이 사자처럼 비밀번호 초기화 링크입니다");

            String htmlContent = """
                      <h1 style="color:orange;">안녕하세요 멋쟁이 사자처럼 비밀번호 초기화 링크입니다</h1>
                      <p><a href="%s">패스워드초기화</a></p>
                    """.formatted(resetLink);                                                              // HTML 본문임
            helper.setText(htmlContent, true);                                // HTML 모드 설정

            mailSender.send(mimeMessage);                                     // HTML 메일 전송 호출

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
