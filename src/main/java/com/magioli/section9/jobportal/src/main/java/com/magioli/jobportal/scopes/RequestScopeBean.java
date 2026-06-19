package com.magioli.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter @Setter
public class RequestScopeBean {

    private String username;

    public RequestScopeBean() {
        System.out.println("RequestScopeBean created");
    }

}
