package ecommerce;

import java.util.Scanner;
import java.io.Console;
import java.sql.Connection;

class Ecommerce {
	static Scanner sc = new Scanner(System.in);
	private static Connection con = Database.getInstance().makeConnection();
	public static void main(String args[])
	{
		try 
		{

			TableCreation.tables();
			Scanner sc = new Scanner(System.in);
			Console console = System.console();
			Admin a = new Admin();
			Validation valid = new Validation();
			System.out.println("\n\t\t **** Welcome to pharm Easy ****");
			System.out.println("\n\t\t Enter your option");
			boolean flag = true;
			CustomerActivity cus_activ = new CustomerActivity();
			while(flag)
			{
				System.out.println("\n\t\t1 - Owner login \n\t\t2 - admin login \n\t\t3 - user login \n\t\t4 - Supplier login\n\t\t5 - as a visiter \n\t\t6 - exit");
				int opt = sc.nextInt();
				switch(opt)
				{
					case 1 : System.out.println("Enter login as owner:");
						 String login = console.readLine();
						 System.out.println("Enter the password");
						 String pass = console.readLine();
						 OwnerActivity activ = new OwnerActivity();
						 SuppliersActivity supact = new SuppliersActivity();
						 supact.insert10Cat();
						 if(activ.ownerLogin(login,pass)== true)
						 {
							 System.out.println("\t YOU ARE THE OWNER OH PHARM EASY");
							 activ.ownerOptions();
						 }
						 else
						 {
							 System.out.println("\t\t YOU ARE NOT THE OWNER OF PHARM EASY");
						 }
						 break;
					case 2 : System.out.println("Enter login as admin");
						 login  = console.readLine();
						 a.setLogin(login);
						 System.out.println("Enter password(in numbers)");
						 int pwd = sc.nextInt();
						 a.setPass(pwd);
						 AdminActivity admin = new AdminActivity();
						 if(valid.isValidAdmin(login,pwd) == true )
						 {
							admin.adminOptions();
						 }
						 else
						 {
							 System.out.println("\t\t YOU ARE NOT A VALID ADMIN");
						 }
						 break;
					case 3 : 
						 cus_activ.customerChoise();
						 break;
					case 4 : System.out.println("Enter login as supplier");
						 login = console.readLine();
						 System.out.println("Enter password:");
						 pass = console.readLine();
						 SuppliersActivity sup_activ = new SuppliersActivity();
						 if(valid.isValidSupplier(login,pass))
						 {
							 sup_activ.suppliersOptions();
						 }
						 else
						 {
							 System.out.println("\t\t YOU ARE NOT A VALID SUPPLIER");
						 }
						 break;
					case 5 : Visitor.visitorsView();
						 break;
					case 6 : flag = false;
						 System.out.println("\n\t\t THANK YOU FOR SHIPPING");
						 break;
					default : System.out.println("Invalid input !!!");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("\tGive a valid input");
		}
		finally
		{
			try
			{
				Database.close();
				System.out.println("Database closed");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
