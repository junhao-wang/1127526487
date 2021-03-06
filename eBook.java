/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description:eBook class has methods to return price and readable type.
*/

//class eBook is a subclass of Readable
public class eBook extends Readable{ 

	public eBook(int sNo, String name, String authorName, int price, int quantity) {
		super(sNo, name, authorName, price, quantity); //inherit constructors from Readable
	}
	public int getPrice(){//method to return Price of Book as an int.
		return super.getPrice(); //+super.getPrice()/50;
	}
	public String getType(){ //method to get Readable type as a String
		return "eBook"; //method returns the Readable type as a String
	}
}

