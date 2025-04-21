package com.inspire12.likelionsecurity.infrastructure.security;

import com.inspire12.likelionsecurity.infrastructure.entity.UserEntity;
import com.inspire12.likelionsecurity.infrastructure.memoryrepository.UserMemoryRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMemoryRepository userRepository;

    public CustomOAuth2UserService(UserMemoryRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");

        // 회원정보 저장/업데이트
        UserEntity user = userRepository.findByUsername(email);
        if (user == null) {
            user = new UserEntity(email, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
            userRepository.save(user);
        }
//        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), provider));

        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.getAttributes(),
            "email"
        );
    }
}
