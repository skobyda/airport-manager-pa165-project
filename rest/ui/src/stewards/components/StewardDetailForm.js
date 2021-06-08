import React from "react";
import {DialogContent, TextField} from "@material-ui/core";

export default function StewardDetailForm({error, steward, handleChange}) {
    const countryCodeValidation = error && steward.countryCode.length !== 2;
    const passportValidation = error && steward.passportNumber.length !== 9;

    return (
        <DialogContent dividers>
            <div className="create-modal-inputs__container">
                <TextField
                    autoFocus
                    className="create__input"
                    value={steward.firstName}
                    error={error && steward.firstName === ""}
                    helperText={(error && steward.firstName === "") ? "At least one character" : ""}
                    name="firstName"
                    label="First name"
                    variant="outlined"
                    onChange={handleChange}
                />
                <TextField
                    className="create__input"
                    value={steward.lastName}
                    error={error && steward.lastName.length <= 1}
                    helperText={(error && steward.lastName.length <= 1) ? "At least two characters" : ""}
                    name="lastName"
                    label="Last name"
                    variant="outlined"
                    onChange={handleChange}
                />
            </div>

            <div className="create-modal-inputs__container">
                <TextField
                    className="create__input"
                    value={steward.countryCode}
                    error={countryCodeValidation}
                    helperText={countryCodeValidation ? "Exactly 2 letters" : ""}
                    name="countryCode"
                    label="Country code"
                    variant="outlined"
                    onChange={handleChange}
                />
                <TextField
                    className="create__input"
                    value={steward.passportNumber}
                    error={passportValidation}
                    helperText={passportValidation ? "Exactly 9 characters" : ""}
                    name="passportNumber"
                    label="Passport number"
                    variant="outlined"
                    onChange={handleChange}
                />
            </div>
        </DialogContent>
    )
}