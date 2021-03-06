package com.juezlti.repository.auth;

import java.util.Optional;

import com.juezlti.repository.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${auth.register.secret}")
	String registerSecret;

	@Autowired
	private UsersAuthService usersAuthService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {

		authenticate(user.getUserName(), user.getPassword());

		final UserDetails userDetails = usersAuthService
				.loadUserByUsername(user.getUserName());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok().body(new LoginResponse(token));
	}
	
	@Data
	@AllArgsConstructor
	class LoginResponse {
		String accessToken;
	}


	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(
			@RequestBody User user,
			@RequestHeader(value = "auth-secret", required = false) String secret
	) throws Exception {
		if(!registerSecret.equals(secret)){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing \"auth-secret\" header");
		}
		return ResponseEntity.ok(usersAuthService.createUser(user));
	}


	@RequestMapping(value = "/me", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> userInfo(Authentication authentication){
		UserDetails userDetails = Optional.ofNullable(authentication.getPrincipal())
				.filter(UserDetails.class::isInstance)
				.map(UserDetails.class::cast)
				.orElse(null);
		if(userDetails == null){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(userDetails.getUsername());
	}


	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}