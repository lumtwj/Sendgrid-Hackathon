package app.enigma.hackathon.object;

import java.util.Date;

public class ShowStatistic {
	Date date;
	int delivered;
	int requests;

	public ShowStatistic(Date date, int delivered, int requests) {
		super();
		this.date = date;
		this.delivered = delivered;
		this.requests = requests;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDelivered() {
		return delivered;
	}

	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}
}
