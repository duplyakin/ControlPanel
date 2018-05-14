import React from 'react';
import Button from "react-bootstrap/es/Button";
import Well from "react-bootstrap/es/Well";
import _ from "lodash";

export class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: 'empty',
        }
    }

    componentDidMount() {
        fetch('http://localhost:8090/users/get/user', {
            method: "GET",
            headers: {
                'Access-Control-Allow-Origin': '*',
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            credentials: 'include',
            mode: "no-cors",
            redirect: "follow",
        }).then(response => {
            console.log(response);
            this.setState({user: response})});
    }

    render() {
        return <div>
            <Well>Hi! It's user {_.get(this.props, "match.params.username", "")} edit form!</Well>
            <Well>{this.state.user.toString()}</Well>
            <Button>Edit user</Button>
        </div>
    }

}