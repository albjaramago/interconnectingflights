package com.interconnectingflights.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "stops", "legs" })
public class InterconnectedDto {

	@JsonProperty("stops")
	private Integer stops;
	@JsonProperty("legs")
	private List<LegDto> legs;


	@JsonProperty("stops")
	public Integer getStops() {
		return stops;
	}

	@JsonProperty("stops")
	public void setStops(Integer stops) {
		this.stops = stops;
	}

	@JsonProperty("legs")
	public List<LegDto> getLegs() {
		return legs;
	}

	@JsonProperty("legs")
	public void setLegs(List<LegDto> legs) {
		this.legs = legs;
	}

	
	public InterconnectedDto() {
		super();
	}

	public InterconnectedDto(Integer stops, List<LegDto> legs) {
		super();
		this.stops = stops;
		this.legs = legs;
	}
	
	
}