package ecommerce;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Display {
	Validation valid = new Validation();
	public HashMap<Integer,String> categoriesList()
	 {
			 HashMap<Integer,String> cat_list = new HashMap<>();
		 try
		 {
			 //HashMap<Integer,String> cat_list = new HashMap<>();
			 String str = "select * from categories";
			 ResultSet rs = Database.st.executeQuery(str);
			 while(rs.next())
			 {
				 cat_list.put(rs.getInt(1),rs.getString(2));
			 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return cat_list;
	 }
	 public void viewCategories() // all can view category
	 {
		 try
		 {
			 HashMap<Integer,String> cat_list = categoriesList();
			 System.out.println("\n_____________________________________________________\n\n\t\tAvailable Categories\n______________________________________________________\n");
			 for(Map.Entry<Integer,String> list : cat_list.entrySet())
			 {
				 System.out.println("\t\t"+list.getKey()+" - "+list.getValue());
			 }
			 System.out.println("________________________________________________________");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 public void viewProfile(String login) // user can view them profile
	 {
		 try
		 {
			 ArrayList<CustomerDetails> customer_details = new ArrayList<>();
			 String query = "select customer_id,name,login,password,email,phone,area from customers where login = '"+login+"'";
			 ResultSet rs = Database.st.executeQuery(query);
			 while(rs.next())
			 {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				login = rs.getString(3);
				String pass = rs.getString(4);
				String email = rs.getString(5);
				long phone = rs.getLong(6);
				String area = rs.getString(7);
				CustomerDetails details = new CustomerDetails(name,login,pass,email,phone,area);
				customer_details.add(details);
			 System.out.println("\t COUSTOMER ID : "+ id);
			 }
			 for(CustomerDetails detail : customer_details)
			 {

				 System.out.println(detail);
			 }

		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 public void viewProductsWithCategory(String cat) // display prtd with category
	 {
		 String query = "select product_id,product_name,brand_name from products_list where category ='"+cat+"'";
		 try(ResultSet rs = Database.st.executeQuery(query))
		 {
			 while(rs.next())
			 {
				 System.out.println("\nProduct id :\t"+rs.getInt(1)+"\t Product Name :\t"+rs.getString(2)+"\tBrand :\t"+rs.getString(3));
			 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	public void showProductWithId(String product,String brand,String cat) // display prdt with id
	{
		try
		{
			String query = "select product_id,product_name,brand_name,category from products_list where product_name ='"+product+"' and brand_name = '"+brand+"' and category = '"+cat+"'";
			ResultSet rs = Database.st.executeQuery(query);
			System.out.println("Product id \t product \t brand \t Category");
			while(rs.next())
			{
				System.out.println("\n"+rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void showCart(String login) // user can view them cart
	{
		try
		{
			int cus_id = valid.getCusId(login);
			String query = "select product_id,piece from cart where customer_id = "+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				System.out.println("\nProduct id : "+rs.getInt(1)+"\t"+"Piece Count :"+rs.getInt(2));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewProductList()
	{
		try
		{
			String query = "select Product_id,Product_name,category_id,category from products_list";
			ResultSet rs = Database.st.executeQuery(query);
			System.out.println("Product_id \t Product name \t category_id \t category\n");
			while(rs.next())
			{
				System.out.println(rs.getInt(1) +"\t"+rs.getString(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewPurchaseList() // admin can view pruchase history
	{
		try
		{
			String query = "select customer_id,product_id,piece,ordered_date,is_returned,ratings_star from shopping_history";
			ResultSet rs = Database.st.executeQuery(query);
			System.out.println("customer_id \t Product_id \t piece \t oredered_date\t returned status \t ratings\n");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getTimestamp(4)+"\t\t"+rs.getBoolean(5)+"\t"+rs.getInt(6));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewProducts() // owner,user and admin can view all products
	{
		try 
		{
			Products pros = new Products();
			System.out.println("\t\t PRODUCTS ARE DISPLAYING");
			System.out.println("press 1 for display sorting based price \t press 2 for display product name in alphabatic order");
			int opt = Ecommerce.sc.nextInt();
			if(opt == 1)
			{
				pros.displayPriceSorting();
			}
			else if(opt == 2)
			{
				pros.displayNameSorting();
			}
			else
			{
				System.out.println("Enter any valid option");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void viewMyShoppingHistory(String login) // user can view them history
	{
		try
		{
			int cus_id = valid.getCusId(login);
			String query = "select customer_id,product_id,piece,ordered_date,is_returned,ratings_star from shopping_history where customer_id ="+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
                        System.out.println("customer_id \t Product_id \t piece \t oredered_date\t returned status \t ratings\n");
                        while(rs.next())
                        {
                                System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getTimestamp(4)+"\t\t"+rs.getBoolean(5)+"\t"+rs.getInt(6));
                        }
                }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void dis()
	{
		System.out.println("Inside the display method");
	}	
}
