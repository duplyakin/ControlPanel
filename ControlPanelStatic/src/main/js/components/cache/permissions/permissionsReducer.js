import {modelReducer} from "react-redux-form";

const defaultState = {roles: [], privileges: []};

export default modelReducer("permissionsCache", defaultState)