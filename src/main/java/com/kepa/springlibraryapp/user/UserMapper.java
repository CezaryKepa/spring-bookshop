package com.kepa.springlibraryapp.user;

class UserMapper {

    static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }

    static User toEntity(UserDto user) {
        User entity = new User();
        entity.setId(user.getId());
        entity.setFirstname(user.getFirstname());
        entity.setLastname(user.getLastname());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }
}