import java.sql.*;
import java.util.*;
class list{
public static void main(String args[]){

try{
Scanner sc=new Scanner(System.in);
System.out.print("These are available in our supermarket\n");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/super","root","yash0409");
Statement st=c.createStatement();
String ab="select * from products";
ResultSet r=st.executeQuery(ab);
while(r.next()){
System.out.print("ID: "+r.getInt("Product_ID")+" ");
System.out.print("|NAME: "+r.getString("Name_of_product")+"\n");
}

while(true){
System.out.print("Enter the id of the product to check its price and quantity available(press 0 to exit): ");
int a=sc.nextInt();
if(a<5 && a>0){
String d="select Name_of_product,Qunatity_Available,Price_Of_Product from products where  Product_ID=?";
PreparedStatement ps=c.prepareStatement(d);
ps.setInt(1,a);
ResultSet rs=ps.executeQuery();
while(rs.next()){
System.out.print("NAME: "+rs.getString("Name_of_product")+" ");
System.out.print("|Quantity Availabe: "+rs.getInt("Qunatity_Available")+" ");
System.out.print("|Cost: "+rs.getFloat("Price_Of_Product")+"\n");
break;
}

}
else if(a==0){
break;
}
else{
System.out.println("enter correct id:");
continue;
}
}
}
catch(Exception e){
e.printStackTrace();
}
}
}
