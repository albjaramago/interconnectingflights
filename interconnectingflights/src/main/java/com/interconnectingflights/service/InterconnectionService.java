package com.interconnectingflights.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.InterconnectedDto;
import com.interconnectingflights.dto.LegDto;
import com.interconnectingflights.dto.ScheduleDto;

public interface InterconnectionService {

	/**
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param allDirect
	 * @return
	 */
	List<LegDto> getAllDirectLegs(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime, ScheduleDto allDirect);

	
	/**
	 * @param availableLegsMap
	 * @return
	 */
	List<InterconnectedDto> getAllInterconnected(Map<DepartureArrival, List<LegDto>> availableLegsMap);

}
