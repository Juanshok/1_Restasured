package builder;

import models.PetPostBodyModel;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class PetPostBodyBuilder implements Question<PetPostBodyModel> {

    private final String username;

    private final String password;



    public PetPostBodyBuilder(String username, String password) {
        this.username = username;
        this.password = password;

    }

    @Override
    public PetPostBodyModel answeredBy(Actor actor) {

        return PetPostBodyModel.builder()
                .username(username)
                .password(password)
                .build();

    }
    public static PetPostBodyBuilder is(String username, String password){
        return new PetPostBodyBuilder(username, password);
    }


}
