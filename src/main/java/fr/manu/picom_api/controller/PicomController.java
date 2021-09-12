package fr.manu.picom_api.controller;

import fr.manu.picom_api.models.Role;
import fr.manu.picom_api.models.User;
import fr.manu.picom_api.services.RoleService;
import fr.manu.picom_api.services.UserService;
import fr.manu.picom_api.services.impl.UserDetailsImpl;
import fr.manu.picom_api.services.impl.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PicomController {

    private final RoleService roleService;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;


    public PicomController(RoleService roleService, UserService userService, UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager) {
        this.roleService = roleService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
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

    @PostMapping(value = "/public/api/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(LocalDate.now() + " :: new API_REST POST_public/api/signin");
        System.out.println(LocalDate.now() + " :: DEBUG - email=" + loginRequest.getEmail() + ", password=" + loginRequest.getPassword());

        // Vérifier l'auth, si le couple email + mdp est correct
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        System.out.println(LocalDate.now() + " :: DEBUG - authentication=" + authentication);

        // Set le 'UserDetails' actuel dans SecurityContext en utilisant la méthode setAuthentication(authentication)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Créer un userDetails depuis le getName()
        System.out.println("authentication.getName()=" + authentication.getName());

        UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(authentication.getName());
        System.out.println(LocalDate.now() + " :: DEBUG - userDetails=" + userDetails);

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

}
