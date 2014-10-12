
public class SendGridGraphObject {

	private String date;
	private int delivered;
	private int unsubscribes;
	private int invalidemail;
	private int bounces;
	private int repeatunsubscribes;
	private int uniqueClicks;
	private int blocked;
	private int spam_drop;
	private int repeatBounces;
	private int repeatSpamreports;
	private int request;
	private int spamreports;
	private int clicks;
	private int opens;
	private int uniqueopens;
	
	public SendGridGraphObject()
	{	
	}
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String d)
	{
		date = d;
	}
	
	public int getdelivered()
	{
		return delivered;
	}
	public void setdelivered(int d)
	{
		delivered = d;
	}
	
	public int getunsubscribes()
	{
		return unsubscribes;
	}
	public void setunsubscribes(int d)
	{
		unsubscribes = d;
	}
	
	public int getdinvalidemail()
	{
		return invalidemail;
	}
	public void setinvalidemail(int d)
	{
		invalidemail = d;
	}
	
	public int getbounces()
	{
		return bounces;
	}
	public void setbounces(int d)
	{
		bounces = d;
	}
	
	public int getrepeatunsubscribes()
	{
		return repeatunsubscribes;
	}
	public void setrepeatunsubscribes(int d)
	{
		repeatunsubscribes = d;
	}

	public int getuniqueClicks()
	{
		return uniqueClicks;
	}
	public void setuniqueClicks(int d)
	{
		uniqueClicks = d;
	}
	
	public int getblocked()
	{
		return blocked;
	}
	public void setblocked(int d)
	{
		blocked = d;
	}
	
	public int getspam_drop()
	{
		return spam_drop;
	}
	public void setspam_drop(int d)
	{
		spam_drop = d;
	}
	
	public int getrepeatBounces()
	{
		return repeatBounces;
	}
	public void setrepeatBounces(int d)
	{
		repeatBounces = d;
	}
	
	public int getrepeatSpamreports()
	{
		return repeatSpamreports;
	}
	public void setrepeatSpamreports(int d)
	{
		repeatSpamreports = d;
	}
	
	public int getrequest()
	{
		return request;
	}
	public void setrequest(int d)
	{
		request = d;
	}
	
	public int getspamreports()
	{
		return spamreports;
	}
	public void setspamreports(int d)
	{
		spamreports = d;
	}
	
	public int getclicks()
	{
		return clicks;
	}
	public void setclicks(int d)
	{
		clicks = d;
	}
	
	public int getopens()
	{
		return opens;
	}
	public void setopens(int d)
	{
		opens = d;
	}
	
	public int getuniqueopens()
	{
		return uniqueopens;
	}
	public void setuniqueopens(int d)
	{
		uniqueopens = d;
	}
}
