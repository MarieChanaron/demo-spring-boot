package fr.training.demo.repository;

import fr.training.demo.model.Fruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FruitJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<String> getAllNames() {
        String querySql = "SELECT name FROM fruit";
        return this.jdbcTemplate.queryForList(querySql, String.class);
    }

    public List<Fruit> getAllFruits() {
        String query = "select id, name from fruit";
        List<Fruit> fruits = this.jdbcTemplate.query(query, new RowMapper<Fruit>() {
            @Override
            public Fruit mapRow(ResultSet rs, int rowNum) throws SQLException {
                Fruit fruit = new Fruit();
                fruit.setId(rs.getInt("id"));
                fruit.setName(rs.getString("name"));
                return fruit;
            }
        });
        return fruits;
    }

    public Optional<String> getFruitById(int id) {
        String query = "SELECT name FROM fruit WHERE id = ?;";
        String name = this.jdbcTemplate.queryForObject(query, String.class, id);
        return Optional.of(name);
    }

    public void addFruit(Fruit fruit) {
        String query = "INSERT INTO fruit (name) VALUES (?);";
        this.jdbcTemplate.update(query, fruit.getName());
    }
}
