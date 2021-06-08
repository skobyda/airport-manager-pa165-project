import React, {useContext, useState} from 'react';
import {Alert} from '@material-ui/lab'
import axios from "axios";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText} from '@material-ui/core';
import StewardContext from "../context/StewardContext";

export default function DeleteConfirmationModal({open, closeModal, stewardId}) {
    const [error, setError] = useState(false);
    const {fetchData, loggedUser} = useContext(StewardContext);

    async function handleConfirmation() {
        try {
            await axios.delete(`http://localhost:8080/pa165/rest/stewards/${stewardId}`, {
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

    function handleClose() {
        setError(false);
        closeModal();
    }

    return (
        <Dialog
            open={open}
            onClose={() => {
                setError(false);
                closeModal()
            }}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Are you sure you want to delete this steward?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => {
                    setError(false);
                    closeModal()
                }} color="primary" variant="contained">
                    No
                </Button>
                <Button onClick={handleConfirmation} color="secondary" autoFocus variant="contained">
                    Yes
                </Button>
            </DialogActions>
            {error && <Alert severity="error">Steward was not deleted!</Alert>}
        </Dialog>
    );
}