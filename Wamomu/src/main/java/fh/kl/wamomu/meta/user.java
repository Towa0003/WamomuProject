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

    public void setPassword(String password) {
        this.password = password;
    }

    public List<meal> getMeals() {
        return meals;
    }

    public void setMeals(List<meal> meals) {
        this.meals = meals;
    }

    public List<measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<measurement> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(measurement ms){
        measurements.add(ms);
    }

    public void addMeal(meal me) {
        meals.add(me);
    }
}
