package com.engineerpro.rest.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.dto.UpdateUserRequest;
import com.engineerpro.rest.example.service.UserService;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import com.engineerpro.rest.example.dto.CreateUserRequest;
import com.engineerpro.rest.example.dto.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping("{id}/balance")
	public ResponseEntity<GetUserBalanceResponse> getUserBalance(@PathVariable Integer id) {

		final GetUserBalanceResponse registrationResponse = userService
				.getUserBalance(GetUserBalanceRequest.builder().userId(id).build());

		return ResponseEntity.status(HttpStatus.OK).body(registrationResponse);
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
		final UserResponse response = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(userService.getAllUsers(pageable));
	}

	@PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUserRequest request) {
        
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

	
}
