package com.ivanfranchin.orderapi.factory;

import com.ivanfranchin.orderapi.strategy.EmailNotificationStrategy;
import com.ivanfranchin.orderapi.strategy.MessageNotificationStrategy;
import com.ivanfranchin.orderapi.strategy.NotificationStrategy;

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
