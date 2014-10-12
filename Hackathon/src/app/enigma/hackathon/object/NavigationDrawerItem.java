package app.enigma.hackathon.object;

import java.util.ArrayList;

import app.enigma.hackathon.R;

public class NavigationDrawerItem {
	public static final int BLOCK = 0;
	public static final int BOUNCE = 1;
	public static final int INVALID_EMAIL = 2;
	public static final int UNSUBSCRIBES = 3;
	public static final int SPAM_REPORT = 4;
	public static final int LOGOUT = 5;

	ArrayList<NavigationDrawerItem> al;
	int drawableResource;
	String title;

	public NavigationDrawerItem() {
		al = new ArrayList<NavigationDrawerItem> ();
	}

	private NavigationDrawerItem(int drawableResource, String title) {
		this.drawableResource = drawableResource;
		this.title = title;
	}

	public ArrayList<NavigationDrawerItem> getItem() {
		al.add(new NavigationDrawerItem(R.drawable.blocked, "Block"));
		al.add(new NavigationDrawerItem(R.drawable.bounce, "Bounce"));
		al.add(new NavigationDrawerItem(R.drawable.invalid, "Invalid email"));
		al.add(new NavigationDrawerItem(R.drawable.unsub, "Unsubscribes"));
		al.add(new NavigationDrawerItem(R.drawable.spam, "Spam report"));
		al.add(new NavigationDrawerItem(R.drawable.logout, "Logout"));

		return al;
	}

	public int getDrawableResource() {
		return drawableResource;
	}

	public void setDrawableResource(int drawableResource) {
		this.drawableResource = drawableResource;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
