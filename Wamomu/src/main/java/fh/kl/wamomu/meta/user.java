package fh.kl.wamomu.meta;

import java.util.List;

/**
 * Meta Klasse, in dem alle Daten zum Benutzer gespeichert werden
 */

public class user {

    String name;
    String password;
    Long id;
    List<meal> meals;
    List<measurement> measurements;

    /**
     * Konstruktor
     * @param name
     * @param password
     * @param meals
     * @param measurements
     */
    public user(String name, String password, List<meal> meals, List<measurement> measurements, Long id) {
        this.name = name;
        this.password = password;
        this.meals = meals;
        this.measurements = measurements;
        this.id = id;
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

    public Long getId(){return id;}

    public void  setId(Long id){this.id = id;}
}
