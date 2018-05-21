export const endpoints = {
    GET: "users/get",
    ADD: "/add",
    DELETE: "/delete",
    UPDATE: "/update",
    SET_ROLES: "/setRoles",
    SET_PRIVILEGES: "/setPrivileges"
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

export const executeRequest = ({
                                   endpoint,
                                   postprocess = (e) => {
                                   },
                                   method = "GET",
                                   body = {}
                               }) => {
    const request = method === "GET"
        ? constructGetRequest()
        : constructModifyingRequest({method, body});
    fetch(`http://localhost:8090/${endpoint}`, request)
        .then(response => response.json())
        .then(responseJson => postprocess(responseJson))
        .catch(e => console.log(e))
};