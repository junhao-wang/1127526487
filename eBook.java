
public class eBook extends Readable{

	public eBook(int sNo, String name, String authorName, int price, int quantity) {
		super(sNo, name, authorName, price, quantity);
	}
	public int getPrice(){
		return super.getPrice();
	}
	public String getType(){
		return "eBook";
	}
}

