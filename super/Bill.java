import java.sql.*;
import java.util.*;
class ProductPurchase{
String name;
float cost;
int quantity;
double item;
public ProductPurchase(String name,float cost,int quantity,double item){
this.name=name;
this.cost=cost;
this.quantity=quantity;
this.item=item;
}
}
class Bill extends List1{
try{
Scanner sc=new Scanner(System.in);
double bill=0.0;
List<ProductPurchase> purchasedProducts = new ArrayList<>();
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/super","root","yash0409");
String d="select Name_of_product,Price_Of_Product,Qunatity_Available from products where Product_ID=?";
while(true){
System.out.println("Enter the product id to purchase the item(enter 0 to exit)");
int a=sc.nextInt();

if(a==0){
System.out.println("thank u for shopping");
break;
}
else{
PreparedStatement ps=c.prepareStatement(d);
ps.setInt(1,a);
ResultSet rs=ps.executeQuery();
if(rs.next()){
String name=rs.getString("Name_of_product");
Float cost=rs.getFloat("Price_Of_Product");
int availableQuantity = rs.getInt("Qunatity_Available");
System.out.print("name: "+name);
System.out.print(" cost: "+cost+"\n");
if(availableQuantity<=0){
System.out.println("Product not available at present");
continue;
}
System.out.println("Enter the Quantity:");
int quantity=sc.nextInt();
if(availableQuantity<quantity){
System.out.println("Quantity not avaialable please enter correct number");
continue;
}
double item=cost*quantity;
System.out.println("cost of the item is:"+item);
bill+=item;
ProductPurchase purchase = new ProductPurchase(name, cost, quantity, item);                        purchasedProducts.add(purchase);
int remainingQuantity = availableQuantity - quantity;
String updateQuery = "UPDATE products SET Qunatity_Available=? WHERE Product_ID=?";
PreparedStatement up = c.prepareStatement(updateQuery);
up.setInt(1, remainingQuantity);
up.setInt(2, a);
up.executeUpdate();

}
else{
System.out.println("product not found");
}
}
}
System.out.println("Products Purchased:");
for (ProductPurchase purchase : purchasedProducts) {
System.out.println(purchase.name + " | Quantity: " + purchase.quantity + " | Cost: " + purchase.item);
}
double gst=bill*0.2;
double discount=bill*0.3;
double total=bill+gst-discount;
System.out.println("the bill without discount is: "+bill);
System.out.println("the total bill with discount is :"+total);
}
catch(Exception e){
e.printStackTrace();

}
}