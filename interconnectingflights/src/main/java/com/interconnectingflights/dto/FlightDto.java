package com.interconnectingflights.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "number", "departureTime", "arrivalTime" })
public class FlightDto {

	@JsonProperty("number")
	private String number;
	@JsonProperty("departureTime")
	private String departureTime;
	@JsonProperty("arrivalTime")
	private String arrivalTime;

	@JsonProperty("number")
	public String getNumber() {
		return number;
	}

	@JsonProperty("number")
	public void setNumber(String number) {
		this.number = number;
	}

	@JsonProperty("departureTime")
	public String getDepartureTime() {
		return departureTime;
	}

	@JsonProperty("departureTime")
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	@JsonProperty("arrivalTime")
	public String getArrivalTime() {
		return arrivalTime;
	}

	@JsonProperty("arrivalTime")
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	
	public FlightDto() {
		super();
	}

	public FlightDto(String number, String departureTime, String arrivalTime) {
		super();
		this.number = number;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	
	
}
