
public class Book extends Readable{ //class Book is a subclass of Readable

	public Book(int sNo, String name, String authorName, int price, int quantity) { 
		super(sNo, name, authorName, price, quantity);//inherit constructors from Readable
		
	}
	public int getPrice(){ //getter method to return Price of Book as an int.
		return super.getPrice(); //+super.getPrice()/50;
		/*Note: it DOES NOT MAKE SENSE to add the 2% environmental fee here!
		 *You print the price of each item and the total environmental fee SEPERATELY
		 *on the checkout page (P10). The item's BASE price is still displayed (see P10)
		 *throughout the check out process.
		*/
	}
	public String getType(){ //getter method to get Readable type as a String
		return "Book";  //method returns the Readable type as a String
	}
	
	
	
	
}
