package com.magioli.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dummy/users")
public class UserController {

    @GetMapping({"/{userId}/posts/{postId}", "/{userId}/posts"})
    public String searchUserPostWithMultiPathVariables(@PathVariable Long userId, @PathVariable(required = false) Long postId) {
        String response;

        if(postId == null) {
            response = "Fetched user with id: " + userId;
        } else {
            response = "Fetched user with id: " + userId + " and post id: " + postId;
        }

        return response;
    }

    @GetMapping({"/{userId}/orders/{orderId}"})
    public String searchUserOrderWithMultiPathVariables(@PathVariable(name = "userId") Long customerId, @PathVariable Long orderId) {

        return "Fetched user with id: " + customerId + " and order id: " + orderId;
    }

    @GetMapping({"/{userId}/address/{addressId}"})
    public String searchUserAddressWithMultiPathVariables(@PathVariable Map<String, String> pathVariablesMap) {

        return "Fetched user with id: " + pathVariablesMap.get("userId") + " and address id: " + pathVariablesMap.get("addressId");
    }
}
