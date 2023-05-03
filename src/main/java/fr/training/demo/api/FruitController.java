package fr.training.demo.api;

import fr.training.demo.model.Fruit;
import fr.training.demo.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping // @RequestMapping(method = RequestMethod.GET)
    public List<Fruit> all() {
        return fruitService.fetchAllFruits();
    }

    @GetMapping("{id}")
    public Optional<String> getFruit(@PathVariable int id) {
        return fruitService.fetchFruit(id);
    }

    @PostMapping("{name}")
    public void addFruit(@PathVariable String name) {
        fruitService.addFruit(new Fruit(name));
    }
}
