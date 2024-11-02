package com.SuperMarket.QUINTET_BackEnd.Controller;

import com.SuperMarket.QUINTET_BackEnd.Dto.LoginDto;
import com.SuperMarket.QUINTET_BackEnd.Dto.UserDto;
import com.SuperMarket.QUINTET_BackEnd.Entity.Role;
import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import com.SuperMarket.QUINTET_BackEnd.Repository.RolesRepo;
import com.SuperMarket.QUINTET_BackEnd.Repository.UserRepo;
import com.SuperMarket.QUINTET_BackEnd.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.LoginException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/name")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome Authuntication");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginRequest) {
        try {
            System.out.println("Login Processing");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession(true);

            System.out.println("Session ID after authentication: " + session.getId());

            String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .findFirst().orElseThrow(() -> new RuntimeException("No role for this user"));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            User user=userService.findByname(authentication.getName());
            response.put("userId", String.valueOf(user.getId()));
            response.put("role", role);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Login failed. Invalid username or password."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
        try{
            User user=new User();
            user.setUsername(userDto.getName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            Role role=rolesRepo.getReferenceById((long)1);
            user.setRole(role);

            userService.saveUser(user);
            return new ResponseEntity<>("User register succussfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
