package fr.training.demo.controller;

import fr.training.demo.model.Fruit;
import fr.training.demo.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/fruits")
public class FruitRestController {

    @Autowired
    private FruitService fruitService;

    @GetMapping("") // @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Fruit>> all() {
        List<Fruit> fruits = fruitService.fetchAllFruits();
        if (fruits.size() == 0) {
            return new ResponseEntity<List<Fruit>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Fruit>>(fruits, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Fruit> getFruit(@PathVariable int id) {
        Optional<Fruit> fruit = fruitService.fetchFruit(id);
        if (fruit.isPresent()) {
            return new ResponseEntity<Fruit>(fruit.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Fruit>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{name}")
    public void addFruit(@PathVariable String name) {
        fruitService.addFruit(name);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = fruitService.deleteFruit(new Fruit(id));
        if (deleted) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("")
    public ResponseEntity<Fruit> update(@RequestBody Fruit fruit) {
        int id = fruit.getId();
        String name = fruit.getName();
        Optional<Fruit> updatedFruit;

        if (id > 0 && name != null) {
            updatedFruit = fruitService.updateFruit(id, name);
        } else {
            return new ResponseEntity<Fruit>(HttpStatus.BAD_REQUEST);
        }

        if (updatedFruit.isPresent()) {
            return new ResponseEntity<Fruit>(updatedFruit.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Fruit>(HttpStatus.NOT_FOUND);
        }
    }

}
