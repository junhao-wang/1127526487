
/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: UserInterface class is used control the flow of the program from one page to another, and print the approriate contents of each page.
*/

import java.io.*;
import java.util.*;

public class UserInterface {
	private ArrayList<Readable> readables; // Array that stores all readables
	private ArrayList<Audio> audioProducts; // Array that store all audio products
	private int currentPage; // the state variable for the current page
	private ShoppingCart activeUser; // this is the shopping cart of the user
	Scanner usrIn; // User Input Scanner
	private boolean audiopricesorttoggle; //toggle the price sort for audio from high-low to low-high and vice versa (ADMIN)
	private boolean readablepricesorttoggle; //toggle the price sort for readable from high-low to low-high and vice vers (ADMIN)
	private boolean audionamesorttoggle;  //toggle the price sort for audio from A-Z to Z-A and vice versa (ADMIN)
	private boolean readablenamesorttoggle; //toggle the price sort for readable from A-Z to Z-A and vice versa (ADMIN)

	public UserInterface() { //constructor
		// Initialize variables
		this.usrIn = new Scanner(System.in); //usrIn is the Scanner used to read user input
		this.readables = new ArrayList<Readable>(); //arrayList to store readables
		this.audioProducts = new ArrayList<Audio>(); //arrayList to store audio products
		this.audiopricesorttoggle = true; //initialize audiopricesort to true
		this.readablepricesorttoggle = true;//initialize readablepricesort to true
		this.audionamesorttoggle = true;//initialize audionamesort to true
		this.readablenamesorttoggle = true;//initialize readablenamesort to true
		this.init(); //calls initialization methods
	}

	public void init() {
		// calls the initialization methods
		this.getReadables();// loads readables
		this.getAudioProducts();// loads audio
		this.changeCurrentPage(1);// set the page to page 1
	}

