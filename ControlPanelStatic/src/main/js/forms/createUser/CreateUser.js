import React from 'react';
import {Well} from "react-bootstrap";
import User from "../../components/user/User";
import {executeRequest} from "../mainActions";

export class CreateUser extends React.Component {
    addUser(user) {
        executeRequest({
            endpoint: "users/add",
            method: "PUT",
            body: user,
            postprocess: (e) => this.setState({user: e})
        })
    }

    constructor(props) {
        super(props);
        this.addUser = this.addUser.bind(this);
    }

    render() {
        return <div>
            <Well>Hi! It's user create form!</Well>
            <User onSubmit={this.addUser}/>
        </div>
    }

}
