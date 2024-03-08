package ecommerce;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

class TableCreation {
	
	static void tables()
	{
		try
		{
			createOwner();
			createAdmin();
			createSupplier();
			createCustomers();
			createCategory();
			createAllProducts();
			createCart();
			createPurchase();
			createShoppingHistory();
			createReturned();
			OwnerActivity o = new OwnerActivity();
			o.insertOwner();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static void createOwner()
	{
		try
		{
			String str = "create table if not exists owner(owner_id int unique,name varchar(20),login varchar(20) unique,pwd varchar(20))";
			Database.st.executeUpdate(str);
			System.out.println("Owner table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static void createAdmin()throws Exception
	{
		try 
		{
			String query = "create table if not exists admin(admin_id serial primary key,name varchar(20),login varchar(20) unique,password int not null)";
			Database.st.executeUpdate(query);
			System.out.println("Admin table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			System.out.println("Admin table not created :"+e.getMessage());
		}
	}
	static void createSupplier()
	{
		try
		{
			String query = "create table if not exists suppliers(supplier_id serial primary key,supplier_name text,login text unique,password text,supplier_location text,supplier_phone bigint)";
			Database.st.executeUpdate(query);
			System.out.println("Supplier table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static void createCustomers()
	{
		try
		{
			String str = "create table if not exists customers(customer_id serial primary key,name varchar(20) unique not null,login varchar(20) not null,password varchar(20),email text,phone bigint,area text)";
			Database.st.executeUpdate(str);
			System.out.println("user table created");
		}
		catch(Exception e)
		{
			System.out.println("user table not created :"+e.getMessage());
		}
	}
	static void createCategory()
	{
		try
		{
			String str = "create table if not exists categories(cat_id int unique,category_name text unique)";
			Database.st.executeUpdate(str);
			System.out.println("Category table created");
		}
		catch(Exception e)
		{
			System.out.println("Category table not created :"+e.getMessage());
		}
	}
	static void createAllProducts()
	{
		try
		{
			String str = "create table if not exists products_list(product_id serial primary key,product_name text,brand_name text,category_id int,category text,price_₹ int,available_stock int)";
			Database.st.executeUpdate(str);
			System.out.println("All products table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static void createCart()
	{
		try
		{
			String str = "create table if not exists cart(customer_id int,constraint fk_users_cart foreign key(customer_id) references customers(customer_id) on delete cascade on update cascade,product_id int,constraint fk_allPro_cart foreign key(product_id) references products_list (product_id) on delete cascade on update cascade,piece int)";
			Database.st.executeUpdate(str);
			System.out.println("Cart table created");
		}
		catch(Exception e)
		{
			System.out.println("Cart table not created :"+e.getMessage());
		}
	}
	static void createPurchase()
	{
		try
		{
			String str = "create table if not exists purchase(customer_id int, constraint fk_us_purchase foreign key(customer_id) references customers(customer_id) on delete cascade on update cascade,product_id int,constraint fk_single_purchase foreign key(product_id) references products_list (product_id) on delete cascade on update cascade,oredered_date timestamp not null,piece_count int,received_date timestamp not null)";
			Database.st.executeUpdate(str);
		}
		catch(Exception e)
		{
			System.out.println("Purchase table not created :"+e.getMessage());
		}
	}
	static void createShoppingHistory()
	{
		try
		{
			String str = "create table if not exists shopping_history(customer_id int,constraint fk_usr_his foreign key(customer_id) references customers(customer_id) on delete cascade on update cascade,product_id int ,constraint fk_product_history foreign key(product_id) references products_list(product_id) on delete cascade on update cascade,piece int,ordered_date timestamp,delivery_date timestamp,is_returned boolean default 'false',ratings_star int)";
			Database.st.executeUpdate(str);
		}
		catch(Exception e)
		{
			System.out.println("shopping history table not created :"+e.getMessage());
		}
	}
	static void createReturned()
	{
		try
		{
			String query = "create table if not exists returned_products(customer_id int,constraint fk_usr_his foreign key(customer_id) references customers(customer_id) on delete cascade on update cascade,product_id int ,constraint fk_product_history foreign key(product_id) references products_list(product_id) on delete cascade on update cascade,piece int,returned_date timestamp)";
			Database.st.executeUpdate(query);
			System.out.println("Returned table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
