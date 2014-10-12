package app.enigma.hackathon.object;

public class Block {
	String status;
	String reason;
	String email;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "Block [status=" + status + ", reason="
				+ reason + ", email=" + email + "]";
	}
}
