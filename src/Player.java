import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Player {
	String name;
	String refid;
	int buy;
	int sell;
	int profit;
	
	public Player(String tname,String trefid)
	{
		name=tname;
		refid=trefid;
		String url="http://theshownation.com/marketplace/listing?item_ref_id="+refid;
		try {
			Document page=Jsoup.connect(url).get();
			Elements BuyTable=page.select("table:has(th:contains(Buy Price))");
			Elements SellTable=page.select("table:has(th:contains(Sell Price))");
			try
			{
				buy=Integer.parseInt(BuyTable.select("td").first().text().replaceAll(",", ""));
				sell=Integer.parseInt(SellTable.select("td").first().text().replaceAll(",", ""));
				double pro=buy-(buy*.1)-sell;
				pro=Math.round(pro);
				profit=(int) pro;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		return "Name: "+name+" Buy Now: "+buy+" Sell Now: "+sell+" Profit: "+profit;
	}
	
	public String getID()
	{
		return refid;
	}
}
