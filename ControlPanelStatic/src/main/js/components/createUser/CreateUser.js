import React from 'react';
import {TextBox} from '../basic/TextBox'
import {Button, Col, Form, FormGroup, Well} from "react-bootstrap";
import {SelectBox} from "../basic/SelectBox";

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
        this.state = {
            user: {
                username: "",
                password: "",
                roles: ["USER"],
                privileges: ["READ"],
                accountNonExpired: true,
                accountNonLocked: true,
                credentialsNonExpired: true,
                enabled: true
            }
        };
        this.handleChange = this.handleChange.bind(this);
        this.addUser = this.addUser.bind(this);
    }

    handleChange(change, event) {
        const toChange = this.state.user;
        toChange[change] = event.target.value;
        this.setState({user: toChange});
    }

    addUser() {
        const csrfToken = getCookie('XSRF-TOKEN');

        fetch('http://localhost:8090/users/add', {
            method: "PUT",
            headers:{
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
        console.log(this.state);
        return <div>
            <Well>Hi! It's user create form!</Well>

            <Form horizontal>

                <TextBox label={'Имя пользователя'}
                         value={this.state.user.username}
                         onChange={this.handleChange.bind(this, 'username')}/>

                <TextBox label={'Пароль'}
                         type={"password"}
                         value={this.state.user.password}
                         onChange={this.handleChange.bind(this, 'password')}/>

                <SelectBox label={"Роли"}
                           value={this.state.user.roles}
                           onChange={this.handleChange.bind(this, 'roles')}/>

                <SelectBox label={"Права"}
                           value={this.state.user.privileges}
                           onChange={this.handleChange.bind(this, 'privileges')}/>

                <FormGroup controlId="formHorizontalSubmit">
                    <Col sm={1}/>
                    <Col componentClass={Button} sm={2} onClick={this.addUser}>Create user</Col>
                </FormGroup>
            </Form>
        </div>
    }

}