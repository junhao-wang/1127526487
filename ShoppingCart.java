import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;
public class ShoppingCart extends User{
	
	private ArrayList<String> content;
	
	public ShoppingCart(String usr,String pwd){
		super(usr,pwd);
		String fileName = "Cart_"+usr+".txt";
		File temp = new File(fileName);
		if (!temp.exists()){
			try {
				PrintStream create = new PrintStream(temp);
				create.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		fileRead(this.content,fileName);	
	}
	public ShoppingCart(String usr){
		super(usr);
		String fileName = "Cart_"+usr+".txt";
		File temp = new File(fileName);
		if (!temp.exists()){
			try {
				PrintStream create = new PrintStream(temp);
				create.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		fileRead(this.content,fileName);
	}
	
	public String[] getContent(){
		return this.content.toArray(new String[this.content.size()]);
	}
	
	public void addItem(Item itm,int quant){
		int index = indexItem(itm);
		if ( index == -1){
			String temp = "";
			temp += String.valueOf(itm.getsNo())+",";
			temp += itm.getName()+",";
			String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			temp += date+",";
			temp += String.valueOf(quant);
			this.content.add(temp);
		}else{
			String temp = this.content.get(index);
			String[] tmp = temp.split(",");
			String newDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			int newQuant = Integer.parseInt(tmp[3])+quant;
			temp = String.valueOf(itm.getsNo()) +","+itm.getName()+","+newDate+","+String.valueOf(newQuant);
			this.content.set(index, temp);
		}
		this.writeCart();
		
	}
	
	public int indexItem(Item itm){
		String[] temp = this.getContent();
		for (int i = 0;i<temp.length;i++){
			String name = temp[i].split(",")[1].trim();
			if (itm.getName() == name){
				return i;
			}
		}
		return -1;
	}
	public void clearCart(){
		this.content.clear();
		this.writeCart();
	}
	
	public void writeCart(){
		String fileName = "Cart_"+this.getName()+".txt";
		try {
			PrintStream out = new PrintStream(new File(fileName));
			for (String line: this.content){
				out.println(line);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	//=============Utility
	public static void fileRead(ArrayList<String> dest,String filename){
		try{
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()){
				dest.add(input.nextLine());
			}
			input.close();
		}catch(FileNotFoundException e){
			System.out.println("Required txt file missing: "+filename);
		}
	}
	
}
