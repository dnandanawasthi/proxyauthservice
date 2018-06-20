package com.authservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.config.JwtTokenUtil;
import com.authservice.model.AuthToken;
import com.authservice.model.LoginUser;
import com.authservice.model.User;
import com.authservice.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        System.out.println("AuthResource:AuthResource... login user detail: "+loginUser);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        System.out.println("AuthResource:AuthResource... auth setting up in context: ");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("AuthResource:AuthResource... fetching user from table: ");
        final User user = userService.findOne(loginUser.getUsername());
        System.out.println("AuthResource:AuthResource... making request to generate token: ");
        final String token = jwtTokenUtil.generateToken(user);
        System.out.println("AuthResource:AuthResource... responding with generated token: ");
        return ResponseEntity.ok(new AuthToken(token));
    }

}
