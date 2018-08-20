package com.interconnectingflights.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "airportFrom", "airportTo"})
public class RouteDto {

	@JsonProperty("airportFrom")
	private String airportFrom;
	@JsonProperty("airportTo")
	private String airportTo;


	@JsonProperty("airportFrom")
	public String getAirportFrom() {
		return airportFrom;
	}

	@JsonProperty("airportFrom")
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	@JsonProperty("airportTo")
	public String getAirportTo() {
		return airportTo;
	}

	@JsonProperty("airportTo")
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}



}