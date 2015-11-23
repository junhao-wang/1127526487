import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class UserInterface {
	private ArrayList<Readable> readables;
	private ArrayList<Audio> audioProducts;
	private int currentPage;
	private ShoppingCart activeUser;
	Scanner usrIn;
	//User Input Scanner

	
	public UserInterface(){
		this.usrIn = new Scanner(System.in);
		this.readables = new ArrayList<Readable>();
		this.audioProducts = new ArrayList<Audio>();
		this.init();
	}
	public void init(){
		this.getReadables();
		this.getAudioProducts();
		this.changeCurrentPage(1);
	}
	public void getCurrentPage(){
		String inp;
		int choice;
		
		if(this.currentPage == 1){
			int[] op = {1,2};
			choice = getInt(op,this.usrIn);
			
			if (choice == 1){
				System.out.println("Please enter your username: ");
				inp = this.usrIn.nextLine();
				String usr = User.findUser(inp);
				if (usr.equals("")){
					this.changeCurrentPage(4);
				}else if(usr.split(",").length>1){
					System.out.println("Please enter your password: ");
					inp = this.usrIn.nextLine();
					if (!inp.equals(usr.split(",")[1])){
						System.out.println("Invalid password. Access Denied \n Press Enter to Continue");
						this.usrIn.nextLine(); //
						this.changeCurrentPage(4);
					}else{
						this.activeUser = new ShoppingCart(usr.split(",")[0],usr.split(",")[1]);
						this.changeCurrentPage(3);
					}
				}else{
					this.activeUser = new ShoppingCart(usr.split(",")[0]);
					this.changeCurrentPage(3);
				}
			}else{
				this.changeCurrentPage(2);
			}
			
		}else if(this.currentPage == 2){
			inp = this.usrIn.nextLine();
			while(!User.findUser(inp).equals("")){
				System.out.println("That username is already taken. Please try again,");
				System.out.println("Choose your username:");
				inp = this.usrIn.nextLine();
			}
			this.activeUser = new ShoppingCart(inp);
			this.activeUser.writeUser();
			System.out.println("Username successfully added. Press Enter to Continue.");
			this.usrIn.nextLine();
			this.changeCurrentPage(5);
			
		}else if(this.currentPage == 3){
			usrIn.nextLine();
			this.changeCurrentPage(5);	
		}else if(this.currentPage == 4){
			usrIn.nextLine();
			this.changeCurrentPage(1);	
		}else if(this.currentPage == 5){
			int[] op = {1,2,3};
			choice = getInt(op,this.usrIn);
			if(choice == 1){
				this.changeCurrentPage(6);
			}else if(choice == 2){
				this.changeCurrentPage(7);
			}else {
				this.changeCurrentPage(1);
			}
		}else if(this.currentPage == 6){
			int[] op = {1,2,-1};
			choice = getInt(op,this.usrIn);
			if(choice == 1){
				this.changeCurrentPage(8);
			}else if(choice == 2){
				this.changeCurrentPage(9);
			}else{
				this.changeCurrentPage(5);
			}
		}else if(this.currentPage == 7){
			usrIn.nextLine();
			this.changeCurrentPage(5);
			
		}else if(this.currentPage == 8){
			ArrayList<Integer> optemp = new ArrayList<Integer>();
			for (Readable r: this.readables){
				optemp.add(r.sNo);
				
			}
			int[] op = new int[optemp.size()+1];
			op[optemp.size()] = -1;
			for(int i = 0;i<optemp.size();i++){
				op[i] = optemp.get(i);
			}
			choice = getInt(op,this.usrIn);
			if(choice == -1){
				this.changeCurrentPage(6);
			}else{
				Readable temp = this.findReadable(choice);
				if (temp.getQuantity() == 0){
					System.out.println("Item out of Stock.Please Try a different item.");
					this.changeCurrentPage(8);
				}else{
					int q = getQuant(temp.getQuantity(),this.usrIn);
					temp.subQuant(q);
					this.writeReadable();
					this.activeUser.addItem(temp, q);
					System.out.println(String.valueOf(q)+" "+temp.getName()+" successfully added to your cart.");
					System.out.println("Enter -2 to continue shopping or 0 to checkout.");
					int[] op2 = {-2,0};
					choice = getInt(op2,this.usrIn);
					if (choice == -2){
						this.changeCurrentPage(6);
					}else{
						this.changeCurrentPage(10);
					}
				}
			}
			
		}else if(this.currentPage == 9){
			ArrayList<Integer> optemp = new ArrayList<Integer>();
			for (Audio r: this.audioProducts){
				optemp.add(r.getsNo());
			}
			int[] op = new int[optemp.size()+1];
			op[optemp.size()] = -1;
			for(int i = 0;i<optemp.size();i++){
				op[i] = optemp.get(i);
			}
			choice = getInt(op,this.usrIn);
			if(choice == -1){
				this.changeCurrentPage(6);
			}else{
				Audio temp = this.findAudio(choice);
				if (temp.getQuantity() == 0){
					System.out.println("Item out of Stock.Please Try a different item.");
					this.changeCurrentPage(8);
				}else{
					int q = getQuant(temp.getQuantity(),this.usrIn);
					temp.subQuant(q);
					this.writeAudio();
					this.activeUser.addItem(temp, q);
					System.out.println(String.valueOf(q)+" "+temp.getName()+" successfully added to your cart.");
					System.out.println("Enter -2 to continue shopping or 0 to checkout.");
					int[] op2 = {-2,0};
					choice = getInt(op2,this.usrIn);
					if (choice == -2){
						this.changeCurrentPage(6);
					}else{
						this.changeCurrentPage(10);
					}
				}
			}
		}else if(this.currentPage == 10){
			boolean buy = yesNo("Would you like to buy? [Yes/No]: ",this.usrIn);
			if (buy){
				int s;
				try{
					Scanner temp = new Scanner(new File("cnum.txt"));
					s = temp.nextInt();
					temp.close();
					PrintStream out = new PrintStream(new File("cnum.txt"));
					out.println(String.valueOf(s+1));
					out.close();
				}catch(Exception e){
					s = 100;
					try {
						PrintStream out = new PrintStream(new File("cnum.txt"));
						out.println(String.valueOf(s+1));
						out.close();
					} catch (FileNotFoundException e1) {
					}
					
				}
				System.out.println("Confirmation ID: U"+String.valueOf(s));
				System.out.println("Items shipped to: "+this.activeUser.getName());
			}else{
				boolean clear = yesNo("Would you like to return the items in your shopping cart? [Yes/No]: ",this.usrIn);
				if(clear){
					for(String line: this.activeUser.getContent()){
						String[] temp = line.split(",");
						this.retItm(temp[1].trim(), Integer.parseInt(temp[3].trim()));
					}
					this.writeReadable();
					this.writeAudio();
					this.activeUser.clearCart();
					System.out.println("Cart Returned! Have a nice day.");
					System.exit(0);
				}else{
					System.out.println("Have a nice day.");
					System.exit(0);
				}
			}
		}else{
			System.out.println("ERROR: Invalid Page Number");
		}	

	}
	
	public void changeCurrentPage(int i){
		this.currentPage = i;
		clearPage();
		if(this.currentPage == 1){
			System.out.println("1.Sign in");//these are separate print statements for better code readability
			System.out.println("2.Sign up");
			System.out.println();
			System.out.println("Choose your option:");
			
		}else if(this.currentPage == 2){
			System.out.println("Choose your username:");
		}else if(this.currentPage == 3){
			System.out.println("Welcome "+activeUser.getName());
			System.out.println("Press Enter to Continue");			
		}else if(this.currentPage == 4){
			System.out.println("No Access");
			System.out.println("Press Enter to Return to Previous Menu");
		}else if(this.currentPage == 5){
			System.out.println("1.View Items by Category");//these are separate print statements for better code readability
			System.out.println("2.View Shopping Cart");
			System.out.println("3.Sign out");
			System.out.println();
			System.out.println("Choose your option:");
		}else if(this.currentPage == 6){
			System.out.println("1.Readables");//these are separate print statements for better code readability
			System.out.println("2.Audio");
			System.out.println();
			System.out.println("Choose your option:");
			System.out.println();
			System.out.println("Enter -1 to return to previous menu");
		}else if(this.currentPage == 7){
			for (String s:activeUser.getContent()){
				System.out.println(s);
			}
			System.out.println();
			System.out.println("Enter -1 to return to previous menu");
		}else if(this.currentPage == 8){
			System.out.println("Readables: ");
			System.out.println();
			this.showReadables();
			System.out.println("Choose your option:");
			System.out.println("Enter -1 to return to previous menu");
		}else if(this.currentPage == 9){
			System.out.println("Audio: ");
			System.out.println();
			this.showAudioProducts();
			System.out.println("Choose your option:");
			System.out.println("Enter -1 to return to previous menu");
		}else if(this.currentPage == 10){
			System.out.println("Billing Information: ");
			int total = 0;
			int envTax = 0;
			int tax = 0;
			int shipping = 0;
			String out = String.format("%-25s%-25s%-25s\n", "Name","Quantity","Price");
			System.out.println(out);
			String[] rawData = this.activeUser.getContent();
			for(String line: rawData){
				String name = line.split(",")[1].trim();
				int quant = Integer.parseInt(line.split(",")[3].trim());
				int[] info = this.getPriceInfo(name);
				out = String.format("%-25s%-25s%-25s\n", name,String.valueOf(quant),String.valueOf(info[0]));
				System.out.println(out);
				total += info[0];
				envTax += info[1];
				shipping += info[2];
				tax += (info[0]*13)/100;	
			}
			total += envTax+shipping+tax;
			out = String.format("%-30s%-10s%-40s\n", "Environment Tax","2%",String.valueOf(envTax));
			System.out.println(out);
			out = String.format("%-30s%-10s%-40s\n", "HST","13%",String.valueOf(tax));
			System.out.println(out);
			out = String.format("%-30s%-10s%-40s\n", "Shipping","10%",String.valueOf(shipping));
			System.out.println(out);
			out = String.format("%-40s%-40s\n", " ","------------");
			System.out.println(out);
			out = String.format("%-40s%-40s\n", "Total%",String.valueOf(total));
			System.out.println(out);
			
			
			
			
		}else{
			System.out.println("ERROR IN NAVIGATION: PAGE NOT FOUND");
		}
		
		this.getCurrentPage();
	}
	
	public void getReadables(){
		parseObjects(this.readables, "Books.txt");
		parseObjects(this.readables, "eBooks.txt");
	}
	
	public void getAudioProducts(){
		parseObjects(this.audioProducts, "CDs.txt");
		parseObjects(this.audioProducts, "MP3.txt");
	}
	public void showReadables(){
		System.out.printf("%-7s%-30s%-14s%-8s%-20s%-10s\n","S.No","Name of the Book","Author","Price($)","Quantity in Store","Type");
		for (Readable itm: this.readables){
			String s = String.format("%-7s%-30s%-14s%-8s%-20s%-10s\n", String.valueOf(itm.getsNo()),itm.getName(),itm.getAuthor(),String.valueOf(itm.getPrice()),String.valueOf(itm.getQuantity()),itm.getType());
			System.out.println(s);
		}
		System.out.println();
	}
	public void showAudioProducts(){
		System.out.printf("%-7s%-30s%-14s%-8s%-20s%-10s\n","S.No","Name ","Artist","Price($)","Quantity in Store","Type");
		for (Audio itm: this.audioProducts){
			String s = String.format("%-7s%-30s%-14s%-8s%-20s%-10s\n", String.valueOf(itm.getsNo()),itm.getName(),itm.getArtist(),String.valueOf(itm.getPrice()),String.valueOf(itm.getQuantity()),itm.getType());
			System.out.println(s);
		}
		System.out.println();
	}
	public Audio findAudio(int sNo){
		for (Audio a:this.audioProducts){
			if (a.getsNo() == sNo){
				return a;
			}
		}
		return null;
	}
	public Readable findReadable(int sNo){
		for (Readable r:this.readables){
			if (r.getsNo() == sNo){
				return r;
			}
		}
		return null;
	}
	public void writeReadable(){
		try {
			PrintStream bk = new PrintStream(new File("Books.txt"));
			PrintStream ebk = new PrintStream(new File("eBooks.txt"));
			for (Readable r:this.readables){
				if (r.getType().equals("Book")){
					bk.println(r.getInfo());
				}else{
					ebk.println(r.getInfo());
				}
			}
			bk.close();
			ebk.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void writeAudio(){
		try {
			PrintStream cd = new PrintStream(new File("CDs.txt"));
			PrintStream mp3 = new PrintStream(new File("MP3.txt"));
			for (Audio a:this.audioProducts){
				if (a.getType().equals("CD")){
					cd.println(a.getInfo());
				}else{
					mp3.println(a.getInfo());
				}
			}
			cd.close();
			mp3.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//looks up price information from the readables and audio arrays as shopping cart
	//does not store this information.
	//The first element is the price, the second is the environmental fee(2% , rounded down)
	//and the third element is the shipping(10%, rounded down)
	public int[] getPriceInfo(String name){
		int[] results = {0,0,0};
		for (Readable r: this.readables){
			if (r.getName().equals(name)){
				results[0] = r.getPrice();
				if (r.getType().equals("Book")){
					results[1] = results[0]/50;
					results[2] = results[0]/10;
				}
			}
		}
		for (Audio a: this.audioProducts){
			if (a.getName().equals(name)){
				results[0] = a.getPrice();
				if (a.getType().equals("CD")){
					results[1] = results[0]/50;
					results[2] = results[0]/10;
				}
			}
		}
		return results;
	}
	
	public void retItm(String name,int quant){
		for (Readable r: this.readables){
			if (r.getName().equals(name)){
				r.subQuant(quant*-1);
			}
		}
		for (Audio a: this.audioProducts){
			if (a.getName().equals(name)){
				a.subQuant(quant*-1);
			}
		}
	}
	//============================UTILITY======================================
	public static boolean yesNo(String prompt,Scanner usrIn){
;
		while (true){
			System.out.println(prompt);
			String s = usrIn.nextLine();
			if (s.equalsIgnoreCase("yes")){
				return true;
			}else if (s.equalsIgnoreCase("no")){
				return false;
			}else{
				System.out.println("Invalid input, try again.");
				continue;
			}
		}
	}
	public static int getQuant(int max,Scanner usrIn){

		while (true){
			try{
				int temp = Integer.parseInt(usrIn.nextLine());
				if(temp <= max && temp >0){

					return temp;
				}
				System.out.println("Above max or negative/zero quantity, please try again.");
				System.out.println("Enter Quantity:");
				
			}catch(Exception e){
				System.out.println("Invalid input, please try again.");
				System.out.println("Enter Quantity:");
				continue;
			}
		}
	}
	
	public static int getInt(int[] options,Scanner usrIn){
		while (true){
			try{
				int temp = Integer.parseInt(usrIn.nextLine());
				for(int i:options){
					if (i==temp){
						return temp;
					}
				}
				System.out.println("That is not one of the options,try again.");
				System.out.println("Choose your option:");
				
			}catch(Exception e){
				System.out.println("Invalid input, please try again.");
				System.out.println("Choose your option:");
				continue;
			}
		}
		
	}
	
	
	
	public static void parseObjects(ArrayList dest, String filename){
		ArrayList<String> src = new ArrayList<String>();
		fileRead(src,filename);	
		
		for(String line: src){
			String[] dat = line.split(",");
			int sNo = Integer.parseInt(dat[0].trim());
			String name = dat[1].trim();
			String field3 = dat[2].trim();
			int price = Integer.parseInt(dat[3].trim());
			int quant = Integer.parseInt(dat[4].trim());
			if(filename.equals("Books.txt")){
				Book temp = new Book(sNo,name,field3,price,quant);
				dest.add(temp);
			}else if(filename.equals("eBooks.txt")){
				eBook temp = new eBook(sNo,name,field3,price,quant);
				dest.add(temp);
			}else if(filename.equals("CDs.txt")){
				CD temp = new CD(sNo,name,field3,price,quant);
				dest.add(temp);
			}else if(filename.equals("MP3.txt")){
				MP3 temp = new MP3(sNo,name,field3,price,quant);
				dest.add(temp);
			}
		}
	}
	
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
	
	public static void clearPage(){
		for (int i = 1; i<= 30;i++){
			System.out.println();
		}
	}
}

