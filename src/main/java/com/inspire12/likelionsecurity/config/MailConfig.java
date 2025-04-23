package com.inspire12.likelionsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender(@Value("${spring.mail.host}") String host,
                                     @Value("${spring.mail.port}") int port,
                                     @Value("${spring.mail.username}") String username,
                                     @Value("${spring.mail.password}") String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
//        mailSender.setPassword(encryptor().decrypt(password));
        mailSender.setPassword(password);
        mailSender.setProtocol("smtp");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");
        props.put("mail.debug", "true");
        return mailSender;
    }

//    @Bean
//    public StringEncryptor encryptor() {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("likelion-backend-plus-4th-process-1st-project-secretkey");                     // 자물쇠 비밀번호
//        config.setAlgorithm("PBEWithMD5AndDES");            // 암호화 알고리즘
//        config.setKeyObtentionIterations("1000");           // 반복 횟수
//        config.setPoolSize("1");                            // 풀 사이즈
//        config.setStringOutputType("base64");               // 출력 인코딩
//        encryptor.setConfig(config);
//        return encryptor;
//    }
}
