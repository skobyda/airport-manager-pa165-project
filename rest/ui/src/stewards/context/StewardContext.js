import {createContext} from "react";

export const initialFilter = {
    firstName: "",
    lastName: "",
    countryCode: "",
    passportNumber: "",
}

const StewardContext = createContext({
    loggedUser: {},
    fetchData: () => {
    }
})

export default StewardContext;