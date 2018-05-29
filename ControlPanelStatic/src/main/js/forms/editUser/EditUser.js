import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import {TextInput} from "../../components/basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest, endpoints} from "../mainActions";
import {connect} from "react-redux";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import UserValidator from "../../components/basic/security/UserValidator";
import _ from 'lodash';
import {privileges, roles} from "../../components/basic/security/authorities";

export class EditUser extends React.Component {

    deleteUser() {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: `${endpoints.DELETE}/${this.state.user.username}`,
            method: "DELETE",
            errorMessage: "Не удалось удалить пользователя",
            dispatch
        });
        this.setState({user: {}})
    }

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            name: "",
        };
        this.getUser = this.getUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
    }

    handleChange(event) {
        const {user} = this.state;
        this.setState({user, name: event.target.value});
    }

    getUser() {
        const {name} = this.state;
        this.setState({user: {}});
        const {dispatch} = this.props;
        executeRequest({
            popupIfSuccess: false,
            endpoint: `${endpoints.GET}/${name}`,
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось найти пользователя",
            dispatch
        })
    }

    updateUser(user) {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: endpoints.UPDATE,
            method: "POST",
            body: user,
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось обновить пользователя",
            dispatch
        })
    };

    render() {
        const {user, name} = this.state;
        return <UserValidator  privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
            <Well>Hi! It's user edit form!</Well>
            <UniformGrid>
                <TextInput value={name} onChange={this.handleChange} label="Имя пользователя"/>
                <Button disabled={_.isEmpty(name)} onClick={this.getUser}>Найти</Button>
            </UniformGrid>
            {!_.isEmpty(user) && <User user={user} onSubmit={this.updateUser}/>}
            {!_.isEmpty(user) &&
            <UniformGrid>
                <Button onClick={this.deleteUser}>Удалить</Button>
            </UniformGrid>
            }
        </UserValidator>
    }
}

export default connect()(EditUser)