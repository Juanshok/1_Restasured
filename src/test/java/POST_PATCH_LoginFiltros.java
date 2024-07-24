import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class POST_PATCH_LoginFiltros {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";   // Indicamos la base de la url
        RestAssured.basePath = "/api";               // Indicamos el path base de la url

        //Los filtros ayudan a reciclar codigo y no tener la necesidad de sobre escritura,
        //En este caso, con el "new RequestLoggingFilter()" y el "LogDetail.ALL"
        //No habria necesidad de colocar en cada metodo el log().all()

        //Aqui especificamos los parametros que siempre se repiten para no sobre escribir codigo

        //Parametros del Request
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .build();

        //Parametros del Response
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

    }


    @Test
    public void testLogin() {

        String response = RestAssured.
                given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("/login")
                .then()  //Entonces
                .extract() //Extraiga
                .asString(); //toda la respuesta como un String

        //System.out.println(response);

    }

    @Test
    public void testLoginAssersiones() {

        RestAssured.
                given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("/login")
                .then()  //Entonces
                .statusCode(200)   //Codigo de respuesta esperado
                .body("token", notNullValue());   //Decimos que valide que el body en la clave "token" no sea nulo

    }

    @Test
    public void testPatchSingleUser(){
        String userName = RestAssured
                .given()
                .when()
                .body("{\n" +
                        "    \"name\": \"Juan\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("users/2")
                .then()  //Entonces
                .statusCode(200)  //Codigo de respuesta esperado
                .extract()
                .jsonPath().getString("name"); //Extrae el valor que se le solicite de clave

        assertThat(userName,equalTo("Juan")); // Validacion que el valor sea igual al esperado

    }

}
