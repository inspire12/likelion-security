package com.inspire12.likelionsecurity.service;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionCheckService {

    private Logger log = LoggerFactory.getLogger(SessionCheckService.class);
    private final SessionRegistry sessionRegistry;

    public SessionCheckService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public List<Object> getAllPrincipals() {
        return sessionRegistry.getAllPrincipals();
    }

    public String getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean hasRoleAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("USER"));
        log.info("get auth {} {}", username, hasRoleAdmin);
        return username;
    }

    public String setSessionInfo(HttpSession session) {
        // 세션에서 특정 속성 가져오기
        String username = (String) session.getAttribute("username");
        // 세션에 데이터 저장하기
        session.setAttribute("customData", "Hello, Session!");
        return username;
    }


    public List<String> getAllSessions() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<String> sessionInfos = new ArrayList<>();

        for (Object principal : principals) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation session : sessions) {
                sessionInfos.add("Principal: " + principal + ", Session ID: " + session.getSessionId());
            }
        }
        return sessionInfos;
    }
}
