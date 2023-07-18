import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Basket {
    private Map<Integer, Integer> cart;
    private Product[] products;

    public Basket(Product[] products, double[] prices) {
        this.products = products;
        cart = new HashMap<>();
        for (int i = 0; i < products.length; i++) {
            cart.put(i, 0);
        }
    }

    public void addToCart(int productNum, int amount) {
        cart.put(productNum, cart.get(productNum) + amount);
    }

    public void printCart() {
        double total = 0;
        int totalCount = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            int quantity = cart.get(i);
            if (quantity > 0) {
                double price = products[i].getPrice();
                System.out.printf("%s x%d: ₽%.2f\n", products[i].getName(), quantity, price * quantity);
                total += price * quantity;
                totalCount += quantity;
            }
        }
        System.out.printf("Всего штук: %d\n", totalCount);
        System.out.printf("Общая стоимость: ₽%.2f\n", total);
    }

    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < products.length; i++) {
                writer.write(String.format("%d:%d\n", i, cart.get(i)));
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile, Product[] products, double[] prices) throws IOException {
        Basket basket = new Basket(products, prices);
        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":");
                int productNum = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                basket.addToCart(productNum, quantity);
            }
        }
        return basket;
    }

    public Product[] getProducts() {
        return products;
    }

    public Map<Integer, Integer> getCart() {
        return cart;
    }
}