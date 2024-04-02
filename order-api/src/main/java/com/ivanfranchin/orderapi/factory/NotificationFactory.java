package com.ivanfranchin.orderapi.factory;

import com.ivanfranchin.orderapi.notification.EmailNotificationStrategy;
import com.ivanfranchin.orderapi.notification.MessageNotificationStrategy;
import com.ivanfranchin.orderapi.notification.NotificationStrategy;

public class NotificationFactory {
    public static NotificationStrategy getEmailNotificationStrategy(){
        return new EmailNotificationStrategy();
    }

    public static NotificationStrategy getSMSNotificationStrategy(){
        return new MessageNotificationStrategy("sms");
    }

    public static NotificationStrategy getWhatsAppNotificationStrategy(){
        return new MessageNotificationStrategy("whatsapp");
    }
}
