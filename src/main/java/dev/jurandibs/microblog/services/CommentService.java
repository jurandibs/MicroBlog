package dev.jurandibs.microblog.services;

import dev.jurandibs.microblog.models.Comment;

public interface CommentService {
    Comment send(Comment comment);
}

