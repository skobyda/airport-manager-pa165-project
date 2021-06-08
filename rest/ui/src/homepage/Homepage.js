import React, {useEffect, useState} from "react";
import {Card} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import axios from "axios";
import CustomTable from "../components/CustomTable";

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
                <FlightsCard title="Most recent departures" data={arrivals} departure/>
                <FlightsCard title="Most recent arrivals" data={departures}/>
            </div>
        </>
    )
}

function FlightsCard({title, departure, data}) {
    const header = ["Flight code", departure ? "Origin airport" : "Destination airport", "Time"];

    const formattedData = data.map(a => {
        return [
            a.flightCode,
            departure ? a.destinationAirport.name : a.originAirport.name,
            departure ? a.arrival.replace('T', ' ') : a.departure.replace('T', ' '),
        ]
    });

    return (
        <Card>
            <CardContent>
                <h4>{title}</h4>
                <CustomTable header={header} data={formattedData} hideOrder/>
            </CardContent>
        </Card>
    )
}
