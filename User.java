import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class User {
	private String username;
	private String password;
	private boolean admin;
	
	public User(String name,String pwd){
		this.username = name;
		this.password = pwd;
		this.admin = true;
	}
	
	public User(String name){
		this.username = name;
		this.admin = false;
	}
	
	public String getName(){
		return this.username;
	}
	
	public static String findUser(String usrnm){
		try {
			Scanner input = new Scanner(new File("Users.txt"));
			while(input.hasNextLine()){
				String temp = input.nextLine();
				String test = temp.split(",")[0];
				if(test.equals(usrnm)){
					input.close();
					return temp;
				}
			}
			input.close();
			return "";
		} catch (FileNotFoundException e) {
			System.out.println("Error: Missing File Users.txt");
			return "";
		}
		
	}
	//updates user information in the text file
	public void writeUser(){
		System.out.println("writing user");
		ArrayList<String> data = new ArrayList<String>();
		try{
			Scanner input = new Scanner(new File("Users.txt"));
			while(input.hasNextLine()){
				data.add(input.nextLine());
			}
			input.close();
			boolean found = false;
			for (int i = 0; i< data.size();i++){
				if (data.get(i).split(",")[0].equals(this.username)){
					data.set(i, (this.username+","+this.password));
					found = true;
				}
			}
			if (!found){
				data.add(this.username);
			}
			PrintStream out = new PrintStream(new File("Users.txt"));
			for (String line: data){
				out.println(line);
			}
			out.close();
			
		}catch (FileNotFoundException e) {
			System.out.println("Error: Missing File Users.txt");
		}
	}
	
	public void changePass(){
		if(!this.admin){
			System.out.println("This is not an admin account.");
			return;
		}
		Scanner input = new Scanner(System.in);
		String inp,inp2;
		while(true){
			System.out.println("Please enter your current password: ");
			inp = input.nextLine();
			if(inp.equals(this.password)){
				while(true){
					System.out.println("Please enter your new password: ");
					inp = input.nextLine();
					System.out.println("Please confirm your new password: ");
					inp2 = input.nextLine();
					if (inp.equals(inp2)){
						this.password = inp;
						System.out.println("Password has been changed.");
						break;
					}else{
						System.out.println("Passwords do not match, please try again.");
						continue;
					}
				}
				break;
				
			}else{
				System.out.println("Incorrect password entered.Please try again.");
				continue;
			}
			
		}
		input.close();
	}
}
