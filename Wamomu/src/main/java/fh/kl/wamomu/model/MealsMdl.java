package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Max on 09.04.2014.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MealsMdl implements Serializable {

    @JsonProperty("meal")
    List<MealMdl> meal;

    public List<MealMdl> getMeal() {
        return meal;
    }

    public void setMeal(List<MealMdl> meal) {
        this.meal = meal;
    }
}
