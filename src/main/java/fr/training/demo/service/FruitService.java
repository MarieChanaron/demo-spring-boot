package fr.training.demo.service;

import fr.training.demo.model.Fruit;
import fr.training.demo.repository.FruitJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FruitService {

    // Simulation de la base de données (vouée à disparaître)
    //private static List<String> fruits = Arrays.asList("Kiwi", "Fraises", "Groseilles");

    @Autowired
    private FruitJdbcRepository repository;

    public List<String> fetchAllNames() {
        return repository.getAllNames();
    }

    public List<Fruit> fetchAllFruits() {
        return repository.getAllFruits();
    }

    public Optional<String> fetchFruit(int id) {
        return repository.getFruitById(id);
    }

    public void addFruit(Fruit fruit) {
        repository.addFruit(fruit);
    }
}
