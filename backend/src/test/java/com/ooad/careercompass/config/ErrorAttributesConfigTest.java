//package com.ooad.careercompass.config;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//
//class ErrorAttributesConfigTest {
//
//    @Test
//    void errorAttributes_shouldReturnDefaultErrorAttributesWithIncludedOptions() {
//        // Arrange
//        ErrorAttributesConfig config = new ErrorAttributesConfig();
//        WebRequest webRequest = mock(WebRequest.class);
//
//        // Act
//        ErrorAttributes errorAttributes = config.errorAttributes();
//        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));
//
//        // Assert
//        assertTrue(attributes.containsKey("exception"));
//        assertTrue(attributes.containsKey("message"));
//        assertTrue(attributes.containsKey("errors"));
//    }
//}