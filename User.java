/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: User class is used to store new users to file, and retrieve information about existing users
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class User {
	
	private String username;//the username
	private String password;// the password, if applicable
	private boolean admin;//if the user is an admin
	
	//constructor for admin user (not used)
	public User(String name,String pwd){
		//initialize variables according to passed in arguments
		this.username = name;
		this.password = pwd;
		this.admin = true;//this is an admin account
	}
	
	//constructor for regular user
	public User(String name){
		//initialize username
		this.username = name;
		this.admin = false;//not an admin
	}
	//returns the username
	public String getName(){
		return this.username;
	}
	//If the user already exists, return the username (and the password, if its an admin account)
	public static String findUser(String usrnm){
		try {
			//initialize scanner for the Users.txt file
			Scanner input = new Scanner(new File("Users.txt"));
			while(input.hasNextLine()){//while there is still lines remaining in Users.txt
				String temp = input.nextLine();//temporarily store the next line
				String test = temp.split(",")[0];//split the csv to retrieve the username
				if(test.equalsIgnoreCase(usrnm)){//if the username matches
					input.close();//close the scanner
					return temp;//return the username
				}
			}
			//if the user was not found
			input.close();//close scanner
			return "";//return empty string
		} catch (FileNotFoundException e) {//if the Users.txt file cannot be found
			System.out.println("Error: Missing File Users.txt");//prints out error
			return "";//return null string
		}
		
	}
	//updates user information in the text file
	public void writeUser(){
		//temporary array to store the information read from Users.tct
		ArrayList<String> data = new ArrayList<String>();
		try{
			//initialize scanner
			Scanner input = new Scanner(new File("Users.txt"));
			while(input.hasNextLine()){//while the text file still has a next line
				data.add(input.nextLine());//add the line as a string to the arraylist
			}
			input.close();//close the scanner as we no longer need it
			boolean found = false;//flag for the user already existing 
			//this code block was originally meant to determine whether writeuser was called to change an admin's password or create a new user
			for (int i = 0; i< data.size();i++){//iterates through the strings read from the .txt file
				if (data.get(i).split(",")[0].equalsIgnoreCase(this.username)){//if the username matches
					data.set(i, (this.username+","+this.password));//updates the line with the new user information
					found = true;//breaks the loop
				}
			}
			if (!found){//if this is a new user
				data.add(this.username);//add the user information to the end of the list
			}
			//initialize a print stream to write to the Users.txt file
			PrintStream out = new PrintStream(new File("Users.txt"));
			for (String line: data){//iterates through the strings in the modified arraylist data
				out.println(line);//writes each line to the file
			}
			out.close();//close the print stream and the file
			
		}catch (FileNotFoundException e) {//if the file is not found
			System.out.println("Error: Missing File Users.txt"); //print error
		}
	}
	
	//Originally a function to change the admin's password, never implemented
	public void changePass(Scanner input){
		//if this account is not an admin account
		if(!this.admin){
			System.out.println("This is not an admin account."); //print error
			return;
		}
		//declare string variables that will be used
		String inp,inp2;
		while(true){//keeps trying until it succeeds
			System.out.println("Please enter your current password: ");//prompt for current password
			inp = input.nextLine();//stores user input
			if(inp.equals(this.password)){//if password is correct
				while(true){//inner loop for changing password
					System.out.println("Please enter your new password: ");//prompt for new pass
					inp = input.nextLine();//stores input
					System.out.println("Please confirm your new password: ");//asks for comfirm pass
					inp2 = input.nextLine();//stores input
					if (inp.equals(inp2)){//compares the two, if they are the same
						this.password = inp;//change pass
						this.writeUser(); //updates user
						System.out.println("Password has been changed.");//success message
						break;//break loop
					}else{//if new pass and confirm  does not match
						System.out.println("Passwords do not match, please try again.");//print error
						continue;//keep trying
					}
				}
				break;//outer loop broken
				
			}else{//if old pass was not correctly entered
				System.out.println("Incorrect password entered.Please try again.");//print error
				continue;//keep trying
			}
			
		}
	}
}
