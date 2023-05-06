package fr.training.demo.controller;

import fr.training.demo.model.Fruit;
import fr.training.demo.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FruitWebController {

    @Autowired
    private FruitService fruitService;

    @GetMapping("/")
    public String getAll(Model model) {
        List<Fruit> allFruits = fruitService.fetchAllFruits();
        model.addAttribute("fruitList", allFruits);
        return "fruits";
    }

    @GetMapping("/add-fruit")
    public String addFruit(Model model) {
        model.addAttribute("fruit", new Fruit());
        return "add-fruit";
    }

    @PostMapping("/add-fruit")
    public String saveFruit(@ModelAttribute Fruit fruit) {
        fruitService.addFruit(fruit.getName());
        return "redirect:/";
    }

    @GetMapping("/fruit-details")
    public String getFruit(@RequestParam int id, Model model) {
        Optional<Fruit> fruit = fruitService.fetchFruit(id);
        if (fruit.isPresent()) model.addAttribute("fruit", fruit.get());
        return "fruit-details";
    }

}
