package com.interconnectingflights.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.interconnectingflights.client.FlightAPIClient;
import com.interconnectingflights.dto.DayDto;
import com.interconnectingflights.dto.FlightDto;
import com.interconnectingflights.dto.ScheduleDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceTest {

	@MockBean
	FlightAPIClient flightAPIClient;
	
	@Autowired
	FlightService flightService;
	
	
	@Test
	public void getAllDirectSchedulesTest() {
		
		String departure = "DUB";
		String arrival = "WRO";
		
		String year = "2018";
		String month ="9";
		
		String flightNumber = "1926";
		String departureTime = "17:25";
		String arrivalTime = "21:00";
		
		ScheduleDto scheduleDto = this.getScheduleDto(departure, arrival, year, month, flightNumber, departureTime,
				arrivalTime);

		//FlightAPIClient mock
		when(flightAPIClient.getSchedules(departure, arrival, year, month)).thenReturn(scheduleDto);
		
		flightService.getAllDirectSchedules(departure, arrival, year, month, this.getRouteMap());

	}
	
	
	@Test
	public void getAllInterconnectedSchedulesTest() {
		
	
		String departure = "DUB";
		String arrival = "WRO";
		
		String year = "2018";
		String month ="9";
		
		String flightNumber = "1926";
		String departureTime = "17:25";
		String arrivalTime = "21:00";
		
		ScheduleDto scheduleDto = this.getScheduleDto(departure, arrival, year, month, flightNumber, departureTime,
				arrivalTime);

		//FlightAPIClient mock
		when(flightAPIClient.getSchedules(departure, arrival, year, month)).thenReturn(scheduleDto);
		
		flightService.getAllInterconnectedSchedules(departure, arrival, year, month, this.getRouteMap());
	}
	
	
	
	private ScheduleDto getScheduleDto(String departure, String arrival, String year, String month, String flightNumber,
			String departureTime, String arrivalTime) {

		List<FlightDto> flights = new ArrayList<FlightDto>();
		flights.add(new FlightDto(flightNumber, departureTime, arrivalTime));

		List<DayDto> dayDtoList = new ArrayList<DayDto>();
		dayDtoList.add(new DayDto(new Integer(2), flights));

		return new ScheduleDto(Integer.valueOf(month), dayDtoList);
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
	
	

}