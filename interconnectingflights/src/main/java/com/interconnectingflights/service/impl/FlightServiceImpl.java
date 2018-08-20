package com.interconnectingflights.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.interconnectingflights.client.FlightAPIClient;
import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.ScheduleDto;
import com.interconnectingflights.exception.ExceptionMessage;
import com.interconnectingflights.exception.InterconnectingFlightsException;
import com.interconnectingflights.service.FlightService;


@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	FlightAPIClient flightAPIClient;
	
	
	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.FlightService#getAllDirectSchedules(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map) 
	 *
	 *All Direct Schedules
	 */
	@Override
	@Cacheable("allDirectShedules")
	public ScheduleDto getAllDirectSchedules(String departure, String arrival, String year,
			String month, Map<String, List<String>> routesMap) {

		List<String> departureList = routesMap.get(arrival);
	
		ScheduleDto schedules = null;
		
		//Calling the flight API Client to get the Schedule of a direct flight.
		if(departureList.contains(departure))
			schedules = flightAPIClient.getSchedules(departure, arrival, year, month);
			
		
		return schedules;
	}

	
	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.FlightService#getAllInterconnectedSchedules(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 *
	 *All Interconnected Schedules
	 */
	@Override
	@Cacheable("allInterconnectedShedules")
	public List<Map<DepartureArrival, ScheduleDto>> getAllInterconnectedSchedules(String departure, String arrival, String year,
			String month, Map<String, List<String>> routesMap) {
		
		List<String> connectionList;
		List<Map<DepartureArrival, ScheduleDto>> interconnectionScheduleMapList = new ArrayList<Map<DepartureArrival, ScheduleDto>>();
		
		
		if(routesMap.containsKey(arrival)) 
			connectionList = routesMap.get(arrival);
		else
			throw new InterconnectingFlightsException(ExceptionMessage.ARRIVAL_IATA_CODE_NOT_EXISTING.getValue());
		
		
		connectionList.forEach(connection ->{
			
			if(routesMap.containsKey(connection) && routesMap.get(connection).contains(departure)) {
				
				ScheduleDto scheduleDto;
				Map<DepartureArrival, ScheduleDto> connectionScheduleMap = new LinkedHashMap <DepartureArrival, ScheduleDto>();
				
				//Calling the flight API Client to get the Schedule of the interconnected flight.
				//The first Schedule matches with the route from the departure airport to the connection airport.
				scheduleDto = flightAPIClient.getSchedules(departure, connection, year, month);
				if(scheduleDto != null)
					connectionScheduleMap.put(new DepartureArrival(departure,connection), scheduleDto);
				
				//The second Schedule mathes with the route from the connection airport to the arrival airport.
				scheduleDto = flightAPIClient.getSchedules(connection, arrival, year, month);
				if(scheduleDto != null)
					connectionScheduleMap.put(new DepartureArrival(connection,arrival), scheduleDto);
				
				interconnectionScheduleMapList.add(connectionScheduleMap);
			}
				
				
		});
		
		return interconnectionScheduleMapList;
	}

}
