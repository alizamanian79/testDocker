package com.app.sodor24_server.service;

import com.app.sodor24_server.dto.request.RegisterDto;
import com.app.sodor24_server.exception.AppExistException;
import com.app.sodor24_server.exception.AppNotFoundException;
import com.app.sodor24_server.model.Role;
import com.app.sodor24_server.repository.UserRepository;
import com.app.sodor24_server.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Register user
    public User registerUser(RegisterDto req) {

       Optional<User> exist = userRepository.findUserByUsername(req.getUsername());
       if (exist.isPresent()) {
           throw new AppExistException("کاربر با این نام کاربری وجود دارد");
       }

        User registerUser = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Set.of(Role.USER))
                .build();
    return userRepository.save(registerUser);
    }

    // Users list
    public List<User> users() {
      List<User> list = userRepository.findAll();
      return list.reversed();
    }

    // Find user by id
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new AppNotFoundException("کاربر با ایدی" + " " + id+" پیدا نشد ."));
    }

    // Find user by username
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->new AppNotFoundException("کاربر با نام کاربری" + " " + username+" پیدا نشد ."));
    }

    // Update user
    @Transactional
    public User updateUser(User user) {
        User existingUser = findUserByUsername(user.getUsername());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    // Delete user by id
    public String deleteUser(String username) {
        User existingUser = findUserByUsername(username);
        userRepository.delete(existingUser);
        return "کاربر با موفقیت حذف شد";
    }


}
