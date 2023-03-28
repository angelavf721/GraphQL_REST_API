package br.ce.rest.test;

import br.ce.rest.core.Base;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class User extends Base {
    private static Integer userId;
    @Test
    public void t01_listarUsuario(){
        given()
        .when()
            .get("/users")
        .then()
                .log().all()
            .statusCode(200);
    }
    @Test
    public void t02_criarUsuario(){
        Map<String, String> user = new HashMap<>();
        user.put("name","Maria Do Carmo");
        user.put("email","MariadoCarmo@gamail.com");
        user.put("gender","male");
        user.put("status","active");
        userId = given()
                .body(user)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("id");
    }
    @Test
    public void t03_EditarUsuario(){
        Map<String, String> user = new HashMap<>();
        user.put("name","Maria");
        user.put("email","Maria@gamail.com");
        user.put("gender","male");
        user.put("status","active");
        given()
                .body(user)
                .pathParam("id", userId)
                .when()
                .put("/users/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void t04_ExcluirUsuario(){
        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204);
    }

}
