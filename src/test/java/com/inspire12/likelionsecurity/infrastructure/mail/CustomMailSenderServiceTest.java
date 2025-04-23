package com.inspire12.likelionsecurity.infrastructure.mail;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(classes = {CustomMailSender.class, MailConfig.class,
//        StringEncryptor.class, PooledPBEStringEncryptor.class, JasyptConfig.class}
//        , properties = {"spring.mail.host=ox4443@example.com", "spring.mail.password=ENC(fmJGw6RK2z+RIlT+Tq/PQm0NhcCYJ2nS)"}
//)
@SpringBootTest
@AutoConfigureMockMvc
class CustomMailSenderServiceTest {

//    @Autowired
//    private CustomMailSender customMailSender;

    @Autowired
    private StringEncryptor encryptor;


//    CustomMailSenderServiceTest() {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("likelion-backend-plus-4th-process-1st-project-secretkey");                     // 자물쇠 비밀번호
//        config.setAlgorithm("PBEWithMD5AndDES");            // 암호화 알고리즘
//        config.setKeyObtentionIterations("1000");           // 반복 횟수
//        config.setPoolSize("1");                            // 풀 사이즈
//        config.setStringOutputType("base64");               // 출력 인코딩
//        encryptor.setConfig(config);
//        this.encryptor = encryptor;
//    }

    @Test
    void sendMail() {

//        customMailSender.sendMail("ox4443@naver.com", "naver.com");
    }

    @Test
    void encodePassword() {
        String raw = "";
        String enc = encryptor.encrypt(raw);
        String dec = encryptor.decrypt(enc);
        System.out.printf("[Jasypt] ENC=%s, DEC=%s%n", enc, dec);
    }
}