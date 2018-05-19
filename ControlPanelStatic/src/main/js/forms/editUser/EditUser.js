import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import {TextInput} from "../../components/basic/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest} from "../mainActions";

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
        this.setState({user, name: event.target.value});
    }

    getUser() {
        executeRequest({
            endpoint: `users/get/${this.state.name}`,
            postprocess: (e) => this.setState({user: e})
        })
    }

    updateUser(user) {
        executeRequest({
            endpoint: "users/add",
            method: "PUT",
            body: user,
            postprocess: (e) => this.setState({user: e})
        })
    }

    render() {
        const {user, name} = this.state;
        return <div>
            <Well>Hi! It's user edit form!</Well>
            <div style={{marginLeft: "10px"}}>
                < TextInput value={name} onChange={this.handleChange} label="Имя пользователя"/>
                <Button onClick={this.getUser}>Get user</Button>
                {!_.isEmpty(user) && <User user={user} onSubmit={this.updateUser}/>}
            </div>
        </div>
    }

}