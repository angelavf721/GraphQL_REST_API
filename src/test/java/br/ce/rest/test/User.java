package br.ce.rest.test;

import br.ce.rest.core.Base;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;


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
        user.put("email","MariaCarmo@gmail.com");
        user.put("gender","female");
        user.put("status","active");
        userId = given()
                    .body(user)
                .when()
                    .post("/users")
                .then()
                    .statusCode(201)
                    .extract().path("id");
    }
    @Test
    public void t03_naoCriarUsuarioComMesmoEmail(){
        Map<String, String> user = new HashMap<>();
        user.put("name","Maria");
        user.put("email","MariaCarmo@gmail.com");
        user.put("gender","female");
        user.put("status","active");
        given()
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(422)
            .body("$",hasSize(1))
            .body("field", hasItem("email"))
            .body("message", hasItem("has already been taken"));
    }
    @Test
    public void t04_EditarUsuario(){
        Map<String, String> user = new HashMap<>();
        user.put("name","Maria");
        user.put("email","MariaDoCarmo@gamail.com");
        user.put("gender","female");
        user.put("status","active");
        given()
            .body(user)
            .pathParam("id", userId)
        .when()
            .put("/users/{id}")
        .then()
            .statusCode(200);
    }

    @Test
    public void t05_ExcluirUsuario(){
        given()
            .pathParam("id", userId)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    public void t06_naoCriarUsuarioVazio(){
        given()
        .when()
            .post("/users")
        .then()
            .statusCode(422)
            .body("$",hasSize(4))
            .body("message", hasItems(
            "can't be blank", "can't be blank",
            "can't be blank, can be male of female","can't be blank"));
    }


}
