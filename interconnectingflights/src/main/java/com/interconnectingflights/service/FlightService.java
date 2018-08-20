package com.interconnectingflights.service;

import java.util.List;
import java.util.Map;

import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.ScheduleDto;

public interface FlightService {

	/**
	 * @param departure
	 * @param arrival
	 * @param year
	 * @param month
	 * @param routesMap
	 * @return
	 */
	ScheduleDto getAllDirectSchedules(String departure, String arrival, String year, String month,
			Map<String, List<String>> routesMap);

	/**
	 * @param departure
	 * @param arrival
	 * @param year
	 * @param month
	 * @param routesMap
	 * @return
	 */
	List<Map<DepartureArrival, ScheduleDto>> getAllInterconnectedSchedules(String departure, String arrival, String year, String month,
			Map<String, List<String>> routesMap);

}
