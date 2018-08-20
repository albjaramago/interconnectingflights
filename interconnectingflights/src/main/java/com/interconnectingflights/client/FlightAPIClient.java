package com.interconnectingflights.client;

import java.util.List;

import com.interconnectingflights.dto.RouteDto;
import com.interconnectingflights.dto.ScheduleDto;

public interface FlightAPIClient {
	
	
	
	/**
	 * @return
	 */
	List<RouteDto> getRoutes();
	
	/**
	 * @param departure
	 * @param arrival
	 * @param year
	 * @param month
	 * @return
	 */
	ScheduleDto getSchedules(String departure, String arrival, String year, String month);
	

}
