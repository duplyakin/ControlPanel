import {modelReducer} from "react-redux-form";

const defaultState = {
    success: false,
    error: false,
};

export default modelReducer("callStatus", defaultState);