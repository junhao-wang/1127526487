/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description:CD class has methods to return price and audio type.
*/

public class CD extends Audio { //class CD is a subclass of Audio

	public CD(int sNo, String name, String artistName, int price, int quantity) {
		super(sNo, name, artistName, price, quantity); //inherit constructors from Audio
		// TODO Auto-generated constructor stub
	}
	public int getPrice(){ //method to return Price of CD as an int
		return super.getPrice(); //+super.getPrice()/50;
		/*Note: it DOES NOT MAKE SENSE to add the 2% environmental fee here!
		 *You print the price of each item and the total environmental fee SEPERATELY
		 *on the checkout page (P10). The item's BASE price is still displayed (see P10)
		 *throughout the check out process.
		*/
	}
	public String getType(){ //method to return Audio type as String
		return "CD";  //returns the Audio type as a String
	}

}
