/**
 * @author Simon Kobyda
 **/

import React, {useContext, useEffect, useState} from 'react';
import axios from 'axios';

import {Alert, AlertTitle} from '@material-ui/lab';
import Button from '@material-ui/core/Button';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import {Add, DeleteForever, Edit} from "@material-ui/icons";

import CustomTable from '../components/CustomTable.js';

import './airplanes.css';
import UserContext from "../context/UserContext";

const AirplaneTypes = ["JET", "COMMUTER", "PISTON", "TURBOPROP"]

const AirplaneBody = ({name, type, capacity, setName, setType, setCapacity, error}) => {
    const nameValidation = (error && (name.length < 2 || name.length > 30));

    return (
        <div className="airplane-form__container">
            <TextField label="Capacity"
                       type="number"
                       value={capacity}
                       error={!!error && !capacity}
                       helperText={(error && !capacity) ? "Capacity is required" : ""}
                       variant="outlined"
                       onChange={e => setCapacity(e.target.value)}/>
            <TextField label="Name"
                       value={name}
                       error={!!error && (name.length < 2 || name.length > 30)}
                       helperText={nameValidation ? "Name between 2 and 30 characters" : ""}
                       variant="outlined"
                       onChange={e => setName(e.target.value)}/>
            <FormControl variant="outlined" style={{marginBottom: 20}}>
                <InputLabel id="select-id">Type</InputLabel>
                <Select value={type}
                        labelId="select-id"
                        error={!!error && !type}
                        onChange={e => setType(e.target.value)}>
                    {AirplaneTypes.map((type, index) => <MenuItem key={index} value={type}>{type}</MenuItem>)}
                </Select>
            </FormControl>
        </div>
    );
}

const CreateAirplane = ({fetchAirplanes}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState("");
    const [type, setType] = useState("");
    const [capacity, setCapacity] = useState(undefined);
    const {user} = useContext(UserContext);

    const handleClose = () => {
        setShowModal(false);
        setErrorMessage(null);
        setName("");
        setType("");
        setCapacity(null);
    }

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)
                },
                body: JSON.stringify({name, type, capacity}),
            }
        )
            .then(response => {
                if (response.status === 201) {
                    fetchAirplanes();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not create airplane");
                    setErrorMessage("Response status: " + response.status + ", Response text: " + response.statusText);
                }
            })
            .catch(error => setErrorMessage(error))
    }

    return (
        <>
            <Button onClick={() => setShowModal(true)} variant="contained" color="primary">
                <Add style={{marginRight: "5px"}}/> Add airplane
            </Button>
            <Dialog open={showModal}
                    onClose={handleClose}
                    maxWidth={'xs'}
                    fullWidth>
                <DialogTitle>Create Airplane</DialogTitle>
                <DialogContent>
                    <AirplaneBody name={name}
                                  type={type}
                                  capacity={capacity}
                                  setName={setName}
                                  setType={setType}
                                  setCapacity={setCapacity}
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
                    <Button onClick={deleteHandler} color="primary" variant="contained">
                        CREATE
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

const EditAirplane = ({airplane, fetchAirplanes}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState(airplane.name);
    const [type, setType] = useState(airplane.type);
    const [capacity, setCapacity] = useState(airplane.capacity);
    const {user} = useContext(UserContext);

    const editHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/" + airplane.id,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)
                },
                body: JSON.stringify({id: airplane.id, name, type, capacity}),
            }
        )
            .then(response => {
                if (response.status === 200) {
                    fetchAirplanes();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not edit airplane");
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
                <DialogTitle>{"Edit Details of Airplane " + airplane.name}</DialogTitle>
                <DialogContent>
                    <AirplaneBody name={name}
                                  type={type}
                                  capacity={capacity}
                                  setName={setName}
                                  setType={setType}
                                  setCapacity={setCapacity}
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

const DeleteAirplane = ({airplane, fetchAirplanes}) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const {user} = useContext(UserContext);

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/" + airplane.id, {
            method: 'DELETE',
            headers: {'Authorization': 'Basic ' + btoa(`${user.email}:${user.password}`)}
        }).then(response => {
                if (response.status === 204) {
                    fetchAirplanes();
                    setShowModal(false);
                } else {
                    setErrorTitle("Could not delete airplane");
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
                <DialogTitle>{"Remove Airplane " + airplane.name}</DialogTitle>
                <DialogContent>
                    Are you sure you want to delete this airplane?
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

export const Airplanes = () => {
    const [airplanes, setAirplanes] = useState([]);
    const {user} = useContext(UserContext);

    const fetchAirplanes = () => {
        axios.get("http://localhost:8080/pa165/rest/airplanes", {
            auth: {username: user.email, password: user.password}
        }).then(res => setAirplanes(res.data.sort((a, b) => a.name > b.name)));
    }
    useEffect(() => fetchAirplanes(), []);

    // The last headerless column is for cctions
    const header = ["Name", "Capacity", "Type", ""];
    const data = airplanes.map(a => {
        return [
            a.name,
            a.capacity,
            a.type,
            <div style={{textAlign: "right"}}>
                <EditAirplane airplane={a} fetchAirplanes={fetchAirplanes}/>
                <DeleteAirplane airplane={a} fetchAirplanes={fetchAirplanes}/>
            </div>
        ]
    });

    const create = (<CreateAirplane fetchAirplanes={fetchAirplanes}/>);

    return (
        <Card className="big-card--bottom-margin">
            <CardContent>
                <CustomTable title="List of Airplanes" header={header} data={data} create={create}/>
            </CardContent>
        </Card>
    );
};
