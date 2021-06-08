import React from 'react';
import './stylesheets/App.css';

import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import {List, ListItem, ListItemText, Menu, MenuItem, AppBar, Tabs, Tab, Button} from "@material-ui/core";

import {Flights} from './flights/Flights.js';
import {Airports} from './airports/Airports.js';
import {Airplanes} from './airplanes/Airplanes.js';
import Stewards from './stewards/Stewards.js';
import LoginModal from "./components/LoginModal";
import Homepage from "./homepage/Homepage";
import UserContext from "./context/UserContext";
import { ROLES, hasPermission } from "./helpers/helpers";

const menu = { // index of menu items
    HOME: 0,
    DEPARTURES: 1,
    ARRIVALS: 2,
    STEWARDS: 3,
    AIRPLANES: 4,
    AIRPORTS: 5,
}

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            page: 0,
            airports: [],
            openModal: false,
            anchorEl: null,
            selectedIndex: 0,
            user: {isLogged: false, email: '', password: '', role: null}
        };

        this.onValueChanged = this.onValueChanged.bind(this);
    }

    onValueChanged(key, value) {
        this.setState({[key]: value});
    }

    handleAirportSelect(event, index) {
        this.setState({anchorEl: null, selectedIndex: index});
    }

    handleLogoutPage() {
        this.setState({page: 0});
    }

    authenticate = (isLogged, credentials, role) => {
        this.setState({user: {
                isLogged: isLogged,
                email: credentials.email,
                password: credentials.password,
                role: role
        }});
    }

    componentDidMount() {
        const onValueChanged = this.onValueChanged;

        fetch("http://localhost:8080/pa165/rest/airports")
            .then(res => res.json())
            .then(json => onValueChanged("airports", json));
    }

    render() {
        const {page, airports, selectedIndex, user} = this.state;

        return (
            <UserContext.Provider value={{user}}>
                <div className="App">
                    <AppBar position="sticky">
                        <Tabs aria-label="menu"
                              onChange={(event, value) => this.onValueChanged("page", value)}
                              value={page}>
                            <Tab label="Home"/>
                            <Tab label="Departures"/>
                            <Tab label="Arrivals"/>
                            {hasPermission(ROLES.FLIGHT_MANAGER, user.role) && <Tab label="Stewards"/>}
                            {hasPermission(ROLES.AIRPORT_MANAGER, user.role) && <Tab label="Airplanes"/>}
                            {hasPermission(ROLES.AIRPORT_MANAGER, user.role) && <Tab label="Airports"/>}
                        </Tabs>

                        <div className="right-menu">
                            {airports.length &&
                            <>
                                <List component="nav" aria-label="Device settings">
                                    <ListItem
                                        button
                                        aria-haspopup="true"
                                        aria-controls="lock-menu"
                                        onClick={(event) => this.onValueChanged("anchorEl", event.currentTarget)}
                                    >
                                        <ListItemText primary={airports[selectedIndex].name}/> <ArrowDropDownIcon/>
                                    </ListItem>
                                </List>

                                <Menu
                                    id="simple-menu"
                                    anchorEl={this.state.anchorEl}
                                    keepMounted
                                    open={Boolean(this.state.anchorEl)}
                                    onClose={() => this.onValueChanged("anchorEl", null)}
                                >
                                    {this.state.airports.map((airport, index) =>
                                        <MenuItem key={airport.id}
                                                  selected={index === selectedIndex}
                                                  onClick={(event) => this.handleAirportSelect(event, index)}>
                                            {airport.name}
                                        </MenuItem>
                                    )}
                                </Menu>
                            </>
                            }
                            <LoginButton isLogged={user.isLogged}
                                         click={(key, val) => this.onValueChanged(key, val)}
                                         email={user.email}
                                         handlePage={() => this.handleLogoutPage()}
                                         logout={this.authenticate}
                            />
                        </div>
                    </AppBar>
                    <div className="App-page">
                        <div className="App-content">
                            {page === menu.HOME &&
                            <Homepage airport={airports.length ? airports[selectedIndex] : null} />}
                            {page === menu.DEPARTURES &&
                            <Flights airport={airports.length ? airports[selectedIndex] : null} departuresPage />}
                            {page === menu.ARRIVALS &&
                            <Flights airport={airports.length ? airports[selectedIndex] : null} departuresPage={false} />}
                            {page === menu.STEWARDS &&
                            <Stewards airport={airports.length ? airports[selectedIndex] : null}/>}
                            {page === menu.AIRPORTS &&
                            <Airports airport={airports.length ? airports[selectedIndex] : null}/>}
                            {page === menu.AIRPLANES &&
                            <Airplanes airport={airports.length ? airports[selectedIndex] : null} />}
                        </div>
                    </div>
                    <LoginModal openModal={this.state.openModal}
                                handleClose={() => this.onValueChanged("openModal", false)}
                                handleAuthentication={this.authenticate}
                    />
                </div>
            </UserContext.Provider>
        );
    }
}

function LoginButton({isLogged, click, email, logout, handlePage}) {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        handlePage();
        logout(false, {email: '', password: '', role: null});
    }

    if (isLogged) {
        return (
            <div style={{display: "flex", alignItems: "center"}}>
                <Button aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick}>
                    {email} <ArrowDropDownIcon/>
                </Button>
                <Menu
                    id="simple-menu"
                    anchorEl={anchorEl}
                    keepMounted
                    open={Boolean(anchorEl)}
                    onClose={handleClose}
                >
                    <MenuItem onClick={handleLogout}>Logout</MenuItem>
                </Menu>
            </div>
        )
    }

    return (
        <Button
            className="login__button cursor-pointer"
            onClick={() => click("openModal", true)}
        >
            LOGIN
        </Button>
    )
}

export default App;
