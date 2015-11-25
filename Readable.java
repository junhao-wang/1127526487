/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: Readable class has methods for getting information (price, type, quantity etc...) about the readable item.
*/

//Readable is a subclass of Item, representing Books and eBooks
public class Readable extends Item{ 
	protected String authorName; //declare authorName instance variable
	public String getInfo(){ //getter method that returns sNo, Name, Author etc... in a String
		String s = String.valueOf(this.sNo) +","+this.name+","+this.authorName+","+String.valueOf(this.price)+","+String.valueOf(this.quantity); //initialize String
		return s; //return String
		
	}
	public Readable(int sNo,String name,String authorName,int price,int quantity){ //constructor
		this.sNo = sNo; //set sNo 
		this.price = price; //set price
		this.authorName = authorName; //set author
		this.quantity = quantity; //set quantity
		this.name = name; //set name
	}
	@Override
	public int getPrice() {//getter method to returns price
		// TODO Auto-generated method stub
		return this.price; //return price of object
	}
	@Override
	public int getQuantity() { //getter method to return quantity
		// TODO Auto-generated method stub
		return this.quantity; //return quantity of object
	}
	@Override
	public String getName() {  //getter method to return name
		// TODO Auto-generated method stub
		return this.name; //return name of object
	}
	@Override
	public int getsNo() { //getter method to return sNo
		// TODO Auto-generated method stub
		return this.sNo; //return sNo of object
	}
	public String getAuthor(){ //getter method to return author
		return this.authorName; //return author of object
	}

	public void subQuant(int x){ //method to reduce quantity by x
		this.quantity = this.quantity - x; //quantity decreases by x
	}
	
	public void addQuant(int x){ //method to increase quantity by x
		this.quantity= this.quantity+x; //quantity increases by x
	}
	public String getType() { //return Type
		// TODO Auto-generated method stub
		return null; //will be overridden by eBook and Book classes so returns null.
	}
}
