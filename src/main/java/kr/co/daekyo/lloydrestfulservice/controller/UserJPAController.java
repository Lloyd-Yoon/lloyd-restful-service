package kr.co.daekyo.lloydrestfulservice.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.daekyo.lloydrestfulservice.bean.Post;
import kr.co.daekyo.lloydrestfulservice.bean.User;
import kr.co.daekyo.lloydrestfulservice.exception.PostNotFoundException;
import kr.co.daekyo.lloydrestfulservice.exception.UserNotFoundException;
import kr.co.daekyo.lloydrestfulservice.repository.PostRepository;
import kr.co.daekyo.lloydrestfulservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러입니다.")
public class UserJPAController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJPAController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")  // /jpa/users
    //Spring MVC에서 HTTP 응답 본문에 반환 값을 포함시키는데 사용됩니다.
    // 그리고 @RequestMapping 어노테이션이 붙은 메소드에서 사용되면,
    // 해당 메소드의 반환 값이 Response Body로 사용됩니다.
    @ResponseBody
    public Map<String, Object> retrieveAllUsers() {
        List<User> users = userRepository.findAll();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("count", users.size());

        return response;
    }

    @GetMapping("/users/{id}")  // /jpa/user/{id}
    public ResponseEntity retrieveUsersById (@Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%d] not found", id));
        }

        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // all-users -> http://localhost:8088/jpa/users

        return ResponseEntity.ok(entityModel);

    }
    @DeleteMapping ("/users/{id}")  // /jpa/users
    public  void deleteUsersById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users") // /jpa/users
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts") // /jpa/users/{id}/posts
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID[%d] not found", id));
        }
        User user = userOptional.get();
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts") // /jpa/users/{id}/posts
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID[%d] not found", id));
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    // post 삭제
    @DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int userId, @PathVariable int postId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID[%d] not found", userId));
        }

        User user = userOptional.get();

        Post matchedPost = null;
        for(Post post : user.getPosts()){
            if(post.getId() == postId){
                matchedPost = post;
                break;
            }
        }

        if(matchedPost == null){
            throw new PostNotFoundException(String.format("Post with ID[%d] not found", postId));
        }

        postRepository.delete(matchedPost);

        return ResponseEntity.noContent().build();
    }

    // post 상세조회
    @GetMapping("/users/{userId}/posts/{postId}") // /jpa/users/{userId}/posts/{postId}
    public Post retrievePostById(@PathVariable int userId, @PathVariable int postId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID[%d] not found", userId));
        }

        User user = userOptional.get();

        Post matchedPost = null;
        for(Post post : user.getPosts()){
            if(post.getId() == postId){
                matchedPost = post;
                break;
            }
        }

        if(matchedPost == null){
            throw new PostNotFoundException(String.format("Post with ID[%d] not found", postId));
        }

        return matchedPost;
    }
}
