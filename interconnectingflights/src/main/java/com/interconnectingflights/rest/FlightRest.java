package com.interconnectingflights.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interconnectingflights.controller.FlightController;
import com.interconnectingflights.dto.InterconnectedDto;
import com.interconnectingflights.exception.ExceptionMessage;
import com.interconnectingflights.exception.InterconnectingFlightsException;

@RestController
@RequestMapping("/interconnections")
public class FlightRest {
	
	
	@Autowired
	FlightController flightController;
	
	
	/**
	 * @param departure
	 * @param arrival
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @return
	 */
	@GetMapping
	public ResponseEntity findInterconnections(@RequestParam(value = "departure", required = true) String departure,
			@RequestParam(value = "arrival", required = true) String arrival,
			@RequestParam(value = "departureDateTime", required = true) String departureDateTime,
			@RequestParam(value = "arrivalDateTime", required = true) String arrivalDateTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime formatedDepartureDateTime = LocalDateTime.parse(departureDateTime, formatter);
        LocalDateTime formatedArrivalDateTime = LocalDateTime.parse(arrivalDateTime, formatter);
        
		List<InterconnectedDto> allDirect = flightController.getAllDirect(departure, arrival, formatedDepartureDateTime, formatedArrivalDateTime);

		List<InterconnectedDto> allInterconnected = flightController.getAllInterconnected(departure, arrival, formatedDepartureDateTime, formatedArrivalDateTime);

		List<InterconnectedDto> allFlights = Stream.concat(allDirect.stream(), allInterconnected.stream())
				.collect(Collectors.toList());

		return new ResponseEntity<>(allFlights, HttpStatus.OK);
	}

}
