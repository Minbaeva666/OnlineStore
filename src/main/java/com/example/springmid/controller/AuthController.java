package com.example.springmid.controller;

import com.example.springmid.dto.request.AuthRequestDTO;
import com.example.springmid.dto.request.AuthResponseDTO;
import com.example.springmid.dto.request.CustomerRequestDTO;
import com.example.springmid.dto.request.RefreshTokenRequestDTO;
import com.example.springmid.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(
        name = "Controller for authentication/registration"
)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(
            summary = "Registration"
    )
    public ResponseEntity<?> signUp(@Valid @RequestBody CustomerRequestDTO requestDTO,
                                    HttpServletRequest servletRequest) throws MessagingException {
        return authService.signUp(requestDTO, servletRequest);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authentication"
    )
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.authenticate(authRequestDTO);
    }

    @Operation(
            summary = "JWT token refreshing"
    )
    @PostMapping("/refreshToken")
    public AuthResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return authService.refreshToken(refreshTokenRequestDTO);
    }

    @GetMapping("/verify")
    @Operation(
            summary = "Email verification"
    )
    public String verifyEmail(@RequestParam("token") String token){
        return authService.verifyEmail(token);
    }
}