	public void getCurrentPage() {
		String inp;// this is a general storage variable for user inputs
		int choice;// this ia a general storage variable for the option the user selects

		if (this.currentPage == 1) {
			int[] op = { 1, 2 }; // This is an Array of the valid options the user can enter
			choice = getInt(op, this.usrIn);// calls the user query function with the current valid options and input scanner

			if (choice == 1) {// if the user chooses option 1
				System.out.println("Please enter your username: ");// prompt for username
				inp = this.usrIn.nextLine();// stores input in input storage variable
				String usr = User.findUser(inp); // calls the static findUser method to see if user exists and if so gets user data as a comma seperated string
				if (usr.equals("")) {// if user is not found
					this.changeCurrentPage(4);// redirect to the "No Access" page
				} else if (usr.split(",").length > 1) {// if the user has a password (i.e. is an admin)
					System.out.println("Please enter your password: ");// prompt for password
					inp = this.usrIn.nextLine();// stores input in input variable
					if (!inp.equals(usr.split(",")[1])) {// if incorrect pasword is entered
						System.out.println("Invalid password. Access Denied \n Press Enter to Continue"); // print error
						this.usrIn.nextLine(); // waits for user to press enter
						this.changeCurrentPage(4);// goes to NO Access page
					} else {// if password is correct
						this.activeUser = new ShoppingCart(usr.split(",")[0], usr.split(",")[1]);// loads shopping card object
						this.changeCurrentPage(3);// goes to welcome page
					}
				} else {// if a regular user enters
					this.activeUser = new ShoppingCart(usr.split(",")[0]);// loads shopping cart
					this.changeCurrentPage(3);// go to welcome page (P3)
				}
			} else {// if the user wants to sign up
				this.changeCurrentPage(2);// go to signup page (P2)
			}

		} else if (this.currentPage == 2) {// Page 2 user interaction logic
			inp = this.usrIn.nextLine();// stores the user name the user has choosen in inp (see the printed P2 in changeCurrentPage(2))
			while (!User.findUser(inp).equals("")) {// if the user exists
				System.out.println("That username is already taken. Please try again,");// print error
				System.out.println("Choose your username:");// ask again
				inp = this.usrIn.nextLine();// store user input in inp
			}
			this.activeUser = new ShoppingCart(inp);// makes a shopping cart for the user
			this.activeUser.writeUser();// writes the new user to Users.txt
			System.out.println("Username successfully added. Press Enter to Continue.");// prompt enter to continue
			this.usrIn.nextLine();// waits for user to hit enter
			this.changeCurrentPage(5);// go to page 5

		} else if (this.currentPage == 3) {// page 3 welcome page
			usrIn.nextLine();// waits for user to hit enter
			this.changeCurrentPage(5); // goes to next menu (page 5)
		} else if (this.currentPage == 4) {// page 4 no access page
			usrIn.nextLine();// waits for user to hit enter
			this.changeCurrentPage(1);// goes back to main menu (page 1)
		} else if (this.currentPage == 5) {// page 5 menu page
			int[] op = { 1, 2, 3, 3};// array for valid user inputs
			if (this.activeUser.admin) op[3] = 4;// added option for admin
			choice = getInt(op, this.usrIn);// query user input function called
			if (choice == 1) {// if user chooses option 1
				this.changeCurrentPage(6);// go to page 6
			} else if (choice == 2) {// if user chooses option 2
				this.changeCurrentPage(7);// go to page 7
			}else if (choice == 4) { //if ADMIN chooses option 4
				this.activeUser.changePass(this.usrIn); //call User method that changes the password
				this.changeCurrentPage(3); //come back to the original page
			} else {// the user must have choosen option 3
				this.changeCurrentPage(1);// go to page 1
			}
		} else if (this.currentPage == 6) {// page 6 logic
			int[] op = { 1, 2, -1 };// valid options array
			choice = getInt(op, this.usrIn);// query user function
			if (choice == 1) {// if user choose readables (option 1)
				this.changeCurrentPage(8);// go to readables page (page 8)
			} else if (choice == 2) {// if user choose audio (option 2)
				this.changeCurrentPage(9);// go to audio products page (page 9)
			} else {// otherwise the user must have choosen option -1
				this.changeCurrentPage(5);// go back to previous menu (page 5)
			}
		} else if (this.currentPage == 7) {// page 7 shopping cart logic
			usrIn.nextLine();// wait for enter before going back to page 5
			this.changeCurrentPage(5);// goes back to page 5

		} else if (this.currentPage == 8) {// page 8 readables logic
			ArrayList<Integer> optemp = new ArrayList<Integer>();// Dynamic ArrayList for available options
			for (Readable r : this.readables) {// iterates through all readables
				optemp.add(r.sNo);// adds their serial number (sNo) to the dynamic array of available options
			}
			int[] op = new int[optemp.size() + 4];// makes a static array from the dynamic ArrayList for compatibility with getInt(Int[] i)
			op[optemp.size() + 3] = -1; // set an additional option to be -1 (note that the statuc array is 4 elements bigger than the dynamic array)
			if (this.activeUser.admin) { //only if the active user is ADMIN, do we add more options to select.
				op[optemp.size() + 2] = -2; //if ADMIN, set an additional option to -2
				op[optemp.size() + 1] = -3; //if ADMIN, set an additional option to -3
				op[optemp.size()] = -4; //if ADMIN, set an additional option -4
			}
			for (int i = 0; i < optemp.size(); i++) {// iterates through the dynamic array with an index variable
				op[i] = optemp.get(i);// assigns serial numbers to static array via indices
			}
			choice = getInt(op, this.usrIn);// queries user to make a valid choice
			if (choice == -1) {// if user chooser to go back
				this.changeCurrentPage(6);// goes back to page 6
			} else if (choice == -2) { //if the ADMIN, can choose -2  to sort by price
				this.readables = ReadablebyPrice(this.readables); //call the function which will rearrange readables array according to price.
				this.changeCurrentPage(8); //refresh the page with changes
			} else if (choice == -3) { //if the ADMIN, can choose -3 to sort by name
				this.readables = ReadablebyName(this.readables); //call the function which will rearrange readables array according to name.
				this.changeCurrentPage(8); //refresh the page with changes
			} else if (choice == -4) { //if the ADMIN, can choose -4 to proceed to page for stocking inventory
				this.changeCurrentPage(11); //readable inventory page
			} else {// otherwise
				Readable temp = this.findReadable(choice);// lookup the item the user has choosen via sNo and store it in temp
				if (temp.getQuantity() == 0) {// if the quantity is already at 0
					System.out.println("Item out of Stock. Please Try a different item.");// print error
					System.out.println("Please press enter to continue");// ask to continue
					usrIn.nextLine();// waits for user to hit enter
					this.changeCurrentPage(8);// refreshes the page
				} else {
					int q = getQuant(temp.getQuantity(), this.usrIn);// queries user for a valid input in a given range
					temp.subQuant(q);// subtracts the specified ammount from the  item in the readables array
					this.writeReadable();// saves the readable array to the files Book.txt and eBook.txt
					this.activeUser.addItem(temp, q);// adds the items to the
														// cart of the user
					System.out.println(String.valueOf(q) + " " + temp.getName() + " successfully added to your cart.");// success message
					System.out.println("Enter -2 to continue shopping or 0 to checkout.");// prompt for next step
					int[] op2 = { -2, 0 };// setup array with valid options
					choice = getInt(op2, this.usrIn);// Query the user
					if (choice == -2) {// if choice is -2
						this.changeCurrentPage(6);// go back to page 6
					} else {// if choice is 0
						this.changeCurrentPage(10);// proceed to checkout
					}
				}
			}

		} else if (this.currentPage == 9) {// audio page
			ArrayList<Integer> optemp = new ArrayList<Integer>();// Dynamic ArrayList for available options
			for (Audio r : this.audioProducts) {// iterates through audio products
				optemp.add(r.getsNo());// adds the serial to the arraylist of available options
			}
			int[] op = new int[optemp.size() + 4];//only if the active user is ADMIN, do we add more options to select.
			op[optemp.size() + 3] = -1; // set an additional option to be -1 (note that the statuc array is 4 elements bigger than the dynamic array)
			if (this.activeUser.admin) {//if ADMIN...
				op[optemp.size() + 2] = -2; //if ADMIN, set an additional option to -2
				op[optemp.size() + 1] = -3; //if ADMIN, set an additional option to -3
				op[optemp.size()] = -4; //if ADMIN, set an additional option to -4
			}
			for (int i = 0; i < optemp.size(); i++) {// iterates through dynamic array with index
				op[i] = optemp.get(i);// assigns serial numbers from dynamic array to static array with indicies
			}
			choice = getInt(op, this.usrIn);// query user for valid input
			if (choice == -1) {// if the choice is -1
				this.changeCurrentPage(6);// go back to page 6
			} else if (choice == -2) {//if ADMIN chooses -2 (price sort)
				this.audioProducts = AudiobyPrice(this.audioProducts); //call the function which will rearrange audio array according to price.
				this.changeCurrentPage(9); //refresh the page
			} else if (choice == -3) {//if ADMIN chooses -3 (name sort)
				this.audioProducts = AudiobyName(this.audioProducts);//call the function which will rearrange audioProducts array according to name.
				this.changeCurrentPage(9); //refresh the page
			} else if (choice == -4) {//if ADMIN chooses -4 (new item)
				this.changeCurrentPage(12); //go to the page for stocking audio inventory
			} else {// otherwise
				Audio temp = this.findAudio(choice);// find the audio item the user has specified and store in temp
				if (temp.getQuantity() == 0) {// if item is sold out
					System.out.println("Item out of Stock. Please Try a different item.");// print error
					System.out.println("Please press enter to continue");// ask  to continue
					usrIn.nextLine();// waits for user to hit enter
					this.changeCurrentPage(9);// refreshes current page
				} else {// otherwise if item is available
					int q = getQuant(temp.getQuantity(), this.usrIn);// prompts user  for  an  input in range (1 to quantity)
					temp.subQuant(q);// subtracts the ammount from the item in the audio arrray
					this.writeAudio();// writes the audio array
					this.activeUser.addItem(temp, q);// adds the item to the user's shopping cart
					System.out.println(String.valueOf(q) + " " + temp.getName() + " successfully added to your cart.");// success message
					System.out.println("Enter -2 to continue shopping or 0 to checkout.");// prompts  user for further action
					int[] op2 = { -2, 0 };// setup valid options
					choice = getInt(op2, this.usrIn);// query user for input
					if (choice == -2) {// if user choose -2
						this.changeCurrentPage(6);// go back to page 6
					} else {// if user choose 0
						this.changeCurrentPage(10);// go to checkout
					}
				}
			}
		} else if (this.currentPage == 10) {// logic for page 10 (checkout page)
			boolean buy = yesNo("Would you like to buy? [Yes/No]: ", this.usrIn);// prompts  user for confirmation on buying
			if (buy) {// if they want to buy
				int s;// used to produce the confirmation number
				try {
					Scanner temp = new Scanner(new File("cnum.txt"));// throws FileNotFoundException if there is no such file
					s = temp.nextInt();// scans the file for the confirmation number to use
					temp.close();// close the scanner
					PrintStream out = new PrintStream(new File("cnum.txt"));// opens  the file for writing this time
					out.println(String.valueOf(s + 1));// writes the next  integer
					out.close();// close the PrintStream and File
				} catch (Exception e) {// if there is no file cnum.txt
					s = 1000;// this must be the first order, thus confirmation
								// is U1000
					try {// need this try catch block for the printstream
						PrintStream out = new PrintStream(new File("cnum.txt"));// make a  new cnum.txt
						out.println(String.valueOf(s + 1));// write in 1001
						out.close();// close PrintStream and File
					} catch (FileNotFoundException e1) {// This block should not normally be reached unless cnum.txt already exists and we cannot overwrite it
					}

				}
				System.out.println("Confirmation ID: U" + String.valueOf(s));// print confirmation number
				System.out.println("Items shipped to: " + this.activeUser.getName());// prints item shipped to : [username]
				this.activeUser.clearCart();// clears the user's cart
				System.out.println("Please press enter to exit");// ask to continue
				usrIn.nextLine();// waits for user to hit enter
				System.exit(0);
			} else {// if the user said no to buying the items in cart
					// ask if they would like to clear the cart
				boolean clear = yesNo("Would you like to return the items in your shopping cart? [Yes/No]: ",
						this.usrIn);
				if (clear) {// if they said yes to clearing the cart
					for (String line : this.activeUser.getContent()) {// for every  line in  the  cart
						String[] temp = line.split(",");// split the string to get the individual fields
						this.retItm(temp[1].trim(), Integer.parseInt(temp[3].trim()));// calls  the return item function
					}
					this.writeReadable();// write the readables to their respective files
					this.writeAudio();// writes the audio to their respective files
					this.activeUser.clearCart();// clears the user's cart
					System.out.println("Cart Returned! Have a nice day.");// prints message
					System.out.println("Please press enter to exit");// ask to  continue
					usrIn.nextLine();// waits for user to hit enter
					System.exit(0);
				} else {// if the user does not want to clear the cart
					System.out.println("Have a nice day.");// prints message
					System.out.println("Please press enter to exit");// ask to
																		// continue
					usrIn.nextLine();// waits for user to hit enter
					System.exit(0); //system exit
				}
			}
		} else if (this.currentPage == 11) {//logic for page 11 (readables inventory page)
			String name=""; //initalize name to arbitrary value.
			String author=""; //initalize author to arbitrary value
			String type="";//initalize type to arbitrary value
			int sNo=0;//initalize sNo to arbitrary value
			int price = 0;//initalize price to arbitrary value
			int quantity = 0;//initalize quantity to arbitrary value
			
			System.out.println("Enter sNo:"); //print the promt for sNo
			boolean sNoinput = true; //boolean for while loop
			while (sNoinput) { //while true
				try { //try block
					sNo = Integer.parseInt(usrIn.nextLine()); //user input is (attempted to be) converted to an integer.
					while (findAudio(sNo) != null || sNo<1) { //while serial number is used by audio product or not positive integer
						if (sNo<1) System.out.print("Invalid serial number. Try again:"); //error message
						if (findAudio(sNo) != null) System.out.print("This serial number is already in use by a readable. Try again:"); //error message
						sNo = Integer.parseInt(usrIn.nextLine()); //user input for sNo
					}
				sNoinput = false;//after successful user input, boolean is switched and the while loop terminates
				} catch (Exception e) { //catch block for non-integer input
					System.out.print("Invalid serial number. Try again:"); //error message
					continue;//keep going
				}
			}
	
			boolean autofill; //boolean for whether to "autofill" the details of the item or ask for the details.
			if (findReadable(sNo) == null) { //if the sNo is not already used by a readable product
				autofill = false; //set autofill to false (must ask for item details)
			} else 
				autofill = true; //otherwise, we can autofill item details

			if (autofill) { 
				name=findReadable(sNo).getName(); //use Readable.getname()
				System.out.println("Enter name of readable: " + name); //print the message indicating the item name
				author=findReadable(sNo).getAuthor(); //use Readable.getAuthor()
				System.out.println("Enter author: " + author); //print the message indicating author
				type=findReadable(sNo).getType(); //use Readable.getType()
				System.out.println("Book or eBook: " + type); //print the message indicating type (Book or eBook)
				price=findReadable(sNo).getPrice(); //use Readable.getPrice()
				System.out.println("Enter price: " + String.valueOf(price)); //print message indicating the item price
			}

			else { //if the item is not already in readable inventory...
				System.out.println("Enter name of readable:"); //prompt for name
				name = usrIn.nextLine(); //user input

				System.out.println("Enter author name:"); //prompt for author
				author = usrIn.nextLine(); //user input

				System.out.println("Book or eBook:"); //prompt for type
				type = usrIn.nextLine();//user input
				while (!(type.equals("Book") || type.equals("eBook"))) {
					System.out.print("Invalid input. Try again:");//repeat asking if they do not input on the two allowed types
					type = usrIn.nextLine(); //user input
				}

				System.out.println("Enter price:"); //prompt for pice
				boolean priceinput = true; //boolean for while loop
				while (priceinput) { //while true
					try {
						price = Integer.parseInt(usrIn.nextLine()); //attempt to parse input into integer
						while (price < 0) { //if price is negative
							System.out.print("Invalid price. Try again:"); //error message
							price = Integer.parseInt(usrIn.nextLine()); //attempt to parse input into integer
						}
						priceinput = false; //once successful, while loop is terminated by boolean switching to false
					} catch (Exception e) { //catch block for non-integer input
						System.out.print("Invalid price. Try again:"); //error message
						continue; //keep going
					}
				}
			}
			System.out.print("Enter quantity to add:"); //prompt for quantity
			boolean quantityinput = true; //boolean for while loop
			while (quantityinput) { //while true 
				try {
					quantity = Integer.parseInt(usrIn.nextLine()); //attempt to parse input into integer
					while (quantity <= 0) { //if the quantity is 0 or negative
						System.out.print("Invalid quantity. Try again:"); //error message
						quantity = Integer.parseInt(usrIn.nextLine()); //attempt to parse input into integer
					}
					quantityinput = false; //once successful, while loop is terminated by boolean switching to false
				} catch (Exception e) { //catch block for non-integer input
					System.out.print("Invalid quantity. Try again:");//error message
					continue;//keep going
				}
			}

			if (autofill) //if autofill
				findReadable(sNo).addQuant(quantity); //simply increase the quantity of a given readable by calling the addQuant method
			else{
				if (type.equals("Book")) //if it is a Book...
					this.readables.add(new Book(sNo, name, author, price, quantity)); //add a Book object to the readables array
				if (type.equals("eBook")) //if it is an eBook...
					this.readables.add(new eBook(sNo, name, author, price, quantity)); //add an eBook object to the readables array
			}
			writeReadable(); //write the changes to the readables array to file
			System.out.println("Inventory updated. Press enter to return to previous page."); //print update message
			usrIn.nextLine();// waits for user to hit enter //wait for user to press enter
			this.changeCurrentPage(8); //go back to the readables display page

		} else if (this.currentPage == 12) { //logic for page 11 (readables inventory page)
			String name=""; //initialize  to an arbitrary value
			String artist="";//initialize  to an arbitrary value
			String type="";//initialize  to an arbitrary value
			int sNo=0;//initialize  to an arbitrary value
			int price = 0;//initialize  to an arbitrary value
			int quantity = 0;//initialize  to an arbitrary value
			
			System.out.println("Enter sNo:"); //prompt for sNo
			boolean sNoinput = true;//boolean for while loop
			while (sNoinput) {//while true
				try {
					sNo = Integer.parseInt(usrIn.nextLine());//user input is (attempted to be) converted to an integer.
					while (findReadable(sNo) != null || sNo<1) {//while serial number is used by readable product or not positive integer
						if (findReadable(sNo) != null) System.out.print("There is an readable product with this serial number. Try again:"); //error message
						if (sNo<1) System.out.print("Invalid serial number. Try again:"); //error message
						sNo = Integer.parseInt(usrIn.nextLine()); //user input is attempted to be converted to integer
					}
					sNoinput = false;//after successful user input, boolean is switched and the while loop terminates
				} catch (Exception e) {//catch block for non-integer input
					System.out.print("Invalid serial number. Try again:"); //error message
					continue; //keep going
				}
			}
			boolean autofill = true;//boolean for whether to "autofill" the details of the item or ask for the details.
			if (findAudio(sNo) == null) { //if the sNo is not already used by a audio product
				autofill = false; //set autofill to false (must ask for item details)
			}
			if (autofill) { //otherwise, we can autofill item details
				name=findAudio(sNo).getName();  //use Audio.getname()
				System.out.println("Enter name of audio: " + name);//print the message indicating the item name
				artist=findAudio(sNo).getArtist();//use Audio.getArtist()
				System.out.println("Enter artist name: " + artist);//print the message indicating artist
				type=findAudio(sNo).getType();//use Audio.getType()
				System.out.println("CD or MP3: " + type);//print the message indicating type (CD or MP3)
				price=findAudio(sNo).getPrice();//use Audio.getPrice()
				System.out.println("Enter price: " + String.valueOf(price));//print message indicating the item price
			} else {//if the item is not already in audio inventory...
				System.out.println("Enter name of audio:");//prompt for name
				name = usrIn.nextLine();//user input

				System.out.println("Enter artist name:");//prompt for artist
				artist = usrIn.nextLine(); //user input

				System.out.println("CD or MP3:"); //prompt for type
				type = usrIn.nextLine(); //user input
				while (!(type.equals("CD") || type.equals("MP3"))) { //while not the two valid types
					System.out.print("Invalid input. Try again:"); //error message
					type = usrIn.nextLine(); //redo user input
				}

				System.out.println("Enter price:"); //prompt for price
				boolean priceinput = true;//boolean for while loop
				while (priceinput) {//while true
					try {
						price = Integer.parseInt(usrIn.nextLine());//attempt to parse input into integer
						while (price < 0) {//if price is negative
							System.out.print("Invalid price. Try again:");//error message
							price = Integer.parseInt(usrIn.nextLine());//attempt to parse input into integer
						}
						priceinput = false; //once successful, while loop is terminated by boolean switching to false
					} catch (Exception e) {//catch block for non-integer input
						System.out.print("Invalid price. Try again:");//error message
						continue; //keep going
					}
				}
			}
			System.out.print("Enter quantity to add:");//prompt for quantity
			boolean quantityinput = true;//boolean for while loop
			while (quantityinput) {//while true
				try {
					quantity = Integer.parseInt(usrIn.nextLine());//attempt to parse input into integer
					while (quantity <= 0) {//if the quantity is 0 or negative
						System.out.print("Invalid quantity. Try again:");//error message
						quantity = Integer.parseInt(usrIn.nextLine());//attempt to parse input into integer
					}
					quantityinput = false;//once successful, while loop is terminated by boolean switching to false
				} catch (Exception e) { //catch block for non-integer input
					System.out.print("Invalid quantity. Try again:"); //error message
					continue;
				}
			}

			if (autofill) //if autofill
				findAudio(sNo).addQuant(quantity); //simply increase the quantity of a given audio product by calling the addQuant method
			else{
				if (type.equals("MP3")) //if it is an MP3
					this.audioProducts.add(new MP3(sNo, name, artist, price, quantity)); //add an MP3 object to audioProducts
				if (type.equals("CD")) //if it is a CD
					this.audioProducts.add(new CD(sNo, name, artist, price, quantity)); //add a CD object to audioProducts
			}
			writeAudio(); //write changes to audio to file
			System.out.println("Inventory updated. Press enter to return to previous page."); //update message printed
			usrIn.nextLine();// waits for user to hit enter
			this.changeCurrentPage(9); //go back to audio products display

		} else

		{// if an invalid page numer was somehow reached
			System.out.println("ERROR: Invalid Page Number");
		}

	}

