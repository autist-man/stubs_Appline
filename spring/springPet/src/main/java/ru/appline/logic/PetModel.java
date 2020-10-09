package ru.appline.logic;

import java.util.HashMap;
import java.util.Map;

public class PetModel {
    private static final PetModel instance = new PetModel();

    public static PetModel getInstance() {
        return instance;
    }

    private final Map<Integer, Pet> model;

    public PetModel(){
        model = new HashMap<Integer, Pet>();
    }

    public void add(Pet pet, int id){
        model.put(id, pet);
    }

    public Pet getFromList(int id){
        return model.get(id);
    }

    public Map<Integer, Pet> getAll(){
        return model;
    }
}
