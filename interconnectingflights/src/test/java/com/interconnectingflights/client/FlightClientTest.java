package com.interconnectingflights.client;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.interconnectingflights.client.FlightAPIClient;
import com.interconnectingflights.dto.RouteDto;
import com.interconnectingflights.dto.ScheduleDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightClientTest {

	@Autowired
	FlightAPIClient flightClient;
	
	
	@Test
	public void getRoutesTest() {
		
		List<RouteDto> routeListDto = flightClient.getRoutes();
		
		assertTrue(!routeListDto.isEmpty());
	}
	
	
	@Test
	public void getSchedulesTest() {
		
		String departure = "DUB";
		String arrival = "WRO";
		
		String year = "2018";
		String month ="9";
		
		ScheduleDto scheduleDto= flightClient.getSchedules(departure, arrival, year, month);
		
		assertTrue(!scheduleDto.getDays().isEmpty());
	}


}