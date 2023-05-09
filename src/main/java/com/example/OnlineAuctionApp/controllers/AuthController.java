    package com.example.OnlineAuctionApp.controllers;

    import com.example.OnlineAuctionApp.controllers.model.LoginBody;
    import com.example.OnlineAuctionApp.controllers.model.LoginResponse;
    import com.example.OnlineAuctionApp.controllers.model.RegisterBody;
    import com.example.OnlineAuctionApp.exceptions.UserAlreadyExistsException;
    import com.example.OnlineAuctionApp.models.User;
    import com.example.OnlineAuctionApp.service.UserService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequiredArgsConstructor
    public class AuthController {
        private final UserService userService;

        @PostMapping("/register")
        public ResponseEntity register(@Valid @RequestBody RegisterBody registerBody){
            try {
                userService.register(registerBody);
                return ResponseEntity.ok().build();
            } catch (UserAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
            String jwt = userService.loginEmail(loginBody);
            if(jwt == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                LoginResponse response = new LoginResponse();
                response.setJwt(jwt);
                return ResponseEntity.ok(response);
            }
        }

        @GetMapping("/me")
        public User getLoggedUser(@AuthenticationPrincipal User user){
            return user;
        }
    }
