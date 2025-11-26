package dev.jurandibs.microblog.services.impl;

import dev.jurandibs.microblog.models.Comment;
import dev.jurandibs.microblog.services.CommentService;
import dev.jurandibs.microblog.services.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserService userService;

    @Value("${MicroBlog.rabbitmq.exchange}")
    private String exchange;

    @Value("${MicroBlog.rabbitmq.routingkey}")
    private String routingkey;

   @Override
   public Comment send(Comment comment) {
    comment.setUser(userService.get(comment.getUser().getUserId()));

    amqpTemplate.convertAndSend(exchange, routingkey, comment);
    System.out.println("Send msg = " + comment);
    return comment;
}
}