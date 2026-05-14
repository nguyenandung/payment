package com.engineerpro.rest.example.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.dto.UpdateUserRequest;
import com.engineerpro.rest.example.exception.UserNotFoundException;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import com.engineerpro.rest.example.dto.CreateUserRequest;
import com.engineerpro.rest.example.dto.UserResponse;
import com.engineerpro.rest.example.exception.DuplicatedIdempotentKeyException;
import com.engineerpro.rest.example.exception.DuplicatedNameKeyException;




@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request) {
    final User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
    return GetUserBalanceResponse.builder().balance(user.getBalance()).build();
  }


@Override
@Transactional
public UserResponse createUser(CreateUserRequest request) {
  if (userRepository.existsByName(request.getName())) {
      throw new DuplicatedIdempotentKeyException();
    }
    User user = User.builder().name(request.getName()).balance(request.getInitialBalance())
    .build();
    user = userRepository.save(user);
    return UserResponse.builder().id(user.getId()).name(user.getName()).balance(user.getBalance()).build();
}


@Override
public UserResponse getUserById(Integer id) {
  User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  
  return UserResponse.builder().id(user.getId()).name(user.getName()).balance(user.getBalance()).build();
}


@Override
public Page<UserResponse> getAllUsers(Pageable pageable) {
   return userRepository.findAll(pageable).map(this::convertToResponse);
}


@Override
public UserResponse updateUser(Integer id, UpdateUserRequest request) {
    User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    if(request.getName() != null && !request.getName().equals(user.getName())){
      boolean isDuplicate =userRepository.existsByNameAndIdNot(request.getName(), id);
      if(isDuplicate){
        throw new DuplicatedNameKeyException();
      }

      user.setName(request.getName());
    }
    if(request.getBalance() != null){
      user.setBalance(request.getBalance());
    }
     user = userRepository.save(user);

    return convertToResponse(user);
  
}


@Override
public void deleteUser(Integer id) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
}

private UserResponse convertToResponse(User user) {
  return UserResponse.builder()
          .id(user.getId())
          .name(user.getName())
          .balance(user.getBalance())
          .build();
}

}