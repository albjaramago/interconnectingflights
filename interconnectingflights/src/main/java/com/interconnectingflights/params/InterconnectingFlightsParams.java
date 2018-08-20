package com.interconnectingflights.params;


public enum InterconnectingFlightsParams {


	/**
	 * ROUTES_API_URL
	 */
	ROUTES_API_URL("https://api.ryanair.com/core/3/routes/"),
	
	
	/**
	 * SCHEDULES_API_URL
	 */
	SCHEDULES_API_URL("https://api.ryanair.com/timetable/3/schedules/"),
	

	/**
	 * INTERVAL_MINUTES_BETWEEN_FLIGHTS
	 */
	INTERVAL_MINUTES_BETWEEN_FLIGHTS("120");

	
	private String value;
	
	private InterconnectingFlightsParams(String value) {
		
		this.value = value;
	}
	
	
	public String getValue() {
		
		return this.value;
	}
}
