package ecommerce;

import java.util.Scanner;
import java.io.Console;

class Visitor {

	public static void visitorsView()
	{
		Scanner sc = new Scanner(System.in);
		Display display = new Display();

		try
		{
			System.out.println("\t\t YOU ENTER AS A VISITOR \n\t\t YOU CAN SEE OUR PRODUCTS AND THEIR PRICES");
			boolean flag = true;
			while(flag)
			{
				System.out.println("1 - view vategories \t2 - view Products \t3 - exit");
				System.out.println("\t\t IF YOU WANT TO ORDER ANY PRODUCT YOU SHOULD SIGN UP");
				int opt = sc.nextInt();
				if(opt == 1)
					display.viewCategories();
				else if(opt == 2)
					display.viewProducts();
				else if(opt == 3){
					flag = false;
				break;
				}
				else
					System.out.println("Enter any valid options between 1 to 3");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
