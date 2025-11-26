package dev.jurandibs.microblog.controllers;

import dev.jurandibs.microblog.models.User;
import dev.jurandibs.microblog.services.UserService;
import dev.jurandibs.microblog.services.v2.UserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceV2 userServiceV2;

    @PostMapping("/save")
    private @ResponseBody User save(@RequestBody User user){
        return userService.save(user);
    }

    @Cacheable(value = "users", key = "'allUsers'")
    @GetMapping("/getAll")
    public List<User> getAll() {
        System.out.println("=== Buscando usuários no banco (não está no cache) ===");
        return userService.getAll();
    }

    // Versionamento por parâmetro de URI
    // e via parâmetro no cabeçalho
    @GetMapping(path = "/get")
    private @ResponseBody ResponseEntity<Object> get(@RequestParam final Long id, @RequestParam final String uriVersion,
                                                     @RequestHeader(name = "Accept-Version") final String acceptVersion) {

        if (uriVersion.equals("v2") || acceptVersion.equals("v2")){
            return ResponseEntity.ok(userServiceV2.get(id));
        }
        return ResponseEntity.ok(userService.get(id));
    }


    @PostMapping(path = "/update")
    private @ResponseBody User update(@RequestParam final Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping(path = "/delete")
    private void delete(@RequestParam final Long id) {
        userService.delete(id);
    }
}



