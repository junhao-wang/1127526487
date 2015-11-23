
public class Audio extends Item {
	protected String artistName;
	public Audio(int sNo,String name,String artistName,int price,int quantity ){
		this.sNo = sNo;
		this.price = price;
		this.artistName = artistName;
		this.quantity = quantity;
		this.name = name;
	}
	
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
	public String getArtist(){
		return this.artistName;
	}

	@Override
	public String getInfo() {
		String s = String.valueOf(this.sNo) +","+this.name+","+this.artistName+","+String.valueOf(this.price)+","+String.valueOf(this.quantity);
		return s;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void subQuant(int x){
		this.quantity = this.quantity - x;
	}

}
