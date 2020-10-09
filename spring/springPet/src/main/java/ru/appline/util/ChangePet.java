package ru.appline.util;

import ru.appline.logic.Pet;

//Транспортный класс "ChangePet"
public class ChangePet{

    private int id;
    private Pet pet;

    public int getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }
    public ChangePet() {
        super();
    }

    public ChangePet(int id, Pet pet) {
        this.id = id;
        this.pet = pet;
    }
}