	// This is a function that changes the state vaiable and prints the page
	public void changeCurrentPage(int i) {
		this.currentPage = i;// sets current page state variable to i
		clearPage();// prints a ton of "\n" s
		// The print statements in the following block will not be commented as
		// they simply print out the text of the page
		if (this.currentPage == 1) {
			System.out.println("1.Sign in");// these are separate print
											// statements for better code
											// readability
			System.out.println("2.Sign up");
			System.out.println();
			System.out.println("Choose your option:");

		} else if (this.currentPage == 2) {
			System.out.println("Choose your username:");
		} else if (this.currentPage == 3) {
			System.out.println("Welcome " + activeUser.getName());// gets the username field of the current user
			System.out.println("Press Enter to Continue");
		} else if (this.currentPage == 4) {
			System.out.println("No Access");
			System.out.println("Press Enter to Return to Previous Menu");
		} else if (this.currentPage == 5) {
			System.out.println("1.View Items by Category");// these are separate print statements for better code readability
			System.out.println("2.View Shopping Cart");
			System.out.println("3.Sign out");
			if (this.activeUser.admin) System.out.println("4.Change admin password");
			System.out.println();
			System.out.println("Choose your option:");
		} else if (this.currentPage == 6) {
			System.out.println("1.Readables");// these are separate print statements for better code readability
			System.out.println("2.Audio");
			System.out.println();
			System.out.println("Choose your option:");
			System.out.println();
			System.out.println("Enter -1 to return to previous menu");
		} else if (this.currentPage == 7) {
			for (String s : this.activeUser.getContent()) {// iterates through current user's shopping cart
				System.out.println(s);// prints out the lines
			}
			System.out.println();
			System.out.println("Press enter to return to previous menu");
		} else if (this.currentPage == 8) {
			System.out.println("Readables: ");
			System.out.println();
			this.showReadables(); // calls the formatting method to print out readables in a table
			System.out.println("Choose your option:");
			System.out.println("Enter -1 to return to previous menu");
			if (this.activeUser.admin) { //added interface for the ADMIN
				if (readablepricesorttoggle) //if readablepricesorttoggle is set to true...
					System.out.println("Enter -2 to sort items by price from low to high"); //print option to sort from low to high
				else //otherwise (toggle is false)
					System.out.println("Enter -2 to sort items by price from high to low"); //print option to sort from high to low
				if (readablenamesorttoggle) //if readablenamesorttoggle is set to true...
					System.out.println("Enter -3 to sort items by name from A-Z"); //option is to sort instead from A-Z
				else
					System.out.println("Enter -3 to sort items by name from Z-A"); //otherwise (toggle is false), option is to sort from Z-A
				System.out.println("Enter -4 to add new Readable to the stock"); //final option is to add a new readable item to the stock
			}
		} else if (this.currentPage == 9) { 
			System.out.println("Audio: ");
			System.out.println();
			this.showAudioProducts();// calls the formatting method to print out audio items in a table
			System.out.println("Choose your option:");
			System.out.println("Enter -1 to return to previous menu");
			if (this.activeUser.admin) {
				if (audiopricesorttoggle)//if audio pricesorttoggle is set to true...
					System.out.println("Enter -2 to sort items by price from low to high");//print option to sort from low to high
				else
					System.out.println("Enter -2 to sort items by price from high to low"); //if toggle is false, print option to sort from high to low
				if (audionamesorttoggle)//if audionamesorttoggle is set to true...
					System.out.println("Enter -3 to sort items by name from A-Z");//option is to sort instead from A-Z
				else
					System.out.println("Enter -3 to sort items by name from Z-A");//otherwise (toggle is false), option is to sort from Z-A
				System.out.println("Enter -4 to add new Audio product to the stock");//final option is to add a new audio item to the stock
			}
		} else if (this.currentPage == 10) {// some calculations are done here for this page
			System.out.println("Billing Information: ");
			int total = 0;// variable for the total shown at the bottom
			int envTax = 0;// variable for total environmental tax paid
			int tax = 0;// variable for the HST paid
			int shipping = 0;// variable for shipping
			String out = String.format("%-25s%-25s%-25s\n", "Name", "Quantity", "Price");// formatter for headings
			System.out.println(out);// prints the string
			String[] rawData = this.activeUser.getContent();// gets shopping cart
			for (String line : rawData) {// iterates through items in shopping cart
				String name = line.split(",")[1].trim();// name is the 2nd item in the csv line
				int quant = Integer.parseInt(line.split(",")[3].trim());// parses the quantity from the 4th item in  the line
				int[] info = this.getPriceInfo(name);// calls the method that looks up the price, environmental tax(if any) and shipping fees(if any)
				out = String.format("%-25s%-25s%-25s\n", name, String.valueOf(quant), String.valueOf(info[0]));// formats information into  table form
				System.out.println(out);
				total += info[0];// adds price to total
				envTax += info[1] * quant;// adds environmental tax (0 if none) to total environmental tax
				shipping += info[2] * quant;// adds shipping (0 if none) to  total shipping fees
				tax += (info[0] * 13) * quant;// adds 13% of price to total HST(divide by 100 at the end for precision issues with integers)
			}
			tax = tax / 100;// divides by 100 at the end
			total += envTax + shipping + tax;// total now adds the environmentaltax, shipping and HST for a grand total
			out = String.format("%-30s%-10s%-40s\n", "Environment Tax", "2%", String.valueOf(envTax));// prints environmental tax
			System.out.println(out);
			out = String.format("%-30s%-10s%-40s\n", "HST", "13%", String.valueOf(tax));// prints HST
			System.out.println(out);
			out = String.format("%-30s%-10s%-40s\n", "Shipping", "10%", String.valueOf(shipping));// prints shipping fees
			System.out.println(out);
			out = String.format("%-40s%-40s\n", " ", "------------");// line above total
			System.out.println(out);
			out = String.format("%-40s%-40s\n", "Total%", String.valueOf(total));// prints total
			System.out.println(out);

		} else if (this.currentPage == 11) { //readable inventory page
			System.out.println("**Add readables inventory**"); 
			System.out.println("Please enter the required information about this item");
			System.out.println();
		} else if (this.currentPage == 12) { //audio inventory page
			System.out.println("**Add audio inventory**");
			System.out.println("Please enter the required information about this item");
			System.out.println();
		} else {// if a invalid page was requested
			System.out.println("ERROR IN NAVIGATION: PAGE NOT FOUND");// print error message
		}

		this.getCurrentPage();// calls the logic/user interaction function for the page after page change
	}

