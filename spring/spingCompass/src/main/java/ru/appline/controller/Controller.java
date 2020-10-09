package ru.appline.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.appline.logic.Compass;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private static final Compass compass = Compass.getInstance();

    /**
     * Структура JSON
     * {
     *     "North": "337-22",
     *     "North-East":  "23-68",
     *     "East":  "69-114",
     *     "South-East": "115-160",
     *     "South": "161-206",
     *     "South-West": "207-252",
     *     "West": "253-298",
     *     "North-West":  "299-336"
     * }
     *
     */
    @PostMapping(value = "/createCompass",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createCompass(@RequestBody Map<String,String> allSide){
        compass.initCompass(allSide);
        return ("Компас создан!");
    }

    /**
     * Структура JSON
     * {
     *     "Degree": 170
     * }
     */
    @GetMapping(value = "/getSide",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSide(@RequestBody Map<String,Integer> degree){
        if(degree.get("Degree") < 0){
            return (new ResponseEntity<String>("Введите положительный угол!", HttpStatus.OK));
        } else if (degree.get("Degree") > 360){
            return (new ResponseEntity<String>("Данный градус не входит в диапазон от 0 до 359 градусов!", HttpStatus.OK));
        }

        Map<String,String> response = new HashMap<String, String>(1);
        for(Map.Entry<String, String> side : compass.getSides().entrySet()){
            if(compass.checkSide(side.getValue(),degree.get("Degree"))){
                response.put("Side", side.getKey());
                break;
            }
        }
        return  (new ResponseEntity<Map<String,String>>(response, HttpStatus.OK));
    }

}
