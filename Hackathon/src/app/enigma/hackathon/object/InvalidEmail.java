package app.enigma.hackathon.object;

public class InvalidEmail {
	String created;
	String reason;
	String email;

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "InvalidEmail [created=" + created + ", reason=" + reason
				+ ", email=" + email + "]";
	}
}
