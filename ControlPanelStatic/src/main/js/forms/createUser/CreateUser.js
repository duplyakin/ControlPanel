import React from 'react';
import {Well} from "react-bootstrap";
import User from "../../components/user/User";
import {executeRequest} from "../mainActions";
import {DialogWithConfirmation} from "../../components/basic/DialogWithConfirmation";

export class CreateUser extends React.Component {
    addUser(user) {
        executeRequest({
            endpoint: "users/add",
            method: "PUT",
            body: user,
            postprocess: (e) => this.setState({user: e}),
            handleError: e => this.setState({errorHappened: true, errorMessage: "Не удалось добавить пользователя"})
        })
    }



    constructor(props) {
        super(props);
        this.state = {
            errorHappened: false,
            errorMessage: "",
        };
        this.addUser = this.addUser.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleClose() {
        this.setState({errorHappened: false});
    }

    render() {
        const {errorMessage, errorHappened} = this.state;
        return <div>
            <Well>Hi! It's user create form!</Well>
            <DialogWithConfirmation errorMessage={errorMessage}
                                    handleClose={this.handleClose}
                                    handleRetry={this.addUser}
                                    isOpen={errorHappened}
                                    title="Ошибка на форме создания"/>
            <User onSubmit={this.addUser} mode="CREATE"/>
        </div>
    }

}
