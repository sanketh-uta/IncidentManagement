package com.sanbro.AuthenticationService.repository;

import com.sanbro.AuthenticationService.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    Optional<Address> findByLatitudeAndLongitude(String latitude, String longitude);
}
