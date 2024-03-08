package ecommerce;

class Supplier {
	private String supplierName,login,password;
	private String supplierLocation;
	private long supplierPhone;

	public Supplier(String supplierName,String login,String password,String supplierLocation,long supplierPhone)
	{
		this.supplierName = supplierName;
		this.login = login;
		this.password = password;
		this.supplierLocation = supplierLocation;
		this.supplierPhone = supplierPhone;
	}
	Supplier()
	{
	}
	public void setName(String supplierName)
	{
		this.supplierName = supplierName;
	}
	public String getName()
	{
		return supplierName;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getLogin()
	{
		return login;
	}
	public void setPass(String password)
	{
		this.password = password;
	}
	public String getPass()
	{
		return password;
	}
	public void setLocation(String supplierLocation)
	{
		this.supplierLocation = supplierLocation;
	}
	public String getLocation()
	{
		return supplierLocation;
	}
	public void setPhone(long supplierPhone)
	{
		this.supplierPhone = supplierPhone;
	}
	public long getPhone()
	{
		return supplierPhone;
	}
}
