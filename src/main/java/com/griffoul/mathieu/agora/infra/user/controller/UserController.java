package com.griffoul.mathieu.agora.infra.user.controller;

import com.griffoul.mathieu.agora.infra.user.model.UserVue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("/user")
public class UserController {

    @GetMapping("/test")
    @ApiOperation(value = "Sign in a user", response = UserVue.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return jwtToken if credentials matching", response = String.class)
    })
    public ResponseEntity<String> signIn(@RequestHeader("Authorization") String token) {
        System.out.printf(token);
        return ResponseEntity.ok(token);
    }
}
