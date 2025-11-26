package dev.jurandibs.microblog.services.v2;

import dev.jurandibs.microblog.models.User;
import dev.jurandibs.microblog.models.v2.UserV2;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserServiceV2 {
    UserV2 save (final UserV2 user);
    List<UserV2> getAll();

    UserV2 get(Long id);

    UserV2 update(Long id, UserV2 user);

    void delete(Long id);
}
