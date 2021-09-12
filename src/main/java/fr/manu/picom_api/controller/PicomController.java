package fr.manu.picom_api.controller;

import fr.manu.picom_api.models.Role;
import fr.manu.picom_api.models.User;
import fr.manu.picom_api.services.RoleService;
import fr.manu.picom_api.services.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PicomController {

    private final RoleService roleService;
    private final UserService userService;


    public PicomController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        if (roleService.getRoles().isEmpty()) {
            roleService.addRole("ADMIN");
            roleService.addRole("USER");
        }

        if (userService.getUsers().isEmpty()) {
            String password = "administrateur";
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            Set<Role> roles = new HashSet<>();
            Role role = roleService.getRoleByRole("ADMIN");
            Role role1 = roleService.getRoleByRole("USER");
            roles.add(role);
            roles.add(role1);
            User user = new User("admin", "admin@gmail.com", hashed);
            user.setRoles(roles);
            userService.addUser(user);
        }
    }

    /**
     * Méthode A : Récupérer tous les users
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * Méthode B : Récupérer tous les roles
     */
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    /**
     * Méthode C : Ajouter client
     */
    @PostMapping("/users/{name}/{mail}/{password}/{phone}")
    public User addUser(@PathVariable String name, @PathVariable String mail,
                             @PathVariable String password, @PathVariable String phone) {
        return userService.addUser(new User(name, mail, password, phone));
    }

    /**
     * Méthode D : Récupérer client par email
     */
    @GetMapping("/users/{email}")
    public User findByUserEmail(@PathVariable String email) {
        return userService.findByUserEmail(email);
    }

}
