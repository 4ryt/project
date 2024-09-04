import java.sql.*;
import java.lang.*;
import java.util.*;
class Customer{
public static void main(String args[]){
Scanner sc=new Scanner(System.in);
String user=sc.next();
String pass=sc.next();
try{
Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/super",user,pass);
System.out.println("Enter customer id");
int id=sc.nextInt();
System.out.println("Enter customer name");
String name=sc.next();
System.out.println("Enter customer phone no");
int phone=sc.nextInt();
System.out.println("Enter customer bill");
double bill=sc.nextDouble();

String q="insert into customer values(?,?,?,?)";
PreparedStatement ps=co.prepareStatement(q);
ps.setInt(1,id);
ps.setString(2,name);
ps.setInt(3,phone);
ps.setDouble(4,bill);
ps.execute();
co.close();
}
catch(Exception e){
e.printStackTrace();
}
}
}
