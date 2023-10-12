package Exercises;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        Supplier<Customer> customerSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Customer(rndm.nextInt(0,1000),faker.name().firstName(),rndm.nextInt(1,3));
        };

        Supplier<Product> productSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Product(rndm.nextInt(0,9999),faker.book().title(),faker.color().name(),rndm.nextDouble(0,999));
        };

        Supplier<List> productListSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new ArrayList<Product>(Arrays.asList(productSupplier.get(),productSupplier.get(),productSupplier.get(),productSupplier.get(),productSupplier.get()));
        };

        Supplier<Order> orderSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Order(rndm.nextInt(0,9999),"delivery", LocalDate.now(),LocalDate.now().plusDays(3),productListSupplier.get(),customerSupplier.get() );
        };

        ArrayList<Order> orderlist1 = new ArrayList<Order>();
        for (int i = 0; i < 20; i++){
            orderlist1.add(orderSupplier.get());
        }
        System.out.println("*******************************EXERCISE 1*******************************");
        Map<Customer, List<Order>> customerOrdersMap = orderlist1.stream().collect(Collectors.groupingBy(Order::getCustomer));
        System.out.println(customerOrdersMap.toString());
        System.out.println("*******************************EXERCISE 2*******************************");
        Map<Customer, Double> customerTotalMap = orderlist1.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(Order -> Order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
        System.out.println(customerTotalMap);
        System.out.println("*******************************EXERCISE 3*******************************");
    }
}
