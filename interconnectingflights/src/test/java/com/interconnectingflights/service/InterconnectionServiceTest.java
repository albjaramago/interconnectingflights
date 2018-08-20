package com.interconnectingflights.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.interconnectingflights.dto.DayDto;
import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.FlightDto;
import com.interconnectingflights.dto.LegDto;
import com.interconnectingflights.dto.ScheduleDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterconnectionServiceTest {

	@Autowired
	InterconnectionService interconnectionService;
	
	
	@Test
	public void getAllDirectLegsTest() {
		String departure = "DUB";
		String arrival = "WRO";
		
		String year = "2018";
		String month ="9";
		
		String flightNumber = "1926";
		String departureTime = "17:25";
		String arrivalTime = "21:00";
	
		
		ScheduleDto allDirectSchedules = this.getScheduleDto(departure, arrival, year, month, flightNumber, departureTime,
				arrivalTime);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse("2018-09-02T00:00", formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse("2018-09-02T23:00", formatter);
		
		
		interconnectionService.getAllDirectLegs(departure, arrival, departureDateTime, arrivalDateTime, allDirectSchedules);
	
	}
	
	
	@Test
	public void getAllInterconnectedTest() {
		
		interconnectionService.getAllInterconnected(this.getAvailableLegsMap());
	
	}
	
	
	private ScheduleDto getScheduleDto(String departure, String arrival, String year, String month, String flightNumber,
			String departureTime, String arrivalTime) {

		List<FlightDto> flights = new ArrayList<FlightDto>();
		flights.add(new FlightDto(flightNumber, departureTime, arrivalTime));

		List<DayDto> dayDtoList = new ArrayList<DayDto>();
		dayDtoList.add(new DayDto(new Integer(2), flights));

		return new ScheduleDto(Integer.valueOf(month), dayDtoList);
	}

	private Map<DepartureArrival, List<LegDto>> getAvailableLegsMap(){
		
		
		DepartureArrival departureArrivalA = new DepartureArrival("DUB", "ALC");		
		LegDto availableLegA1 = new LegDto("DUB", "ALC", "2018-09-02T06:30", "2018-09-02T10:20");
		LegDto availableLegA2 = new LegDto("DUB", "ALC", "2018-09-02T10:10", "2018-09-02T14:05");
		
		List<LegDto> legsA = new ArrayList<LegDto>();
		legsA.add(availableLegA1);
		legsA.add(availableLegA2);
		
		
		DepartureArrival departureArrivalB = new DepartureArrival("ALC", "WRO");
		LegDto availableLegB1 = new LegDto("ALC", "WRO", "2018-09-02T16:50", "2018-09-02T20:00");
		
		List<LegDto> legsB = new ArrayList<LegDto>();
		legsB.add(availableLegB1);

		
		Map<DepartureArrival, List<LegDto>> legsMap = new HashMap<DepartureArrival, List<LegDto>>();
		legsMap.put(departureArrivalA, legsA);
		legsMap.put(departureArrivalB, legsB);
		
		return legsMap;
	}

}