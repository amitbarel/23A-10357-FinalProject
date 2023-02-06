package com.amitb.a23a_10357_soccerwager.Utils;

public class Team {
    private String name;
    private int strength;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public Team setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", strength=" + strength +
                '}';
    }
}
