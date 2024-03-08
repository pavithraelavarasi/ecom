package ecommerce;

import java.util.Scanner;
import java.io.Console;
import java.sql.PreparedStatement;

class Admin {
	private String name;
	private String login;
	private int pwd;
	
	Console console = System.console();
	Admin(String name,String login,int pwd)
	{
		this.name = name;
		this.login = login;
		this.pwd = pwd;
	}
	Admin()
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
	public void setPass(int pwd)
	{
		this.pwd = pwd;
	}
	public int getPass()
	{
		return pwd;
	}

	public String toString()
	{
		return "Admin : "+ name;
	}
}
