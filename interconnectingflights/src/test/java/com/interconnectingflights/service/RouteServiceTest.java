package com.interconnectingflights.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceTest {

	@Autowired
	RouteService routeService;
	
	
	@Test
	public void getRoutesMapTest() {
		
		Map<String, List<String>> routesMap = routeService.getRoutesMap();
		assertTrue(!routesMap.isEmpty());
	}

}