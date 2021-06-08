import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import {Alert} from '@material-ui/lab'
import {Button, Dialog, DialogActions, DialogTitle} from "@material-ui/core";
import {mapValues} from 'lodash';

import {initialSteward} from "../helpers/helpers";
import StewardContext from "../context/StewardContext";
import StewardDetailForm from "./StewardDetailForm";

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
            fetchSteward().then(res => setSteward(res.data));
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
            await axios.put(`http://localhost:8080/pa165/rest/stewards/${stewardId}`, stewardWithNulls, {
                auth: {
                    username: loggedUser.email,
                    password: loggedUser.password
                }
            });
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
            <StewardDetailForm error={error} steward={steward} handleChange={handleChange}/>
            <DialogActions className="dialog-actions__container">
                <Button onClick={handleEdit} color="primary" variant="contained">
                    Save changes
                </Button>
            </DialogActions>
            {error && <Alert severity="error">Steward was not updated!</Alert>}
        </Dialog>
    )
}