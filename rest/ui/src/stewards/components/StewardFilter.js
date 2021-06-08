import React, {useContext, useState} from "react";
import axios from "axios";
import {Button, Card, CardContent, TextField} from "@material-ui/core";
import {SearchOutlined} from "@material-ui/icons";
import {mapValues} from 'lodash';

import StewardContext from "../context/StewardContext";

export default function StewardFilter({handleClick}) {
    const [filter, setFilter] = useState({firstName: "", lastName: "", countryCode: ""});
    const {loggedUser} = useContext(StewardContext);

    const handleChange = event => {
        const {name, value} = event.target;
        setFilter(prevState => ({...prevState, [name]: value}));
    }

    function fetchData() {
        const filterWithNulls = mapValues(filter, v => v === '' ? null : v);
        axios.get("http://localhost:8080/pa165/rest/stewards", {
            params: filterWithNulls,
            auth: {username: loggedUser.email, password: loggedUser.password}
        })
            .then(res => handleClick(res.data))
    }

    return (
        <Card className="filter__card">
            <CardContent>
                <h5 className="card__title">Filter</h5>
                <div className="filter__container">
                    <TextField className="filter__inputs"
                               value={filter.firstName}
                               name="firstName"
                               id="outlined-basic"
                               label="First name"
                               variant="outlined"
                               size="small"
                               onChange={handleChange}
                    />
                    <TextField className="filter__inputs"
                               value={filter.lastName}
                               name="lastName"
                               id="outlined-basic"
                               label="Last Name"
                               variant="outlined"
                               size="small"
                               onChange={handleChange}
                    />
                    <TextField className="filter__inputs"
                               value={filter.countryCode}
                               name="countryCode"
                               id="outlined-basic"
                               label="Country code"
                               variant="outlined"
                               size="small"
                               onChange={handleChange}
                    />
                    <Button className="search-button"
                            variant="contained"
                            color="primary"
                            onClick={() => fetchData()}>
                        <SearchOutlined style={{marginRight: "5px"}}/> Search
                    </Button>
                </div>
            </CardContent>
        </Card>
    )
}