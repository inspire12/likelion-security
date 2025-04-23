package com.inspire12.likelionsecurity;

import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LikelionSecurityApplication {
    private final Logger log = LoggerFactory.getLogger(LikelionSecurityApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LikelionSecurityApplication.class, args);
    }

//    @Component
//    public class EncryptorVerifier implements CommandLineRunner {
//        private final StringEncryptor encryptor;
//        public EncryptorVerifier(StringEncryptor encryptor) { this.encryptor = encryptor; }
//
//        @Override
//        public void run(String... args) {
//            // 암호문을 본인이 암호화한 값으로 교체
//            log.info("encode = " + encryptor.encrypt(pw));
//            log.info("decrypt = " + encryptor.decrypt(enc));  // 평문이 출력돼야 정상
//        }
//    }
}
