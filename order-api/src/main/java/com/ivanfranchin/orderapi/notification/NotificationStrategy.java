package com.ivanfranchin.orderapi.notification;

import com.ivanfranchin.orderapi.model.JobApplication;
import com.ivanfranchin.orderapi.model.User;

public interface NotificationStrategy {
    void sendNotification(User user);

//    void verifyAccount(User user);
}