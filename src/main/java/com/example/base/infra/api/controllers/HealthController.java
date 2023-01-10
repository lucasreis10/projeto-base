package com.example.base.infra.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "health")
@Tag(name = "Health")
public class HealthController {

    @GetMapping
    public String statusOk() {
        return "Ok";
    }

}
