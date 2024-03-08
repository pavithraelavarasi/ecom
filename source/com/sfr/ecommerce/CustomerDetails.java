package ecommerce;

class CustomerDetails {
	private String name,email,login_id,password,area;
	private long phone_num;
	CustomerDetails(String name,String email,String login_id,String password,long phone_num,String area)
	{
		this.name = name;
		this.email = email;
		this.login_id = login_id;
		this.password = password;
		this.phone_num = phone_num;
		this.area = area;
	}
	CustomerDetails(String login_id,String password)
	{
		this.login_id = login_id;
		this.password = password;
	}
	CustomerDetails()
	{
	}
	public String getName()
	{
		return name;
	}
	public String getEmail()
	{
		return email;
	}
	public String getLogin()
	{
		return login_id;
	}
	public String getPassword()
	{
		return password;
	}
	public long getPhone()
	{
		return phone_num;
	}
	public String getArea()
	{
		return area;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setLogin(String login_id)
	{
		this.login_id = login_id;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setPhone(long phone_num)
	{
		this.phone_num = phone_num;
	}
	public void setArea(String area)
	{
		this.area = area;
	}
	public String toString()
	{
		return "Name : "+ name +"\nLogin :"+login_id+"\nPassword :"+password+"\nEmail_id :"+email+"\nPhone Number :"+phone_num+"\nAddress :"+area;
	}
}