	public void getReadables() {// this function loads Books and eBooks from the txt into the Readables Array
		parseObjects(this.readables, "Books.txt");// load Books
		parseObjects(this.readables, "eBooks.txt");// load eBooks
	}

	public void getAudioProducts() {// this function loads CDs and MP3 from the txt into the Audio Array
		parseObjects(this.audioProducts, "CDs.txt");// load CDs
		parseObjects(this.audioProducts, "MP3.txt");// load MP3
	}

	public void showReadables() {// Prints out all readables in a nice table
		System.out.printf("%-7s%-30s%-14s%-8s%-20s%-10s\n", "S.No", "Name of the Book", "Author", "Price($)",
				"Quantity in Store", "Type");// formatter for headings
		for (Readable itm : this.readables) {// goes through each item in readables formats and prints the neccessary information in their respective places by first calling the method to get the information
			String s = String.format("%-7s%-30s%-14s%-8s%-20s%-10s\n", String.valueOf(itm.getsNo()), itm.getName(),
					itm.getAuthor(), String.valueOf(itm.getPrice()), String.valueOf(itm.getQuantity()), itm.getType());
			System.out.println(s);
		}
		System.out.println(); //print empty line
	}

	public void showAudioProducts() {// Prints out all audio items in a nice table 
		//formatter for header
		System.out.printf("%-7s%-30s%-14s%-8s%-20s%-10s\n", "S.No", "Name ", "Artist", "Price($)", "Quantity in Store",
				"Type");
		for (Audio itm : this.audioProducts) {
			// formats and prints the neccessary information in their respective places by first calling the method to get the information
			String s = String.format("%-7s%-30s%-14s%-8s%-20s%-10s\n", String.valueOf(itm.getsNo()), itm.getName(),
					itm.getArtist(), String.valueOf(itm.getPrice()), String.valueOf(itm.getQuantity()), itm.getType());
			System.out.println(s);
		}
		System.out.println(); //print empty line
	}

