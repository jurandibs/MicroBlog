package dev.jurandibs.microblog.controllers;

import dev.jurandibs.microblog.models.Tag;
import dev.jurandibs.microblog.services.TagService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping(path = "/save")
    private @ResponseBody Tag save(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @Cacheable
    @GetMapping(path = "/getAll")
    private @ResponseBody List<Tag> getAll() {
        return tagService.getAll();

    }

    @GetMapping(path = "/get")
    private @ResponseBody Tag get(@RequestParam final Long id) {
        return tagService.get(id);
    }

    @PostMapping(path = "/update")
    private @ResponseBody Tag update(@RequestParam final Long id, @RequestBody Tag tag) {
        return tagService.update(id, tag);
    }

    @DeleteMapping(path = "/delete")
    private void delete(@RequestParam final Long id) {
        tagService.delete(id);
    }
}