package dev.jurandibs.microblog.consumers;

import dev.jurandibs.microblog.models.Comment;
import dev.jurandibs.microblog.models.User;
import dev.jurandibs.microblog.repositories.CommentRepository;
import dev.jurandibs.microblog.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConsumer {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;


    @RabbitListener(queues = "${MicroBlog.rabbitmq.queue}")
    public void receivedMessage(Comment comment) {
        User user = userRepository.findById(comment.getUser().getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        comment.setUser(user);
        System.out.println("Received Message From RabbitMQ: " + comment);
        commentRepository.save(comment);
    }
}