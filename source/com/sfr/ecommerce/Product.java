package ecommerce;
import java.util.Comparator;
import java.util.TreeSet;
import java.sql.ResultSet;

class Product {
	private String product,brand,category;
	private int product_id,price;

	Product(int product_id,String product,String brand,String category,int price)
	{
		this.product_id = product_id;
		this.product = product;
		this.brand = brand;
		this.category = category;
		this.price = price;
	}
	Product(int product_id,String product,int price)
	{
		this.product_id = product_id;
		this.product = product;
		this.price = price;
	}
	public void setProduct_id(int product_id)
	{
		this.product_id = product_id;
	}
	public int getProduct_id()
	{
		return product_id;
	}
	public void setProduct(String product)
	{
		this.product = product;
	}
	public String getProduct()
	{
		return product;
	}
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	public String getBrand()
	{
		return brand;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getCategory()
	{
		return category;
	}
	public void SetPrice(int price)
	{
		this.price = price;
	}
	public int getPrice()
	{
		return price;
	}
/*	public String toString()
	{
		return product_id+"\t : "+product+"\t : "+brand+"\t : "+category+"\t : "+price;
	}*/
	public String toString()
	{
		return "Product id :"+ product_id+"\t Product :"+product+"\t\t Price :"+ price;
	}
}

class PriceComparator implements Comparator<Product> {
	public int compare(Product p1,Product p2)
	{
		return p1.getPrice() - p2.getPrice();
	}
}

class Products {
	public void displayPriceSorting()throws Exception
	{
		TreeSet<Product> products = new TreeSet<>(new PriceComparator());
		String query = "select product_id,product_name,price_₹ from products_list";
		ResultSet rs = Database.st.executeQuery(query);
		while(rs.next())
		{
			int id = rs.getInt(1);
			String name = rs.getString(2);
			int price = rs.getInt(3);
			Product product = new Product(id,name,price);
			products.add(product);
		}
		System.out.println("\t\t Sorting based on products price\n-----------------------------------------------------------");
		for(Product pro : products)
		{
			System.out.println(pro);
		}
	}
	public void displayNameSorting()throws Exception
	{
		String query = "select product_id,product_name from products_list order by products_list.product_name";
		ResultSet rs = Database.st.executeQuery(query);
		System.out.println("\t\tSorting based on product name\n------------------------------------------------------------");
		while(rs.next())
		{
			System.out.println("Prduct_id :"+rs.getInt(1) +"\tProduct :"+ rs.getString(2));
		}
	}
}

