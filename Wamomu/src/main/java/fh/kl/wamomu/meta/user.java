package fh.kl.wamomu.meta;

import java.util.List;

public class user {

    String name;
    String password;
    List<meal> meals;
    List<measurement> measurements;

    public user(String name, String password, List<meal> meals, List<measurement> measurements) {
        this.name = name;
        this.password = password;
        this.meals = meals;
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
}
