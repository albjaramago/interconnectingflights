package com.interconnectingflights.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "day", "flights" })
public class DayDto {

	@JsonProperty("day")
	private Integer day;
	@JsonProperty("flights")
	private List<FlightDto> flights;


	@JsonProperty("day")
	public Integer getDay() {
		return day;
	}

	@JsonProperty("day")
	public void setDay(Integer day) {
		this.day = day;
	}

	@JsonProperty("flights")
	public List<FlightDto> getFlights() {
		return flights;
	}

	@JsonProperty("flights")
	public void setFlights(List<FlightDto> flights) {
		this.flights = flights;
	}
	
	
	public DayDto() {
		super();
	}

	public DayDto(Integer day, List<FlightDto> flights) {
		super();
		this.day = day;
		this.flights = flights;
	}
	


}