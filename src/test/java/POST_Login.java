import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.notNullValue;

public class POST_Login {

    @Test
    public void testLogin(){

        String response = RestAssured
                .given()
                .log().all() // Que me muestre en consola lo que se está enviando (No es necesaria a menos que queramos)
                .contentType(ContentType.JSON) //SE LE INDICA EL TIPO DE ARCHIVO QUE SE ENVIARA
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()  //Entonces
                .log() // Dado que esta despues del then, le estamos indicando en pantalla que nos muestre
                .all() // todos los parametros de la respuesta (No es necesaria a menos que queramos)
                .extract() //Extraiga
                .asString(); //toda la respuesta como un String

        System.out.println(response);

    }

    @Test
    public void testLoginAssersiones() throws IOException {

        String jsonBody = new String(Files.readAllBytes(Paths.get("datosJson/Login.json")));

        RestAssured
                .given()
                .log().all() // Que me muestre en consola lo que se está enviando (No es necesaria a menos que queramos)
                .contentType(ContentType.JSON) //SE LE INDICA EL TIPO DE ARCHIVO QUE SE ENVIARA
                .body(jsonBody)
                .post("https://reqres.in/api/login")
                .then()  //Entonces
                .log() // Dado que esta despues del then, le estamos indicando en pantalla que nos muestre
                .all() // todos los parametros de la respuesta (No es necesaria a menos que queramos)
                .statusCode(200)   //Codigo de respuesta esperado
                .body("token",notNullValue());   //Decimos que valide que el body en la clave "token" no sea nulo

    }

}
