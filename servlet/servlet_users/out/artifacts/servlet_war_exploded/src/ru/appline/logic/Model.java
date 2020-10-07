package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {

    private static final Model instance = new Model();

    private final Map<Integer, User> model;

    public static Model getInstance(){
        return instance;
    }

    private Model(){
        model = new HashMap<>();

        model.put(1, new User("Темур", "Мавлянов", 50000));
        model.put(2, new User("Таня", "Русакова", 70000));
        model.put(3, new User("Саша", "Иванов", 60000));
    }

    public void addUser(User user){
        int key = 1;
        while (model.containsKey(key)!=false){
            key++;
        }
        model.put(key,user);
    }

    public Map<Integer, User> getFromList(){
        return model;
    }

}
