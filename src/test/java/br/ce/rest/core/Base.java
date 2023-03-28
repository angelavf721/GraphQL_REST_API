package br.ce.rest.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;

public class Base {

    @BeforeClass
     public static void stup(){
        RestAssured.baseURI = "https://gorest.co.in/public/v2";

        RequestSpecBuilder resSpec = new RequestSpecBuilder();
        resSpec.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = resSpec.build();

        ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();
        responseSpec.expectResponseTime(Matchers.lessThan(5000L));
        RestAssured.responseSpecification = responseSpec.build();

        RestAssured.requestSpecification.header("Authorization", "Bearer " + "743b3591f92698cfd9c3f38f964586bac9382b63f69c1ba7044d32582055864e");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
