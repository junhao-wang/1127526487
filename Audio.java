/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: Readable class has methods for getting information (price, type, quantity etc...) about the audio item.
*/

//Audio is a subclass of Item, representing CD and MP3
public class Audio extends Item {
	protected String artistName;
	public Audio(int sNo,String name,String artistName,int price,int quantity ){ //constructor
		/*
		Initialize variables
		*/
		this.sNo = sNo;
		this.price = price;
		this.artistName = artistName;
		this.quantity = quantity;
		this.name = name;
	}
	
	public int getPrice() {
		//returns price
		return this.price;
	}
	@Override
	public int getQuantity() {
		//returns quantity
		return this.quantity;
	}
	@Override
	public String getName() {
		//returns name
		return this.name;
	}
	@Override
	public int getsNo() {
		// returns serial number
		return this.sNo;
	}
	public String getArtist(){
		//returns the artistname
		return this.artistName;
	}

	@Override
	public String getInfo() {
		//outputs the string "sNo,name,artistName,price,quantity"
		String s = String.valueOf(this.sNo) +","+this.name+","+this.artistName+","+String.valueOf(this.price)+","+String.valueOf(this.quantity);
		return s; //return the string of info
	}

	@Override
	public String getType() {
		// This method is meant to be overriden by Book and eBook class
		return null;
	}

	public void subQuant(int x){ //method to decrease the quantity by x
		this.quantity = this.quantity - x; //quantity decreases by x
	}
	public void addQuant(int x){ //method to increase the quantity by x
		this.quantity=this.quantity+x; //quantity increases by x
	}

}
