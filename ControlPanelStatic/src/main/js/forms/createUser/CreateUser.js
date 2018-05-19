import React from 'react';
import {Well} from "react-bootstrap";
import User from "../../components/user/User";

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

export class CreateUser extends React.Component {

    constructor(props) {
        super(props);
        this.addUser = this.addUser.bind(this);
    }

    addUser() {
        const csrfToken = getCookie('XSRF-TOKEN');

        fetch('http://localhost:8090/users/add', {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken
            },
            credentials: "include",
            redirect: "follow",
            mode: "cors",
            body: JSON.stringify(this.state.user)
        }).then(response => response.json())
            .then(responseJson => {
                this.setState({user: responseJson})
            });
    }

    render() {
        return <div>
            <Well>Hi! It's user create form!</Well>
            <User onSubmit={this.addUser}/>
        </div>
    }

}
