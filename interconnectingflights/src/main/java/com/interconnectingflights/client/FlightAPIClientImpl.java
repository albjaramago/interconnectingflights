package com.interconnectingflights.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.interconnectingflights.dto.RouteDto;
import com.interconnectingflights.dto.ScheduleDto;
import com.interconnectingflights.params.InterconnectingFlightsParams;


@Component
public class FlightAPIClientImpl implements FlightAPIClient{

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightAPIClientImpl.class);
	
	/* (non-Javadoc)
	 * @see com.interconnectingflights.client.FlightAPIClient#getRoutes()
	 */
	@Cacheable("routes")
	public List<RouteDto> getRoutes() {
	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<RouteDto>> response = restTemplate.exchange(
				InterconnectingFlightsParams.ROUTES_API_URL.getValue(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RouteDto>>() {
				});
		List<RouteDto> routes = response.getBody();

		return routes;
		
	}
	

	/* (non-Javadoc)
	 * @see com.interconnectingflights.client.FlightAPIClient#getSchedules(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ScheduleDto getSchedules(String departure, String arrival, String year, String month) {

		StringBuilder uri = new StringBuilder(InterconnectingFlightsParams.SCHEDULES_API_URL.getValue());
		
		uri.append(departure);
		uri.append("/");
		uri.append(arrival);
		uri.append("/years/");	
		uri.append(year);
		uri.append("/months/");
		uri.append(month);
		
		
		RestTemplate restTemplate = new RestTemplate();
		ScheduleDto response = null;
		
		LOGGER.info("departure "+departure);
		LOGGER.info("arrival "+arrival);
		
		try {
			response =  restTemplate.getForObject(uri.toString(),ScheduleDto.class);
		}finally {
			return response;
		}

	}



}
