import React from 'react';
import Button from "react-bootstrap/es/Button";
import Well from "react-bootstrap/es/Well";
import _ from "lodash";

export class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
        };
        this.getUser = this.getUser.bind(this);
    }

    componentDidMount() {
        this.getUser()
    }

    getUser() {
        fetch('http://localhost:8090/users/get/user', {
            method: "GET",
            credentials:"include",
            redirect: "follow",
            mode: "cors"
        }).then(response => response.json())
            .then(responseJson => {
                this.setState({user: responseJson})});
    }

    render() {
        return <div>
            <Well>Hi! It's user {_.get(this.props, "match.params.username", "")} edit form!</Well>
            <Well>{this.state.user.toString()}</Well>
            <Button>Edit user</Button>
        </div>
    }

}