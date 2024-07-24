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

public class PUT_DELETE_Test {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore3.swagger.io";   // Indicamos la base de la url
        RestAssured.basePath = "/api/v3/pet";                   // Indicamos el path base de la url

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
    public void testDeletePet(){
        RestAssured
                .given()
                .delete("/10")
                .then()  //Entonces
                .statusCode(200);   //Codigo de respuesta esperado
    }


    @Test
    public void testPutPet(){
        String petName = RestAssured
                .given()
                .when()
                .body("{\n" +
                        "  \"id\": 20,\n" +
                        "  \"name\": \"Juan\",\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Cats\"\n" +
                        "  },\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .put("")
                .then()  //Entonces
                .statusCode(200)  //Codigo de respuesta esperado
                .extract()
                .jsonPath().getString("category.name"); //Extrae el valor que se le solicite de clave
        assertThat(petName,equalTo("Cats")); // Validacion que el valor sea igual al esperado

    }
}
