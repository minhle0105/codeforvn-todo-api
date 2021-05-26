package com.codeforvn.service.authentication;

import com.codeforvn.dto.RegisterRequest;
import com.codeforvn.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setUserEmail(registerRequest.getEmail());
        user.setUserPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
    }
}
