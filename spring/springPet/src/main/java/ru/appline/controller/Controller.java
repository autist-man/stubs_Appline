package ru.appline.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;
import ru.appline.util.ChangePet;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel  petModel = PetModel.getInstance();
    private static final AtomicInteger id = new AtomicInteger(1);

    @PostMapping(value = "/createPet",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public String createPet(@RequestBody Pet pet){
        petModel.add(pet, id.getAndIncrement());
        if(id.get() > 2){
            return ("В приюте появился новый питомец по кличке " + pet.getName() + "!");
        }
        return ("В приюте появился первый питомец по кличке " + pet.getName() + "!");
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        if (petModel.getAll().size() != 0) {
            return (new ResponseEntity<Map<Integer,Pet>>(petModel.getAll(), HttpStatus.OK));
        }
        return (new ResponseEntity<String>("В приюте сейчас нет питомцев!", HttpStatus.OK));
    }

    @GetMapping(value = "/getPet",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPet(@RequestBody Map<String, Integer> id){
        if(id.get("id") < 0){
            return (new ResponseEntity<String>("Номер питомца должен быть больше 0!" , HttpStatus.OK));
        }else if(petModel.getAll().containsKey(id.get("id"))){
            return (new ResponseEntity<Pet>(petModel.getFromList(id.get("id")), HttpStatus.OK));
        }
        return (new ResponseEntity<String>("Питомца с номером " + id.get("id") + " в приюте нет!" , HttpStatus.OK));
    }

    @DeleteMapping(value = "/delete",
                   consumes = MediaType.APPLICATION_JSON_VALUE,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody Map<String,Integer> id){
        if(id.get("id") < 0){
            return (new ResponseEntity<String>("Номер питомца должен быть больше 0!" , HttpStatus.OK));
        }
        if (petModel.getAll().size() == 0){
            return (new ResponseEntity<String>("В приюте нет питомцев!" , HttpStatus.OK));
        }else if((id.get("id") != 0) & (petModel.getAll().remove(id.get("id")) != null)){
            return (new ResponseEntity<Map<Integer,Pet>>(petModel.getAll(), HttpStatus.OK));
        } else if (id.get("id") == 0){
            petModel.getAll().clear();
            return (new ResponseEntity<String>("Всех питомцев забрали из приюта!" , HttpStatus.OK));
        }
        return (new ResponseEntity<String>("Питомца с номером " + id.get("id") + " в приюте нет!" , HttpStatus.OK));
    }

    /**
     * Метод "put" для изменения питомца с помощью транспортного класса
     * Структура JSON
     * {
     *     "id" : "1",
     *     "pet" : {
     *             "name": "Вэш",
     *             "type": "Вредный кот",
     *             "age": 3
     *             }
     * }
    */
    @PutMapping(value = "/change",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> put(@RequestBody ChangePet pet){

        if(pet.getId() < 0){
            return (new ResponseEntity<String>("Номер питомца должен быть больше 0!" , HttpStatus.OK));
        }
        if (petModel.getAll().size() == 0){
            return (new ResponseEntity<String>("В приюте нет питомцев!" , HttpStatus.OK));
        }else if(petModel.getAll().containsKey(pet.getId())){
            petModel.getAll().put(pet.getId(),pet.getPet());
            return (new ResponseEntity<Map<Integer,Pet>>(petModel.getAll(), HttpStatus.OK));
        }
        return (new ResponseEntity<String>("Питомца с номером " + pet.getId() + " в приюте нет!" , HttpStatus.OK));
    }

    /**
     *
     * Структура JSON
     * {
     *     "id" : "1",
     *     "name": "Вэш",
     *     "type": "Вредный кот",
     *     "age": "3"
     * }
     */
    /*
    @PutMapping(value = "/change",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> put(@RequestBody Map<String, String> pet){


        int id = Integer.parseInt(pet.get("id"));
        String name = pet.get("name");
        String type = pet.get("type");
        int age = Integer.parseInt(pet.get("age"));

        if(id < 0){
            return (new ResponseEntity<String>("Номер питомца должен быть больше 0!" , HttpStatus.OK));
        }
        if (petModel.getAll().size() == 0){
            return (new ResponseEntity<String>("В приюте нет питомцев!" , HttpStatus.OK));
        }else if(petModel.getAll().containsKey(id)){
            petModel.getAll().put(id,new Pet(name, type, age));
            return (new ResponseEntity<Map<Integer,Pet>>(petModel.getAll(), HttpStatus.OK));
        }
        return (new ResponseEntity<String>("Питомца с номером " + id + " в приюте нет!" , HttpStatus.OK));
    }
    */
}
