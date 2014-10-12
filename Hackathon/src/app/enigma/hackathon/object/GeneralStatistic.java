package app.enigma.hackathon.object;

public class GeneralStatistic {
	String date;
	int delivered;
	int unsubscribes;
	int invalid_email;
	int bounces;
	int repeat_unsubscribes;
	int unique_clicks;
	int blocked;
	int spam_drop;
	int repeat_bounces;
	int repeat_spamreports;
	int requests;
	int spamreports;
	int clicks;
	int opens;
	int unique_opens;

	public GeneralStatistic(int delivered, int unsubscribes, int bounces,
			int unique_clicks, int clicks, int opens, int unique_opens) {
		super();
		this.delivered = delivered;
		this.unsubscribes = unsubscribes;
		this.bounces = bounces;
		this.unique_clicks = unique_clicks;
		this.clicks = clicks;
		this.opens = opens;
		this.unique_opens = unique_opens;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDelivered() {
		return delivered;
	}
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	public int getUnsubscribes() {
		return unsubscribes;
	}
	public void setUnsubscribes(int unsubscribes) {
		this.unsubscribes = unsubscribes;
	}
	public int getInvalid_email() {
		return invalid_email;
	}
	public void setInvalid_email(int invalid_email) {
		this.invalid_email = invalid_email;
	}
	public int getBounces() {
		return bounces;
	}
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	public int getRepeat_unsubscribes() {
		return repeat_unsubscribes;
	}
	public void setRepeat_unsubscribes(int repeat_unsubscribes) {
		this.repeat_unsubscribes = repeat_unsubscribes;
	}
	public int getUnique_clicks() {
		return unique_clicks;
	}
	public void setUnique_clicks(int unique_clicks) {
		this.unique_clicks = unique_clicks;
	}
	public int getBlocked() {
		return blocked;
	}
	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}
	public int getSpam_drop() {
		return spam_drop;
	}
	public void setSpam_drop(int spam_drop) {
		this.spam_drop = spam_drop;
	}
	public int getRepeat_bounces() {
		return repeat_bounces;
	}
	public void setRepeat_bounces(int repeat_bounces) {
		this.repeat_bounces = repeat_bounces;
	}
	public int getRepeat_spamreports() {
		return repeat_spamreports;
	}
	public void setRepeat_spamreports(int repeat_spamreports) {
		this.repeat_spamreports = repeat_spamreports;
	}
	public int getRequests() {
		return requests;
	}
	public void setRequests(int requests) {
		this.requests = requests;
	}
	public int getSpamreports() {
		return spamreports;
	}
	public void setSpamreports(int spamreports) {
		this.spamreports = spamreports;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	public int getOpens() {
		return opens;
	}
	public void setOpens(int opens) {
		this.opens = opens;
	}
	public int getUnique_opens() {
		return unique_opens;
	}
	public void setUnique_opens(int unique_opens) {
		this.unique_opens = unique_opens;
	}
	@Override
	public String toString() {
		return "GeneralStatistic [date=" + date + ", delivered=" + delivered
				+ ", unsubscribes=" + unsubscribes + ", invalid_email="
				+ invalid_email + ", bounces=" + bounces
				+ ", repeat_unsubscribes=" + repeat_unsubscribes
				+ ", unique_clicks=" + unique_clicks + ", blocked=" + blocked
				+ ", spam_drop=" + spam_drop + ", repeat_bounces="
				+ repeat_bounces + ", repeat_spamreports=" + repeat_spamreports
				+ ", requests=" + requests + ", spamreports=" + spamreports
				+ ", clicks=" + clicks + ", opens=" + opens + ", unique_opens="
				+ unique_opens + "]";
	}
}
