package com.ivanfranchin.orderapi.notification;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.rest.dto.GenericResponse;

public interface NotificationStrategy {
    GenericResponse sendNotification(User user);

    GenericResponse validateAccount(User user,String authSecret);
}