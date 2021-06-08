/**
 * @author Simon Kobyda
 **/

import React, {useContext, useEffect, useState} from 'react';
import axios from 'axios';

import {Alert, AlertTitle} from '@material-ui/lab';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Button from '@material-ui/core/Button';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import {makeStyles} from '@material-ui/core/styles';
import {Add, DeleteForever, Edit} from "@material-ui/icons";

import './airports.css';
import CustomTable from '../components/CustomTable.js';
import {countries} from "../helpers/helpers";

import UserContext from "../context/UserContext";

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

const AirportBody = ({name, country, city, setName, setCountry, setCity, error}) => {
    const classes = useStyles();

    return (
        <div className="airport-form__container">
            <TextField label="City"
                       value={city}
                       error={!!error && !city}
                       helperText={(error && !city) ? "City name is required" : ""}
                       variant="outlined"
                       onChange={e => setCity(e.target.value)}/>
            <TextField label="Name"
                       value={name}
                       error={!!error && !name}
                       helperText={(error && !name) ? "Name of airport is required" : ""}
                       variant="outlined"
                       onChange={e => setName(e.target.value)}/>
            <Autocomplete
                id="country-select-demo"
                value={countries.find(c => c.code === country)}
                onChange={(event, newValue) => setCountry(newValue.code)}
                style={{marginBottom: 20}}
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
                        error={error && !country}
                        helperText={(!!error && !country) ? "Country is required" : ""}
                        inputProps={{
                            ...params.inputProps,
                            autoComplete: 'new-password', // disable autocomplete and autofill
                        }}
                    />
                )}
            />
        </div>
    );
}

const CreateAirport = ({fetchAirports}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [city, setCity] = useState(undefined);
    const {user} = useContext(UserContext);

    const handleClose = () => {
        setShowModal(false);
        setErrorMessage(null);
        setName("");
        setCountry("");
        setCity(null);
    }

    const createHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)
                },
                body: JSON.stringify({name, country, city}),
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

    return (
        <>
            <Button onClick={() => setShowModal(true)} variant="contained" color="primary">
                <Add style={{marginRight: "5px"}}/> Add airport
            </Button>
            <Dialog open={showModal}
                    onClose={handleClose}
                    maxWidth={'xs'}
                    fullWidth>
                <DialogTitle>Create Airport</DialogTitle>
                <DialogContent>
                    <AirportBody name={name}
                                 country={country}
                                 city={city}
                                 setName={setName}
                                 setCountry={setCountry}
                                 setCity={setCity}
                                 error={errorMessage}
                    />
                    {errorMessage &&
                    <Alert severity="error">
                        <AlertTitle>{errorTitle}</AlertTitle>
                        {errorMessage}
                    </Alert>}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="default" variant="contained">
                        Close
                    </Button>
                    <Button onClick={createHandler} color="primary" variant="contained">
                        CREATE
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

const EditAirport = ({airport, fetchAirports}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState(airport.name);
    const [country, setCountry] = useState(airport.country);
    const [city, setCity] = useState(airport.city);
    const {user} = useContext(UserContext);

    const editHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/" + airport.id,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)
                },
                body: JSON.stringify({id: airport.id, name, country, city}),
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

    return (
        <>
            <Edit className="cursor-pointer" style={{fill: "#FEC601"}} onClick={() => setShowModal(true)}/>
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
                                 setCity={setCity}
                                 error={errorMessage}
                    />
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

const DeleteAirport = ({airport, fetchAirports}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const {user} = useContext(UserContext);

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airports/" + airport.id,
            {
                method: 'DELETE',
                headers: {'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)}
            }).then(response => {
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

    return (
        <>
            <DeleteForever className="cursor-pointer" style={{fill: "#C20114"}} onClick={() => setShowModal(true)}/>
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
}

export const Airports = () => {
    const [airports, setAirports] = useState([]);
    const {user} = useContext(UserContext);

    const fetchAirports = () => {
        axios.get("http://localhost:8080/pa165/rest/airports", {
            auth: {username: user.email, password: user.password}
        }).then(res => setAirports(res.data));
    }
    useEffect(() => fetchAirports(), []);

    // The last headerless column is for cctions
    const header = ["Name", "Country", "City", ""];
    const data = airports.map(a => {
        return [
            a.name,
            countries.find(c => c.code === a.country).label,
            a.city,
            <div style={{textAlign: "right"}}>
                <EditAirport airport={a} fetchAirports={fetchAirports}/>
                <DeleteAirport airport={a} fetchAirports={fetchAirports}/>
            </div>
        ]
    });

    const create = (<CreateAirport fetchAirports={fetchAirports}/>);

    return (
        <Card>
            <CardContent>
                <CustomTable title="List of Airports" header={header} data={data} create={create}/>
            </CardContent>
        </Card>
    );
};
