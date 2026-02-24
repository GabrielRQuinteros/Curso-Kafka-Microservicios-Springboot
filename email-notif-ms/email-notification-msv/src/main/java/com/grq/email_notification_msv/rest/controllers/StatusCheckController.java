package com.grq.email_notification_msv.rest.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class StatusCheckController {


    @GetMapping("/200")
    public ResponseEntity<String> statusOk() {
        return ResponseEntity.ok().body("200");
    }


    @GetMapping("/500")
    public ResponseEntity<String> statusInternalServerError() {
        return ResponseEntity.internalServerError().body("500");
    }


}
