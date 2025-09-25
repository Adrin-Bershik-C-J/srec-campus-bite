package com.srec.fc.dto;

import com.srec.fc.entity.User;
import com.srec.fc.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String rollNumber;
    private Role role;

    // convenience constructor
    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getName(); // or user.getUsername() if field exists
        this.rollNumber = user.getRollNo();
        this.role = user.getRole();
    }
}
