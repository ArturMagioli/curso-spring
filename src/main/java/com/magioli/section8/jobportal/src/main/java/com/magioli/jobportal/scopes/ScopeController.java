package com.magioli.jobportal.scopes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scope")
@RequiredArgsConstructor
public class ScopeController {

    private final RequestScopeBean requestScopeBean;
    private final SessionScopeBean sessionScopeBean;
    private final ApplicationScopeBean applicationScopeBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope() {
        requestScopeBean.setUsername("John Doe");
        return ResponseEntity.ok(requestScopeBean.getUsername());
    }

    @GetMapping("/session")
    public ResponseEntity<String> testSessionScope() {
        sessionScopeBean.setUsername("John Doe");
        return ResponseEntity.ok(sessionScopeBean.getUsername());
    }

    @GetMapping("/application")
    public ResponseEntity<Integer> testApplicationScope() {
        applicationScopeBean.incrementVisitorsCount();
        return ResponseEntity.ok(applicationScopeBean.getVisitorsCount());
    }

    @GetMapping("/test")
    public ResponseEntity<Integer> testScope() {
//        return ResponseEntity.ok(requestScopeBean.getUsername());
//        return ResponseEntity.ok(sessionScopeBean.getUsername());
        return ResponseEntity.ok(applicationScopeBean.getVisitorsCount());
    }
}
