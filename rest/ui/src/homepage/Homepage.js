import React, { useEffect, useState } from "react";
import { Card, makeStyles } from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import axios from "axios";
import CustomTable from "../components/CustomTable";

const useStyles = makeStyles({
    root: {
        minWidth: 275,
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
});

export default function Homepage({airport}) {
    const [arrivals, setArrivals] = useState([]);
    const [departures, setDepartures] = useState([]);

    const limit = 2;

    function fetchArrivals() {
        return axios.get(`http://localhost:8080/pa165/rest/flights/${airport.id}/arrivals/${limit}`);
    }

    function fetchDepartures() {
        return axios.get(`http://localhost:8080/pa165/rest/flights/${airport.id}/departures/${limit}`);
    }

    useEffect(() => {
        if (airport) {
            fetchArrivals().then(res => setArrivals(res.data));
            fetchDepartures().then(res => setDepartures(res.data));
        }
    }, [airport]);


    if (!airport) {
        return <></>
    }

    return (
        <>
            <h2 style={{color: "black"}}>{airport.name}</h2>
            <div className="homepage-grid__container">
                <FlightsCard title="Most recent arrivals" data={arrivals} arrival />
                <FlightsCard title="Most recent departures" data={departures} />
            </div>
        </>
    )
}

function FlightsCard({title, arrival, data}) {
    const classes = useStyles();
    const header = ["Flight code", arrival ? "Origin airport" : "Destination airport", "Time"];

    const formattedData = data.map(a => {
        return [
            a.flightCode,
            arrival ? "origin airport" : "destination airport",
            arrival ? a.arrival : a.departure,
        ]
    });

    return (
        <Card className={classes.root}>
            <CardContent>
                <h4>{title}</h4>
                <CustomTable header={header} data={formattedData} />
            </CardContent>
        </Card>
    )
}
