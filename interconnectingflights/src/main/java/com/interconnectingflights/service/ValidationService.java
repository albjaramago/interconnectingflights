package com.interconnectingflights.service;

import java.time.LocalDateTime;

public interface ValidationService {

	void checkIATACodes(String departure, String arrival);
	
	void checkDateTimes(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime);
	
}
