import {combineReducers} from "redux";
import permissionsCache from "../components/permissions/permissionsReducer";
import callStatus from "../components/basic/popups/callStatus";

export const mainReducer = combineReducers({
    permissionsCache,
    callStatus,
});