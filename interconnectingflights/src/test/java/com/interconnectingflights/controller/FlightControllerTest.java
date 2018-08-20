package com.interconnectingflights.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.interconnectingflights.dto.DayDto;
import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.FlightDto;
import com.interconnectingflights.dto.InterconnectedDto;
import com.interconnectingflights.dto.LegDto;
import com.interconnectingflights.dto.ScheduleDto;
import com.interconnectingflights.service.FlightService;
import com.interconnectingflights.service.InterconnectionService;
import com.interconnectingflights.service.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightControllerTest {
	
	@MockBean
	RouteService routeService;
	
	@MockBean
	FlightService flightService;
	
	@MockBean
	InterconnectionService interconnectionService;

	@Autowired
	FlightController flightController;
	
	
	@Test
	public void getAllDirectTest() {
		
		String departure = "DUB";
		String arrival = "WRO";
		
		String year = "2018";
		String month = "9";
		
		String flightNumber = "1926";
		String departureTime = "17:25";
		String arrivalTime = "21:00";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse("2018-09-02T00:00", formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse("2018-09-02T23:00", formatter);
        
		ScheduleDto scheduleDto = this.getScheduleDto(departure, arrival, year, month, flightNumber, departureTime,
				arrivalTime);

		List<LegDto> legs = new ArrayList<LegDto>();
		legs.add(new LegDto(departure, arrival, "2018-09-02T17:25", "2018-09-02T21:00"));
		
		
		//RouteService mock
		when(routeService.getRoutesMap()).thenReturn(this.getRouteMap());

		//FlightService mock
		when(flightService.getAllDirectSchedules(departure, arrival, year, month, this.getRouteMap()))
				.thenReturn(scheduleDto);
		
		//InterconnectionService mock
		when(interconnectionService.getAllDirectLegs(departure, arrival, departureDateTime, arrivalDateTime, scheduleDto))
				.thenReturn(legs);

		List<InterconnectedDto> allDirect = flightController.getAllDirect(departure, arrival,
				departureDateTime, arrivalDateTime);
		
		assertTrue(!allDirect.isEmpty());
	}
	
	
	@Test
	public void getAllInterconnectedTest() {
		
		String departure1 = "DUB";
		String arrival1 = "LBA";
		
		String flightNumber1 = "1934";
		String departureTime1 = "08:00";
		String arrivalTime1 = "09:05";
		
		String departure2 = "LBA";
		String arrival2 = "WRO";
		
		String flightNumber2 = "1362";
		String departureTime2 = "14:15";
		String arrivalTime2 = "17:30";
		
		String year = "2018";
		String month = "9";
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse("2018-09-02T00:00", formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse("2018-09-02T23:00", formatter);
        
        ScheduleDto schedule1 = this.getScheduleDto(departure1, arrival1, year, month, flightNumber1, departureTime1,
				arrivalTime1);
        
        ScheduleDto schedule2 = this.getScheduleDto(departure2, arrival2, year, month, flightNumber2, departureTime2,
				arrivalTime2);
        
        Map<DepartureArrival, ScheduleDto> interconnectedSchedulesMap = new LinkedHashMap<DepartureArrival, ScheduleDto>();
		interconnectedSchedulesMap.put(new DepartureArrival(departure1, arrival1), schedule1);
		interconnectedSchedulesMap.put(new DepartureArrival(departure2, arrival2), schedule2);
		
		List<Map<DepartureArrival, ScheduleDto>> interconnectedSchedulesMapList = new ArrayList<Map<DepartureArrival, ScheduleDto>>();
		interconnectedSchedulesMapList.add(interconnectedSchedulesMap);

		List<LegDto> legs1 = new ArrayList<LegDto>();
		legs1.add(new LegDto(departure1, arrival1, "2018-09-02T08:00", "2018-09-02T09:05"));
		List<LegDto> legs2 = new ArrayList<LegDto>();
		legs1.add(new LegDto(departure2, arrival2, "2018-09-02T14:15", "2018-09-02T17:30"));
		
		List<LegDto> legs = new ArrayList<LegDto>();
		legs.add(new LegDto(departure1, arrival1, "2018-09-02T08:00", "2018-09-02T09:05"));
		legs.add(new LegDto(departure2, arrival2, "2018-09-02T14:15", "2018-09-02T17:30"));
		
		Map<DepartureArrival, List<LegDto>> availableLegsMap = new LinkedHashMap<DepartureArrival, List<LegDto>>();
		availableLegsMap.put(new DepartureArrival(departure1, arrival1), legs1);
		availableLegsMap.put(new DepartureArrival(departure2, arrival2), legs2);
	
		List<InterconnectedDto> interconnectedLegs = new ArrayList<InterconnectedDto>();
		interconnectedLegs.add(new InterconnectedDto(new Integer(1),legs));
		
		
		// RouteService mock
		when(routeService.getRoutesMap()).thenReturn(this.getRouteMap());

		// FlightService mock
		when(flightService.getAllInterconnectedSchedules(departure1, arrival2, year, month, this.getRouteMap()))
				.thenReturn(interconnectedSchedulesMapList);

		// InterconnectionService mock
		when(interconnectionService.getAllDirectLegs(departure1, arrival1, departureDateTime, arrivalDateTime,
				schedule1)).thenReturn(legs1);
		when(interconnectionService.getAllInterconnected(availableLegsMap)).thenReturn(interconnectedLegs);

		
		List<InterconnectedDto> allInterconnected = flightController.getAllInterconnected(departure1, arrival2,
				departureDateTime, arrivalDateTime);

		assertTrue(!allInterconnected.isEmpty());
		
	}
	
	
	
	private Map<String, List<String>> getRouteMap(){
		
		
		//departure airports to "WRO"
		List<String> departureAirports1 = new ArrayList<String>();
		departureAirports1.add("DUB");
		departureAirports1.add("RYG");
		departureAirports1.add("ALC");
		
		//Departure airports to "RYG"
		List<String> departureAirports2 = new ArrayList<String>();
		departureAirports1.add("POZ");
		departureAirports1.add("GDN");
		departureAirports1.add("KRK");
		
		//Departure airports to "ALC"
		List<String> departureAirports3 = new ArrayList<String>();
		departureAirports1.add("DUB");
		departureAirports1.add("ABZ");
		departureAirports1.add("PMO");
		

		Map<String, List<String>> routesMap = new HashMap<String, List<String>>();
		
		routesMap.put("WRO", departureAirports1);
		routesMap.put("RYG", departureAirports2);
		routesMap.put("ALC", departureAirports3);
		
		
		return routesMap;
	}
	
	
	private ScheduleDto getScheduleDto(String departure, String arrival, String year, String month, String flightNumber,
			String departureTime, String arrivalTime) {

		List<FlightDto> flights = new ArrayList<FlightDto>();
		flights.add(new FlightDto(flightNumber, departureTime, arrivalTime));

		List<DayDto> dayDtoList = new ArrayList<DayDto>();
		dayDtoList.add(new DayDto(new Integer(2), flights));

		return new ScheduleDto(Integer.valueOf(month), dayDtoList);
	}


	

}