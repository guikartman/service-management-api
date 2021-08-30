package com.servicemanagement.controller;

import com.servicemanagement.domain.User;
import com.servicemanagement.dto.UserDTO;
import com.servicemanagement.dto.UserNewDTO;
import com.servicemanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewUser(@RequestBody @Valid UserNewDTO userNewDTO){
        User user = userService.createNewUser(userNewDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/retrieve-password")
    public ResponseEntity<Void> retrievePassword(@RequestParam(value="email") String email) {
        userService.retrievePassword(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{email}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable(name = "email") String email, @RequestParam(value = "oldPassword") String oldPassword,@RequestParam(value = "newPassword") String newPassword) {
        userService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable(name = "email") String email) {
        User user = userService.findUserByEmail(email);
        UserDTO dto = new UserDTO(user);
        return ResponseEntity.ok(dto);
    }

}
