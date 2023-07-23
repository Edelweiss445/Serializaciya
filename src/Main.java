import java.io.File;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Product[] products = {
                new Product("Apple", 55.50),
                new Product("Banana", 99.35),
                new Product("Milk", 85.50),
                new Product("Bread", 35.75)
        };
        double[] prices = {55.50, 99.35, 85.50, 35.75};

        Basket basket = new Basket(products, prices);
        File file = new File("basket.bin");

        Basket.saveBin(file);

        Basket cart = Basket.loadFromBinFile(file);
        if (cart != null) {
            System.out.println(file);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Товары для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d. %s - ₽%.2f\n", i + 1, products[i].getName(), products[i].getPrice());
            }
            System.out.println("Выберете товар и количество или введите 'end': ");
            String line = scanner.nextLine();
            if (line.equals("end")) {
                break;
            }
            String[] parts = line.split(" ");
            if (parts.length != 2) {
                System.out.println("Ввели не полные данные.");
                continue;
            }
            try {
                int productNum = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                if (productNum >= products.length || productNum < 0 || quantity <= 0) {
                    System.out.println("Не верное введенное количество");
                    continue;
                }
                basket.addToCart(productNum, quantity);
                basket.saveTxt(file);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again.");
            } catch (IOException e) {
                System.out.println("Error saving basket to file.");
            }
        }
        basket.printCart();
    }
}








