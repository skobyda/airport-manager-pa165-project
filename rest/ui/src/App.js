import React from 'react';
import './stylesheets/App.css';

import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';

import { Flights } from './flights/Flights.js';
import { Airports } from './airports/Airports.js';
import { Airplanes } from './airplanes/Airplanes.js';
import Stewards from './stewards/Stewards.js';
import LoginModal from "./components/LoginModal";
import Homepage from "./homepage/Homepage";
import Button from "@material-ui/core/Button";
import {List, ListItem, ListItemText, Menu, MenuItem} from "@material-ui/core";
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

const menu = { // index of menu items
    HOME: 0,
    FLIGHTS: 1,
    AIRPORTS: 2,
    AIRPLANES: 3,
    STEWARDS: 4,
}

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            page: 0,
            loggedUser: undefined,
            airports: [],
            openModal: false,
            anchorEl: null,
            selectedIndex: 0,
            user: {isLogged: false, email: '', password: ''}
        };

        this.onValueChanged = this.onValueChanged.bind(this);
    }

    onValueChanged(key, value) {
        this.setState({ [key]: value });
    }

    handleAirportSelect(event, index) {
        this.setState({anchorEl: null, selectedIndex: index});
    }

    authenticate = (isLogged, credentials) => {
        this.setState({user: {isLogged: isLogged, email: credentials.email, password: credentials.password}});
    }

    componentDidMount() {
        const onValueChanged = this.onValueChanged;

        fetch("http://localhost:8080/pa165/rest/airports")
            .then(res => res.json())
            .then(json => onValueChanged("airports", json));

        onValueChanged("loggedUser", { email: "fakeuser@fakeemail.cz", role: "admin" });
    }

    render() {
        const {loggedUser, page, airports, selectedIndex, user} = this.state;

        return (
            <div className="App">
                <AppBar position="static">
                    <Tabs aria-label="menu"
                          onChange={(event, value) => this.onValueChanged("page", value)}
                          value={page}>
                        <Tab label="Home" />
                        <Tab label="Flights" />
                        { user.isLogged && <Tab label="Airports" /> }
                        { user.isLogged && <Tab label="Airplanes" /> }
                        { user.isLogged && <Tab label="Stewards" /> }
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
                                    <ListItemText primary={airports[selectedIndex].name}/> <ArrowDropDownIcon />
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
                                     logout={this.authenticate}
                        />
                    </div>
                </AppBar>
                <div className="App-page">
                    <div className="App-content">
                        { page === menu.HOME && <Homepage airport={airports.length ? airports[selectedIndex] : null} loggedUser={loggedUser}/> }
                        { page === menu.FLIGHTS && <Flights airport={airports.length ? airports[selectedIndex] : null} loggedUser={loggedUser}/> }
                        { page === menu.AIRPORTS && <Airports loggedUser={loggedUser} airport={airports.length ? airports[selectedIndex] : null}/> }
                        { page === menu.AIRPLANES && <Airplanes loggedUser={loggedUser} airport={airports.length ? airports[selectedIndex] : null}/> }
                        { page === menu.STEWARDS && <Stewards loggedUser={user} airport={airports.length ? airports[selectedIndex] : null}/> }
                    </div>
                </div>
                <LoginModal openModal={this.state.openModal}
                            handleClose={() => this.onValueChanged("openModal", false)}
                            handleAuthentication={this.authenticate}
                />
            </div>
        );
    }
}

function LoginButton({isLogged, click, email, logout}) {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        logout(false, {email: '', password: ''});
    }

    if (isLogged) {
        return (
            <div style={{display: "flex", alignItems: "center"}}>
                <Button aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick}>
                    {email} <ArrowDropDownIcon />
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
