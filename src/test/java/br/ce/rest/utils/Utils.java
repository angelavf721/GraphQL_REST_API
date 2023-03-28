package br.ce.rest.utils;

import io.restassured.RestAssured;

public class Utils {
    public static Integer getIdUser(String nome) {
        return RestAssured.get("/users?name="+nome).then().extract().path("id[0]");
    }
}
