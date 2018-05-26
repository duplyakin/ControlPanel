import React from 'react';
import {TextInput} from "../../components/basic/inputs/TextInput";
import {connect} from "react-redux";
import {executeRequest} from "../../forms/mainActions";
import Well from "react-bootstrap/es/Well";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import Button from "@material-ui/core/es/Button/Button";
import {actions} from "react-redux-form";

class ChangePassword extends React.Component {

    changePassword() {
        const {oldPass, newPass, newPassRepeated} = this.state;
        const {dispatch} = this.props;
        if (newPass !== newPassRepeated) {
            dispatch(actions.merge("callStatus", {error: true, errorMessage: "Пароли не совпадают!"}))
            return;
        }
        executeRequest({
            method: "POST",
            endpoint: `changePassword`,
            body: {oldPass, newPass},
            errorMessage: "Не удалось сменить пароль",
            dispatch
        })
    }

    constructor(props) {
        super(props);
        this.state = {
            oldPass: "",
            newPass: "",
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
        const {oldPass, newPass, newPassRepeated} = this.state;
        return _.isEmpty(oldPass)
            || _.isEmpty(newPass)
            || newPass !== newPassRepeated;
    }

    render() {
        const {oldPass, newPass, newPassRepeated} = this.state;
        return <div>
            <Well>Hi! It's change password form!</Well>
            <UniformGrid>
                <TextInput value={oldPass}
                           onChange={this.handleInputChange.bind(this, "oldPass")}
                           label="Старый пароль"/>
            </UniformGrid>
            <UniformGrid>
                <TextInput value={newPass}
                           onChange={this.handleInputChange.bind(this, "newPass")}
                           label="Новый пароль"/>
                <TextInput value={newPassRepeated}
                           onChange={this.handleInputChange.bind(this, "newPassRepeated")}
                           label="Повторите пароль"/>
            </UniformGrid>
            <UniformGrid>
                <Button disabled={this.disabled()} onClick={this.changePassword}>Сменить пароль</Button>
            </UniformGrid>
        </div>
    }
}

export default connect()(ChangePassword);