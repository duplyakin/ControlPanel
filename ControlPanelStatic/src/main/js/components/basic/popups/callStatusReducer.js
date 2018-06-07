import {modelReducer} from "react-redux-form";

const defaultState = {
    success: false,
    error: false,
    errorMessage: ""
};
//редьюсер для всплывающих окон, сообщающих об ошибке или, наоборот,
// успешном результате (как правило, какого-нибудь обращения к серверу)
export default modelReducer("callStatus", defaultState);