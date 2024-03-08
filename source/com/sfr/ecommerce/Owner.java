package ecommerce;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.Console;
import java.sql.PreparedStatement;

class Owner {
	private String name;
	private String login;
	private String pwd;
	
	Console console = System.console();
	Scanner sc = new Scanner(System.in);

	Owner(String name,String login,String pwd)
	{
		this.name = name;
		this.login = login;
		this.pwd = pwd;
	}
	Owner()
	{
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getLogin()
	{
		return login;
	}
	public void setPass(String pwd)
	{
		this.pwd = pwd;
	}
	public String getPass()
	{
		return pwd;
	}
}
