# interconnectingflights
Interconnecting Flights is a Spring Boot application based RESTFUL Api which serves information about possible direct
and interconnected flights (maximum 1 stop) based on the data consumed from external APIs.

The application can consume data from the following two microservices: - Routes API:
https://api.ryanair.com/core/3/routes/ which returns a list of all available routes based on the
airport's IATA codes.
 
Schedules API:
https://api.ryanair.com/timetable/3/schedules/{departure}/{arrival}/years/{year}/months/{month}
which returns a list of available flights for a given departure airport IATA code, an arrival airport
IATA code, a year and a month. For example
(https://api.ryanair.com/timetable/3/schedules/DUB/WRO/years/2016/months/6)

The application returns a list of flights departing from a given departure airport not earlier
than the specified departure datetime and arriving to a given arrival airport not later than the
specified arrival datetime. The list should consist of:

-All direct flights if available (for example: DUB - WRO)
-All interconnected flights with a maximum of one stop if available (for example: DUB - STN -
WRO)

The application responses to following request URI with given query parameters:
http://<HOST>/<CONTEXT>/interconnections?departure={departure}&arrival={arrival}&depa
rtureDateTime={departureDateTime}&arrivalDateTime={arrivalDateTime} 

i.e:http://localhost:8080/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-09-02T00:00&arrivalDateTime=2018-09-02T23:00



The application has been created using MVC design pattern with the following structure of interfaces:

-FlightRest: REST Controller which receive the request with the given parammeters(departure,arrival,departureDateTime,arrivalDateTime).

-FlightController: 
		-Returns a list containing all direct flights.
		-Returns a list containing all interconnnected flights.
				  
-RouteService: 
		-Returns a Map of routes. The key is the arrival IATA airport code The value is a list containing all IATA departure 	                   airport codes. Routes in the map are direct between the airport key and each airport in the value list.
				  
				  
-FlightService:
		-Returns a Schedule containg the list of all direct flights.
		-Returns a list of maps for interconnected flights. In the list, each Map contains two items. The first item of the Map 		  is the flight from the departure airport to the connection airport. The second item of the Map is the flight 				  from the connection airport to the arrival airport.
					

-InterconnectionService: 
		-Returns a list containing legs for all direct flights.
		-Returns a list containing legs for interconnected flights.
				

Building the applicaction a .jar is generated.  To run it, execute:
 $ java -jar interconnectingflights-0.0.1-SNAPSHOT.jar

A dockerfile and a maven plugin for docker has also been included. Starting dockers in you machine and building the project,a docker image will be generated. 
To run the application throught the docker image, execute:
$ docker run -p 8080:8080 -t springio/interconnectingflights
