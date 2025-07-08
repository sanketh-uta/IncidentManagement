package com.sanbro.AuthenticationService.service;

import com.sanbro.AuthenticationService.entity.User;
import com.sanbro.AuthenticationService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails = userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not exists"));
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(userDetails.getUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(username,userDetails.getPassword(),roles);
     }
}
