package ru.appline.logic;

import java.util.HashMap;
import java.util.Map;

public class Compass {

    private static final Compass instance = new Compass();

    public static Compass getInstance(){
        return instance;
    }
    private final Map<String,String> side;

    public Compass() {
        side = new HashMap<String, String>();
    }

    public void initCompass(Map<String,String> side){
        this.side.putAll(side);
    }

    public Map<String, String> getSides() {

        return side;
    }

    public boolean checkSide(String rangeDegrees, int degree){
        String[] range = rangeDegrees.split("-");
        if((Integer.parseInt(range[0]) <= degree & degree <= Integer.parseInt(range[1]))){
            return true;
        } else if(Integer.parseInt(range[0]) - Integer.parseInt(range[1]) > 0 &
                  (degree <= Integer.parseInt(range[1]) | degree >= Integer.parseInt(range[0]))){
            return true;
        }

        return false;
    }

}
