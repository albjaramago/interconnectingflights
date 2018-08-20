package com.interconnectingflights.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.interconnectingflights.client.FlightAPIClient;
import com.interconnectingflights.dto.RouteDto;
import com.interconnectingflights.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService{

	@Autowired
	FlightAPIClient flightAPIClient;
	
	/* (non-Javadoc)
	 * @see com.interconnectingflights.service.RouteService#getRoutesMap()
	 * 
	 * Map of routes
	 */
	@Override
	@Cacheable("routesmap")
	public Map<String, List<String>> getRoutesMap() {
		
		List<RouteDto> routeListDto = flightAPIClient.getRoutes();
		
		//Creating the Map of routes.
		//The key is the arrival IATA airport code.
	    //The value is a list containing all IATA departure airport codes.  
		//Routes are direct between the airport key and each airport in the value list.
		Map<String, List<String>> routesMap = new HashMap<String, List<String>>();
		
		routeListDto.forEach(route ->{
			List<String> aiportFromList;

			if (routesMap.containsKey(route.getAirportTo()))
				aiportFromList = routesMap.get(route.getAirportTo());
			else
				aiportFromList = new ArrayList<String>();

			aiportFromList.add(route.getAirportFrom());
			routesMap.put(route.getAirportTo(), aiportFromList);
			
			
		}); 
		
		
		return routesMap;
	}

	
}
