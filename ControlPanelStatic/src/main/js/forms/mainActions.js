import {actions} from "react-redux-form";

export const endpoints = {
    GET: "users/get",
    ADD: "users/add",
    DELETE: "users/delete",
    UPDATE: "users/update",
    SET_ROLES: "roles/set",
    GET_ALL_ROLES: "roles/getAll",
    SET_PRIVILEGES: "privileges/set",
    GET_ALL_PRIVILEGES: "privileges/getAll",
    GET_CURRENT_USER: "currentUser/get",
    CHANGE_PASSWORD: "currentUser/changePassword",
    ADD_EQUIPMENT: "equipmentTypes/add",
    EQUIPMENT_GET: "equipmentTypes/get",
    EQUIPMENT_GET_ALL: "equipmentTypes/getAll",
    EQUIPMENT_UNIT_GET: "equipmentUnits/get",
    EQUIPMENT_UNIT_ADD: "equipmentUnits/add",
    EQUIPMENT_UNIT_UPDATE: "equipmentUnits/update",
    EVENT_TYPE_ADD: "eventTypes/add",
    EVENT_TYPES_GET_ALL: "eventTypes/getAll",
    EVENT_UNIT_ADD: "events/add",
    EQUIPMENT_UNIT_ADD_EVENT: "equipmentUnits/addEvent",
};

function getCookie(name) {
    if (!document.cookie) {
        return null;
    }

    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith(name + '='));

    if (xsrfCookies.length === 0) {
        return null;
    }

    return decodeURIComponent(xsrfCookies[0].split('=')[1]);
}

const constructModifyingRequest = ({method, body}) => {
    return {
        method,
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCookie('XSRF-TOKEN'),
        },
        credentials: "include",
        redirect: "follow",
        mode: "cors",
        body: JSON.stringify(body)
    }
};

const constructGetRequest = () => {
    return {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: "include",
        redirect: "follow",
        mode: "cors",
    }
};

/**
 * template-метод для отправки запросов к серверу.
 * @param dispatch - redux#store.dispatch. Нужен для оповещения store об успешном/неуспешном результате запроса
 * @param endpoint - элемент из mainActions#endpoints
 * @param postprocess = (response) => {...} - хук для использования результата запроса.
 * Позволяет записать ответ от сервера в состояние компонента, в котором был осуществлён запрос.
 * @param method - http-метод.
 * @param body - тело запроса
 * @param errorMessage - надпись во всплывающем окне в случае ошибки.
 * @param popupIfSuccess -  отображать ли всплывающее окно в случае успешного совершения запроса.
 */
export const executeRequest = (
    {
        dispatch,
        endpoint,
        postprocess = (e) => e,
        method = "GET",
        body = {},
        errorMessage,
        popupIfSuccess = true,
    } = {}) => {
    const request = method === "GET"
        ? constructGetRequest()
        : constructModifyingRequest({method, body});
    // Путь к серверу возьмём из webpack.config
    fetch(`${SERVER_PATH}/${endpoint}`, request)
        .then(response => {
            if (!response.ok) {
                // Если запрос завершился неуспешно - выведем какое-нибудь сообщение об ошибке
                // в соответствии со статусом ответа.
                switch (response.status) {
                    case 403:
                        throw new Error("Запрещено");
                    case 404:
                        throw new Error("Не найдено");
                    case 412:
                        throw new Error("Нарушены предусловия для совершения действия");
                    default:
                        throw new Error("response is not ok")
                }
            }
            return response.json()
        })
        // применим хук для постобработки
        .then(responseJson => {
            postprocess(responseJson);
            return responseJson
        })
        .then((resp) => {
            // Отобразим всплывающее окно об успехе
            popupIfSuccess && dispatch(actions.merge("callStatus", {
                success: true,
                successMessage: resp.id
            }))
        })
        .catch(error => {
            // Отобразим всплывающее окно о неудаче.
            dispatch(actions.merge("callStatus", {error: true, errorMessage: `${errorMessage}: ${error.message}`}))
        })
};