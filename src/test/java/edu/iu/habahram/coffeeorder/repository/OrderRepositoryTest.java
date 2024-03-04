package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.OrderData;
import edu.iu.habahram.coffeeorder.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private OrderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new OrderRepository();
    }

    @Test
    void add1() throws Exception {
        OrderData order = new OrderData("dark roast", List.of());
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Dark roast"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add2() throws Exception {
        OrderData order = new OrderData("houseblend", List.of("milk"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("HouseBlend, Milk"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add3() throws Exception {
        OrderData order = new OrderData("espresso", List.of("mocha", "whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Espresso, Mocha, Whip"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add4() throws Exception {
        OrderData order = new OrderData("decaf", List.of("milk", "mocha", "soy", "whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Decaf, Milk, Mocha, Soy, Whip"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add5() {
        OrderData order = new OrderData("green tea", List.of());
        Exception exception = assertThrows(Exception.class, () -> repository.add(order));
        assertTrue(exception.getMessage().contains("Beverage type 'green tea' is not valid!"));
    }

    @Test
    void add6() {
        OrderData order = new OrderData("espresso", List.of("caramel"));
        Exception exception = assertThrows(Exception.class, () -> repository.add(order));
        assertTrue(exception.getMessage().contains("Condiment type 'caramel' is not valid"));
    }

    @Test
    void add7() throws Exception {
        OrderData order = new OrderData("houseblend", List.of("soy"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("HouseBlend, Soy"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add8() throws Exception {
        OrderData order = new OrderData("dark roast", List.of("milk", "milk"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Dark roast, Milk, Milk"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add9() throws Exception {
        OrderData order = new OrderData("espresso", List.of("mocha"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Espresso, Mocha"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add10() throws Exception {
        OrderData order = new OrderData("decaf", List.of());
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Decaf"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add11() throws Exception {
        OrderData order = new OrderData("houseblend", List.of("whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("HouseBlend, Whip"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add12() throws Exception {
        OrderData order = new OrderData("dark roast", List.of("soy", "whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Dark roast, Soy, Whip"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add13() throws Exception {
        OrderData order = new OrderData("espresso", List.of("milk", "mocha", "soy", "whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Espresso, Milk, Mocha, Soy, Whip"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add14() throws Exception {
        OrderData order = new OrderData("houseblend", List.of("milk", "mocha"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("HouseBlend, Milk, Mocha"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add15() throws Exception {
        OrderData order = new OrderData("decaf", List.of("milk", "soy"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Decaf, Milk, Soy"));
        assertTrue(receipt.cost() > 0);
    }

    @Test
    void add16() throws Exception {
        OrderData order = new OrderData("dark roast", List.of("whip", "whip", "whip"));
        Receipt receipt = repository.add(order);
        assertTrue(receipt.description().contains("Dark roast, Whip, Whip, Whip"));
        assertTrue(receipt.cost() > 0);
    }
}