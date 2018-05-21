import React from 'react';
import {Well} from "react-bootstrap";
import {executeRequest} from "../mainActions";
import {TextInput} from "../../components/basic/TextInput";
import Button from "@material-ui/core/es/Button/Button";

export class DeleteUser extends React.Component {

    deleteUser() {
        executeRequest({
            endpoint: `users/delete/${this.state.username}`,
            method: "DELETE",
        })
    }

    setUsername(e) {
        return this.setState({username: e.target.value});
    }


    constructor(props) {
        super(props);
        this.state = {
            username: ""
        };
        this.deleteUser = this.deleteUser.bind(this);
        this.setUsername = this.setUsername.bind(this);
    }

    render() {
        return <div>
            <Well>Hi! It's user delete form!</Well>
            <TextInput label="Имя пользователя" value={this.state.username} onChange={this.setUsername}/>
            <Button onClick={this.deleteUser}>Удалить</Button>
        </div>
    }

}
