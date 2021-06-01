import React, { useState, useContext } from "react";
import axios from "axios";
import { Add } from "@material-ui/icons";
import { Alert } from '@material-ui/lab'
import {
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField
} from "@material-ui/core";
import { mapValues } from 'lodash';

import { initialSteward } from "../helpers/helpers";
import StewardContext from "../context/StewardContext";

export default function CreateStewardModal() {
    const [open, setOpen] = useState(false);
    const [steward, setSteward] = useState(initialSteward);
    const [error, setError] = useState(false);
    const {fetchData, loggedUser} = useContext(StewardContext);

    const handleClose = () => {
        setSteward(initialSteward);
        setError(false);
        setOpen(false);
    }

    const handleChange = event => {
        const {name, value} = event.target;
        setSteward(prevState => ({...prevState, [name]: value}));
    }

    const handleCreate = async () => {
        const stewardWithNulls = mapValues(steward, v => v === '' ? null : v);
        try {
            await axios.post("http://localhost:8080/pa165/rest/stewards", stewardWithNulls, {
                auth: {
                    username: loggedUser.email,
                    password: loggedUser.password
                }
            });
            fetchData();
            handleClose();
        } catch (e) {
            setError(true);
        }
    }

    return (
        <>
            <Button variant="contained" color="primary" onClick={() => setOpen(true)}>
                <Add style={{marginRight: "5px"}}/> Add steward
            </Button>
            <Dialog onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
                <DialogTitle id="customized-dialog-title" onClose={handleClose}>
                    Create new steward
                </DialogTitle>
                <DialogContent dividers>
                    <div className="create-modal-inputs__container">
                        <TextField
                            autoFocus
                            className="create__input"
                            value={steward.firstName}
                            name="firstName"
                            label="First name"
                            variant="outlined"
                            size="small"
                            onChange={handleChange}
                        />
                        <TextField
                            className="create__input"
                            value={steward.lastName}
                            name="lastName"
                            label="Last name"
                            variant="outlined"
                            size="small"
                            onChange={handleChange}
                        />
                    </div>

                    <div className="create-modal-inputs__container">
                        <TextField
                            className="create__input"
                            value={steward.countryCode}
                            name="countryCode"
                            label="Country code"
                            variant="outlined"
                            size="small"
                            onChange={handleChange}
                        />
                        <TextField
                            className="create__input"
                            value={steward.passportNumber}
                            name="passportNumber"
                            label="Passport number"
                            variant="outlined"
                            size="small"
                            onChange={handleChange}
                        />
                    </div>
                </DialogContent>
                <DialogActions className="dialog-actions__container">
                    <Button onClick={handleCreate} color="primary" variant="contained">
                        Save changes
                    </Button>
                </DialogActions>
                { error && <Alert severity="error">Steward was not created!</Alert> }
            </Dialog>
        </>
    )
}