	// finds an audio item given the serial number
	public Audio findAudio(int sNo) {
		for (Audio a : this.audioProducts) {// iterates through the audioProducts array
			if (a.getsNo() == sNo) {// if serial numbers match
				return a;// return the item
			}
		}
		return null;// if not found, return null
	}// finds a readable item given the serial number

	public Readable findReadable(int sNo) {
		for (Readable r : this.readables) {// iterates through the readables array
			if (r.getsNo() == sNo) {// if serial numbers match
				return r;// return the item
			}
		}
		return null;// if not found, return null pointer
	}

	// writes the readable array into the correspoinding files
	public void writeReadable() {
		try {
			// opens a PrintStream for each of the files
			PrintStream bk = new PrintStream(new File("Books.txt"));
			PrintStream ebk = new PrintStream(new File("eBooks.txt"));

			for (Readable r : this.readables) {// iterates through the readables
				if (r.getType().equals("Book")) {// if type is book
					bk.println(r.getInfo());// write into the Book.txt file
				} else {// otherwise it must be an eBook
					ebk.println(r.getInfo());// write into the eBook.txt file
				}
			}
			// closes the PrintStreams
			bk.close();
			ebk.close();
		} catch (FileNotFoundException e) {// This block should not be reached unless an access error has occured
			e.printStackTrace();
		}
	}

