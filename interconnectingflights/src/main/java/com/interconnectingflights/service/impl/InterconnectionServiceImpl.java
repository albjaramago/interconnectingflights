package com.interconnectingflights.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.interconnectingflights.dto.DepartureArrival;
import com.interconnectingflights.dto.InterconnectedDto;
import com.interconnectingflights.dto.LegDto;
import com.interconnectingflights.dto.ScheduleDto;
import com.interconnectingflights.params.InterconnectingFlightsParams;
import com.interconnectingflights.service.InterconnectionService;

@Service
public class InterconnectionServiceImpl implements InterconnectionService{


	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.InterconnectionService#getAllDirectLegs(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, com.interconnectingflights.dto.ScheduleDto)
	 *
	 *All Direct Legs
	 */
	@Override
	public List<LegDto> getAllDirectLegs(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime, ScheduleDto allDirectSchedules) {
		
        List<LegDto> directFlightsLeg = new ArrayList<LegDto>();
        
        //Filtering the days in the period of dates.
		allDirectSchedules.getDays().stream()
		.filter(day -> day.getDay() >= departureDateTime.getDayOfMonth()
						&& day.getDay() <= arrivalDateTime.getDayOfMonth())
		.forEach(dayFlight -> {

     		dayFlight.getFlights().forEach(flight ->{
        			
        			Integer departureMinutesTime = this.getMinutes(flight.getDepartureTime());
        			Integer arrivalMinutesTime = this.getMinutes(flight.getArrivalTime());
        			
        			//Each flight is added to a leg only if its hours and minutes are in the period.
        			if(departureMinutesTime >= (departureDateTime.getHour()*60 + departureDateTime.getMinute()) && 
        					arrivalMinutesTime <= (arrivalDateTime.getHour()*60 + arrivalDateTime.getMinute())){
        						
						String deptDateTime = this.getDateTime(departureDateTime.getYear(),
								departureDateTime.getMonthValue(), dayFlight.getDay(), flight.getDepartureTime());
						String arrvlDateTime = this.getDateTime(arrivalDateTime.getYear(),
								arrivalDateTime.getMonthValue(), dayFlight.getDay(), flight.getArrivalTime());
						
						directFlightsLeg.add(new LegDto(departureAirport, arrivalAirport, deptDateTime,arrvlDateTime));

        			}
        			
        		});   	
        	
        });
        
        
		return directFlightsLeg;
	}

	

	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.InterconnectionService#getAllInterconnected(java.util.Map)
	 *
	 *All Interconnected Legs
	 */
	@Override
	public List<InterconnectedDto> getAllInterconnected(Map<DepartureArrival, List<LegDto>> availableLegsMap) {
		
		
		//In the Map, the first leg is from the departure airport to the connection airport
		//and the second leg is from the connection airport the the arrival airport.
		List<LegDto> firstLeg = availableLegsMap.get(availableLegsMap.keySet().toArray()[0]);
		List<LegDto> secondLeg = availableLegsMap.get(availableLegsMap.keySet().toArray()[1]);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		List<InterconnectedDto> interconnectedList = new ArrayList<InterconnectedDto>();
		
		Integer intervalMinutesBetweenFlights = Integer.valueOf(InterconnectingFlightsParams.INTERVAL_MINUTES_BETWEEN_FLIGHTS.getValue());
		
		//Each first leg is compared with each second leg from the list in order to 
		//fulfil the minimum two hours of difference between flighs.
		firstLeg.forEach(firsLegItem -> {
			
			secondLeg.forEach(secondLegItem ->{
				
				LocalDateTime firsLegItemArrivalDateTime = LocalDateTime.parse(firsLegItem.getArrivalDateTime(), formatter);
				LocalDateTime secondLegItemDepartureDateTime = LocalDateTime.parse(secondLegItem.getDepartureDateTime(), formatter);
				
				if((firsLegItemArrivalDateTime.getDayOfMonth() <= secondLegItemDepartureDateTime.getDayOfMonth())
						&&((firsLegItemArrivalDateTime.getHour()*60 + firsLegItemArrivalDateTime.getMinute())+intervalMinutesBetweenFlights
						<= (secondLegItemDepartureDateTime.getHour()*60+secondLegItemDepartureDateTime.getMinute()))) {
					
					List<LegDto> legs = new ArrayList<LegDto>();
					legs.add(firsLegItem);
					legs.add(secondLegItem);
					
					InterconnectedDto interconnectedDto = new InterconnectedDto(new Integer(1), legs);
					interconnectedList.add(interconnectedDto);	
				}		
				
			});
		});
		
		return interconnectedList;
	}
	
	
	/**
	 * @param time
	 * @return minutes from "HH:mm"
	 *
	 */
	private Integer getMinutes(String time) {
		
		String[] hourMinutes = time.split(":");
		
		Integer hour = Integer.valueOf(hourMinutes[0])*60;
		Integer minutes = Integer.valueOf(hourMinutes[1]);
			
		return hour+minutes;
		
	}
	
	
	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param time
	 * @return String of DateTime
	 *
	 */
	private String getDateTime(Integer year, Integer month, Integer day, String time) {
		
	
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, Integer.valueOf(time.split(":")[0]),
				Integer.valueOf(time.split(":")[1]));
	    
	 
		return dateTime.toString();
		
		
	}



}
