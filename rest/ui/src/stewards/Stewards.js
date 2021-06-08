import React, {useEffect, useState, useContext} from "react";
import axios from "axios";
import {Card, CardContent} from "@material-ui/core";
import {mapValues} from "lodash";
import {DeleteForever, Edit} from "@material-ui/icons";

import CreateStewardModal from "./components/CreateStewardModal.js";
import DeleteConfirmationModal from "./components/DeleteConfirmationModal.js";
import EditStewardModal from "./components/EditStewardModal.js";
import StewardFilter from './components/StewardFilter.js';
import CustomTable from '../components/CustomTable.js';
import StewardContext, {initialFilter} from './context/StewardContext.js';
import './steward.css';
import UserContext from "../context/UserContext";


export default function Stewards() {
    const [stewards, setStewards] = useState([]);
    const [deleteModal, setDeleteModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [stewardId, setStewardId] = useState(null);
    const {user} = useContext(UserContext);

    function handleModal(id, editModal = false) {
        setStewardId(id);
        editModal ? setEditModal(true) : setDeleteModal(true);
    }

    function handleCloseModal(editModal) {
        setStewardId(null);
        editModal ? setEditModal(false) : setDeleteModal(false);
    }


    function fetchData(filter = initialFilter) {
        const filterWithNulls = mapValues(filter, v => v === '' ? null : v);
        axios.get("http://localhost:8080/pa165/rest/stewards", {
            params: filterWithNulls,
            auth: {username: user.email, password: user.password}
        }).then(res => handleClick(res.data))
    }

    useEffect(() => {
        fetchData();
    }, []);


    function handleClick(stewards) {
        setStewards(stewards);
    }

    // The last headerless column is for actions
    const header = ["First name", "Last name", "Country code", "Passport number", ""];
    const data = stewards.map(s => {
        return [
            s.firstName,
            s.lastName,
            s.countryCode,
            s.passportNumber,
            <div style={{textAlign: "right"}}>
                <Edit className="cursor-pointer" style={{fill: "#FEC601"}} onClick={() => handleModal(s.id, true)}/>
                <DeleteForever className="cursor-pointer" style={{fill: "#C20114"}} onClick={() => handleModal(s.id)}/>
            </div>
        ]
    });
    const create = (<CreateStewardModal/>);

    return (
        <StewardContext.Provider value={{fetchData, loggedUser: user}}>
            <StewardFilter handleClick={handleClick}/>
            <Card className="big-card--bottom-margin">
                <CardContent>
                    <CustomTable title="List of Stewards" header={header} data={data} create={create}/>
                </CardContent>
            </Card>
            <DeleteConfirmationModal stewardId={stewardId} open={deleteModal}
                                     closeModal={() => handleCloseModal(false)}/>
            {stewardId &&
            <EditStewardModal stewardId={stewardId} open={editModal} closeModal={() => handleCloseModal(true)}/>}
        </StewardContext.Provider>
    )
}
