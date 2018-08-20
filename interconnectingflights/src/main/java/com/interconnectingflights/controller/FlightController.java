package com.interconnectingflights.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.interconnectingflights.dto.InterconnectedDto;

public interface FlightController {

	
	/**
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 */
	List<InterconnectedDto> getAllDirect(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime);
	
	
	/**
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 */
	List<InterconnectedDto> getAllInterconnected(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime);
}
