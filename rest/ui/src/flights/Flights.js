import React, {useContext, useEffect, useState} from 'react';
import {Alert, AlertTitle, Autocomplete} from '@material-ui/lab';
import {
    Box,
    Button,
    Card,
    CardContent,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    IconButton,
    List,
    ListItem,
    ListItemSecondaryAction,
    ListItemText,
    TextField,
} from '@material-ui/core';
import {Add, DeleteForever, Edit} from "@material-ui/icons";
import PeopleIcon from '@material-ui/icons/People';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';

import CustomTable from '../components/CustomTable.js';

import '../stylesheets/styles.css';
import UserContext from "../context/UserContext";
import { ROLES, hasPermission } from "../helpers/helpers";

const FlightBody = ({
    flightCode,
    arrivalDate,
    arrivalTime,
    departureDate,
    departureTime,
    originAirport,
    destinationAirport,
    airplane,
    setFlightCode,
    setArrivalDate,
    setDepartureDate,
    setArrivalTime,
    setDepartureTime,
    setOriginAirport,
    setDestinationAirport,
    setAirplane,
    airplanes,
    departuresPage,
    airports
}) => {
    return (<>
        <Box m={1} component="span" display="block">
            <TextField label="Flight Code"
                       value={flightCode}
                       onChange={e => setFlightCode(e.target.value)}
                       fullWidth
            />
        </Box>
      <Grid container justify="space-around">
        <Grid item xs={6}>
            <Box m={1} component="span" display="block">
                <TextField
                    label="Departure date"
                    type="date"
                    value={departureDate}
                    onChange={e => setDepartureDate(e.target.value)}
                    fullWidth
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </Box>
        </Grid>
        <Grid item xs={6}>
            <Box m={1} component="span" display="block">
                <TextField
                    id="time"
                    label="Departure time"
                    type="time"
                    defaultValue={departureTime}
                    onChange={e => setDepartureTime(e.target.value)}
                    fullWidth
                    InputLabelProps={{
                      shrink: true,
                    }}
                    inputProps={{
                      step: 300, // 5 min
                    }}
                  />
            </Box>
        </Grid>
      </Grid>
      <Grid container justify="space-around">
        <Grid item xs={6}>
            <Box m={1} component="span" display="block">
                <TextField
                    label="Arrival date"
                    type="date"
                    value={arrivalDate}
                    onChange={e => setArrivalDate(e.target.value)}
                    fullWidth
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </Box>
        </Grid>
        <Grid item xs={6}>
            <Box m={1} component="span" display="block">
                <TextField
                    id="time"
                    label="Arrival time"
                    type="time"
                    defaultValue={arrivalTime}
                    onChange={e => setArrivalTime(e.target.value)}
                    fullWidth
                    InputLabelProps={{
                      shrink: true,
                    }}
                    inputProps={{
                      step: 300, // 5 min
                    }}
                  />
            </Box>
        </Grid>
      </Grid>
        { departuresPage
            ? <Box m={1} component="span" display="block">
                <Autocomplete
                    options={airports}
                    value={destinationAirport}
                    onChange={(e, value) => setDestinationAirport(value)}
                    getOptionLabel={airport => airport && airport.country + ", " + airport.city + ", " + airport.name}
                    style={{maxWidth: 500}}
                    renderInput={(params) => <TextField {...params} label="Destination airport" variant="outlined"/>}
                />
            </Box>
            : <Box m={1} component="span" display="block">
                <Autocomplete
                    options={airports}
                    value={originAirport}
                    getOptionLabel={airport => airport && airport.country + ", " + airport.city + ", " + airport.name}
                    onChange={(e, value) => setOriginAirport(value)}
                    style={{maxWidth: 500}}
                    renderInput={(params) => <TextField {...params} label="Origin airport" variant="outlined"/>}
                />
            </Box>
        }
        <Box m={1} component="span" display="block">
            <Autocomplete
                options={airplanes}
                value={airplane}
                getOptionLabel={airplane => airplane.name}
                onChange={(e, value) => setAirplane(value)}
                style={{maxWidth: 500}}
                renderInput={(params) => <TextField {...params} label="Airplane" variant="outlined"/>}
            />
        </Box>
    </>);
};

