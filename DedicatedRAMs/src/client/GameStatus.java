package client;

import java.util.Date;

public class GameStatus {
	public enum Status {NOTSTARTED, INPROGRESS, COMPLETED}; 
	private String gameResult;
	private Status status;
	private Date start = null;
	private Date end = null;
	
	public String getGameResult() {
		return gameResult;
	}
	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
