import React from 'react';
import {Well} from "react-bootstrap";
import User from "../../components/user/User";
import {executeRequest} from "../mainActions";
import {connect} from "react-redux";
import UserValidator from "../../components/basic/security/UserValidator";

class CreateUser extends React.Component {
    addUser(user) {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: "users/add",
            method: "PUT",
            body: user,
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось добавить пользователя",
            dispatch
        })
    }

    constructor(props) {
        super(props);
        this.addUser = this.addUser.bind(this);
    }

    render() {
        return <UserValidator privilegesRequired={["WRITE"]} rolesRequired={["ADMIN"]}>
            <Well>Hi! It's user create form!</Well>
            <User onSubmit={this.addUser} mode="CREATE"/>
        </UserValidator>
    }

}

export default connect()(CreateUser)