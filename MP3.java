
public class MP3 extends Audio {

	public MP3(int sNo, String name, String artistName, int price, int quantity) {
		super(sNo, name, artistName, price, quantity);
		// TODO Auto-generated constructor stub
	}
	public int getPrice(){
		return super.getPrice();
	}
	public String getType(){
		return "MP3";
	}

}