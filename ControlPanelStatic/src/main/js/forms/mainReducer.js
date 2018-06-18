import {combineReducers} from "redux";
import permissionsCache from "../components/cache/permissions/permissionsReducer";
import currentUserCache from "../components/cache/currentUser/currentUserCacheReducer";
import callStatus from "../components/basic/popups/callStatusReducer";
import addInventoryReducer from "../forms/addInventory/addInventoryReducer"

export const mainReducer = combineReducers({
    permissionsCache,
    callStatus,
    currentUserCache,
    addInventoryReducer,
});