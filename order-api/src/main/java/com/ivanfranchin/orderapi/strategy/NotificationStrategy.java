package com.ivanfranchin.orderapi.strategy;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;

public interface NotificationStrategy {
    GenericResponse sendNotification(User user);

    GenericResponse validateAccount(User user,String authSecret);
}