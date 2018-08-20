package com.interconnectingflights.exception;


public enum ExceptionMessage {


	/**
	 * ARRIVAL_IATA_CODE_NOT_FOUND
	 */
	ARRIVAL_IATA_CODE_NOT_EXISTING("ARRIVAL IATA CODE NOT EXISTING"),
	
	
	/**
	 * DEPARTURE_IATA_CODE_NOT_FOUND
	 */
	DEPARTURE_IATA_CODE_NOT_EXISTING("DEPARTURE IATA CODE NOT EXISTING"),
	
	
	/**
	 * DEPARTURE_DATE_GREATER_THAN_ARRIVAL_DATE
	 */
	DEPARTURE_DATE_GREATER_THAN_ARRIVAL_DATE("DEPARTURE DATE GREATER THAN ARRIVAL DATE");
	
	

	
	private String value;
	
	private ExceptionMessage(String value) {
		
		this.value = value;
	}
	
	
	public String getValue() {
		
		return this.value;
	}
}
