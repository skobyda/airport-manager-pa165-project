/**
 * @author Simon Kobyda
 * @created 28.05.2021
 * @project airport-manager
 **/

import React, { useState, useEffect } from 'react';

import { Alert, AlertTitle } from '@material-ui/lab';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import { Add, DeleteForever, Edit } from "@material-ui/icons";

import CustomTable from '../components/CustomTable.js';
import {countries} from "../helpers/helpers";

import '../stylesheets/styles.css';

function countryToFlag(isoCode) {
  return typeof String.fromCodePoint !== 'undefined'
    ? isoCode
        .toUpperCase()
        .replace(/./g, (char) => String.fromCodePoint(char.charCodeAt(0) + 127397))
    : isoCode;
}

const useStyles = makeStyles({
  option: {
    fontSize: 15,
    '& > span': {
      marginRight: 10,
      fontSize: 18,
    },
  },
});

const AirportBody = ({ name, country, city, setName, setCountry, setCity }) => {
    const classes = useStyles();

    return (<>
        <Box m={1} component="span" display="block">
            <TextField label="City"
                value={city}
                onChange={e => setCity(e.target.value)} />
        </Box>
        <Box m={1} component="span" display="block">
            <TextField label="Name"
                value={name}
                onChange={e => setName(e.target.value)} />
        </Box>
        <Box m={1} component="span" display="block">
            <Autocomplete
              id="country-select-demo"
              value={countries.find(c => c.code === country)}
              onChange={(event, newValue) => setCountry(newValue.code)}
              style={{ width: 300 }}
              options={countries}
              classes={{
                option: classes.option,
              }}
              autoHighlight
              getOptionLabel={(option) => option.label}
              renderOption={(option) => (
                <React.Fragment>
                  <span>{countryToFlag(option.code)}</span>
                  {option.label} ({option.code})
                </React.Fragment>
              )}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="Choose a country"
                  variant="outlined"
                  inputProps={{
                    ...params.inputProps,
                    autoComplete: 'new-password', // disable autocomplete and autofill
                  }}
                />
              )}
            />
        </Box>
    </>);
}

const CreateAirport = ({ loggedUser, fetchAirports }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [city, setCity] = useState(undefined);

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/",
              {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ name, country, city }),
              }
        )
                .then(response => {
                    if (response.status === 201) {
                        fetchAirports();
                        setShowModal(false);
                    } else {
                        setErrorTitle("Could not create airport");
                        setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                    }
                })
                .catch(error => setErrorMessage(error))
    }

    if (loggedUser) {
        return (
            <>
                <Button onClick={() => setShowModal(true)} variant="contained" color="primary">
                    <Add style={{marginRight: "5px"}}/> Add airport
                </Button>
                <Dialog open={showModal}
                    onClose={() => setShowModal(false)}
                    maxWidth={'xs'}
                    fullWidth>
                    <DialogTitle>Create Airport</DialogTitle>
                    <DialogContent>
                        <AirportBody name={name}
                            country={country}
                            city={city}
                            setName={setName}
                            setCountry={setCountry}
                            setCity={setCity} />
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
                            CREATE
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

const EditAirport = ({ airport, loggedUser, fetchAirports }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState(airport.name);
    const [country, setCountry] = useState(airport.country);
    const [city, setCity] = useState(airport.city);

    const editHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/" + airport.id,
              {
                  method: 'PUT',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ id: airport.id, name, country, city }),
              }
        )
                .then(response => {
                    if (response.status === 200) {
                        fetchAirports();
                        setShowModal(false);
                    } else {
                        setErrorTitle("Could not edit airport");
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
                    <DialogTitle>{"Edit Details of Airport " + airport.name}</DialogTitle>
                    <DialogContent>
                        <AirportBody name={name}
                            country={country}
                            city={city}
                            setName={setName}
                            setCountry={setCountry}
                            setCity={setCity} />
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

const DeleteAirport = ({ airport, loggedUser, fetchAirports }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/" + airport.id, { method: 'DELETE' })
                .then(response => {
                    if (response.status === 204) {
                        fetchAirports();
                        setShowModal(false);
                    } else {
                        setErrorTitle("Could not delete airport");
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
                    <DialogTitle>{"Remove Airport " + airport.name}</DialogTitle>
                    <DialogContent>
                        Are you sure you want to delete this airport?
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

export const Airports = ({ loggedUser }) => {
    const [airports, setAirports] = useState([]);
    const fetchAirports = () => {
        fetch("http://localhost:8080/pa165/rest/airports")
            .then(res => res.json())
            .then(json => setAirports(json.sort((a, b) => a.name > b.name)));
    }
    useEffect(() => fetchAirports(), []);

    // The last headerless column is for cctions
    const header = ["Name", "Country", "City", ""];
    const data = airports.map(a => {
        return [
            a.name,
            a.country,
            <span>{countryToFlag(a.country)}{countries.find(c => c.code === a.country).label}</span>,
            <div style={{textAlign: "right"}}>
                <EditAirport airport={a} loggedUser={loggedUser} fetchAirports={fetchAirports}/>
                <DeleteAirport airport={a} loggedUser={loggedUser} fetchAirports={fetchAirports}/>
            </div>
        ]
    });

    const create = (<CreateAirport loggedUser={loggedUser} fetchAirports={fetchAirports} />);

    return(
            <Card>
                <CardContent>
                    <CustomTable title="List of Airports" header={header} data={data} create={create} />
                </CardContent>
            </Card>
    );
};
