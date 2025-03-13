package com.example.demo.User;

import com.example.demo.User.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return this.userRepository.findById(id);
    }

    public User createUser(CreateUserDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getPhoneNumber()
        );
        return this.userRepository.save(user);
    }

    public User updateUser(Integer id, CreateUserDto updatedUser) {
        Optional<User> existingUser = this.userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            return this.userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public String deleteUser(Integer id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return "User deleted successfully.";
        } else {
            return "User not found.";
        }
    }
}
