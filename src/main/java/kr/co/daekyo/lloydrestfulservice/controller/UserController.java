package kr.co.daekyo.lloydrestfulservice.controller;

import jakarta.validation.Valid;
import kr.co.daekyo.lloydrestfulservice.bean.User;
import kr.co.daekyo.lloydrestfulservice.dao.UserDaoService;
import kr.co.daekyo.lloydrestfulservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retriveAllUsers () {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retriveUsers (@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }
        return user;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
   }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUsers (@PathVariable int id) {
        User user = service.deleteByID(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }
        return ResponseEntity.noContent().build();

    }
}
