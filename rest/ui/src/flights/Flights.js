import React, { useState, useEffect } from 'react';
import { Alert, AlertTitle, Autocomplete } from '@material-ui/lab';
import {
    Box,
    Card,
    CardContent,
    Button,
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
import { Add, DeleteForever, Edit } from "@material-ui/icons";
import PeopleIcon from '@material-ui/icons/People';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';

import CustomTable from '../components/CustomTable.js';

import '../stylesheets/styles.css';

const FlightBody = ({
    flightCode,
    arrival,
    departure,
    originAirport,
    destinationAirport,
    airplane,
    setFlightCode,
    setArrival,
    setDeparture,
    setOriginAirport,
    setDestinationAirport,
    setAirplane,
    airplanes,
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
        <Box m={1} component="span" display="block">
            <TextField
              label="Departure time"
              type="date"
              value={departure}
              onChange={e => setDeparture(e.target.value)}
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
            />
        </Box>
        <Box m={1} component="span" display="block">
            <TextField
              label="Arrival time"
              type="date"
              value={arrival}
              onChange={e => setArrival(e.target.value)}
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
            />
        </Box>
        <Box m={1} component="span" display="block">
            <Autocomplete
              options={airports}
              value={originAirport}
              getOptionLabel={airport => airport && airport.country + ", " + airport.city + ", " + airport.name }
              onChange={(e, value) => setOriginAirport(value)}
              style={{ maxWidth: 500 }}
              renderInput={(params) => <TextField {...params} label="Origin airport" variant="outlined" />}
            />
        </Box>
        <Box m={1} component="span" display="block">
            <Autocomplete
              options={airports}
              value={destinationAirport}
              onChange={(e, value) => setDestinationAirport(value)}
              getOptionLabel={airport => airport && airport.country + ", " + airport.city + ", " + airport.name }
              style={{ maxWidth: 500 }}
              renderInput={(params) => <TextField {...params} label="Destination airport" variant="outlined" />}
            />
        </Box>
        <Box m={1} component="span" display="block">
            <Autocomplete
              options={airplanes}
              value={airplane}
              getOptionLabel={airplane => airplane.name }
              onChange={(e, value) => setAirplane(value)}
              style={{ maxWidth: 500 }}
              renderInput={(params) => <TextField {...params} label="Airplane" variant="outlined" />}
            />
        </Box>
    </>);
};

const EditFlight = ({ flight, loggedUser, fetchFlights }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [airplanes, setAirplanes] = useState([]);
    const [airports, setAirports] = useState([]);
    const [flightCode, setFlightCode] = useState(flight.flightCode);
    const [arrival, setArrival] = useState(flight.arrival);
    const [departure, setDeparture] = useState(flight.departure);
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
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
              {
                  method: 'PUT',
                  headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
                  body: JSON.stringify({
                      id: flight.id,
                      flightCode,
                      arrival,
                      departure,
                      originAirport,
                      destinationAirport,
                      airplane,
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
                <Edit className="cursor-pointer" style={{fill: "#FEC601"}} onClick={() => setShowModal(true)} />
                <Dialog open={showModal}
                    onClose={() => setShowModal(false)}
                    maxWidth={'xs'}
                    fullWidth>
                    <DialogTitle>{"Edit Details of Flight " + flight.name}</DialogTitle>
                    <DialogContent>
                        <FlightBody flightCode={flightCode}
                            arrival={arrival}
                            departure={departure}
                            originAirport={originAirport}
                            destinationAirport={destinationAirport}
                            airplane={airplane}
                            setFlightCode={setFlightCode}
                            setArrival={setArrival}
                            setDeparture={setDeparture}
                            setOriginAirport={setOriginAirport}
                            setDestinationAirport={setDestinationAirport}
                            setAirplane={setAirplane}
                            airplanes={airplanes}
                            airports={airports} />
                        {errorMessage &&
                            <Alert severity="error">
                                <AlertTitle>{errorTitle}</AlertTitle>
                                {errorMessage}
                            </Alert>}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setShowModal(false)} color="default">
                            Close
                        </Button>
                        <Button onClick={editHandler} color="primary">
                            Save
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

const CreateFlight = ({ loggedUser, fetchFlights }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [airplanes, setAirplanes] = useState([]);
    const [airports, setAirports] = useState([]);
    const [flightCode, setFlightCode] = useState("");
    const [arrival, setArrival] = useState("");
    const [departure, setDeparture] = useState("");
    const [originAirport, setOriginAirport] = useState("");
    const [destinationAirport, setDestinationAirport] = useState("");
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
        fetch("http://localhost:8080/pa165/rest/flights/",
              {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
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
                            arrival={arrival}
                            departure={departure}
                            originAirport={originAirport}
                            destinationAirport={destinationAirport}
                            airplane={airplane}
                            setFlightCode={setFlightCode}
                            setArrival={setArrival}
                            setDeparture={setDeparture}
                            setOriginAirport={setOriginAirport}
                            setDestinationAirport={setDestinationAirport}
                            setAirplane={setAirplane}
                            airplanes={airplanes}
                            airports={airports} />
                        {errorMessage &&
                            <Alert severity="error">
                                <AlertTitle>{errorTitle}</AlertTitle>
                                {errorMessage}
                            </Alert>}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setShowModal(false)} color="default">
                            Close
                        </Button>
                        <Button onClick={deleteHandler} color="primary">
                            Create 
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

const DeleteFlight = ({ flight, loggedUser, fetchFlights }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
              {
                  method: 'DELETE',
                  headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
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
                <DeleteForever className="cursor-pointer" style={{fill: "#C20114"}} onClick={() => setShowModal(true)} />
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
                        <Button onClick={() => setShowModal(false)} color="default">
                            Close
                        </Button>
                        <Button onClick={deleteHandler} color="secondary">
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

const ManageStewards = ({ flight, loggedUser, fetchFlights }) => {
    const [showModal, setShowModal] = useState(false)
    const [stewards, setStewards] = useState([])
    const [stewardsToRemove, setStewardsToRemove] = useState([])
    const [stewardsToAdd, setStewardsToAdd] = useState([])
    const [inputValue, setInputValue] = useState("")
    // const [selected, setSelected] = useState("")
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")

    useEffect(() => {
        fetch("http://localhost:8080/pa165/rest/stewards",
              {
                  headers: { 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
              }
        )
            .then(res => res.json())
            .then(json => {
                console.log(json);
                setStewards(json.sort((a, b) => a.lastName > b.lastName));
            });
    }, []);

    const submit = () => {
        let renderedStewards = [...new Set(stewardsToAdd.concat(flight.stewards))];
        renderedStewards = renderedStewards.filter(s => stewardsToRemove.findIndex(s2 => s2.id === s.id) === -1);

        const newFlight = {...flight};
        newFlight.stewards = renderedStewards;
        fetch("http://localhost:8080/pa165/rest/flights/" + flight.id,
              {
                  method: 'PUT',
                  body: JSON.stringify(newFlight),
                  headers: { 'Content-Type': 'application/json', 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
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

    let renderedStewards = [...new Set(stewardsToAdd.concat(flight.stewards))];
    renderedStewards = renderedStewards.filter(s => stewardsToRemove.findIndex(s2 => s2.id === s.id) === -1);
    const removeSteward = (
        <List component="nav" aria-label="secondary mailbox folders">
            {renderedStewards && renderedStewards.map(s => {
                return(
                    <ListItem>
                        <ListItemText primary={s.firstName + " " + s.lastName}
                            secondary={"Country: " + s.countryCode + ", Passport number: " + s.passportNumber} />
                        <ListItemSecondaryAction>
                            <IconButton edge="end" aria-label="delete">
                                <RemoveCircleIcon className="cursor-pointer"
                                    style={{fill: "#C20114"}}
                                    onClick={() => {
                                        const tmp = [...stewardsToRemove];
                                        tmp.push(s);
                                        setStewardsToRemove(tmp);
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
                    getOptionLabel={steward => steward && steward.firstName + " " + steward.lastName + ", " + steward.countryCode + ", " + steward.passportNumber }
                    style={{ maxWidth: 500 }}
                    renderInput={(params) => <TextField {...params} label="Add steward to flight" variant="outlined" />}
                />
            </Grid>
            <Grid item xs={2} style={{ textAlign: "right" }}>
                <Button style={{ marginTop: "0.7rem" }}
                    variant="outlined"
                    color="primary"
                    onClick={() => {
                        const tmp = [...stewardsToAdd];
                        tmp.push(inputValue)
                        setStewardsToAdd(tmp);
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
            <PeopleIcon className="cursor-pointer" onClick={() => setShowModal(true)} />
            <Dialog open={showModal}
                onClose={() => setShowModal(false)}
                maxWidth={'sm'}
                fullWidth
            >
                <DialogTitle >Manage Stewards on Flight</DialogTitle>
                <DialogContent>
                    {body}
                    {errorMessage &&
                        <Alert severity="error">
                            <AlertTitle>{errorTitle}</AlertTitle>
                            {errorMessage}
                        </Alert>}
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setShowModal(false)} color="default">
                        Close
                    </Button>
                    <Button onClick={submit} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export const Flights = ({ loggedUser }) => {
    const [flights, setFlights] = useState([]);
    const fetchFlights = () => {
        fetch("http://localhost:8080/pa165/rest/flights",
              {
                  headers: { 'Authorization': 'Basic '+btoa('pepa@example.com:pepa') },
              }
        )
            .then(res => res.json())
            .then(json => setFlights(json.sort((a, b) => a.name > b.name)));
    }
    useEffect(() => fetchFlights(), []);

    // The last headerless column is for cctions
    const header = ["Code", "Departure Time", "Destination", "Expected Arrival", "Airplane", ""];
    const data = flights.map(a => {
        return [
            a.flightCode,
            a.departure,
            a.destinationAirport.name,
            a.arrival,
            a.airplane.name,
            <div style={{textAlign: "right"}}>
                <ManageStewards flight={a} fetchFlights={fetchFlights} loggedUser />
                <EditFlight flight={a} fetchFlights={fetchFlights} loggedUser />
                <DeleteFlight flight={a} fetchFlights={fetchFlights} loggedUser />
            </div>
        ]
    });

    const create = (<CreateFlight fetchFlights={fetchFlights} loggedUser />);
    console.log(flights);

    return(
            <Card>
                <CardContent>
                    <CustomTable title="Flights" header={header} data={data} create={create} />
                </CardContent>
            </Card>
    );
}
