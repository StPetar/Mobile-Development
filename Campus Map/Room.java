package com.example.madcampusmap;

public class Room {
    private String Building;
    private int Number;

    public Room(String Building, int Number) {
        this.Building = Building;
        this.Number = Number;
    }

    public String getBuilding() {

        return Building;
    }

    public String getNumber(){

        return Integer.toString(Number);
    }
    public String getRoom(){
        return Building + Number;
    }
}
