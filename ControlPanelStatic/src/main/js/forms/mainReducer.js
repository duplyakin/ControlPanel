import {combineReducers} from "redux";
import permissionsCache from "../components/permissions/permissionsReducer";
import currentUserCache from "../components/currentUser/currentUserCacheReducer";
import callStatus from "../components/basic/popups/callStatusReducer";

export const mainReducer = combineReducers({
    permissionsCache,
    callStatus,
    currentUserCache
});