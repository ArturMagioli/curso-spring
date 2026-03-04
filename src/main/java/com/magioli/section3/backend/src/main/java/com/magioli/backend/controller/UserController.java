package com.magioli.backend.controller;


import com.magioli.backend.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String searchUserWithQueryParams(@RequestParam(required = false, defaultValue = "Guest") String name, @RequestParam(name = "gender") String sex) {
        return "Fetched user with query params: " + name + " and sex: " + sex;
    }

    @GetMapping("/search/map")
    public String searchUserWithMapQueryParams(@RequestParam Map<String, String> requestParams) {
        return "Fetched user with query params: " + requestParams.get("name") + " and sex: " + requestParams.get("gender");
    }

    @GetMapping("/headers")
    public String readRequestHeaders(@RequestHeader("User-Agent") String userAgent,
                        @RequestHeader(value = "User-Location", required = false, defaultValue = "Campo Grande") String userLocation) {
        return "Received: " + userAgent + " " + userLocation;
    }

    @GetMapping("/headers/map")
    public String readRequestHeadersWithMap(@RequestHeader Map<String, String> requestHeaders) {
        return "Received: " + requestHeaders.get("User-Agent") + " " + requestHeaders.get("User-Location");
    }

    @GetMapping("/headers/http-headers")
    public String readRequestHeadersWithHttpHeaders(@RequestHeader HttpHeaders requestHeaders) {
        return "Received: " + requestHeaders.get("User-Agent") + " " + requestHeaders.get("User-Location");
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        return "User created with data: " + userDto;
    }
}
