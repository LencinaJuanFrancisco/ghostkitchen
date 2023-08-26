package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.services.GhostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class GhostsController {

    private final GhostsService userService;

    @Autowired
    public GhostsController(GhostsService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Ghosts> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Ghosts getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Ghosts createUser(@RequestBody Ghosts user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public Ghosts updateUser(@PathVariable Long id, @RequestBody Ghosts user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

