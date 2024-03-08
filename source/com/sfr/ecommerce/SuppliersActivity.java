package ecommerce;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.Console;
import java.sql.Connection;

class SuppliersActivity {

	Console console = System.console();
	Validation valid = new Validation();
	Display display = new Display();
	private static Connection con = Database.getInstance().makeConnection();
	public void suppliersOptions()
	{
		SuppliersActivity sup_activ = new SuppliersActivity();
		AdminActivity aa = new AdminActivity();
		try
		{
			System.out.println("\t\t Enter any option");
			boolean flag = true;
			while(flag)
			{
				System.out.println("\n1 - add product \t2 - view Products \t3 - exit");
				int opt = Ecommerce.sc.nextInt();
				switch(opt)
				{
					case 1 : sup_activ.addProducts();
						 break;
					case 2 : display.viewProducts();
						 break;
					case 3 : flag = false;
						 System.out.println("---Back to main menu");
						 break;
					default : System.out.println("Enter valid option");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insert10Cat()
	{
		try
		{
			String query = "insert into categories (cat_id,category_name) values(1,'Elderly Care'),(2,'Personal Care'),(3,'Skin Care'),(4,'Baby Care'),(5,'Ayurvedic Care'),(6,'Diabetic Care'),(7,'Health Care'),(8,'Fitness Supplements'),(9,'Energy Supplements'),(10,'Mother Care');";
			Database.st.executeUpdate(query);
			System.out.println("10 Categories are inserted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insertCategories(String cat,int cat_id)
	{
		try
		{
			String query = " insert into categories values("+cat_id+",'"+cat+"')";
			Database.st.executeUpdate(query);
			System.out.println("Category inserted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void addProducts()
	{
		try
		{
			Display display = new Display();
			display.viewCategories();
			System.out.println("Enter the category from table");
			String cat = console.readLine();
			System.out.println("Enter category id:");
			int cat_id = Ecommerce.sc.nextInt();
			/*if(!valid.isCrtCategoryId(cat_id,cat))
			{
				System.out.println("\t\t pls enter crt category id to add product");
				return;
			}*/
			SuppliersActivity supplier = new SuppliersActivity();
			if(!valid.isPresentCategory(cat))
				if(!valid.is10Category())
					supplier.insertCategories(cat,cat_id);
				else{
					System.out.println("\t\t THERE IS ALREADY 10 CATEGORIES \n PLEASE INSERT THE CATEGORY ALREADY THERE IN THE TABLE");
					return;
				}
			if(!valid.isCrtCategoryId(cat_id,cat))
                        {
                                System.out.println("\t\t pls enter crt category id to add product");
                                return;
			}
			if(!valid.isPresentCategory(cat)) {
				System.out.println("\t\t THE CATEGORY NOT AVAILABLE");
				return;
			}
			System.out.println("Enter the brand name");
			String brand = console.readLine();
			System.out.println("Enter the product Name");
			String product = console.readLine();
			if(valid.isSameProductBrand(product,brand))
			{
				System.out.println("\t\t SAME PRODUCT WITH SAME BRANS NAME IS ALREADY THERE YOU CAN INCREASE THE STOCK");
				valid.increaseStock(product,brand);
				return;
			}
			System.out.println("Enter the price of the product");
			int price = Ecommerce.sc.nextInt();
			System.out.println("Enter the stock to the product:");
			int stock = Ecommerce.sc.nextInt();
			String query = "insert into products_list (product_name,brand_name,category_id,category,price_₹,available_stock) values(?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,product);
			pst.setString(2,brand);
			pst.setInt(3,cat_id);
			pst.setString(4,cat);
			pst.setInt(5,price);
			pst.setInt(6,stock);
			pst.executeUpdate();
			System.out.println("\t\tProduct inserted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
