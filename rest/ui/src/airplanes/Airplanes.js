/**
 * @author Simon Kobyda
 * @created 28.05.2021
 * @project airport-manager
 **/

import React, { useState, useEffect } from 'react';

import { Alert, AlertTitle } from '@material-ui/lab';
import Box from '@material-ui/core/Box';
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
import { Add, DeleteForever, Edit } from "@material-ui/icons";

import CustomTable from '../components/CustomTable.js';

import '../stylesheets/styles.css';

const AirplaneTypes = ["JET", "COMMUTER", "PISTON", "TURBOPROP"]

const AirplaneBody = ({ name, type, capacity, setName, setType, setCapacity }) => {
    return (<>
        <Box m={1} component="span" display="block">
            <TextField label="Capacity"
                type="number"
                value={capacity}
                onChange={e => setCapacity(e.target.value)} />
        </Box>
        <Box m={1} component="span" display="block">
            <TextField label="Name"
                value={name}
                onChange={e => setName(e.target.value)} />
        </Box>
        <Box m={1} component="span" display="block">
            <FormControl className="airplane-type">
                <InputLabel>Type</InputLabel>
                <Select value={type}
                    onChange={e => setType(e.target.value)}>
                    {AirplaneTypes.map(type => <MenuItem value={type}>{type}</MenuItem>)}
                </Select>
            </FormControl>
        </Box>
    </>);
}

const CreateAirplane = ({ loggedUser, fetchAirplanes }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState("");
    const [type, setType] = useState("");
    const [capacity, setCapacity] = useState(undefined);

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/",
              {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ name, type, capacity }),
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

    if (loggedUser) {
        return (
            <>
                <Button onClick={() => setShowModal(true)} variant="contained" color="primary">
                    <Add style={{marginRight: "5px"}}/> Add airplane
                </Button>
                <Dialog open={showModal}
                    onClose={() => setShowModal(false)}
                    maxWidth={'xs'}
                    fullWidth>
                    <DialogTitle>Create Airplane</DialogTitle>
                    <DialogContent>
                        <AirplaneBody name={name}
                            type={type}
                            capacity={capacity}
                            setName={setName}
                            setType={setType}
                            setCapacity={setCapacity} />
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

const EditAirplane = ({ airplane, loggedUser, fetchAirplanes }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")
    const [name, setName] = useState(airplane.name);
    const [type, setType] = useState(airplane.type);
    const [capacity, setCapacity] = useState(airplane.capacity);

    const editHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/" + airplane.id,
              {
                  method: 'PUT',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ id: airplane.id, name, type, capacity }),
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

    if (loggedUser) {
        return (
            <>
                <Edit className="cursor-pointer" style={{fill: "#FEC601"}} onClick={() => setShowModal(true)} />
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
                            setCapacity={setCapacity} />
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

const DeleteAirplane = ({ airplane, loggedUser, fetchAirplanes }) => {
    const [showModal, setShowModal] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [errorTitle, setErrorTitle] = useState("")

    const deleteHandler = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes/" + airplane.id, { method: 'DELETE' })
                .then(response => {
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

    if (loggedUser) {
        return (
            <>
                <DeleteForever className="cursor-pointer" style={{fill: "#C20114"}} onClick={() => setShowModal(true)} />
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
    } else {
        return null;
    }
}

export const Airplanes = ({ loggedUser }) => {
    const [airplanes, setAirplanes] = useState([]);
    const fetchAirplanes = () => {
        fetch("http://localhost:8080/pa165/rest/airplanes")
            .then(res => res.json())
            .then(json => setAirplanes(json.sort((a, b) => a.name > b.name)));
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
                <EditAirplane airplane={a} loggedUser={loggedUser} fetchAirplanes={fetchAirplanes}/>
                <DeleteAirplane airplane={a} loggedUser={loggedUser} fetchAirplanes={fetchAirplanes}/>
            </div>
        ]
    });

    const create = (<CreateAirplane loggedUser={loggedUser} fetchAirplanes={fetchAirplanes} />);

    return(
            <Card>
                <CardContent>
                    <CustomTable title="List of Airplanes" header={header} data={data} create={create} />
                </CardContent>
            </Card>
    );
};
