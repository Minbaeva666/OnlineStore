package com.example.springmid.controller;

import com.example.springmid.dto.response.CustomerResponseDTO;
import com.example.springmid.dto.request.CustomerRequestDTO;
import com.example.springmid.entity.Customer;
import com.example.springmid.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "Controller for getting, updating users"
)
public class CustomerController {
    private final CustomerService userService;

    public CustomerController(CustomerService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Creating user"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> createUser(@RequestBody CustomerRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.create(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updating user"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<CustomerResponseDTO> updateUser(@RequestParam String username,
                                                          @RequestParam String email,
                                                          @AuthenticationPrincipal Customer customer) {
        return new ResponseEntity<>(userService.update(customer, username, email), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Getting user by id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<CustomerResponseDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Getting all users"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<CustomerResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

}
