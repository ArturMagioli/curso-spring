package com.magioli.jobportal.client.controller;

import com.magioli.jobportal.client.service.PostService;
import com.magioli.jobportal.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto post) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.create(post));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> update(@PathVariable Long id, @RequestBody PostDto post) {
        return ResponseEntity.ok(postService.update(id, post));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        postService.delete(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
