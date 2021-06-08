import { createContext } from "react";

const UserContext = createContext({
    user: {
        isLogged: false,
        email: '',
        password: '',
        role: null,
    },
})

export default UserContext;
