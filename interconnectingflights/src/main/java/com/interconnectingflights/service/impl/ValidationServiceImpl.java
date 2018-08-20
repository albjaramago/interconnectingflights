package com.interconnectingflights.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interconnectingflights.client.FlightAPIClient;
import com.interconnectingflights.dto.RouteDto;
import com.interconnectingflights.exception.ExceptionMessage;
import com.interconnectingflights.exception.InterconnectingFlightsException;
import com.interconnectingflights.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	FlightAPIClient flightAPIClient;
	
	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.ValidationFlightService#checkIATACodes(java.lang.String, java.lang.String)
	 *
	 *Check validity of IATA codes
	 */
	@Override
	public void checkIATACodes(String departure, String arrival) {

		boolean checkedDepartureIATAcode = false;
		boolean checkedArrivalIATAcode = false;

		for(RouteDto route:flightAPIClient.getRoutes()){
			
			if (!checkedDepartureIATAcode
					&& (departure.equals(route.getAirportTo()) || departure.equals(route.getAirportFrom())))
				checkedDepartureIATAcode = true;
			if (!checkedArrivalIATAcode
					&& (arrival.equals(route.getAirportTo()) || arrival.equals(route.getAirportFrom())))
				checkedArrivalIATAcode = true;
		}
		
		if (!checkedDepartureIATAcode)
			throw new InterconnectingFlightsException(ExceptionMessage.DEPARTURE_IATA_CODE_NOT_EXISTING.getValue());

		if (!checkedArrivalIATAcode)
			throw new InterconnectingFlightsException(ExceptionMessage.ARRIVAL_IATA_CODE_NOT_EXISTING.getValue());

	}

	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.ValidationFlightService#checkDateTimes(java.time.LocalDateTime, java.time.LocalDateTime)
	 *
	 *Check validity of dates
	 */
	@Override
	public void checkDateTimes(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		
	     if(departureDateTime.compareTo(arrivalDateTime) > 0)
	        	throw new InterconnectingFlightsException(ExceptionMessage.DEPARTURE_DATE_GREATER_THAN_ARRIVAL_DATE.getValue());   		
		
	}

}
