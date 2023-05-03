package fr.training.demo.api;

import fr.training.demo.model.Fruit;
import fr.training.demo.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FruitWebController {

    @Autowired
    private FruitService fruitService;

    @GetMapping("/")
    public String all(Model model) {
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
        fruitService.addFruit(fruit);
        return "redirect:/";
    }



}
