package com.ooad.careercompass.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

class UserDtoTest {

    @Test
    void testUserDto() {
        Long id = 1L;
        String username = "fireflies186@gmail.com";
        String name = "Pavan Sai";
        String email = "fireflies186@gmail.com";
        String role = "USER";
        List<UserDto.OrderDto> orders = new ArrayList<>();
        orders.add(new UserDto.OrderDto("order1", "Order 1", ZonedDateTime.now()));
        orders.add(new UserDto.OrderDto("order2", "Order 2", ZonedDateTime.now()));

        UserDto userDto = new UserDto(id, username, name, email, role, orders);
        Assertions.assertEquals(id, userDto.id());
        Assertions.assertEquals(username, userDto.username());
        Assertions.assertEquals(name, userDto.name());
        Assertions.assertEquals(email, userDto.email());
        Assertions.assertEquals(role, userDto.role());
        Assertions.assertEquals(orders, userDto.orders());
    }

    @Test
    void testOrderDto() {
        String id = "order1";
        String description = "Order 1";
        ZonedDateTime createdAt = ZonedDateTime.now();

        UserDto.OrderDto orderDto = new UserDto.OrderDto(id, description, createdAt);
        Assertions.assertEquals(id, orderDto.id());
        Assertions.assertEquals(description, orderDto.description());
        Assertions.assertEquals(createdAt, orderDto.createdAt());
    }
}