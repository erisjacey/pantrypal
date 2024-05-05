package com.pantrypal.grocerytracker.mapper;

import com.pantrypal.grocerytracker.dto.auth.AuthRegisterRequest;
import com.pantrypal.grocerytracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToEntity(AuthRegisterRequest request, String encodedPassword) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        return user;
    }
}
