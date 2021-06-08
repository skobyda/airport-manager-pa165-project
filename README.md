# [PA165] - Enterprise Applications in Java

Project for the subject PA165 at FI MUNI in the spring semester 2021.  
**Topic:** Airport Manager.

## Team "Purple"

- Jozef Vanický (Lead)
- Michal Zelenák (QA)
- Simon Kobyda (Frontend sub-lead)
- Petr Hendrych (Backend sub-lead)

## Assignment

Create an information system managing flight records at an airport.  
The system should allow the users to enter records about stewards, airplanes and destinations.  
It should also be possible to update and delete these records.  
A destination should contain at least the information about the location of an airport (country, city).  
Airplane details should contain the capacity of the plane and its name (and possibly its type as well).  
A steward is described by his first and last name. The system should also allow us to record a flight.  
When recording a flight, it is necessary to set the departure and arrival times, the origin, the destination and the
plane.  
The system should also check that the plane does not have another flight scheduled during the time of the this flight.  
It should also be possible to assign (remove) stewards to (from) a flight while checking for the steward's
availability.  
The ultimate goal is to have a system capable of listing flights ordered by date and displaying the flight details (
origin, destination, departure time, arrival time, plane, list of stewards)."

## How to run it

Need to have npm or yarn on your system.

### Backend:

```
mvn clean install && cd rest && mvn spring-boot:run
```

### Frontend:

```
cd rest/ui && npm install && npm start
```

**Frontend alternatively:**  
```
cd rest/ui && yarn install && yarn start
```



## Links

- [IS homework vault](https://is.muni.cz/auth/el/fi/jaro2021/PA165/ode/)
- [Topic selection form](https://forms.gle/5jwvj8yjEuGKBUwQA)
- [Motivation](https://www.youtube.com/watch?v=Sagg08DrO5U)


## REST layer
Get list of flights  
```
curl -i -X GET http://localhost:8080/pa165/rest/flights
```

Get specific flight with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/flights/1
```

Create flight  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/flights' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{
"departure": "2006-12-03T01:15:30",
"arrival": "2006-12-03T01:15:30",
"originAirportId": 1,
"destinationAirportId": 2,
"airplaneId": 3,
"flightCode": "NV19",
"stewardIds": [2]}'
```

Delete flight with id 1  
```
curl --location --request DELETE 'http://localhost:8080/pa165/rest/flights/2' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'
```
Update flight  
```
curl --location --request PUT 'http://localhost:8080/pa165/rest/flights/2' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{"id":3,
"departure": "2007-12-03T01:15:30",
"arrival": "2007-12-03T01:15:30",
"originAirportId": 1,
"destinationAirportId": 2,
"airplaneId": 3,
"flightCode": "NV189",
"stewardIds": [2]}'
```

Add steward to flight  
```
curl -X POST -i -H "Content-Type: application/json" -u 'jane@example.com:password2' http://localhost:8080/pa165/rest/flights/4/add-steward/2
```

Remove steward from flight  
```
curl -X POST -i -H "Content-Type: application/json" -u 'jane@example.com:password2' http://localhost:8080/pa165/rest/flights/4/delete-steward/2
```

Get arrival flights at airport with id 1 with max limit set to 2 airports  
```
curl -i -X GET -u 'jane@example.com:password2' http://localhost:8080/pa165/rest/flights/1/arrivals/2
```

Get departure flights at airport with id 2 with max limit set to 2 airports  
```
curl -i -X GET -u 'jane@example.com:password2' http://localhost:8080/pa165/rest/flights/2/departures/2
```

Get filtered flights, you can leave any of the params if you want  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/flights' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{"departure": "2007-12-03T01:15:30",
"arrival": "2007-12-03T01:15:30",
"originAirportId": 1,
"destinationAirportId": 1,
"airplaneId": 1,
"flightCode": "NV185",
"stewardIds": [1]}'
```

Log in as airport manager and get list of all stewards  
```
curl --location --request GET 'http://localhost:8080/pa165/rest/stewards' \
--header 'Authorization: Basic dGVzdEB0ZXN0LmNvbTp0ZXN0MTIz'
```

Log in as airport manager and create new steward  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/stewards' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{ "firstName": "Anna",
"lastName": "Smith",
"countryCode": "CZ",
"passportNumber": "123456489" }'
```

Get specific steward with id 1  
```
curl --location --request GET 'http://localhost:8080/pa165/rest/stewards/1' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'
```

Delete steward with id 1  
```
curl --location --request DELETE 'http://localhost:8080/pa165/rest/stewards/2' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'
```

Update steward with id 1  
```
curl -X PUT -i -H "Content-Type: application/json" --data '{"id":1, "firstName": "Anna", "lastName": "Smith", "countryCode": "CZ", "passportNumber": "123456789"}' -u 'jane@example.com:password2' http://localhost:8080/pa165/rest/stewards/1`
```


Log in and create new airplane  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/airplanes' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{"name": "stvorplosnik turbo 4000", "capacity": 400, "type": "PISTON" }'
```

Get list of airplanes  
```
curl -i -X GET http://localhost:8080/pa165/rest/airplanes
```

Get airplane with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/airplanes/1
```

Get airplane with capacity bigger or equal 100  
```
curl -i -X GET http://localhost:8080/pa165/rest/airplanes/findWithBiggerOrEqualCapacity?capacity=100
```

Get airplane with capacity lower or equal 100  
```
curl -i -X GET http://localhost:8080/pa165/rest/airplanes/findWithLowerOrEqualCapacity?capacity=100
```

Get list of Airplanes which are PISTON airplane types  
```
curl -i -X GET http://localhost:8080/pa165/rest/airplanes/findByType?type=PISTON
```

Log in and update airplane type of airplane with id 5  
```
curl --location --request PUT 'http://localhost:8080/pa165/rest/airplanes/5' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{ "id":5, "name": "stvorplosnik turbo 3000", "capacity": 200, "type": "PISTON" }'
```

Log in as airport manager and create new airplane  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/airplanes/' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{"name": "stvorplosnik turbo 3000", "capacity": 200, "type": "PISTON" }'
```

Delete airplane with id 1  
```
curl --location --request DELETE 'http://localhost:8080/pa165/rest/airplanes/1' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'
```

Get list of airports  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports
```

Get airport with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/1
```

Get arrival flights to airport with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/1/getArrivalFlights
```

Get departure flights from airport with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/1/getDepartureFlights
```

Get list of airports filtered by city  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/findByCity?city=Schwechat
```

Get list of airports filtered by name  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/findByName?name=Letiske+Turany
```

Get list of airports filtered by country  
```
curl -i -X GET http://localhost:8080/pa165/rest/airports/findByCountry?country=CZ
```

Log in and update airport type of airport with id 1  (there should not be any flights connected with this airports, with airports, those should be in json too)  
```
curl --location --request PUT 'http://localhost:8080/pa165/rest/airports/1' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{
"id":1,
"name": "Example Airport",
"city": "Example town",
"country": "AR"
}'
```

Log in as airport manager and create new airport  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/airports/' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Example Airport",
"city": "Example town",
"country": "AM"
}'
```

Delete airport with id 1  
```
curl --location --request DELETE 'http://localhost:8080/pa165/rest/airports/5' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'
```

Get list of users  
```
curl -i -X GET http://localhost:8080/pa165/rest/users
```

Get user with id 1  
```
curl -i -X GET http://localhost:8080/pa165/rest/users/1
```

Login as user  
```
curl --location --request POST 'http://localhost:8080/pa165/rest/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{"email": "pepa@example.com", "password": "pepa"}'
```

