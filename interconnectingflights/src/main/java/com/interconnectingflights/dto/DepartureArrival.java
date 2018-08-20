package com.interconnectingflights.dto;

public class DepartureArrival {

	private final String departure;
	private final String arrival;
	
	
	
	public String getDeparture() {
		return departure;
	}
	public String getArrival() {
		return arrival;
	}
		
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrival == null) ? 0 : arrival.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartureArrival other = (DepartureArrival) obj;
		if (arrival == null) {
			if (other.arrival != null)
				return false;
		} else if (!arrival.equals(other.arrival))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		return true;
	}
	

	
	public DepartureArrival(String departure, String arrival) {
		super();
		this.departure = departure;
		this.arrival = arrival;
	}
	
}
