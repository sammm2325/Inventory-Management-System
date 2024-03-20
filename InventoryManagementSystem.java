import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryManagementSystem {
    private static final String FILE_NAME = "inventory.txt";
    private static Map<String, Item> inventory = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventoryFromFile();
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Add item\n2. Update item\n3. Delete item\n4. View inventory\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    updateItem();
                    break;
                case 3:
                    deleteItem();
                    break;
                case 4:
                    viewInventory();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        saveInventoryToFile();
    }

    private static void loadInventoryFromFile() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                inventory.put(name, new Item(name, quantity));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found. Creating a new one.");
        }
    }

    private static void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Item item : inventory.values()) {
                writer.println(item);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving inventory to file.");
        }
    }

    private static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Item item = new Item(name, quantity);
        inventory.put(name, item);
        System.out.println("Item added successfully.");
    }

    private static void updateItem() {
        System.out.print("Enter item name to update: ");
        String name = scanner.nextLine();
        Item item = inventory.get(name);
        if (item != null) {
            System.out.print("Enter new quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            item.setQuantity(quantity);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    private static void deleteItem() {
        System.out.print("Enter item name to delete: ");
        String name = scanner.nextLine();
        Item item = inventory.remove(name);
        if (item != null) {
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    private static void viewInventory() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Item> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - Quantity: " + entry.getValue().getQuantity());
        }
    }
}
