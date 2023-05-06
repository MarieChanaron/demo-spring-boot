package fr.training.demo.repository;

import fr.training.demo.model.Fruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private final class FruitPst implements PreparedStatementCreator {

        Fruit fruit;
        String query;

        public FruitPst(String query, Fruit fruit) {
            this.query = query;
            this.fruit = fruit;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement pst = con.prepareStatement(this.query, new String[] {"id"});
            pst.setString(1, this.fruit.getName());
            return pst;
        }
    }

    public Optional<Integer> addFruit(Fruit fruit) {

        String query = "INSERT INTO fruit (name) VALUES (?);";
        Optional<Integer> key = Optional.of(0);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        FruitPst preparedStatement = new FruitPst(query, fruit);
        this.jdbcTemplate.update(preparedStatement, keyHolder);

        try {
            key = Optional.of(keyHolder.getKey().intValue());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return key;
    }

    public boolean deleteFruit(Fruit fruit) {

        boolean success = false;
        int id = fruit.getId();
        String name = fruit.getName();
        String query = "";

        try {
            if (id != 0) {
                query = "DELETE FROM fruit WHERE id = ?;";
                this.jdbcTemplate.update(query, Integer.valueOf(id));
                success = true;
            } else if (name != null) {
                query = "DELETE FROM fruit WHERE name = ?;";
                this.jdbcTemplate.update(query, name);
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public Optional<String> updateFruit(Fruit fruit) {

        int id = fruit.getId();
        String name = fruit.getName();
        String query = "UPDATE fruit SET name = ? WHERE id = ?;";

        try {
            this.jdbcTemplate.update(query, name, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<String> fruitUpdated = Optional.of("");

        try {
            fruitUpdated = this.getFruitById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fruitUpdated;
    }


}
