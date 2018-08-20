package com.interconnectingflights.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.InterconnectedDto;
import com.interconnectingflights.dto.LegDto;
import com.interconnectingflights.dto.ScheduleDto;
import com.interconnectingflights.service.FlightService;
import com.interconnectingflights.service.InterconnectionService;
import com.interconnectingflights.service.RouteService;
import com.interconnectingflights.service.ValidationService;

@Controller
public class FlightControllerImpl implements FlightController{

	@Autowired
	ValidationService validationService;
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	InterconnectionService interconnectionService;

	/* (non-Javadoc)
	 * @see com.interconnectingflights.controller.FlightController#getAllDirect(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)
	 *
	 *All Direct
	 */
	@Override
	@Cacheable("directFlights")
	public List<InterconnectedDto> getAllDirect(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		
		
		validationService.checkIATACodes(departure, arrival);	
		validationService.checkDateTimes(departureDateTime, arrivalDateTime);

		//Map of routes.
		//The key is the arrival IATA airport code.
		//The value is a list containing all IATA departure airport codes.  
		//Routes are direct between the airport key and each airport in the value list.
		Map<String, List<String>> routesMap = routeService.getRoutesMap();
		
		List<LegDto> allDirectLegs = new ArrayList<LegDto>();

		int month = departureDateTime.getMonthValue();
		int year = departureDateTime.getYear();
		
		LocalDateTime auxDepartureDateTime = departureDateTime;
		LocalDateTime auxArrivalDateTime = arrivalDateTime;
		
		
		//When the departureDateTime and the arrivalDateTime they don't match in its month or year,
		//flights are searched for every month and year in the period of dates.
		while (month <= arrivalDateTime.getMonthValue() || year != arrivalDateTime.getYear()) {

			//Getting Schedules of direct flights
			ScheduleDto allDirectSchedules = flightService.getAllDirectSchedules(departure, arrival,
					String.valueOf(year), String.valueOf(month), routesMap);
			
			if (month != arrivalDateTime.getMonthValue() || year != arrivalDateTime.getYear())
				auxArrivalDateTime = LocalDateTime.of(year, month, 31, 23, 59);		
			else 
				auxArrivalDateTime = arrivalDateTime;
	
			//Getting legs from the Schedules.	
			if (allDirectSchedules != null)
				interconnectionService
						.getAllDirectLegs(departure, arrival, auxDepartureDateTime, auxArrivalDateTime, allDirectSchedules)
						.forEach(leg -> allDirectLegs.add(leg));

		
			
			if (++month > 12) {
				month = 1;
				year++;
			}
			
			auxDepartureDateTime = LocalDateTime.of(year, month, 1, 0, 0);
		}


		List<InterconnectedDto> allDirect = new ArrayList<InterconnectedDto>();

		allDirect.add(new InterconnectedDto(new Integer(0), allDirectLegs));

		return allDirect;

	}
	
	

	/* (non-Javadoc)
	 * @see com.interconnectingflights.controller.FlightController#getAllInterconnected(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)
	 *
	 *All Interconnected
	 */
	@Override
	@Cacheable("interconnectedFlights")
	public List<InterconnectedDto> getAllInterconnected(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {

		//Map of routes.
		//The key is the arrival IATA airport code.
		//The value is a list containing all IATA departure airport codes.  
		//Routes are direct between the airport key and each airport in the value list.
		Map<String, List<String>> routesMap = routeService.getRoutesMap();
		
		List<InterconnectedDto> allInterconnected = new ArrayList<InterconnectedDto>();

		int month = departureDateTime.getMonthValue();
		int year = departureDateTime.getYear();
		
		LocalDateTime auxDepartureDateTime = departureDateTime;
		LocalDateTime auxArrivalDateTime = arrivalDateTime;
		
		//When departureDateTime arrivalDateTime they don't match in its month or year,
		//flights are searched for every month and year in the period of dates
		while (month <= arrivalDateTime.getMonthValue() || year != arrivalDateTime.getYear()) {

			
			//Getting the Map of Schedules for interconnected flights from the list.
			//In the list, each Map contains two items.
			//The first item of the Map is the flight from the departure airport to the connection airport.
			//The second item of the Map is the flight from the connection airport to the arrival airport.
			List<Map<DepartureArrival, ScheduleDto>> allInterconnectedScheduleMapList = flightService
					.getAllInterconnectedSchedules(departure, arrival, String.valueOf(year),
							String.valueOf(month), routesMap);
			
			
			if(month != arrivalDateTime.getMonthValue() || year != arrivalDateTime.getYear())
				auxArrivalDateTime = LocalDateTime.of(year, month, 31, 23, 59);
			else
				auxArrivalDateTime = arrivalDateTime;
			
		
			//Getting each Map of interconnected flights from the list.
			for(Map<DepartureArrival, ScheduleDto> interconnectedScheduleMap:allInterconnectedScheduleMapList) {

				Map<DepartureArrival, List<LegDto>> availableLegsMap = new LinkedHashMap<DepartureArrival, List<LegDto>>();

				//Getting the entries of interconnected flights from the Map.
				//First entry is the Schedule from the departure airport to connection airport.
				//Second entry is the Schedule from the connection airport to arrival airport.
				for(Map.Entry<DepartureArrival, ScheduleDto> interconnectedSchedule:interconnectedScheduleMap.entrySet()) {

					DepartureArrival departureArrival = interconnectedSchedule.getKey();
					ScheduleDto connectionSchedule = interconnectedSchedule.getValue();

					if (connectionSchedule != null) {
						
						//Getting the legs from the Schedules.	
						List<LegDto> availableLegs = interconnectionService
								.getAllDirectLegs(departureArrival.getDeparture(), departureArrival.getArrival(),
										auxDepartureDateTime, auxArrivalDateTime, connectionSchedule);
				
					
						if (availableLegsMap.containsKey(departureArrival)) {

							List<LegDto> concatlLegs = Stream
									.concat(availableLegsMap.get(departureArrival).stream(), availableLegs.stream())
									.collect(Collectors.toList());
							
							availableLegsMap.put(departureArrival, concatlLegs);
						}else	
							availableLegsMap.put(departureArrival, availableLegs);
						
					}

				} 

				List<InterconnectedDto> interconnectedLegs = new ArrayList<InterconnectedDto>();
				
				//In the interconnectedLegs list, only legs that fulfil the minimal two hours of difference
				//are added to the list.
				if (availableLegsMap.size() == 2) {
					interconnectedLegs = interconnectionService.getAllInterconnected(availableLegsMap);
					interconnectedLegs.forEach(interconnected -> allInterconnected.add(interconnected));
				}

			}
			
			if (++month > 12) {
				month = 1;
				year++;
			}
			auxDepartureDateTime = LocalDateTime.of(year, month, 1, 0, 0);

		}

		return allInterconnected;
	}

	


}
