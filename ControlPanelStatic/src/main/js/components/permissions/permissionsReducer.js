import {modelReducer} from "react-redux-form";

const defaultState = {roles: ["r_1", "r_2"], privileges: ['p_1', 'p_2']};

export default modelReducer("permissionsCache", defaultState)