const EditFlight = ({flight, loggedUser, fetchFlights, departuresPage}) => {
    const arrivalSplit = flight.arrival.split('T');
    const arrivalSplit2 = arrivalSplit[1].split(':');
    const departureSplit = flight.departure.split('T');
    const departureSplit2 = departureSplit[1].split(':');
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [airplanes, setAirplanes] = useState([]);
    const [airports, setAirports] = useState([]);
    const [flightCode, setFlightCode] = useState(flight.flightCode);
    const [arrivalDate, setArrivalDate] = useState(arrivalSplit[0]);
    const [departureDate, setDepartureDate] = useState(departureSplit[0]);
    const [arrivalTime, setArrivalTime] = useState(arrivalSplit2[0] + ':' + arrivalSplit2[1]);
    const [departureTime, setDepartureTime] = useState(departureSplit2[0] + ':' + departureSplit2[1]);
    const [originAirport, setOriginAirport] = useState(flight.originAirport);
    const [destinationAirport, setDestinationAirport] = useState(flight.destinationAirport);
    const [airplane, setAirplane] = useState(flight.airplane);

    useEffect(() => {
        fetch("http://localhost:8080/pa165/rest/airports")
            .then(res => res.json())
            .then(json => setAirports(json.sort((a, b) => a.name > b.name)));
        fetch("http://localhost:8080/pa165/rest/airplanes")
            .then(res => res.json())
            .then(json => setAirplanes(json.sort((a, b) => a.name > b.name)));
    }, []);

    const editHandler = () => {
        const departure = departureDate + "T" + departureTime + ":00.000000";
        const arrival = arrivalDate + "T" + arrivalTime + ":00.000000";
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(loggedUser.email + ':' + loggedUser.password)
                },
                body: JSON.stringify({
                    id: flight.id,
                    flightCode,
                    arrival,
                    departure,
                    originAirportId: originAirport.id,
                    destinationAirportId: destinationAirport.id,
                    airplaneId: airplane.id,
                    stewards: flight.stewards
                }),
            }
        )
            .then(response => {
                if (response.status === 200) {
                    fetchFlights();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not edit flight");
                    setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                }
            })
            .catch(error => setErrorMessage(error))
    }

    if (loggedUser) {
        return (
            <>
                <Edit className="cursor-pointer" style={{fill: "#FEC601"}} onClick={() => setShowModal(true)}/>
                <Dialog open={showModal}
                        onClose={() => setShowModal(false)}
                        maxWidth={'xs'}
                        fullWidth>
                    <DialogTitle>{"Edit Details of Flight " + flight.name}</DialogTitle>
                    <DialogContent>
                        <FlightBody flightCode={flightCode}
                                    arrivalDate={arrivalDate}
                                    departureDate={departureDate}
                                    arrivalTime={arrivalTime}
                                    departureTime={departureTime}
                                    originAirport={originAirport}
                                    destinationAirport={destinationAirport}
                                    airplane={airplane}
                                    setFlightCode={setFlightCode}
                                    setArrivalDate={setArrivalDate}
                                    setDepartureDate={setDepartureDate}
                                    setArrivalTime={setArrivalTime}
                                    setDepartureTime={setDepartureTime}
                                    setOriginAirport={setOriginAirport}
                                    setDestinationAirport={setDestinationAirport}
                                    setAirplane={setAirplane}
                                    airplanes={airplanes}
                                    departuresPage={departuresPage}
                                    airports={airports}/>
                        {errorMessage &&
                        <Alert severity="error">
                            <AlertTitle>{errorTitle}</AlertTitle>
                            {errorMessage}
                        </Alert>}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setShowModal(false)} color="default" variant="contained">
                            Close
                        </Button>
                        <Button onClick={editHandler} color="primary" variant="contained">
                            Save
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

const CreateFlight = ({loggedUser, fetchFlights, departuresPage, airport}) => {
    const tmpDateTime = "2021-01-01T07:30:00.000000"
    const split = tmpDateTime.split('T');
    const split2 = split[1].split(':');
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [airplanes, setAirplanes] = useState([]);
    const [airports, setAirports] = useState([]);
    const [flightCode, setFlightCode] = useState("");
    const [arrivalDate, setArrivalDate] = useState(split[0]);
    const [departureDate, setDepartureDate] = useState(split[0]);
    const [arrivalTime, setArrivalTime] = useState(split2[0] + ':' + split2[1]);
    const [departureTime, setDepartureTime] = useState(split2[0] + ':' + split2[1]);
    const [originAirport, setOriginAirport] = useState(departuresPage ? airport : "");
    const [destinationAirport, setDestinationAirport] = useState(departuresPage ? "" : airport);
    const [airplane, setAirplane] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/pa165/rest/airports")
            .then(res => res.json())
            .then(json => setAirports(json.sort((a, b) => a.name > b.name)));
        fetch("http://localhost:8080/pa165/rest/airplanes")
            .then(res => res.json())
            .then(json => setAirplanes(json.sort((a, b) => a.name > b.name)));
    }, []);

    const deleteHandler = () => {
        const departure = departureDate + "T" + departureTime + ":00.000000";
        const arrival = arrivalDate + "T" + arrivalTime + ":00.000000";
        fetch("http://localhost:8080/pa165/rest/flights/",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(loggedUser.email + ':' + loggedUser.password)
                },
                body: JSON.stringify({
                    flightCode,
                    arrival,
                    departure,
                    originAirportId: originAirport.id,
                    destinationAirportId: destinationAirport.id,
                    airplaneId: airplane.id,
                    stewards: []
                }),
            }
        )
            .then(response => {
                if (response.status === 201) {
                    fetchFlights();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not create flight");
                    setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                }
            })
            .catch(error => setErrorMessage(error))
    }

    if (loggedUser) {
        return (
            <>
                <Button onClick={() => setShowModal(true)} variant="contained" color="primary">
                    <Add style={{marginRight: "5px"}}/> Add flight
                </Button>
                <Dialog open={showModal}
                        onClose={() => setShowModal(false)}
                        maxWidth={'xs'}
                        fullWidth>
                    <DialogTitle>Create Flight</DialogTitle>
                    <DialogContent>
                        <FlightBody flightCode={flightCode}
                                    arrivalDate={arrivalDate}
                                    departureDate={departureDate}
                                    arrivalTime={arrivalTime}
                                    departureTime={departureTime}
                                    originAirport={originAirport}
                                    destinationAirport={destinationAirport}
                                    airplane={airplane}
                                    setFlightCode={setFlightCode}
                                    setArrivalDate={setArrivalDate}
                                    setDepartureDate={setDepartureDate}
                                    setArrivalTime={setArrivalTime}
                                    setDepartureTime={setDepartureTime}
                                    setOriginAirport={setOriginAirport}
                                    setDestinationAirport={setDestinationAirport}
                                    setAirplane={setAirplane}
                                    airplanes={airplanes}
                                    departuresPage={departuresPage}
                                    airports={airports}/>
                        {errorMessage &&
                        <Alert severity="error">
                            <AlertTitle>{errorTitle}</AlertTitle>
                            {errorMessage}
                        </Alert>}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setShowModal(false)} color="default" variant="contained">
                            Close
                        </Button>
                        <Button onClick={deleteHandler} color="primary" variant="contained">
                            Create
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

const DeleteFlight = ({flight, loggedUser, fetchFlights}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(loggedUser.email + ':' + loggedUser.password)
                },
            })
            .then(response => {
                if (response.status === 204) {
                    fetchFlights();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not delete flight");
                    setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                }
            })
            .catch(error => setErrorMessage(error))
    }

    if (loggedUser) {
        return (
            <>
                <DeleteForever className="cursor-pointer" style={{fill: "#C20114"}} onClick={() => setShowModal(true)}/>
                <Dialog open={showModal}
                        onClose={() => setShowModal(false)}
                        maxWidth={'sm'}
                        fullWidth>
                    <DialogTitle>{"Remove Flight " + flight.name}</DialogTitle>
                    <DialogContent>
                        Are you sure you want to delete this flight?
                        {errorMessage &&
                        <Alert severity="error">
                            <AlertTitle>{errorTitle}</AlertTitle>
                            {errorMessage}
                        </Alert>}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setShowModal(false)} color="default" variant="contained">
                            Close
                        </Button>
                        <Button onClick={deleteHandler} color="secondary" variant="contained">
                            Remove
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    } else {
        return null;
    }
}

