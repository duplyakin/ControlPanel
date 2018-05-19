import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import {TextInput} from "../../components/basic/TextInput";
import Button from "@material-ui/core/es/Button/Button";

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

export class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            name: "",
        };
        this.getUser = this.getUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.updateUser = this.updateUser.bind(this);
    }

    handleChange(event) {
        const {user} = this.state;
        this.setState({user, name:event.target.value});
    }

    componentDidMount() {
        this.getUser()
    }

    getUser() {
        fetch(`http://localhost:8090/users/get/${this.state.name}`, {
            method: "GET",
            credentials: "include",
            redirect: "follow",
            mode: "cors"
        }).then(response => response.json())
            .then(responseJson => {
                this.setState({user: responseJson})
            });
    }

    updateUser() {
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
            <Well>Hi! It's user edit form!</Well>
            <TextInput value={this.state.name} onChange={this.handleChange} label="Имя пользователя"/>
            <Button onClick={this.getUser}>Get user</Button>
            {!_.isEmpty(this.state.user) && <User user={this.state.user} onSubmit={this.updateUser}/>}
        </div>
    }

}