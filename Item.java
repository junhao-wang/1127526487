/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: Item is an abstract class with abstract methods for getting information (price, type, quantity etc...) about the item. 
*/

public abstract class Item { //Item class is abstract and cannot be instantiated
	protected int price; //declare variable for price
	protected int sNo; //declare variable for serial number
	protected int quantity; //declare variable for quantity
	protected String name; //declare variable for name
	
	public abstract String getType(); //abstract method for getting item type
	public abstract String getInfo(); //abstract method for getting info
	public abstract int getPrice(); //abstract method for getting price
	public abstract int getQuantity();//abstract method for getting quantity
	public abstract String getName();//abstract method for getting name
	public abstract int getsNo();//abstract method for getting serial number
}
