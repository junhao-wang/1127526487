
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
