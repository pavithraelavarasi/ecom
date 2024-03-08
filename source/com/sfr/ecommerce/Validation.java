package ecommerce;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Connection;

class Validation {
		private static Connection con = Database.getInstance().makeConnection();
	public boolean isValidAdmin(String login,int pass)
	{
                String str = "select login,password from admin where login = '"+login+"' and password = "+pass;
                try(ResultSet rs = Database.st.executeQuery(str))
                {
                        while(rs.next())
                        {
                                if(rs.getString(1).equals(login)&& rs.getInt(2)== pass)
                                {
                                        return true;
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
             return false;
        }
	public CustomerDetails isValidCustomer(String login,String pass)
        {
                String str = "select login,password from customers where login ='"+login+"' and password = '"+pass+"'";
                try(ResultSet rs = Database.st.executeQuery(str))
                {
                        while(rs.next())
                        {
				login = rs.getString(1);
				pass = rs.getString(2);
				return new CustomerDetails(login,pass);
			}
		}
                catch(Exception e)
                {
                        e.printStackTrace();
                }
		return null;
        }
	public boolean isAlreadyCustomer(String login,String pass)
	{
		String str = "select login,password from customers where login ='"+login+"' and password = '"+pass+"'";
                try(ResultSet rs = Database.st.executeQuery(str))
                {
                        while(rs.next())
                        {
			       if(rs.getString(1).equals(login) && rs.getString(2).equals(pass))
                                {
                                        return true;
                                }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isValidSupplier(String login,String pass)
	{
		String query = "select login,password from suppliers where login = '"+login+"' and password = '"+pass+"'";
		try(Statement s = con.createStatement();ResultSet rs = s.executeQuery(query))
		{
			while(rs.next())
			{
				if(rs.getString(1).equals(login) && rs.getString(2).equals(pass))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isValidPhoneNumber(long phone)
	{
		String s = String.valueOf(phone);
		if(s.length() == 10)
		{
			return true;
		}
		return false;
	}
	/*	String pattern = "\\d{10}";
		String digits = phone.replaceAll("[^0-9]"," ");
		return digits.matches(pattern);
	}*/
	public boolean isAlreadySupplier(long phone)
	{
		try
		{
			String str = "select supplier_phone from suppliers where supplier_phone = "+phone;
			ResultSet rs = Database.st.executeQuery(str);
			while(rs.next()) {
			if(rs.getLong(1) == phone)
			{
				return true;
			}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isPresentCategory(String cat)
	{
		String str = "select category_name from categories where category_name = '"+cat+"'";
		try(ResultSet rs = Database.st.executeQuery(str))
		{
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(cat))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean is10Category()
	{
		try
		{
			String str = "select * from categories";
			ResultSet rs = Database.st.executeQuery(str);
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			if(count == 10)
			{
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isStockAvailable(int product_id,int stock)
	{
		try
		{
			String str = "select available_stock from products_list where product_id ="+product_id;
			ResultSet rs = Database.st.executeQuery(str);
			while(rs.next())
			{
				if((rs.getInt(1) == stock) || (rs.getInt(1) > stock))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
        boolean isProductAvailable(String cat,String product,String brand)
        {
                try
                {
                        String str = "select product_name,brand_name from products_list where product_name = '"+product+"' and brand_name = '"+brand+"'";
                        ResultSet rs = Database.st.executeQuery(str);
                        while(rs.next())
                        {
                                if(rs.getString(1).equalsIgnoreCase(product) && rs.getString(2).equalsIgnoreCase(brand))
                                {
                                        return true;
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
                return false;
        }
	public void stockDecrease(int id,int piece)
	{
		String query = "select available_stock from products_list where product_id = "+id;
		try(ResultSet rs = Database.st.executeQuery(query))
		{
			Statement state = con.createStatement();
			while(rs.next())
			{
				int stock = rs.getInt(1);
				stock = stock - piece;
				query = "update products_list set available_stock = "+stock +" where product_id = "+id;
				state.executeUpdate(query);
				System.out.println("------Stcak decreased-----");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public Date getToday()
	{
		return new Date();
	}
	public int getCusId(String login)
	{
		String query = "select customer_id from customers where login = '"+login+"'";
		try(ResultSet rs = Database.st.executeQuery(query))
		{
			rs.next();
			int id = rs.getInt(1);
			return id;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	public boolean isSufficientPiece(String login,int product_id,int order)
	{
		try
		{
			int cus_id = getCusId(login); 
			String query = "select piece from cart where product_id = "+product_id +" and customer_id = "+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if((rs.getInt(1) == order) || (rs.getInt(1) > order))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isEnoughPieceReturn(String login,int product_id,int piece)
	{
		try
		{
			int cus_id = getCusId(login);
			String query = "select piece from shopping_history where customer_id = "+cus_id +" and product_id ="+product_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if((rs.getInt(1) == piece) || (rs.getInt(1) > piece))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public void stockDecreaseInCart(String login,int product_id,int piece)
	{
		int cus_id = getCusId(login);
		String query = "select piece from cart where customer_id = "+cus_id+" and product_id = "+ product_id;
		try(ResultSet rs = Database.st.executeQuery(query))
		{
			while(rs.next())
			{
				int stock = rs.getInt(1);
				stock = stock - piece;
				query = "update cart set piece = "+stock+" where customer_id ="+cus_id+" and product_id ="+product_id;
				Database.st.executeUpdate(query);
				System.out.println("---stock decrese from cart");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean isPresentProductId(int product_id,String cat)
	{
		String query = "select product_id from products_list where product_id = "+product_id+" and category = '"+cat+"'";
		try(ResultSet rs = Database.st.executeQuery(query))
		{
			while(rs.next())
			{
				if((rs.getInt(1) == product_id) && (rs.getString(2).equals(cat)))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isPurchasedId(String login,int product_id)
	{
		try
		{
			int cus_id = getCusId(login);
			String query = "select product_id from shopping_history where customer_id = "+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getInt(1) == product_id)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isPresentIdReturn(String login,int product_id)
	{
		try
		{
			int cus_id = getCusId(login);
			String query = "select product_id from returned_products where customer_id = "+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getInt(1) == product_id)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isPresentIdInProducts(int product_id)
	{
		try
		{
			String query = "select product_id from products_list where product_id = "+product_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getInt(1) == product_id)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isSameProductBrand(String product,String brand)
	{
		try
		{
			String query = "select Product_name,brand_name from products_list where product_name = '"+product+"' and brand_name = '"+brand+"'";
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase(product) && rs.getString(2).equalsIgnoreCase(brand))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public void increaseStock(String product,String brand)
	{
		try
                {
                        String query = "select Product_name,brand_name,available_stock from products_list where product_name = '"+product+"' and brand_name = '"+brand+"'";
                        ResultSet rs = Database.st.executeQuery(query);
                        while(rs.next())
                        {
				int stock = rs.getInt(3);
				System.out.println("Enter the stock count to increase");
				int increase = Ecommerce.sc.nextInt();
				stock = stock+increase;
				query = "update products_list set available_Stock = "+stock+" where product_name = '"+product+"' and brand_name = '"+brand+"'";
				Database.st.executeUpdate(query);
				System.out.println("Stock increased");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean isCrtCategoryId(int id,String cat)
	{
		try 
		{
			String query = "select cat_id from categories where category_name = '"+cat+"'";
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getInt(1) == id)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean isProductIdPresentCart(String login,int product_id)
	{
		try
		{
			int cus_id = getCusId(login);
			String query = "Select product_id from cart where customer_id = "+cus_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				if(rs.getInt(1) == product_id)
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
