package br.ce.rest.test;

import br.ce.rest.core.Base;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Comentarios extends Base {

    private static Integer userId;
    private static Integer PostId;
    private static Integer commentsId;
    @Test
    public void t01_listarComentario(){
        given()
        .when()
            .get("/comments")
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
    public void t03_criarPost(){
        Map<String, String> post = new HashMap<>();
        post.put("title","Revolução Industrial");
        post.put("body","ghfdhsghfjghfuhgiurhgrfhgfsbgvfvgbfbvgf");

        PostId = given()
                .body(post)
                .pathParam("idUser", userId)
                .when()
                .post("/users/{idUser}/posts")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("id");
    }

    @Test
    public void t04_criarComentario(){
        Map<String, String> post = new HashMap<>();
        post.put("name","Maria Do Carmo");
        post.put("email","MariaCarmo@gmail.com");
        post.put("body","ytrtyrtyrt");

        commentsId = given()
                .body(post)
                .pathParam("idPost", PostId)
                .when()
                .post("/posts/{idPost}/comments")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("id");
    }
    @Test
    public void t05_NaocriarComentarioVazio(){


         given()
                .pathParam("idPost", PostId)
                .when()
                .post("/posts/{idPost}/comments")
                .then()
                .statusCode(422)
                .body("message", hasItems("can't be blank","can't be blank, is invalid"))
                .log().all();
    }

    @Test
    public void t06_deletarComentario(){
         given()
                .pathParam("id", commentsId)
                .when()
                .delete("/comments/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void t07_ExcluirPost(){
        given()
                .pathParam("idPost", PostId)
                .when()
                .delete("/posts/{idPost}")
                .then()
                .statusCode(204);
    }
    @Test
    public void t8_ExcluirUsuario(){
        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204);
    }
}
