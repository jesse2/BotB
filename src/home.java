import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class home {
	static LinkedList<Player> Players;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Players=new LinkedList<Player>();
		JFrame frame= new JFrame();
		JPanel panel=new JPanel();
		JLabel minP=new JLabel("Enter Min Price");
		JLabel maxP=new JLabel("Enter Max Price");
		JTextField minF=new JTextField();
		minF.setPreferredSize(new Dimension(50,25));
		JTextField maxF=new JTextField();
		maxF.setPreferredSize(new Dimension(50,25));
		JTextArea results=new JTextArea();
		results.setPreferredSize(new Dimension(680,300));
		JButton search=new JButton("Search");
		search.addActionListener( new ActionListener(){
			
			public void actionPerformed (ActionEvent e)
			{
				Players.clear();
				results.setText("");
				int min=Integer.parseInt(minF.getText());
				int max=Integer.parseInt(maxF.getText());
				int pages=getPages(min,max);
				getPlayers(pages,min,max);
				if(!Players.isEmpty())
				{
					for(int i=0;i<Players.size();i++)
					{
						results.append(Players.get(i).toString()+"\n");
					}
				}
				
				
			}
		});		
		panel.add(minP);
		panel.add(minF);
		panel.add(maxP);
		panel.add(maxF);
		panel.add(search);
		panel.add(results);
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public static int getPages(int min, int max)
	{
		int count=0;
		Document source = null;
		try {
			source = Jsoup.connect("http://theshownation.com/marketplace/search?&main_filter=MLB+Cards&display_name=&min_price="+min+"&max_price="+max)
					.get();
			Elements pages=source.select("a[href*=page]");			
			if(pages.size()>1)
			{
				Element line=pages.get(pages.size()-2);
				count=Integer.parseInt(line.text());
			}
			else
			{
				count=1;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return count;
	}

	public static void getPlayers(int size,int min, int max)
	{
		if(size>0)
		{
			for(int i=1; i<=size;i++)
			{
				String url="http://theshownation.com/marketplace/search?page="+i+"&main_filter=MLB Cards&min_price="+min+"&max_price="+max;
				Document result = null;
				try {
					result = Jsoup.connect(url).get();
					Elements playerLinks=result.select("a[href*=ref_id]");
					for(Element n:playerLinks)
					{
						String refidhtml=n.outerHtml();
						int index=refidhtml.indexOf("d=",0)+2;
						String refid=refidhtml.substring(index, index+5);
						String name=n.text();
						System.out.println("Name: "+name+" ID: "+refid);
						Player player=new Player(name,refid);
						Players.add(player);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
	}
	
}
