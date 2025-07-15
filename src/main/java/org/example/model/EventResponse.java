package org.example.model;

public class EventResponse {
	private EventType eventType;
	private String payload;

	public EventResponse(EventType eventType, String payload){
		this.eventType=eventType;
		this.payload=payload;
	}

	@Override
	public String toString(){
		return eventType+"|"+payload;
	}
}
