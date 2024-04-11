package com.ooad.careercompass.factory;

import com.ooad.careercompass.strategy.EmailNotificationStrategy;
import com.ooad.careercompass.strategy.MessageNotificationStrategy;
import com.ooad.careercompass.strategy.NotificationStrategy;

public class NotificationFactory {
    //        Done: Factory Pattern
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
