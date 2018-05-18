import permissionsCache from "../components/permissions/permissionsReducer";
import {combineReducers} from "redux";

export const mainReducer = combineReducers({
    permissionsCache
});