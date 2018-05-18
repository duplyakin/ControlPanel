import React from 'react';
import {connect} from "react-redux";
import {TextInput} from '../../components/basic/TextInput'
import {MultiTagSelector} from "../../components/basic/MultiTagSelector";
import {CheckBox} from "../../components/basic/CheckBox";
import {Button, Col, Form, FormGroup, Well} from "react-bootstrap";

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

class CreateUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {
                username: "",
                password: "",
                roles: [],
                privileges: [],
                accountNonExpired: true,
                accountNonLocked: true,
                credentialsNonExpired: true,
                enabled: true
            }
        };
        this.handleSelectorChange = this.handleSelectorChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.addUser = this.addUser.bind(this);
    }

    handleSelectorChange(change, event) {
        const {user} = this.state;
        user[change] = event.target.value;
        this.setState({user});
    }

    handleInputChange(change, event) {
        const {user} = this.state;
        user[change] = event.target.value;
        this.setState({user});
    }

    handleCheckboxChange(change, event) {
        const {user} = this.state;
        user[change] = !user[change];
        this.setState({user});
    }

    addUser() {
        // const csrfToken = getCookie('XSRF-TOKEN');
        //
        // fetch('http://localhost:8090/users/add', {
        //     method: "PUT",
        //     headers: {
        //         'Content-Type': 'application/json',
        //         'X-XSRF-TOKEN': csrfToken
        //     },
        //     credentials: "include",
        //     redirect: "follow",
        //     mode: "cors",
        //     body: JSON.stringify(this.state.user)
        // }).then(response => response.json())
        //     .then(responseJson => {
        //         this.setState({user: responseJson})
        //     });
    }

    render() {
        console.log(this.state.user);
        return <div>
            <Well>Hi! It's user create form!</Well>

            <Form horizontal>

                <TextInput label={'Имя пользователя'}
                           value={this.state.user.username}
                           onChange={this.handleInputChange.bind(this, 'username')}/>

                <TextInput label={'Пароль'}
                           type={"password"}
                           value={this.state.user.password}
                           onChange={this.handleInputChange.bind(this, 'password')}/>

                <MultiTagSelector label={"Роли"}
                                  options={this.props.allRoles}
                                  value={this.state.user.roles}
                                  onChange={this.handleSelectorChange.bind(this, 'roles')}/>

                <MultiTagSelector label={"Права"}
                                  options={this.props.allPrivileges}
                                  value={this.state.user.privileges}
                                  onChange={this.handleSelectorChange.bind(this, 'privileges')}/>

                <CheckBox checked={this.state.user.accountNonExpired}
                          label={"CB"}
                          onChange={this.handleCheckboxChange.bind(this, 'accountNonExpired')}
                />

                <FormGroup controlId="formHorizontalSubmit">
                    <Col sm={1}/>
                    <Col componentClass={Button} sm={2} onClick={this.addUser}>Create user</Col>
                </FormGroup>
            </Form>
        </div>
    }

}

const mapStateToProps = (store) => {
    return {
        allRoles: store.permissionsCache.roles,
        allPrivileges: store.permissionsCache.privileges,
    }
};

export default connect(mapStateToProps)(CreateUser);