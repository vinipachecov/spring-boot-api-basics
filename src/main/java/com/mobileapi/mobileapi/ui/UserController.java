package com.mobileapi.mobileapi.ui;

import com.mobileapi.mobileapi.service.UserService;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.model.request.UserDetailsRequestModel;
import com.mobileapi.mobileapi.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("users")
public class UserController {    
    @Autowired
    UserService userService;

        @GetMapping
        public String getUser() {            
            return "hello world";
        }

        @PostMapping
        public UserRest createUser(@RequestBody  UserDetailsRequestModel userDetails) {             
            UserRest returnValue  = new UserRest();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userDetails, userDto);                        

            UserDto createdUser = userService.createUser(userDto);
            BeanUtils.copyProperties(createdUser, returnValue);                        
            return returnValue;
        }

        
        @PutMapping
        public String putMethodName() {                                    
            return "updating";
        }
        
        @DeleteMapping
        public String deleteUser() {
            return "deleteing";
        }
                        
}