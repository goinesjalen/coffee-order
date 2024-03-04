package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private int id;
    public Receipt add(OrderData order) throws Exception {
        Beverage beverage = switch (order.beverage().toLowerCase()) {
            case "dark roast" -> new DarkRoast();
            case "decaf" -> new Decaf();
            case "espresso" -> new Espresso();
            case "houseblend" -> new HouseBlend();
            default -> null;
        };
        if (beverage == null) {
            throw new Exception("Beverage type '%s' is not valid!".formatted(order.beverage()));
        }
        for(String condiment : order.condiments()) {
            beverage = switch (condiment.toLowerCase()) {
                case "milk" -> new Milk(beverage);
                case "mocha" -> new Mocha(beverage);
                case "whip" -> new Whip(beverage);
                case "soy" -> new Soy(beverage);
                default -> throw new Exception("Condiment type '%s' is not valid".formatted(condiment));
            };
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
    public List<Receipt> findAll() throws IOException {
        List<Receipt> result = new ArrayList<>();
        Path path = Paths.get("db.txt");
        List<String> data = Files.readAllLines(path);
        for (String line : data) {
            if(!line.trim().isEmpty()) {
                String[] words = line.split(",");
                String description = String.valueOf(words[2]);
                if (words.length > 3) {
                    description = getString(words);
                }
                Receipt r = new Receipt(Integer.parseInt(words[0]), Float.parseFloat(words[1]), description);
                result.add(r);
            }
        }
        return result;
    }

    private static String getString(String[] words) {
        String[] condiments = Arrays.copyOfRange(words, 2, words.length);
        String description = "";
        boolean first = true;
        for (String cond : condiments) {
            if (first) {
                if (condiments.length > 1)
                    description = cond + " with ";
                else
                    description = cond;
                first = false;
            }
            else
                description += cond + ", ";
        }
        description = description.substring(0, description.length() - 2);
        return description;
    }

    public Receipt getOrder() throws IOException {
        List<Receipt> receipts = findAll();
        return receipts.get(receipts.size() - 1);
    }
}
