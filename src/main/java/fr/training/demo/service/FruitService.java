package fr.training.demo.service;

import fr.training.demo.model.Fruit;
import fr.training.demo.repository.FruitJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class FruitService {

    @Autowired
    private FruitJdbcRepository repository;

    public List<String> fetchAllNames() {
        return repository.getAllNames();
    }

    public List<Fruit> fetchAllFruits() {
        return repository.getAllFruits();
    }

    public Optional<Fruit> fetchFruit(int id) {
        Optional<String> fruitName = repository.getFruitById(id);
        Fruit fruit = new Fruit();
        if (fruitName.isPresent()) {
            fruit.setId(id);
            fruit.setName(fruitName.get());
        }
        return Optional.of(fruit);
    }

    public Fruit addFruit(String name) {
        Optional<Integer> id = repository.addFruit(new Fruit(name));
        Fruit fruit = new Fruit();
        if (id.isPresent()) {
            fruit.setId(id.get());
            fruit.setName(name);
        }
        return fruit;
    }

    public boolean deleteFruit(Fruit fruit) {
        return repository.deleteFruit(fruit);
    }

    public Optional<Fruit> updateFruit(int id, String name) {
        Optional<String> updatedName = repository.updateFruit(new Fruit(id, name));
        Optional<Fruit> updatedFruit = Optional.of(new Fruit());
        if (updatedName.isPresent()) {
            updatedFruit = Optional.of(new Fruit(id, updatedName.get()));
        }
        return updatedFruit;
    }
}
