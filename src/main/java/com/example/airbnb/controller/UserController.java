package com.example.airbnb.controller;

import com.example.airbnb.model.JwtResponse;
import com.example.airbnb.model.OldPassword;
import com.example.airbnb.model.Role;
import com.example.airbnb.model.User;
import com.example.airbnb.service.OldPasswordService;
import com.example.airbnb.service.RoleService;
import com.example.airbnb.service.UserService;
import com.example.airbnb.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private Environment env;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OldPasswordService oldPasswordService;

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult bindingResult) {
        OldPassword oldPass = new OldPassword();
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles() != null) {
            Role role = roleService.findByName("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role role1 = roleService.findByName("ROLE_USER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        oldPass.setPass(user.getPassword());
        oldPass.setDateTime(LocalDateTime.now());
        oldPass.setUser(user);
        user.setAvatar("https://hocban.vn/wp-content/uploads/2018/05/avatar-dep-nhat-33_112147.jpg");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        oldPasswordService.save(oldPass);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        OldPassword oldPass = new OldPassword();
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<OldPassword> oldPasswords = oldPasswordService.findAllByUserIdTop3OldPassword(userOptional.get().getId());
        for (OldPassword oldPassword: oldPasswords) {
            if (user.getPassword().equals(oldPassword.getPass())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        oldPass.setPass(user.getPassword());
        oldPass.setDateTime(LocalDateTime.now());
        oldPass.setUser(user);
        user.setId(userOptional.get().getId());
        user.setUsername(userOptional.get().getUsername());
        user.setEnabled(userOptional.get().isEnabled());
        user.setRoles(userOptional.get().getRoles());
        user.setAvatar(userOptional.get().getAvatar());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getConfirmPassword()));
        user.setEmail(userOptional.get().getEmail());
        user.setAddress(userOptional.get().getAddress());
        user.setSex(userOptional.get().getSex());
        user.setAge(userOptional.get().getAge());
        user.setRoles(userOptional.get().getRoles());
        userService.save(user);
        oldPasswordService.save(oldPass);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping("/users/update-profile/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        user.setPassword(userOptional.get().getPassword());
        user.setConfirmPassword(userOptional.get().getConfirmPassword());
        user.setRoles(userOptional.get().getRoles());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/find-by-username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
