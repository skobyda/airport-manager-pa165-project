import React, { useState, useContext, useEffect } from "react";
import axios from "axios";
import {Alert} from '@material-ui/lab'
import {
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField
} from "@material-ui/core";
import {mapValues} from 'lodash';

import {initialSteward} from "../helpers/helpers";
import StewardContext from "../context/StewardContext";

export default function EditStewardModal({stewardId, open, closeModal}) {
    const [steward, setSteward] = useState(initialSteward);
    const [error, setError] = useState(false);
    const {fetchData, loggedUser} = useContext(StewardContext);

    useEffect(() => {
        async function fetchSteward() {
            return axios.get(`http://localhost:8080/pa165/rest/stewards/${stewardId}`, {
                auth: {
                    username: loggedUser.email,
                    password: loggedUser.password
                }
            });
        }

        try {
            fetchSteward()
                .then(res => setSteward(res.data));
        } catch (e) {
            setError(true);
        }
    }, [stewardId, loggedUser.email, loggedUser.password]);


    const handleChange = event => {
        const {name, value} = event.target;
        setSteward(prevState => ({...prevState, [name]: value}));
    }

    const handleEdit = async () => {
        const stewardWithNulls = mapValues(steward, v => v === '' ? null : v);
        try {
            await axios.put(`http://localhost:8080/pa165/rest/stewards/${stewardId}`, stewardWithNulls);
            fetchData();
            closeModal();
        } catch (e) {
            setError(true);
        }
    }

    return (
        <Dialog onClose={closeModal} aria-labelledby="customized-dialog-title" open={open}>
            <DialogTitle id="customized-dialog-title" onClose={closeModal}>
                Update steward
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
                <Button onClick={handleEdit} color="primary" variant="contained">
                    Save changes
                </Button>
            </DialogActions>
            {error && <Alert severity="error">Steward was not updated!</Alert>}
        </Dialog>
    )
}