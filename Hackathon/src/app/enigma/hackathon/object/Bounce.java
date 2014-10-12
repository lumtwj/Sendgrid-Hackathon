package app.enigma.hackathon.object;

public class Bounce {
	String status;
	String created;
	String reason;
	String email;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
		return "Block [status=" + status + ", created=" + created + ", reason="
				+ reason + ", email=" + email + "]";
	}
}
