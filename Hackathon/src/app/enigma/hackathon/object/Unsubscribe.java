package app.enigma.hackathon.object;

public class Unsubscribe {
	String email;
	String created;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Unsubscribe [email=" + email + ", created=" + created + "]";
	}
}
