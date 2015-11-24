/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description:
*/

public abstract class Item {
	protected int price;
	protected int sNo;
	protected int quantity;
	protected String name;
	
	public abstract String getType();
	public abstract String getInfo();
	public abstract int getPrice();
	public abstract int getQuantity();
	public abstract String getName();
	public abstract int getsNo();
}
