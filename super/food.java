import java.util.*;
import java.sql.*;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemTotal() {
        return menuItem.getPrice() * quantity;
    }
}

class Order {
    private List<OrderItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public double getTotal() {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.getItemTotal();
        }
        return total;
    }

    public void displayOrder() {
        System.out.println("Order Details:");
        for (OrderItem item : orderItems) {
            System.out.println(item.getQuantity() + " x " + item.getMenuItem().getName() + " - $" + item.getItemTotal());
        }
        System.out.println("Total: $" + getTotal());
    }
}

class FoodOrderSystem {
    private List<MenuItem> menu;
    private Order currentOrder;

    public FoodOrderSystem() {
        menu = new ArrayList<>();
        currentOrder = new Order();
        initializeMenu();
    }

    private void initializeMenu() {
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/super", "root","yash0409");
        String sql = "SELECT name, price FROM menu";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            menu.add(new MenuItem(name, price));
        }
        resultSet.close();
        statement.close();
        connection.close();
    } 
catch (Exception e) {
        e.printStackTrace();
    }
}

    

    public void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }

    public void placeOrder() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            System.out.print("Enter the menu item number (0 to finish ordering): ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > menu.size()) {
                System.out.println("Invalid menu item number. Please try again.");
                continue;
            }

            MenuItem selectedItem = menu.get(choice - 1);
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Invalid quantity. Please try again.");
                continue;
            }

            OrderItem orderItem = new OrderItem(selectedItem, quantity);
            currentOrder.addItem(orderItem);
        }

        System.out.println("Order placed successfully!");
        currentOrder.displayOrder();
    }
}

public class food {
    public static void main(String[] args) {
        FoodOrderSystem foodOrderSystem = new FoodOrderSystem();
        foodOrderSystem.placeOrder();
    }
}