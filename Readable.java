
public class Readable extends Item{
	protected String authorName;
	public String getInfo(){
		String s = String.valueOf(this.sNo) +","+this.name+","+this.authorName+","+String.valueOf(this.price)+","+String.valueOf(this.quantity);
		return s;
		
	}
	public Readable(int sNo,String name,String authorName,int price,int quantity){
		this.sNo = sNo;
		this.price = price;
		this.authorName = authorName;
		this.quantity = quantity;
		this.name = name;
	}
	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return this.quantity;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	@Override
	public int getsNo() {
		// TODO Auto-generated method stub
		return this.sNo;
	}
	public String getAuthor(){
		return this.authorName;
	}

	public void subQuant(int x){
		this.quantity = this.quantity - x;
	}
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
