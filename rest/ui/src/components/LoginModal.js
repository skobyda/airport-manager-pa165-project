import React, { useState} from "react";
import { Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import axios from "axios";
import { Alert } from "@material-ui/lab";

export default function LoginModal({openModal, handleClose, handleAuthentication}) {
    const [credentials, setCredentials] = useState({email: '', password: ''});
    const [error, setError] = useState(false);

    async function authenticate() {
        try {
            await axios.get("http://localhost:8080/pa165/rest/users/login", {
                auth: {
                    username: credentials.email,
                    password: credentials.password
                }
            });
            handleAuthentication(true, credentials);
            closeModal();
        } catch (e) {
            setError(true);
        }
    }

    const handleChange = event => {
        const {name, value} = event.target;
        setCredentials(prevState => ({...prevState, [name]: value}));
    }

    const closeModal = () => {
        setCredentials({email: '', password: ''});
        handleClose();
    }

    return (
        <Dialog open={openModal} onClose={closeModal} aria-labelledby="form-dialog-title" maxWidth={'sm'} fullWidth>
            <DialogTitle id="form-dialog-title">Log in to system</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    value={credentials.userName}
                    margin="dense"
                    name="email"
                    label="Email"
                    fullWidth
                    onChange={handleChange}
                />
                <TextField
                    value={credentials.password}
                    margin="dense"
                    name="password"
                    type="password"
                    label="Password"
                    fullWidth
                    onChange={handleChange}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={closeModal} color="default" variant="contained">
                    Cancel
                </Button>
                <Button style={{marginRight: "15px"}} onClick={authenticate} color="primary" variant="contained">
                    Login
                </Button>
            </DialogActions>
            {error && <Alert severity="error">Wrong credentials!</Alert>}
        </Dialog>
    )
}
