package com.inspire12.likelionsecurity.service;


import com.inspire12.likelionsecurity.dto.CustomUserDetails;
import com.inspire12.likelionsecurity.repository.UserMemoryRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMemoryRepository userMemoryRepository;

    public CustomUserDetailsService(UserMemoryRepository userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userMemoryRepository.get(username));
    }
}
