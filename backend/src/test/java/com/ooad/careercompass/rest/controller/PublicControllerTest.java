//package com.ooad.careercompass.rest.controller;
//
//import com.ooad.careercompass.rest.dto.GenericResponse;
//import com.ooad.careercompass.service.JobApplicationService;
//import com.ooad.careercompass.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class PublicControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JobApplicationService jobApplicationService;
//
//    @InjectMocks
//    private PublicController publicController;
//
//    @Test
//    void testGetNumberOfUsers() {
//        List<Object> users = new ArrayList<>();
//        users.add(new Object());
//        users.add(new Object());
//        Mockito.when(userService.getUsers()).thenReturn(users);
//
//        int numberOfUsers = publicController.getNumberOfUsers();
//        assertEquals(2, numberOfUsers);
//    }
//
//    @Test
//    void testGetNumberOfUsersWithEmail() {
//        String email = "test@example.com";
//        GenericResponse expectedResponse = new GenericResponse();
//        expectedResponse.setStatus("User exists");
//        expectedResponse.setUserAccountPresent(true);
//        Mockito.when(userService.checkIfUserExistsAndRegistrationIsCompleted(email)).thenReturn(expectedResponse);
//
//        GenericResponse response = publicController.getNumberOfUsers(email);
//        assertEquals(expectedResponse, response);
//    }
//}