	// writes the AudioProduct array into the correspoinding files
	public void writeAudio() {
		try {
			// opens a PrintStream for each of the files
			PrintStream cd = new PrintStream(new File("CDs.txt"));
			PrintStream mp3 = new PrintStream(new File("MP3.txt"));

			for (Audio a : this.audioProducts) {// iterates through the audio products
				if (a.getType().equals("CD")) {// if type is CD
					cd.println(a.getInfo());// write into the CD.txt file
				} else {// otherwise it must be an MP3
					mp3.println(a.getInfo());// write into the MP3.txt file
				}
			}
			// close the PrintStreams
			cd.close();
			mp3.close();
		} catch (FileNotFoundException e) {// This block should not be reached unless an access error has occured
			e.printStackTrace();
		}
	}

	// looks up price information from the readables and audio arrays as shopping cart does not store this information.
	//The first element is the price, the second is the environmental fee(2% , rounded down) and the third element is the shipping(10%, rounded down)
	public int[] getPriceInfo(String name) {
		int[] results = { 0, 0, 0 };// 3 slot for the return, 0 is price, 1 is environmental tax,2 is shipping
		for (Readable r : this.readables) {// iterates through readables
			if (r.getName().equals(name)) {// if the name matches
				results[0] = r.getPrice();// set element 0 to the price
				if (r.getType().trim().equals("Book")) {// if the item is a book
					results[1] = results[0] / 50;// calculate env tax
					results[2] = results[0] / 10;// calculate shipping
				}
			}
		}
		for (Audio a : this.audioProducts) {// iterates through audio
			if (a.getName().equals(name)) {// if the name matches
				results[0] = a.getPrice();// set element 0 to be the price
				if (a.getType().trim().equals("CD")) {// if the type is CD
					results[1] = results[0] / 50;// apply environmental tax
					results[2] = results[0] / 10;// apply shipping fee
				}
			}
		}
		return results;// returns the 3 element int array containing price, env tax and shipping
	}

