//package com.ooad.careercompass.factory;
//
//import com.ooad.careercompass.strategy.EmailNotificationStrategy;
//import com.ooad.careercompass.strategy.MessageNotificationStrategy;
//import com.ooad.careercompass.strategy.NotificationStrategy;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class NotificationFactoryTest {
//
//    @Test
//    void testGetEmailNotificationStrategy() {
//        NotificationStrategy strategy = NotificationFactory.getEmailNotificationStrategy();
//        assertTrue(strategy instanceof EmailNotificationStrategy);
//    }
//
//    @Test
//    void testGetSMSNotificationStrategy() {
//        NotificationStrategy strategy = NotificationFactory.getSMSNotificationStrategy();
//        assertTrue(strategy instanceof MessageNotificationStrategy);
//        assertEquals("sms", ((MessageNotificationStrategy) strategy).getStrategyType());
//    }
//
//    @Test
//    void testGetWhatsAppNotificationStrategy() {
//        NotificationStrategy strategy = NotificationFactory.getWhatsAppNotificationStrategy();
//        assertTrue(strategy instanceof MessageNotificationStrategy);
//        assertEquals("whatsapp", ((MessageNotificationStrategy) strategy).getStrategyType());
//    }
//}