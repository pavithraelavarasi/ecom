package ecommerce;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.Console;

class AdminActivity {

	Console console = System.console();
	Validation valid = new Validation();
	Display display = new Display();
	Connection con = null;
	public void adminOptions()
        {
                System.out.println("\t\tEnter any options");
                boolean flag = true;
                AdminActivity adminActiv = new AdminActivity();
                OwnerActivity ownerActiv = new OwnerActivity();
                while(flag)
                {
                        System.out.println("\n1 - add/remove suppliers \t2 - view purchase list \t3 - view Products list \t4 - exit");
                        int opt = Ecommerce.sc.nextInt();
                        switch(opt)
                        {
                                case 1 : System.out.println("press 1 to add supplier \t press 2 to remove suppliers");
                                         opt = Ecommerce.sc.nextInt();
                                         if(opt == 1)
                                         {
                                                 adminActiv.addSupplier();
                                         }
                                         else if(opt == 2)
                                         {
                                                adminActiv.removeSupplier();
                                         }
                                         else
                                         {
                                                 System.out.println("Enter 1 or 2 to add / remove");
                                         }
                                         break;
                                case 2 : display.viewPurchaseList();
                                         break;
                                case 3 : display.viewProducts();
                                         break;
                                case 4 : flag = false;
                                         System.out.println("--Back to main menu");
                                         break;
                                default : System.out.println("Enter any valid option");
                        }
                }
	}
	public void removeProduct(String login)
	{
		try 
		{
			System.out.println("Enter the category to remove the product");
			String cat = Ecommerce.sc.nextLine();
			if(valid.isPresentCategory(cat))
			{
				display.viewProductsWithCategory(cat);
				System.out.println("Enter the product id to remove:");
				int product_id = Ecommerce.sc.nextInt();
				if(valid.isPresentIdInProducts(product_id)) {
				String str = "delete from products_list where product_id = "+product_id;
				Database.st.executeUpdate(str);
				System.out.println("\t\t PARTICULAR PRODUCT REMOVED ");
				}
				else
				{
					System.out.println("\t\t PRODUCT ID NOT AVAILABLE");
				}
			}
			else
			{
				System.out.println("\t\t OOppss..CATEGORY");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void addSupplier()
	{
		try
		{
			con = Database.getInstance().makeConnection();
			Supplier supplier = new Supplier();
			System.out.println("Enter the name of the supplier:");
			String name = console.readLine();
			supplier.setName(name);
			System.out.println("Enter the login:");
			String login = console.readLine();
			supplier.setLogin(login);
			System.out.println("Enter password:");
			String pass = console.readLine();
			supplier.setPass(pass);
			System.out.println("Enter the location of the supplier:");
			String location = console.readLine();
			supplier.setLocation(location);
			System.out.println("Enter the phone number of the supplier:");
			long phone = Ecommerce.sc.nextLong();
			while(!valid.isValidPhoneNumber(phone))
			{
				System.out.println("Enter again..(with 10 digits)");
				phone = Ecommerce.sc.nextLong();
			}
			supplier.setPhone(phone);
			if(valid.isAlreadySupplier(phone))
			{
				System.out.println("\t\tTHE SUPPLIER IS ALREADY PRESENT");
				return;
			}
			String query = "insert into suppliers(supplier_name,login,password,supplier_location,supplier_phone) values (?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,supplier.getName());
			pst.setString(2,supplier.getLogin());
			pst.setString(3,supplier.getPass());
			pst.setString(4,supplier.getLocation());
			pst.setLong(5,supplier.getPhone());
			pst.executeUpdate();
			System.out.println("\t\t SUPPLIER ADDED");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void removeSupplier()
	{
		try
		{
			System.out.println("Enter the supplier name to remove:");
			String name = console.readLine();
			getSupplierList(name);
			System.out.println("Enter the id to remove the supplier:");
			int supplier_id = Ecommerce.sc.nextInt();
			String query = "Delete from suppliers where supplier_id = "+supplier_id;
			Database.st.executeUpdate(query);
			System.out.println("\t\t SUPPLIER REMOVED");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void getSupplierList(String name)
	{
		String str = "select * from suppliers where supplier_name = '"+name+"'";
		try(ResultSet rs = Database.st.executeQuery(str))
		{
			System.out.println("\t\t Suppliers list");
			while(rs.next())
			{
				System.out.println(rs.getInt(1) +" - "+rs.getString(2)+"\t - "+rs.getString(3)+" - " +rs.getLong(4));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