	// this function returns an item to the arrays readables or audioProduct from the shoping cart
	public void retItm(String name, int quant) {
		for (Readable r : this.readables) {// looks through items in readables
			if (r.getName().equals(name)) {// if the item name matches
				r.subQuant(quant * -1);// add the quantity back (subtracting the negative of the quantity is the same as adding)
			}
		}
		for (Audio a : this.audioProducts) {// same thing done with items in audio products
			if (a.getName().equals(name)) {
				a.subQuant(quant * -1);
			}
		}
	}

	// ====methods for ADMIN functionality====

	public ArrayList<Readable> ReadablebyPrice(ArrayList<Readable> items) { //method to sort readables by price in an arraylist
		Collections.sort(items, new Comparator<Readable>() { //use collections.sort but override method
			@Override
			public int compare(Readable item1, Readable item2) { //compare method of two readables
				if (item1.getPrice() > item2.getPrice()) //if the price of item1 is higher than item 2...
					if (readablepricesorttoggle) //the toggle will either make the return 1 or -1.
						return 1;
					else
						return -1;
				if (item1.getPrice() < item2.getPrice()) //if the price of item1 is higher than item2...
					if (readablepricesorttoggle) //the toggle will return the opposite (-1 for true and 1 for false)
						return -1;
					else
						return 1;
				return 0;
			}
		});
		readablepricesorttoggle = !readablepricesorttoggle; //after it is called, it should switch the boolean variable to indicate a sort. 
		return items; //return sorted array
	}

	public ArrayList<Audio> AudiobyPrice(ArrayList<Audio> items) {//method to sort audio products by price in an arraylist
		Collections.sort(items, new Comparator<Audio>() {//use collections.sort but override method
			@Override
			public int compare(Audio item1, Audio item2) {//compare method of two readables
				if (item1.getPrice() > item2.getPrice())//if the price of item1 is higher than item 2...
					if (audiopricesorttoggle) //the toggle will either make the return 1 or -1.
						return 1;
					else
						return -1;
				if (item1.getPrice() < item2.getPrice())//if the price of item1 is higher than item2...
					if (audiopricesorttoggle)//the toggle will return the opposite (-1 for true and 1 for false)
						return -1;
					else
						return 1;
				return 0;
			}
		});
		audiopricesorttoggle = !audiopricesorttoggle;//after it is called, it should switch the boolean variable to indicate a sort. 
		return items;//return sorted array
	}

	public ArrayList<Readable> ReadablebyName(ArrayList<Readable> items) {//method to sort readables by name in an arraylist
		Collections.sort(items, new Comparator<Readable>() {//use collections.sort but override method
			@Override
			public int compare(Readable item1, Readable item2) {
				if (readablenamesorttoggle) //toggle leads to opposite results if true or false
					return item1.getName().compareToIgnoreCase(item2.getName()); //compare item1 and item2 alphabetically
				else//toggle leads to opposite results if true or false
					return item2.getName().compareToIgnoreCase(item1.getName());//compare item1 and item2 alphabetically
			}
		});
		readablenamesorttoggle = !readablenamesorttoggle;//after it is called, it should switch the boolean variable to indicate a sort. 
		return items;//return sorted array
	}