const ManageStewards = ({flight, loggedUser, fetchFlights}) => {
    const [showModal, setShowModal] = useState(false)
    const [stewards, setStewards] = useState([])
    const [renderedStewards, setRenderedStewards] = useState([...flight.stewards])
    const [inputValue, setInputValue] = useState("")
    // const [selected, setSelected] = useState("")
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")


    useEffect(() => {
        fetch("http://localhost:8080/pa165/rest/stewards",
            {
                headers: {'Authorization': 'Basic ' + btoa(loggedUser.email + ':' + loggedUser.password)},
            }
        )
            .then(res => res.json())
            .then(json => {
                setStewards(json.sort((a, b) => a.lastName > b.lastName));
            });
    }, []);

    const submit = () => {
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
            {
                method: 'PUT',
                body: JSON.stringify({
                    id: flight.id,
                    flightCode: flight.flightCode,
                    arrival: flight.arrival,
                    departure: flight.departure,
                    originAirportId: flight.originAirport.id,
                    destinationAirportId: flight.destinationAirport.id,
                    airplaneId: flight.airplane.id,
                    stewardIds: renderedStewards.map(s => s.id)
                }),
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(loggedUser.email + ':' + loggedUser.password)
                },
            }
        )
            .then(response => {
                if (response.status === 200) {
                    fetchFlights();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not edit flight");
                    setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                }
            })
            .catch(error => setErrorMessage(error))
    };

    const removeSteward = (
        <List component="nav" aria-label="secondary mailbox folders">
            {renderedStewards && renderedStewards.map(s => {
                return (
                    <ListItem key={s.id}>
                        <ListItemText primary={s.firstName + " " + s.lastName}
                                      secondary={"Country: " + s.countryCode + ", Passport number: " + s.passportNumber}/>
                        <ListItemSecondaryAction>
                            <IconButton edge="end" aria-label="delete">
                                <RemoveCircleIcon className="cursor-pointer"
                                                  style={{fill: "#C20114"}}
                                                  onClick={() => {
                                                      const tmp = [...renderedStewards].filter(s2 => s2.id !== s.id);
                                                      setRenderedStewards(tmp);
                                                  }}/>
                            </IconButton>
                        </ListItemSecondaryAction>
                    </ListItem>
                );
            })}
        </List>
    );

    const renderedAddStewards = stewards.filter(s => renderedStewards.findIndex(s2 => s2.id === s.id) === -1);
    const addSteward = (
        <Grid container spacing={3}>
            <Grid item xs={10}>
                <Autocomplete value={inputValue}
                              onChange={(event, value) => {
                                  setInputValue(value);
                              }}
                              options={renderedAddStewards}
                              getOptionLabel={steward => steward && steward.firstName + " " + steward.lastName + ", " + steward.countryCode + ", " + steward.passportNumber}
                              style={{maxWidth: 500}}
                              renderInput={(params) => <TextField {...params} label="Add steward to flight"
                                                                  variant="outlined"/>}
                />
            </Grid>
            <Grid item xs={2} style={{textAlign: "right"}}>
                <Button style={{marginTop: "0.7rem"}}
                        variant="outlined"
                        color="primary"
                        onClick={() => {
                            const tmp = [...renderedStewards];
                            tmp.push(inputValue);
                            setRenderedStewards(tmp);
                            setInputValue("");
                        }}>
                    Add
                </Button>
            </Grid>
        </Grid>
    );

    const body = (
        <>
            {addSteward}
            {removeSteward}
        </>
    );

    return (
        <>
            <PeopleIcon className="cursor-pointer" onClick={() => setShowModal(true)}/>
            <Dialog open={showModal}
                    onClose={() => setShowModal(false)}
                    maxWidth={'sm'}
                    fullWidth
            >
                <DialogTitle>Manage Stewards on Flight</DialogTitle>
                <DialogContent>
                    {body}
                    {errorMessage &&
                    <Alert severity="error">
                        <AlertTitle>{errorTitle}</AlertTitle>
                        {errorMessage}
                    </Alert>}
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setShowModal(false)} color="default" variant="contained">
                        Close
                    </Button>
                    <Button onClick={submit} color="primary" variant="contained">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export const Flights = ({ airport, departuresPage }) => {
    const [flights, setFlights] = useState([]);
    const {user} = useContext(UserContext);
    const fetchFlights = () => {
        fetch("http://localhost:8080/pa165/rest/flights")
            .then(res => res.json())
            .then(json => setFlights(json.sort((a, b) => {
                if (departuresPage)
                    return a.departure > b.departure;
                else
                    return a.arrival > b.arrival;
            }).filter(a => {
                if (departuresPage)
                    return a.originAirport.id === airport.id;
                else
                    return a.destinationAirport.id === airport.id;
            })));
    }
    useEffect(() => fetchFlights(), [airport]);

    // The last headerless column is for cctions
    const header = ["Code", "Departure Time", departuresPage ? "Destination" : "Origin", "Expected Arrival", "Airplane", ""];
    const data = flights.map(a => {
        return [
            a.flightCode,
            a.departure.replace('T', ' '),
            departuresPage? a.destinationAirport.name : a.originAirport.name,
            a.arrival.replace('T', ' '),
            a.airplane.name,
            hasPermission(ROLES.FLIGHT_MANAGER, user.role) && <div style={{textAlign: "right"}}>
                <ManageStewards flight={a} fetchFlights={fetchFlights} loggedUser={user} />
                <EditFlight airport={airport} flight={a} fetchFlights={fetchFlights} loggedUser={user} departuresPage={departuresPage} />
                <DeleteFlight flight={a} fetchFlights={fetchFlights} loggedUser={user} />
            </div>
        ]
    });

    const create = hasPermission(ROLES.FLIGHT_MANAGER, user.role) && (<CreateFlight airport={airport} fetchFlights={fetchFlights} loggedUser={user} departuresPage={departuresPage} />);

    return (
        <Card>
            <CardContent>
                <CustomTable title={(departuresPage ? "Departures - " : "Arrivals - ") + airport.name} header={header} data={data} create={create} initialOrderItem={departuresPage ? 1 : 3}/>
            </CardContent>
        </Card>
    );
}
