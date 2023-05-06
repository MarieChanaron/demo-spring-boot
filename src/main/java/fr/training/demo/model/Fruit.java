package fr.training.demo.model;

import java.util.Optional;

public class Fruit {

    private int id;
    private String name;


    public Fruit() {}

    public Fruit(int id) {
        this.id = id;
    }

    public Fruit(String name) {
        this.name = name;
    }

    public Fruit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
