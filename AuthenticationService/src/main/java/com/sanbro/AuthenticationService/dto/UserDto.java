package com.sanbro.AuthenticationService.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
    NotNull - applicable to any Object type - fails for the null
    NotEmpty - applicable to string, collection and arrays - fails for the null,[],{},""
    NotBlank - applicable only to strings - fails for ""," ",null
 */

/* as of now in V1 I am not validating the address but assume user enter the valid address so that the latitude longitudes are fetched
  also I am not using the drop down for the country and state but expect that user enter the valid as below form
 country - code in shortcut (US for united states)
 state - shortcut TX for texas*/

@Getter
@Setter
public class UserDto {

    // not black applicable only to strings - ""," ", null are unacceptable values
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, max=10)
    private String userName;

    @NotBlank(message="Full Name cannot be blank")
    @Size(min = 5, max=25)
    private String fullName;

    @NotBlank(message = "Mobile number cannot be blank and should be of length 10")
    @Size(min = 10,max=10)
    private String mobileNumber;


    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min=8,max=20,message = "password must be atleast 8 characters and not more than 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters and include uppercase, lowercase, digit, and special character")
    private String password;

    @NotBlank(message = "street address is mandatory")
    @Size(max=20)
    private String street;

    @NotBlank(message="city is the mandatory field")
    private String city;

    @NotBlank(message="state is the mandatory field")
    @Size(min=2, max = 2, message = "Please enter the valid 2 character state code")
    private String state;

    @NotBlank(message="country is the mandatory field")
    @Size(min=2, max = 2, message = "Please enter the valid 2 character country code")
    private String country;

    @NotBlank(message="Postal code cannot be blank")
    @Size(min=4, max=4, message="Postal code should be of length exactly 5")
    private String postalCode;

    private boolean isNotificationsEnabled = false;
}
