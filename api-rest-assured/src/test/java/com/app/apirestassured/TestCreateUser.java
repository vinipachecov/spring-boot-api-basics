package com.app.apirestassured;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCreateUser {

    private final String CONTEXT_PATH="";

    @BeforeEach
    void setUp() throws Exception {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;

    };

    @Test
    final void testCreateUser() {
        List<Map<String, Object>> userAddresses = new ArrayList<>();

        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("city", "Vancouver");
        shippingAddress.put("country", "Canada");
        shippingAddress.put("streetName", "123 Street name");
        shippingAddress.put("postalCode", "123456");
        shippingAddress.put("type", "shipping");

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("city", "Vancouver");
        billingAddress.put("country", "Canada");
        billingAddress.put("streetName", "123 Street name");
        billingAddress.put("postalCode", "123456");
        billingAddress.put("type", "billing");

        userAddresses.add(shippingAddress);
        userAddresses.add(billingAddress);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Sergey");
        userDetails.put("lastName", "Kargopolov");
        userDetails.put("email", "sergey.kargopolov@swiftdeveloperblog.com");
        userDetails.put("password", "123");
        userDetails.put("addresses", userAddresses);


        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(userDetails)
                .when()
                .post(CONTEXT_PATH + "users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        String userId = response.jsonPath().getString("userId");
        assertNotNull(userId);
    }


}
