package com.sanbro.AuthenticationService.service;

import com.sanbro.AuthenticationService.dto.LoginDto;
import com.sanbro.AuthenticationService.dto.UserDto;
import com.sanbro.AuthenticationService.entity.Address;
import com.sanbro.AuthenticationService.entity.Roles;
import com.sanbro.AuthenticationService.entity.User;
import com.sanbro.AuthenticationService.exception.UserAlreadyPresentException;
import com.sanbro.AuthenticationService.repository.AddressRepository;
import com.sanbro.AuthenticationService.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.log.SubSystemLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// check for the uniqueness of the values username, email and mobileNumber.Lets assume here we are not duplicating
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private GeoService geoService;

    public UserService(UserRepository userRepository,AddressRepository addressRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(GeoService geoService){
        this.geoService = geoService;
    }
    @Transactional
    public ResponseEntity<?> registerUser(UserDto userDto){
        Optional<User> userDetails = userRepository.findByEmail(userDto.getEmail());
        if(userDetails.isEmpty()){
            List<Map<String,Object>> resp = geoService.getCoordinates(userDto);
            Address address = new Address();
            User user = new User();
            String lat = resp.get(0).get("lat").toString();
            String lon = resp.get(0).get("lon").toString();
            if(addressRepository.findByLatitudeAndLongitude(lat,lon).isPresent()){
                user.setAddress(addressRepository.findByLatitudeAndLongitude(lat,lon).get());
            }
            else{
                address.setLongitude(lon);
                address.setLatitude(lat);
                addressRepository.save(address);
                user.setAddress(addressRepository.findByLatitudeAndLongitude(lat,lon).get());
            }
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setFullName(userDto.getFullName());
            user.setUserRole(Roles.USER);
            user.setMobileNumber(userDto.getMobileNumber());
            userRepository.save(user);
            return new ResponseEntity<>("User registration successful",HttpStatus.CREATED);
        }
        throw new UserAlreadyPresentException("User with given email already exists"+userDto.getEmail());
    }

}
