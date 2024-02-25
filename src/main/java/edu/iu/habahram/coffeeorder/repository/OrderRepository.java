package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private int id;
    public Receipt add(OrderData order) throws Exception {
        Beverage beverage = null;
        switch (order.beverage().toLowerCase()) {
            case "dark roast":
                beverage = new DarkRoast();
                break;
            case "decaf":
                beverage = new Decaf();
                break;
            case "espresso":
                beverage = new Espresso();
                break;
            case "houseblend":
                beverage = new HouseBlend();
                break;
        }
        if (beverage == null) {
            throw new Exception("Beverage type '%s' is not valid!".formatted(order.beverage()));
        }
        for(String condiment : order.condiments()) {
            switch (condiment.toLowerCase()) {
                case "milk":
                   beverage = new Milk(beverage);
                   break;
                case "mocha":
                    beverage = new Mocha(beverage);
                    break;
                case "whip":
                    beverage = new Whip(beverage);
                    break;
                case "soy":
                    beverage = new Soy(beverage);
                    break;
                default:
                    throw new Exception("Condiment type '%s' is not valid".formatted(condiment));
            }
        }
        Receipt receipt = new Receipt(id, beverage.cost(), beverage.getDescription());
        id++;

        String string_to_db;
        
        if(beverage.cost() == (long) beverage.cost())
            string_to_db = String.format("%d, %d, %s\n", id, (long) beverage.cost(), beverage.getDescription());
        else
            string_to_db = String.format("%d, %s, %s\n", id, beverage.cost(), beverage.getDescription());

        writeToFile(string_to_db);

        return receipt;
    }

    public static void writeToFile(String input) {
        // Specify the file name
        String fileName = "db.txt";
        
        // Get the current directory
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        
        // Resolve the file path
        Path filePath = currentPath.resolve(fileName);
        
        try {
            Files.write(filePath, input.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Data has been written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
