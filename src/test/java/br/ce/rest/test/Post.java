package br.ce.rest.test;

import br.ce.rest.core.Base;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;



import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Post extends Base {

    private static Integer userId;
    private static Integer PostId;

    @Test
    public void t01_listarPosts(){
        given()
        .when()
            .get("/posts")
        .then()
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
            .extract().path("id");
    }
    @Test
    public void t04_naoDeveCriarPostEmBranco(){
        given()
                .pathParam("idUser", userId)
        .when()
            .post("/users/{idUser}/posts")
        .then()
            .statusCode(422)
            .log().all()
            .body("$", hasSize(2))
            .body("message",hasItems("can't be blank", "can't be blank"));
    }
    @Test
    public void t05_ExcluirPost(){
        given()
                .pathParam("idPost", PostId)
        .when()
            .delete("/posts/{idPost}")
        .then()
            .statusCode(204);
    }
    @Test
    public void t6_ExcluirUsuario(){
        given()
            .pathParam("id", userId)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(204);
    }

}
