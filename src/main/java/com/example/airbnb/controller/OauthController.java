package com.example.airbnb.controller;

import com.example.airbnb.model.Role;
import com.example.airbnb.model.User;
import com.example.airbnb.dto.TokenDto;
import com.example.airbnb.service.RoleService;
import com.example.airbnb.service.UserService;
import com.example.airbnb.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/oauth")
@CrossOrigin
public class OauthController {

    @Value("${secretPsw}")
    String secretPsw;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private String email;

    @PostMapping("/facebook")
    public ResponseEntity<TokenDto> facebook(@RequestBody TokenDto tokenDto) throws IOException {
        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
        final String [] fields = {"email"};
        org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        email = user.getEmail();
        User userFace = new User();
        if(userService.existsByUsername(email)){
            userFace = userService.findByUsername(email);
        } else {
            userFace = createUser(email);
        }
        TokenDto tokenRes = login(userFace);
        return new ResponseEntity(tokenRes, HttpStatus.OK);
    }

    public TokenDto login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), secretPsw));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setValue(jwt);
        return tokenDto;
    }

    private User createUser(String email) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(secretPsw));
        if (user.getRoles() == null) {
            Role role1 = roleService.findByName("ROLE_USER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        user.setAvatar("https://hocban.vn/wp-content/uploads/2018/05/avatar-dep-nhat-33_112147.jpg");
        user.setConfirmPassword(passwordEncoder.encode(secretPsw));
        return userService.save(user);
    }
}
