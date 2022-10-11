package com.hoaxify.ws.auth;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;

@RestController
public class AuthController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
	ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization") String authorization) {
		String base64encoded = authorization.split("Basic")[1];
		String decoded = new String(Base64.getDecoder().decode(base64encoded));
		String[] parts = decoded.split(":");
		String username = parts[0];
		User inDB = userRepository.findByUsername(username);
		return ResponseEntity.ok(inDB);
	}
	
	
}
