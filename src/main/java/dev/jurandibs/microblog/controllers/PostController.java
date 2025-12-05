package dev.jurandibs.microblog.controllers;

import dev.jurandibs.microblog.models.Post;
import dev.jurandibs.microblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping(path = "/save")
    public @ResponseBody Post save(@RequestBody Post post) {
        return postService.save(post);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody List<Post> getAll() {
        return postService.getAll();
    }

    @GetMapping(path = "/get")
    public @ResponseBody Post get(@RequestParam final Long id) {
        return postService.get(id);
    }

    @PostMapping(path = "/update")
    public @ResponseBody Post update(@RequestParam final Long id, @RequestBody Post post) {
        return postService.update(id, post);
    }

    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam final Long id) {
        postService.delete(id);
    }
}