	public ArrayList<Audio> AudiobyName(ArrayList<Audio> items) {//method to sort audio products by name in an arraylist
		Collections.sort(items, new Comparator<Audio>() {//use collections.sort but override method
			@Override
			public int compare(Audio item1, Audio item2) {
				if (audionamesorttoggle) //toggle leads to opposite results if true or false
					return item1.getName().compareToIgnoreCase(item2.getName()); //compare item1 and item2 alphabetically
				else
					return item2.getName().compareToIgnoreCase(item1.getName());//compare item1 and item2 alphabetically
			}
		});
		audionamesorttoggle = !audionamesorttoggle;//after it is called, it should switch the boolean variable to indicate a sort. 
		return items;//return sorted array
	}

	// ============================UTILITY======================================
	// Query the user for a simple yes or no reply
	public static boolean yesNo(String prompt, Scanner usrIn) {
		;
		while (true) {// lopps it over and over until a valid response is give
			System.out.println(prompt);// print out the question that is to be asked
			String s = usrIn.nextLine();// stores the user input in s
			if (s.equalsIgnoreCase("yes")) {// if the user said yes (ignores case)
				return true;
			} else if (s.equalsIgnoreCase("no")) {// if the user said no (again, ignores case)
				return false;
			} else {// if the user enters an invalid response
				System.out.println("Invalid input, try again.");// print error message
				continue;// start over again
			}
		}
	}

	// Query the user for an integer between 1 and max
	public static int getQuant(int max, Scanner usrIn) {

		while (true) {// keeps trying until the user enters an acceptable input
			try {
				System.out.println("Enter Quantity:");
				int temp = Integer.parseInt(usrIn.nextLine());// tries to read the user input as an integer and store in temp
				if (temp <= max && temp > 0) {// check if temp in the range of 1 to max

					return temp;// return temp if it is valid
				}
				// print error and ask user to try again if temp is out of desired range
				System.out.println("Above max or negative/zero quantity, please try again.");

				// dont need continue here as loop will automatically continue until a return statement is reached
			} catch (Exception e) {
				// user enters a string or some other invalid input, print error message
				System.out.println("Invalid input, please try again.");
				System.out.println("Enter Quantity:");// reprint the prompt
				continue;// start over again
			}
		}
	}

	// Query the user for an input that should match one of the options in options
	public static int getInt(int[] options, Scanner usrIn) {
		while (true) {// loop that keeps trying until a return statement is reached
			try {
				int temp = Integer.parseInt(usrIn.nextLine());// try to read the next user input as an integer
				for (int i : options) {// iterates through the integers in options
					if (i == temp) {// if the integer entered is in the options
						return temp;// return what the user entered
					}
				}
				// if it is not within the available options, print an error
				System.out.println("That is not one of the options,try again.");
				System.out.println("Choose your option:");// reprint prompt and the loop continues, getting the user to input a valid option again
			} catch (Exception e) {// if the user enters something that is not an integer
				System.out.println("Invalid input, please try again.");// print error
				System.out.println("Choose your option:");// reprint prompt
				continue;// start over again
			}
		}

	}

	// this function reads Items from a file and creates an object according to its type, then stores them in an arrayList that is returned
	public static void parseObjects(ArrayList dest, String filename) {
		ArrayList<String> src = new ArrayList<String>();// creates an empty array list to hold the strings from reading the file
		fileRead(src, filename); // dumps the content of the file [filename] into the arraylist src function call

		for (String line : src) {// iterates through the strings stored in src
			String[] dat = line.split(",");// splits the comma seperated value line into individual string fields
			int sNo = Integer.parseInt(dat[0].trim());// parse the serial number as an integer
			String name = dat[1].trim();// stores the name as a string
			String field3 = dat[2].trim();// stores either the author or artist name as a string
			int price = Integer.parseInt(dat[3].trim());// Parse the price as an integer
			int quant = Integer.parseInt(dat[4].trim());// parse the quantity as an integer
			if (filename.equals("Books.txt")) {// if the items came from Books.txt
				Book temp = new Book(sNo, name, field3, price, quant);// make a new Book object
				dest.add(temp);// add the object to the array
			} else if (filename.equals("eBooks.txt")) {// if the items came from eBooks.txt
				eBook temp = new eBook(sNo, name, field3, price, quant);// make a new eBook object
				dest.add(temp);// add the object to the array
			} else if (filename.equals("CDs.txt")) {// if the items came from CDs.txt
				CD temp = new CD(sNo, name, field3, price, quant);// make a new  CD object
				dest.add(temp);// add the object to the array
			} else if (filename.equals("MP3.txt")) {// if the items came from  MP3.txt
				MP3 temp = new MP3(sNo, name, field3, price, quant);// make a  new MP3  object
				dest.add(temp);// add the object to the array
			}
		}
	}

	// this function reads a file and dumps out its contents into an arraylist  of Strings (1 string per line in the file)
	public static void fileRead(ArrayList<String> dest, String filename) {
		try {
			Scanner input = new Scanner(new File(filename));// make a scanner to  read the file
			while (input.hasNextLine()) {// while the file still has a next line
				dest.add(input.nextLine());// read the line as a string and add  it to the destination arraylist
			}
			input.close();// close the scanner at the end
		} catch (FileNotFoundException e) {// if the file cannot be found
			System.out.println("Required txt file missing: " + filename);// print  error message along with the missing filename
		}
	}

	// This function prints out a whole bunch of newline characters to "clear the page"
	public static void clearPage() {
		for (int i = 1; i <= 30; i++) {// do this 30 times
			System.out.println();// print a newline character
		}
	}
}
