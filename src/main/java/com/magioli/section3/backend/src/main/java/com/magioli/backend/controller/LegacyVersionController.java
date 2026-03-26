package com.magioli.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/legacy/versions")
public class LegacyVersionController {

    @GetMapping({"", "/", "/v1"})
    public ResponseEntity<String> defaultPathVersion() {
        return ResponseEntity.ok("Response from defaultPathVersion API 1.0.0");
    }

    @GetMapping("/v2")
    public ResponseEntity<String> pathV2Versions() {
        return ResponseEntity.ok("Response from pathV2Version API 2.0.0");
    }

    @GetMapping(params = "version=1")
    public ResponseEntity<String> defaultReqParamVersion() {
        return ResponseEntity.ok("Response from defaultReqParamVersion API 1.0.0");
    }

    @GetMapping(params = "version=2")
    public ResponseEntity<String> v2ReqParamVersion() {
        return ResponseEntity.ok("Response from V2ReqParamVersion API 2.0.0");
    }

    @GetMapping(headers = "X-API-VERSION=1")
    public ResponseEntity<String> defaultReqHeaderVersion() {
        return ResponseEntity.ok("Response from defaultReqHeaderVersion API 1.0.0");
    }

    @GetMapping(headers = "X-API-VERSION=2")
    public ResponseEntity<String> v2ReqHeaderVersion() {
        return ResponseEntity.ok("Response from V2ReqHeaderVersion API 2.0.0");
    }

    @GetMapping(produces = "application/vnd.magioliapp.v1+json")
    public ResponseEntity<String> defaultMediaTypeVersion() {
        return ResponseEntity.ok("Response from defaultMediaTypeVersion API 1.0.0");
    }

    @GetMapping(produces = "application/vnd.magioliapp.v2+json")
    public ResponseEntity<String> v2MediaTypeVersion() {
        return ResponseEntity.ok("Response from V2MediaTypeVersion API 2.0.0");
    }
}
