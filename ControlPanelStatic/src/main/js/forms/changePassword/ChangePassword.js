import React from 'react';
import {TextInput} from "../../components/basic/inputs/TextInput";
import {connect} from "react-redux";
import {endpoints, executeRequest} from "../../forms/mainActions";
import Well from "react-bootstrap/es/Well";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import Button from "@material-ui/core/es/Button/Button";
import {actions} from "react-redux-form";

class ChangePassword extends React.Component {

    changePassword() {
        const {oldPassword, newPassword, newPassRepeated} = this.state;
        const {dispatch} = this.props;
        if (newPassword !== newPassRepeated) {
            dispatch(actions.merge("callStatus", {error: true, errorMessage: "Пароли не совпадают!"}));
            return;
        }
        executeRequest({
            method: "POST",
            endpoint: endpoints.CHANGE_PASSWORD,
            body: {oldPassword, newPassword},
            errorMessage: "Не удалось сменить пароль",
            dispatch
        })
    }

    constructor(props) {
        super(props);
        this.state = {
            oldPassword: "",
            newPassword: "",
            newPassRepeated: "",

        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.disabled = this.disabled.bind(this);
    }

    handleInputChange(change, event) {
        const {state} = this;
        state[change] = event.target.value;
        this.setState({state});
    }

    disabled() {
        const {oldPassword, newPassword, newPassRepeated} = this.state;
        return _.isEmpty(oldPassword)
            || _.isEmpty(newPassword)
            || newPassword !== newPassRepeated;
    }

    render() {
        const {oldPassword, newPassword, newPassRepeated} = this.state;
        return <div>
            <Well>Hi! It's change password form!</Well>
            <UniformGrid>
                <TextInput value={oldPassword}
                           onChange={this.handleInputChange.bind(this, "oldPassword")}
                           label="Старый пароль"
                           type="password"/>
            </UniformGrid>
            <UniformGrid>
                <TextInput value={newPassword}
                           onChange={this.handleInputChange.bind(this, "newPassword")}
                           label="Новый пароль"
                           type="password"/>
                <TextInput value={newPassRepeated}
                           onChange={this.handleInputChange.bind(this, "newPassRepeated")}
                           label="Повторите пароль"
                           type="password"/>
            </UniformGrid>
            <UniformGrid>
                <Button disabled={this.disabled()} onClick={this.changePassword}>Сменить пароль</Button>
            </UniformGrid>
        </div>
    }
}

export default connect()(ChangePassword);