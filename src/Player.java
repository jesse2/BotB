
public class Player {
	String name;
	String refid;
	int buy;
	int sell;
	double profit;
	
	public Player(String tname,String trefid)
	{
		name=tname;
		refid=trefid;
		buy=0;
		sell=0;
		profit=0;
	}
	
	public String toString()
	{
		return "Name: "+name+" ID: "+refid;
	}
}
