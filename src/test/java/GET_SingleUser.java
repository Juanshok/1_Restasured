import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class GET_SingleUser {

    @Test
    public void testGetSingleUser(){

        RestAssured
                .given()
                .log().all() // Que me muestre en consola lo que se está enviando (No es necesaria a menos que queramos)
                .contentType(ContentType.JSON) //SE LE INDICA EL TIPO DE ARCHIVO QUE SE ENVIARA
                .get("https://reqres.in/api/users/2")
                .then()  //Entonces
                .log() // Dado que esta despues del then, le estamos indicando en pantalla que nos muestre
                .all() // todos los parametros de la respuesta (No es necesaria a menos que queramos)
                .statusCode(200)   //Codigo de respuesta esperado
                .body("data.id",equalTo(2));   //Decimos que valide que el body de respuesta en el apartado "data" y clave "id" sea igual a 2

    }
}
