package ecommerce;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Console;

class OwnerActivity {

	Console console = System.console();
	Scanner sc = new Scanner(System.in);
	Validation valid = new Validation();
	Display display = new Display();
	private static Connection con = Database.getInstance().makeConnection();

	public void insertOwner()
        {
                try
                {
                        String str = "select * from owner";
                        ResultSet rs = Database.st.executeQuery(str);
                        int count = 0;
                        while(rs.next())
                        {
                                count++;
                        }
                        if(count == 0)
                        {
                        System.out.println("\t\t you can add owner:");
                        System.out.println("Enter the name:");
                        String name = console.readLine();
                        System.out.println("Enter the login:");
                        String login = console.readLine();
                        System.out.println("Enter password:");
                        String pwd = console.readLine();
                        Owner o = new Owner(name,login,pwd);
                        o.setName(name);
                        o.setLogin(login);
                        o.setPass(pwd);
                        str = "insert into owner(name,login,pwd) values(?,?,?)";
                        PreparedStatement pst = con.prepareStatement(str);
                        pst.setString(1,o.getName());
                        pst.setString(2,o.getLogin());
                        pst.setString(3,o.getPass());
                        pst.executeUpdate();
                        System.out.println("Owner inserted");
                        }
			else
                        {
                                System.out.println("\t\t owner is already there..Owner can login");
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
        public boolean ownerLogin(String login,String pass)
        {
  //              System.out.println("inside the owner check method");
                try
                {
                        String str = "select login,pwd from owner where login = '"+login+"' and pwd = '" + pass+"'";
//                        System.out.println("The quer :"+ str);
                        ResultSet rs = Database.st.executeQuery(str);
                        while(rs.next())
                        {
                                System.out.println("true / false :"+(rs.getString(1).equals(login) && rs.getString(2).equals(pass)));
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
        public void ownerOptions()
        {
                try
                {
                        System.out.println("\t\t Enter any options");
                        boolean flag = true;
                        OwnerActivity activity = new OwnerActivity();
                        while(flag)
                        {
                                System.out.println("1 - add admin \t2 - remove admin \t3 - view catgories \t4 - view products \t5 - exit");
                                int opt = sc.nextInt();
                                switch(opt)
                                {
                                        case 1 : activity.addAdmin();
                                                 break;
                                        case 2 : activity.removeAdmin();
                                                 break;
		                        case 3 : display.viewCategories();
                                                 break;
                                        case 4 : display.viewProducts();
                                                 break;
                                        case 5 : flag = false;
                                                 break;
                                        default : System.out.println("\t Enter any valid option");
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	 public void addAdmin()
	 {
                try
                {
                        Admin a = new Admin();
                        System.out.println("Enter the name of the admin");
                        String name = console.readLine();
                        a.setName(name);
                        System.out.println("Enter the login :");
                        String login = console.readLine();
                        a.setLogin(login);
                        System.out.println("Enter the password (only numbers)");
                        int pwd = sc.nextInt();
                        a.setPass(pwd);
                        String query = "insert into admin (name,login,password) values (?,?,?)";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1,a.getName());
                        pst.setString(2,a.getLogin());
                        pst.setInt(3,a.getPass());
                        pst.executeUpdate();
			System.out.println("\t\t NEW ADMIN ADDED SUCCESSFULLY");
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
	 }
	 public void removeAdmin()
	 {
		 try
		 {
			 Admin a = new Admin();
			 System.out.println("Enter the login id to remove admin");
			 String login = console.readLine();
			 System.out.println("Enter password:");
			 int pass = sc.nextInt();
			 a.setLogin(login);
			 a.setPass(pass);
			 if(valid.isValidAdmin(login,pass) == true)
			 {
			 	String str = "delete from admin where login = '"+login+"'";
				 Database.st.executeUpdate(str);
				 System.out.println("\t\t ADMIN REMOVED SUCCESSFULLY");
			 }
			 else
			 {
				 System.out.println("\t\t NO SUCH ADMIN");
			 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}
}
