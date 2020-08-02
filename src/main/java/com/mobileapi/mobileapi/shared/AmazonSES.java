package com.mobileapi.mobileapi.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.mobileapi.mobileapi.shared.dto.UserDto;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AmazonSES {
        public void verifyEmail(UserDto userDto) {

                final String FROM = "vinipachecov@gmail.com";

                final String Subject = "One last step to complete your registration with MobileApp";

                final String HTMLBODY = "<h1>please veriyfy your email address</h1>"
                                + "<a href='http://localhost:8080/verification-service/email-erification'";

                final String TEXTBODY = "Please verify your email address.";

                AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                                .withRegion(Regions.US_EAST_1).build();

                String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
                String textBody = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

                SendEmailRequest request = new SendEmailRequest()
                                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                                .withMessage(new Message()
                                                .withBody(new Body().withHtml(new Content().withCharset("UTF-8")
                                                                .withData(htmlBodyWithToken)))
                                                .withSubject(new Content().withCharset("UTF-8").withData(Subject)))
                                .withSource(FROM);

                client.sendEmail(request);
        }
}