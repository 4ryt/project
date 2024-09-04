import java.sql.*;
import java.util.*;

class Bill1 {
    class ProductPurchase {
        String name;
        float cost;
        int quantity;
        double itemTotal;

        public ProductPurchase(String name, float cost, int quantity, double itemTotal) {
            this.name = name;
            this.cost = cost;
            this.quantity = quantity;
            this.itemTotal = itemTotal;
        }
    }

    public void Bill() {
        try {
            Scanner sc = new Scanner(System.in);
            double bill = 0.0;
            List<ProductPurchase> purchasedProducts = new ArrayList<>(); // To store purchased products
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/super", "root", "yash0409");
            String d = "SELECT Name_of_product, Price_Of_Product FROM products WHERE Product_ID=?";

            while (true) {
                System.out.println("Enter the product id to purchase the item (Enter 0 to finish shopping or -1 to remove a product):");
                int a = sc.nextInt();

                if (a == 0) {
                    System.out.println("Thank you for shopping!");
                    break;
                } else if (a == -1) {
                    System.out.println("Enter the product id to remove from the bill:");
                    int removeId = sc.nextInt();
                    // Add code to remove the product from the bill if present
                    boolean found = false;
                    Iterator<ProductPurchase> iterator = purchasedProducts.iterator();
                    while (iterator.hasNext()) {
                        ProductPurchase purchase = iterator.next();
                        if (purchase.Product_ID == removeId) {
                            bill -= purchase.itemTotal;
                            iterator.remove();
                            found = true;
                            System.out.println("Product removed from the bill.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Product not found in the bill.");
                    }
                } else {
                    PreparedStatement ps = c.prepareStatement(d);
                    ps.setInt(1, a);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String name = rs.getString("Name_of_product");
                        float cost = rs.getFloat("Price_Of_Product");
                        System.out.print("Name: " + name);
                        System.out.print(" Cost: " + cost + "\n");
                        System.out.println("Enter the Quantity:");
                        int quantity = sc.nextInt();
                        double item = cost * quantity;
                        System.out.println("Cost of the item is: " + item);
                        bill += item;

                        // Store the purchased product details in the list
                        ProductPurchase purchase = new ProductPurchase(name, cost, quantity, item);
                        purchasedProducts.add(purchase);
                    } else {
                        System.out.println("Product not found");
                    }
                }
            }

            double gst = bill * 0.2;
            double discount = bill * 0.5;
            double total = bill + gst - discount;
            System.out.println("The bill without discount is: " + bill);
            System.out.println("The total bill with discount is: " + total);

            // Display the purchased products
            System.out.println("Products Purchased:");
            for (ProductPurchase purchase : purchasedProducts) {
                System.out.println(purchase.name + " - Quantity: " + purchase.quantity + " - Cost: " + purchase.itemTotal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Bill1 s = new Bill1();
        s.Bill();
    }
}
