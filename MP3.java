/*
*Name: Junhao Wang, Colin Vandenhof, Teshaun Murray
*MacID: wangjh2, vandencm, murrayts
*Student Number: 1215428, 1231644, 1227515
*Description: MP3 class has methods to return price and audio type.
*/

//MP3 class is a subset of Audio
public class MP3 extends Audio { 

	public MP3(int sNo, String name, String artistName, int price, int quantity) {
		super(sNo, name, artistName, price, quantity); //inherit the Audio constructors
		// TODO Auto-generated constructor stub
	}
	public int getPrice(){ //method for price
		return super.getPrice(); //+super.getPrice()/50;
	}
	public String getType(){ //method to return the Audio type as a String
		return "MP3"; //method the Audio type as a String
	}

}
