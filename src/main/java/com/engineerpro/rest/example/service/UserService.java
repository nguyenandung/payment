package com.engineerpro.rest.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.dto.UpdateUserRequest;
import com.engineerpro.rest.example.dto.CreateUserRequest;
import com.engineerpro.rest.example.dto.UserResponse;

@Service
public interface UserService {
  GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request);

  UserResponse createUser(CreateUserRequest request);
  
  UserResponse getUserById(Integer id);

  Page<UserResponse> getAllUsers(Pageable pageable);

  UserResponse updateUser(Integer id , UpdateUserRequest request);

  void deleteUser(Integer id);


}
