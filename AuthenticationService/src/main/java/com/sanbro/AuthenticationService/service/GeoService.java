package com.sanbro.AuthenticationService.service;

import com.sanbro.AuthenticationService.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Lazy
@Slf4j
public class GeoService {
    @Value("${geo.key}")
    private String geoKey;

    public List<Map<String,Object>> getCoordinates(UserDto userDto){
        log.info("      -------"+geoKey);
        RestTemplate request = new RestTemplate();
        String url = "https://geocode.maps.co/search?street={street}&city={city}&state={state}&" +
                "postalcode={postalCode}&country={country}&api_key={api_key}";
        Map<String, String> params = new HashMap<>();
        params.put("street",userDto.getStreet());
        params.put("city",userDto.getCity());
        params.put("state",userDto.getState());
        params.put("postalCode",userDto.getPostalCode());
        params.put("country",userDto.getCountry());
        params.put("api_key",geoKey);
        List<Map<String,Object>> resp = request.getForObject(url,List.class,params);
        return resp;
    }
}
