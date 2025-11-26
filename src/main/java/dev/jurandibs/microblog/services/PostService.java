package dev.jurandibs.microblog.services;

import dev.jurandibs.microblog.models.Post;

import java.util.List;

public interface PostService {

    Post save(final Post post);
    List<Post> getAll();
    Post get(final Long id);
    Post update(final Long id, final Post post);
    void delete(final Long id);
}
