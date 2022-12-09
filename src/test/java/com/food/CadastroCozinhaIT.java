package com.food;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {



    @LocalServerPort
    private int port;
    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinha(){

        RestAssured.given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
        .when()
            .get()
        .then()
           .statusCode(HttpStatus.OK.value());
    }

}