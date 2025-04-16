package com.inspire12.likelionsecurity.service;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionCheckService {
    private final SessionRegistry sessionRegistry;


    public SessionCheckService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/active-sessions")
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
