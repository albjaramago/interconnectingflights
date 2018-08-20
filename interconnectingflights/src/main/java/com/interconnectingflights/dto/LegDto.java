package com.interconnectingflights.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "departureAirport", "arrivalAirport", "departureDateTime", "arrivalDateTime" })
public class LegDto {

	@JsonProperty("departureAirport")
	private String departureAirport;
	@JsonProperty("arrivalAirport")
	private String arrivalAirport;
	@JsonProperty("departureDateTime")
	private String departureDateTime;
	@JsonProperty("arrivalDateTime")
	private String arrivalDateTime;


	@JsonProperty("departureAirport")
	public String getDepartureAirport() {
		return departureAirport;
	}

	@JsonProperty("departureAirport")
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	@JsonProperty("arrivalAirport")
	public String getArrivalAirport() {
		return arrivalAirport;
	}

	@JsonProperty("arrivalAirport")
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	@JsonProperty("departureDateTime")
	public String getDepartureDateTime() {
		return departureDateTime;
	}

	@JsonProperty("departureDateTime")
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	@JsonProperty("arrivalDateTime")
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}

	@JsonProperty("arrivalDateTime")
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	
	public LegDto() {
		super();
	}

	public LegDto(String departureAirport, String arrivalAirport, String departureDateTime, String arrivalDateTime) {
		super();
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}
	
	
}