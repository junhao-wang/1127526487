
public class Book extends Readable{

	public Book(int sNo, String name, String authorName, int price, int quantity) {
		super(sNo, name, authorName, price, quantity);
		// TODO Auto-generated constructor stub
	}
	public int getPrice(){
		return super.getPrice(); //+super.getPrice()/50;
		/*Note: it DOES NOT MAKE SENSE to add the 2% environmental fee here!
		 *You print the price of each item and the total environmental fee SEPERATELY
		 *on the checkout page (P10). The item's BASE price is still displayed (see P10)
		 *throughout the check out process.
		*/
	}
	public String getType(){
		return "Book";
	}
	
	
	
	
}