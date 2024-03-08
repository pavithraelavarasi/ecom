package ecommerce;

import java.sql.Connection;
import java.io.Console;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

class CustomerActivity extends Customer {

	Validation valid = new Validation();
	Console console = System.console();
	Display display = new Display();
	CustomerDetails cus_detail = new CustomerDetails();
	private static Connection con = Database.getInstance().makeConnection();
	public void customerChoise()
        {
                try
                {
			CustomerActivity customer = new CustomerActivity();
                        System.out.println("1 - login \t 2 - sign_up");
                        int opt = Ecommerce.sc.nextInt();
                        if(opt == 1)
                        {
                                System.out.println("Enter your login as customer");
                                String login = console.readLine();
				cus_detail.setLogin(login);
				System.out.println("Enter your password");
				String pass = console.readLine();
				cus_detail.setPassword(pass);
                             /*   if(valid.isValidCustomer(login,pass))
				{
					System.out.println("\t\t YOU ARE VALID CUSTOMER");
					customer.customerOptions(login);
				}*/
				cus_detail = valid.isValidCustomer(login,pass);
				if(cus_detail != null)
				{
					System.out.println("\t\t YOU AE A VALID CUSTOMER");
					customer.customerOptions(login);
				}
				else
				{
					System.out.println("\t\t YOU ARE NOT A VALID CUSTOMER ..SIGN UP TO CONTINUE");
					return;
				}
                        }
                        else if(opt == 2)
                        {
                               if(customer.customerSignUp()) {
				       System.out.println("Please re-enter your login to view your options");
			       	       String login = console.readLine();
				       customer.customerOptions(login);
			       }
                        }
                        else
                        {
                                System.out.println("Enter any valid option (1 or 2)");
                        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void customerOptions(String login)
	{
		try {
			CustomerActivity customer = new CustomerActivity();
                        System.out.println("\t\t CUSTOMER OPTIONS");
			boolean flag = true;
                        while(flag)
                        {
				Display display = new Display();
                                System.out.println("\n\t\t1 - view your profile \n\t\t2 - buy Products (order now) \n\t\t3 - Add to cart \n\t\t4 - buy products from cart\n\t\t5 - return product \n\t\t6 - view categories \n\t\t7 - view Products \n\t\t8 - view cart \n\t\t9 - view your shopping history \n\t\t10 - exit");
                                int opt = Ecommerce.sc.nextInt();
                                switch(opt)
                                {
                                        case 1 : display.viewProfile(login);
                                                 break;
                                        case 2 : customer.buyProduct(login);
                                                 break;
                                        case 3 : customer.addToCart(login);
                                                 break;
					case 4 : customer.buyFromCart(login);
						 break;
                                        case 5 : customer.returnProduct(login);
                                                 break;
                                        case 6 : display.viewCategories();
                                                 break;
                                        case 7 : display.viewProducts();
                                                 break;
                                        case 8 : display.showCart(login);
                                                 break;
					case 9 : display.viewMyShoppingHistory(login);
						 break;
                                        case 10 : flag = false;
						 System.out.println("--Back to main menu");
                                                 break;
                                        default : System.out.println("Enter any valid options");
                                }
                        }

                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
        boolean customerSignUp()
        {
                try
                {
                        System.out.println("Enter your name:");
                        String name = console.readLine();
                        System.out.println("Enter your email id:");
                        String email = console.readLine();
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        System.out.println("Enter your password:");
                        String password = console.readLine();
			if(valid.isAlreadyCustomer(login,password)) {
				System.out.println("\t\t The customer login is already existing....");
				return false;
			}
                        System.out.println("Enter your phone number:");
                        long phone = Ecommerce.sc.nextLong();
			while(!valid.isValidPhoneNumber(phone))
			{
				System.out.println("Enter again...(with 10 digits)");
				phone = Ecommerce.sc.nextLong();
			}
                        System.out.println("Enter your address");
                        String area = console.readLine();
                        CustomerDetails customer = new CustomerDetails(name,email,login,password,phone,area);
                        String query = "insert into customers (name,email,login,password,phone,area) values (?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,customer.getName());
                        pst.setString(2,customer.getEmail());
                        pst.setString(3,customer.getLogin());
                        pst.setString(4,customer.getPassword());
                        pst.setLong(5,customer.getPhone());
                        pst.setString(6,customer.getArea());
                        pst.executeUpdate();
                        System.out.println("\t\t CUSTOMER SIGN UP SUCCESSFULLY");
			return true;
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
		return false;
	}
	public void customerLogin(String login,String pass)
	{
		try
		{
			CustomerActivity customer = new CustomerActivity();
	/*		if(valid.isValidCustomer(login,pass))
			{
				customer.customerOptions(login);
			}*/
			cus_detail = valid.isValidCustomer(login,pass);
                        if(cus_detail != null)
                        {
                                customer.customerOptions(login);
                        }
			else
			{
				System.out.println("\t\t YOU ARE NOT THE VALID CUSTOMER");
				System.out.println("\t You can sign up to continue \n press 1 to sign up\n press 2 to exit");
				int opt = Ecommerce.sc.nextInt();
				if(opt == 1)
				{
					customer.customerSignUp();
				}
				else
				{
					System.out.println("\t Website closed");
					System.exit(0);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void buyProduct(String login)
	{
		try
		{
			CustomerActivity customer = new CustomerActivity();
			display.viewCategories();
			System.out.println("Enter the category of the product you want to order");
			String category = console.readLine();
			category = category.toLowerCase();
			System.out.println("category :"+category);
			if(!valid.isPresentCategory(category)) 
			{
				System.out.println("\t\t THE CATEGORY IS UNAVAILABLE");
				return;
			}
			display.viewProductsWithCategory(category);
			System.out.println("Enter the product id ");
			int pro_id = Ecommerce.sc.nextInt();
			if(!valid.isPresentProductId(pro_id,category)) 
			{
				System.out.println("\t\t UNABLE TO FIND PRODUCT ID ");
				return;
			}
			System.out.println("Enter number of pieces you want :");
			int piece = Ecommerce.sc.nextInt();
			String query = "select product_id from products_list";
			if(!valid.isStockAvailable(pro_id,piece))
			{
				System.out.println("\t\t OOPPSSSS....INSUFFICIENT AMOUNT OF STOCK ");
				return;
			}
			System.out.println("\t\t YOU CAN ORDER THIS PRODUCT");
			query = "select price_₹ from products_list where product_id = "+pro_id;
			ResultSet rs = Database.st.executeQuery(query);
			rs.next();
			System.out.println("Product price --  " + rs.getInt(1));
			System.out.println("\t\t Please give ratings to this product (Maximum 5 stars)");
			int ratings = Ecommerce.sc.nextInt();
			System.out.println("\t\t YOU CAN PAY AND BUY THIS PRODUCT");
			customer.buyingOptions(login,pro_id,piece,ratings);
			if(ratings > 5)
			{
				System.out.println(" maximum 5 starts allowed ..pls give again");
				ratings = Ecommerce.sc.nextInt();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void buyingOptions(String login,int pro_id,int piece,int ratings)throws Exception
	{
		CustomerActivity customer = new CustomerActivity();
	      	boolean flag = true;
	    	while(flag)
     	   	{
                	System.out.println("\t\t You can by this prodcut by online payment...\nYour options -- \n\t\t1 - G Pay \t\t2 - Debit Card \t\t3 - Cash on delivery \\t\t4 - exit(back to main)");
	                int opt = Ecommerce.sc.nextInt();
	                switch(opt)
        	        {
                	        case 1 : System.out.println("\t\t GETTING AMOUNT BY G PAY");
                        	         valid.stockDecrease(pro_id,piece);
                                	 customer.insertShoppingHistory(login,pro_id,piece,ratings);
					// customer.deliveryDateUpdated(login,pro_id);
	                                 flag = false;	
        	                         break;
                	        case 2 : System.out.println("\t\t GETTING AMOUNT BY DEBIT CARD");
                        	         valid.stockDecrease(pro_id,piece);
                                	 customer.insertShoppingHistory(login,pro_id,piece,ratings);
					 //customer.deliveryDateUpdated(login,pro_id);
	                                 flag = false;
        	                         break;
                	        case 3 : System.out.println("\t\t YOU ARE SELECT CASH ON DELIVERY");
                        	         valid.stockDecrease(pro_id,piece);
                                	 customer.insertShoppingHistory(login,pro_id,piece,ratings);
					 //customer.deliveryDateUpdated(login,pro_id);
	                                 flag = false;
        	                         break;
                	        case 4 : flag = false;
                        	         break;
			}

                }
        }

	public void insertShoppingHistory(String login,int product_id,int piece,int ratings)
	{
		CustomerActivity customer = new CustomerActivity();
		try
		{
			int customer_id = valid.getCusId(login);
			Timestamp order_date = new Timestamp(valid.getToday().getTime());
			String query = "insert into shopping_history(customer_id,product_id,piece,ordered_date,ratings_star) values (?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,customer_id);
			pst.setInt(2,product_id);
			pst.setInt(3,piece);
			pst.setTimestamp(4,order_date);
			pst.setInt(5,ratings);
			pst.executeUpdate();
			System.out.println("history inserted");
		}
		catch(Exception e){
			e.printStackTrace();
		}
         }
       	public void addToCart(String login)
   	{
		CustomerActivity customer = new CustomerActivity();
		try
		{
			System.out.println("Enter the category of the product you want to order");
	                String category = console.readLine();
			category = category.toLowerCase();
			if(!valid.isPresentCategory(category)) 
			{
				System.out.println("\t\t THE CATEGORY IS UNAVAILABLE");
			}
			display.viewProductsWithCategory(category);
	                System.out.println("Enter the product id ");
               		int pro_id = Ecommerce.sc.nextInt();
			if(!valid.isPresentProductId(pro_id,category)) 
			{
				System.out.println("\t\t UNABLE TO FIND PRODUCT ID");
				return;
			}
		        System.out.println("Enter number of pieces you want :");
              		int piece = Ecommerce.sc.nextInt();
	                String query = "select product_id from products_list";
			if(!valid.isStockAvailable(pro_id,piece))
			{
				System.out.println("\t\t OOPPPSSS....INSUFFICIENT PRODUCT COUNT");
				return;
			}
			System.out.println("\t\t YOU CAN CART THIS PRODUCT");
			customer.insertToCart(login,pro_id,piece);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void insertToCart(String login,int product_id,int piece)
	{
		try
		{
			int id = valid.getCusId(login);
			String query = "insert into cart (customer_id,product_id,piece) values(?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,id);
			pst.setInt(2,product_id);
			pst.setInt(3,piece);
			pst.executeUpdate();
			System.out.println("\t\t cart inserted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void buyFromCart(String login)
	{
		try {
		CustomerActivity customer = new CustomerActivity();
		display.showCart(login);
		System.out.println("Enter the product id ");
		int pro_id = Ecommerce.sc.nextInt();
		if(!valid.isProductIdPresentCart(login,pro_id))
		{
			System.out.println("\t\t The given product id not present in your cart");
			return;
		}
		System.out.println("Enter number of pieces you want :");
		int piece = Ecommerce.sc.nextInt();
		if(!valid.isSufficientPiece(login,pro_id,piece))
		{
			System.out.println("\t\t NOT ENOUGH AMOUNT OF PRODUCTS AVAILABLE");
			return;
		}
		String query = "select product_id from products_list";
		if(!valid.isStockAvailable(pro_id,piece))
		{
			System.out.println("\t\t INSUFFICIENT PRODUCT STOCK");
			return;
		}
		System.out.println("\t\t YOU CAN ORDER THIS PRODUCT");
		query = "select price_₹ from products_list where product_id = "+pro_id;
		ResultSet rs = Database.st.executeQuery(query);
		rs.next();
		System.out.println("Product price --  " + rs.getInt(1));
		System.out.println("\t\t Please give ratings to this product (Maximum 5 stars)");
		int ratings = Ecommerce.sc.nextInt();
		if(ratings > 5)
		{
			System.out.println(" maximum 5 starts allowed ..pls give again");
			ratings = Ecommerce.sc.nextInt();
		}
		System.out.println("\t\t YOU CAN PAY AND BUY THIS PRODUCT");
		customer.buyingOptions(login,pro_id,piece,ratings);
		valid.stockDecreaseInCart(login,pro_id,piece);
//		customer.deliveryDateUpdated(login,pro_id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void returnProduct(String login)
	{
		try
		{
			CustomerActivity customer = new CustomerActivity();
			System.out.println("Enter the product id to return:");
			int pro_id = Ecommerce.sc.nextInt();
			if(!valid.isPurchasedId(login,pro_id)) 
			{
				System.out.println("\t\t YOU DOESN'T PURCHASE THE GIVEN PRODUCT ID");
				return;
			}
			int cus_id = valid.getCusId(login);
			String query = "select ordered_date from shopping_history where product_id ="+pro_id +" and customer_id = "+cus_id;
			System.out.println("The date query :" + query);
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				Timestamp order_date = rs.getTimestamp(1);
				Timestamp today = new Timestamp(valid.getToday().getTime());
				long btw_days = ((today.getTime() - order_date.getTime())/(1000*60*60*24));
				System.out.println("The between days : "+ btw_days);
				if(btw_days > 3)
				{
					System.out.println("\t\t OOppps .. you can't return this product..it's already 3 days completed");
				}
				else
				{
					System.out.println("\t\t You can return this product");
					System.out.println("Enter the piece count to return:");
					int piece = Ecommerce.sc.nextInt();
					if(!valid.isEnoughPieceReturn(login,pro_id,piece)) 
					{
						System.out.println("\t\t Not enough amount of piece is there");
						return;
					}
					query = "insert into returned_products (customer_id,product_id,piece,returned_date) values(?,?,?,?)";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setInt(1,cus_id);
					pst.setInt(2,pro_id);
					pst.setInt(3,piece);
					pst.setTimestamp(4,today);
					pst.executeUpdate();
					System.out.println("\t\t Returned table inserted");
					customer.returnStatus(login,pro_id);
					return;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void returnStatus(String login,int product_id)
	{
		try
		{
			int cus_id = valid.getCusId(login);
			String query = "update shopping_history set is_returned = 'true' where customer_id ="+cus_id +" and product_id = "+product_id;
			Database.st.executeUpdate(query);
			System.out.println("\t\t Return status change in history");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
/*	public void deliveryDateUpdated(String login,int product_id)
	{
		try
		{
			System.out.println("--inside the dellivery date method");
			int cus_id = valid.getCusId(login);
			String query = "select ordered_date from shopping_history where customer_id = "+cus_id+" and product_id ="+product_id;
			ResultSet rs = Database.st.executeQuery(query);
			while(rs.next())
			{
				Timestamp order_date = rs.getTimestamp(1);
				Timestamp today = new Timestamp(valid.getToday().getTime());
                                long btw_days = ((today.getTime() - order_date.getTime())/(1000*60*60*24));
                                System.out.println("The between days : "+ btw_days);
				if(btw_days == 3)
				{
					query = "update shopping_history set delivery_date = '"+today+"' where customer_id = "+cus_id+ " and product_id = "+product_id;
					Database.st.executeUpdate(query);
					System.out.println("\t\t delivery date updated");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
}
