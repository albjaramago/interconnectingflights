package com.interconnectingflights.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "month", "days" })
public class ScheduleDto {

	@JsonProperty("month")
	private Integer month;
	@JsonProperty("days")
	private List<DayDto> days;


	@JsonProperty("month")
	public Integer getMonth() {
		return month;
	}

	@JsonProperty("month")
	public void setMonth(Integer month) {
		this.month = month;
	}

	@JsonProperty("days")
	public List<DayDto> getDays() {
		return days;
	}

	@JsonProperty("days")
	public void setDays(List<DayDto> days) {
		this.days = days;
	}
	

	public ScheduleDto() {
		super();
	}

	public ScheduleDto(Integer month, List<DayDto> days) {
		super();
		this.month = month;
		this.days = days;
	}
	
	
	


}