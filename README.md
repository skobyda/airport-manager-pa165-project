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
When recording a flight, it is necessary to set the departure and arrival times, the origin, the destination and the plane.  
The system should also check that the plane does not have another flight scheduled during the time of the this flight.  
It should also be possible to assign (remove) stewards to (from) a flight while checking for the steward's availability.  
The ultimate goal is to have a system capable of listing flights ordered by date and displaying the flight details (origin, destination, departure time, arrival time, plane, list of stewards)."

## How to run it
Need to have npm or yarn on your system.
### Backend:
`mvn clean install && cd rest && mvn spring-boot:run`

### Frontend:
`cd rest/ui && npm install && npm start`  

**Frontend alternatively:**  
`cd rest/ui && yarn install && yarn start`  

## Test it with CURL

Get list of flights  
`curl -i -X GET http://localhost:8080/pa165/rest/flights`  

Get specific flight  
`curl -i -X GET http://localhost:8080/pa165/rest/flights/1`  

Log in as airport manager and get list of all stewards  
`curl --location --request GET 'http://localhost:8080/pa165/rest/stewards' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'`  

Log in as airport-manager and delete flight with id 1  
`curl --location --request DELETE 'http://localhost:8080/pa165/rest/flights/2' --header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh'`  

Log in and create new airplane  
`curl --location --request POST 'http://localhost:8080/pa165/rest/airplanes' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "stvorplosnik turbo 4000",
    "capacity": 400,
    "type": "PISTON"
}'`

Log in and update airplane type  
`curl --location --request PUT 'http://localhost:8080/pa165/rest/airplanes/1' \
--header 'Authorization: Basic cGVwYUBleGFtcGxlLmNvbTpwZXBh' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "stvorplosnik turbo 3000",
    "capacity": 200,
    "type": "PISTON"
}'`

## Links
- [IS homework vault](https://is.muni.cz/auth/el/fi/jaro2021/PA165/ode/)
- [Topic selection form](https://forms.gle/5jwvj8yjEuGKBUwQA)
- [Motivation](https://www.youtube.com/watch?v=Sagg08DrO5U)
