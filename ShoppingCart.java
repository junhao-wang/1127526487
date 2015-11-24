/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description:
*/

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
public class ShoppingCart extends User{
	
	private ArrayList<String> content;
	//constructor for admins (never used)
	public ShoppingCart(String usr,String pwd){
		super(usr,pwd);//calls constructor for user with username and password
		String fileName = "Cart_"+usr+".txt";//computes the name of the shopping cart file
		File temp = new File(fileName);//creates a file object
		if (!temp.exists()){//if the file doesnt exist already
			try {
				PrintStream create = new PrintStream(temp);//create the file with a printstream
				create.close();//close the printstream
			} catch (FileNotFoundException e) {//this block should not be reached
				e.printStackTrace();
			}
		}
		this.content = new ArrayList<String>();//initialize content
		fileRead(this.content,fileName);//read the content of the shopping cart file into the content array	
	}
	//constructor for regular users
	public ShoppingCart(String usr){
		super(usr);//calls the constructor in User with just the username
		String fileName = "Cart_"+usr+".txt";//computes the shopping cart filename
		File temp = new File(fileName);//creates an object for the file
		if (!temp.exists()){//check if the file actually exists, if not
			try {
				PrintStream create = new PrintStream(temp);//initialize a print stream to make the file
				create.close();//close the print stream
			} catch (FileNotFoundException e) {//this block should not be reached since the file does not exist before creation and thus no access errors should be raised
				e.printStackTrace();
			}
		}
		this.content = new ArrayList<String>();//initialize content
		//at this point the shopping cart file must exist by now, if it wasnt before, the last code block created it
		fileRead(this.content,fileName);//reads the shopping cart file to the content arraylist
	}
	
	//returns the static array representation of the contents of the shopping cart
	public String[] getContent(){
		if (this.content.size() == 0){//safety for empty arrays
			String[] s = {};//return empty string array
			return s;
		}
		return this.content.toArray(new String[this.content.size()]);//converts the content arraylist to a string[] array
	}
	
	//adds an item to the cart, the corresponding deduction from the store is handled by the UserInterface class, here
	//we are just concerned with add the item to the cart.
	public void addItem(Item itm,int quant){
		int index = indexItem(itm);//tries to see if some of the items are already in the cart
		if ( index == -1){//item is not in the cart
			//formats a string to represent the item in the format of the shopping cart txt file
			String temp = "";//start with empty string
			temp += String.valueOf(itm.getsNo())+",";// temp = "sNo,"
			temp += itm.getName()+",";//temp = "sNo,nameOfItem,"
			String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			temp += date+",";//temp = "sNo,nameOfItem,DD/MM/YYYY,"
			temp += String.valueOf(quant);//temp = "sNo,nameOfItem,DD/MM/YYYY,quantity"
			this.content.add(temp);//addds the string to the array list
		}else{//if some of the item is already in the cart
			String temp = this.content.get(index);//find the corresponding string
			String[] tmp = temp.split(",");//split that string into fields
			String newDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());//set the date to the current time
			int newQuant = Integer.parseInt(tmp[3])+quant;//adds the quantity to the pre-existing quantity
			temp = String.valueOf(itm.getsNo()) +","+itm.getName()+","+newDate+","+String.valueOf(newQuant);//create the updated string
			this.content.set(index, temp);//replace the old string at index with the new string
		}
		this.writeCart();//writes the changes to the shopping cart to the file
		
	}
	//tries to find an item in the shopping cart
	//if found, returns its location (index in the array)
	//if not found, return a -1
	public int indexItem(Item itm){
		String[] temp = this.getContent();//dumps the content of the cart into a temporary string array
		for (int i = 0;i<temp.length;i++){//iterates through the strings in the temp array
			String name = temp[i].split(",")[1].trim();//extract the name of the item from the string
			if (itm.getName().equals(name)){//if the extracted name matches the name of the item we are looking for
				return i;//return the index of the item in the shopping cart
			}
		}
		return -1;//if we cannot find the item in the shopping cart, return -1
	}
	//this function simply clears the cart
	public void clearCart(){
		this.content.clear();//removes everything from the content array list
		this.writeCart();//writes the changes to the txt file
	}
	//This function writes the shopping cart in its current state to the corresponding txt file
	public void writeCart(){
		String fileName = "Cart_"+this.getName()+".txt";//computes the correct filename for the shopping cart of the user
		try {
			//initializes a printstream to write to this file
			PrintStream out = new PrintStream(new File(fileName));
			for (String line: this.content){//iterates through item(represneted as a string) in the content arraylist
				out.println(line);//writes the item to the shopping cart file
			}
			out.close();//closes the print stream
		} catch (FileNotFoundException e) {//this code block should not be reached unless an access error occurs
		//i.e. insufficient permissions
			e.printStackTrace();
		}
		
	}
	//=============Utility
	//this function reads lines from a given file (denoted by a filename) , and dumps each line
	//as an individual string to the given arraylist
	public static void fileRead(ArrayList<String> dest,String filename){
		try{
			//initializes a scanner to scan through the file
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()){//while the file still has at least 1 remaining line
				dest.add(input.nextLine());//reads the next line and adds it as a string to the arraylist
			}
			input.close();//close the scanner
		}catch(FileNotFoundException e){//if the file cannot be found
			System.out.println("Required txt file missing: "+filename);//print a missing file error
		}
	}
	
}
