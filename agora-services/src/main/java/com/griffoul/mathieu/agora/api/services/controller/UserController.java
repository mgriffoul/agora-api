package com.griffoul.mathieu.agora.api.services.controller;


import com.griffoul.mathieu.agora.api.domain.model.User;
import com.griffoul.mathieu.agora.api.domain.port.application.UserAdapter;
import com.griffoul.mathieu.agora.api.services.model.UserVue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("/user")
public class UserController {

    private final UserAdapter userService;

    @Autowired
    public UserController(UserAdapter userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Sign in a user", response = UserVue.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return jwtToken if credentials matching", response = String.class)
    })
    public ResponseEntity<UserVue> getUserByToken(final Principal principal) {
        User user = userService.getUserByMail(principal.getName());
        UserVue userVue = new UserVue();
        userVue.setMail(user.getMail());
        userVue.setUsername(user.getUsername());
        return ResponseEntity.ok(userVue);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update user", response = UserVue.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update a user and return it after update")
    })
    public ResponseEntity<UserVue> update(@RequestBody final UserVue userVue) {
        User updatedUser = userService.updateUser(mapToUser(userVue));
        return ResponseEntity.ok(mapToUserVue(updatedUser));
    }

    private User mapToUser (final UserVue userVue) {
        User user = new User();
        user.setUsername(userVue.getUsername());
        user.setMail(userVue.getMail());
        return user;
    }

    private UserVue mapToUserVue(final User user) {
        UserVue updatedUserVue = new UserVue();
        updatedUserVue.setMail(user.getMail());
        updatedUserVue.setUsername(user.getUsername());
        return updatedUserVue;
    }

}
