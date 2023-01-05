package com.upt.doccrypt.rest;

import com.upt.doccrypt.dto.UserDto;
import com.upt.doccrypt.model.NotaryCandidate;
import com.upt.doccrypt.model.Status;
import com.upt.doccrypt.model.user.User;
import com.upt.doccrypt.repository.NotaryQueueRepository;
import com.upt.doccrypt.security.jwt.JwtTokenProvider;
import com.upt.doccrypt.service.NotaryQueueService;
import com.upt.doccrypt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/v1/reg/")
public class RegisterRestController{

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    private final NotaryQueueService notaryQueueService;
    private final NotaryQueueRepository notaryQueueRepository;

    @Autowired
    public RegisterRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, NotaryQueueService notaryQueueService,
                                  NotaryQueueRepository notaryQueueRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.notaryQueueService = notaryQueueService;
        this.notaryQueueRepository = notaryQueueRepository;
    }

    @PostMapping("registration")
    public ResponseEntity registration(@RequestBody UserDto requestDto) {
        if(userService.containUserByEmail(requestDto.getEmail())) throw new BadCredentialsException("Email already exist in DB");
        if(userService.findByUsername(requestDto.getUsername()) != null){
            requestDto.setUsername(requestDto.getUsername() + UUID.randomUUID());
        }
        if(requestDto.getPassword().isEmpty()) throw new BadCredentialsException("Password is empty");
        User user = requestDto.toUser();
        user.setCreated(new Date());
        user.setUpdated(new Date());
//        userService.register(user);
        Map<String, String> response = new HashMap<>();
        response.put("Response", "Ok");
        response.put("username", requestDto.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/registration/notary")
    public String requestNotaryRegistration(@RequestParam("file") MultipartFile file,
                                            @RequestParam("username") String username,
                                            @RequestParam("email") String email,
                                            @RequestParam("password") String password
                                            ) throws IOException {

        System.out.println("username: " + username);
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);


        if(!pattern.matcher(email).matches()) throw new BadCredentialsException("Invalid email");
        if(password.isEmpty() || password.length() < 5) throw new BadCredentialsException("Invalid password");

        NotaryCandidate notaryCandidate = new NotaryCandidate();
        notaryCandidate.setEmail(email);
        notaryCandidate.setPassword(password);
        notaryCandidate.setFirstName(username.split(" ")[0]);
        notaryCandidate.setLastName(username.split(" ")[1]);
        notaryCandidate.setCreated(new Date());
        notaryCandidate.setUpdated(new Date());
        notaryCandidate.setStatus(Status.ACTIVE);
        notaryCandidate.setProveDocument(file.getBytes());
        if(notaryQueueRepository.existsByUsername(username))
            notaryCandidate.setUsername(username + UUID.randomUUID());
        else
            notaryCandidate.setUsername(username);

        System.out.println(notaryQueueService.addAsCandidate(notaryCandidate));
        return file.getOriginalFilename() + " " + username;
    }

}
