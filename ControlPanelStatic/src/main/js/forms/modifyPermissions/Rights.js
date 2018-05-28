import React from 'react';
import {TextInput} from "../../components/basic/inputs/TextInput";
import {MultiTagSelector} from "../../components/basic/inputs/MultiTagSelector";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest} from "../../forms/mainActions";
import _ from 'lodash';
import Well from "react-bootstrap/es/Well";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import UserValidator from "../../components/basic/security/UserValidator";

class Rights extends React.Component {

    updateRoles() {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: "roles/set",
            method: "POST",
            body: {name: this.state.user.username, authorities: this.state.roles},
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось изменить роли пользователя",
            dispatch
        })
    }

    updateAuthorities() {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: "privileges/set",
            method: "POST",
            body: {name: this.state.user.username, authorities: this.state.privileges},
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось изменить права пользователя",
            dispatch
        })
    }

    getUser() {
        const {dispatch} = this.props;
        this.setState({
            user: {},
            roles: [],
            privileges: [],
        })
        executeRequest({
            popupIfSuccess: false,
            endpoint: `users/get/${this.state.username}`,
            postprocess: (e) => this.setState({user: e, roles: e.roles, privileges: e.privileges}),
            errorMessage: "Не удалось загрузить пользователя",
            dispatch
        })
    }

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            roles: [],
            privileges: [],
            user: {},

        };
        this.handleSelectorChange = this.handleSelectorChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.updateRoles = this.updateRoles.bind(this);
        this.updateAuthorities = this.updateAuthorities.bind(this);
        this.getUser = this.getUser.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleSelectorChange(change, event) {
        const {state} = this;
        state[change] = event.target.value;
        this.setState({state});
    }

    handleClose() {
        this.setState({errorHappened: false});
    }

    handleInputChange(event) {
        this.setState({username: event.target.value});
    }

    render() {
        const {user, username, roles, privileges} = this.state;
        return <UserValidator privilegesRequired={["WRITE"]} rolesRequired={["ADMIN"]}>
            <Well>Hi! It's user modify rights form!</Well>
            <UniformGrid>
                <TextInput value={username} onChange={this.handleInputChange} label="Имя пользователя"/>
                <Button disabled={_.isEmpty(username)} onClick={this.getUser}>Найти</Button>
            </UniformGrid>
            {!_.isEmpty(user) &&
            <div>
                <UniformGrid>
                    <TextInput label="Пользователь" value={user.username}/>
                </UniformGrid>
                <UniformGrid>
                    <MultiTagSelector label={"Роли"}
                                      options={this.props.allRoles}
                                      value={roles}
                                      onChange={this.handleSelectorChange.bind(this, 'roles')}/>
                    <Button onClick={this.updateRoles}>Применить роли</Button>
                </UniformGrid>
                <UniformGrid>
                    <MultiTagSelector label={"Права"}
                                      options={this.props.allPrivileges}
                                      value={privileges}
                                      onChange={this.handleSelectorChange.bind(this, 'privileges')}/>
                    <Button onClick={this.updateAuthorities}>Применить права</Button>
                </UniformGrid>
            </div>
            }
        </UserValidator>
    }
}

Rights.propTypes = {
    allPrivileges: PropTypes.array,
    allRoles: PropTypes.array,
};

Rights.defaultProps = {
    allPrivileges: [],
    allRoles: [],
};

const mapStateToProps = (store) => {
    return {
        allRoles: store.permissionsCache.roles,
        allPrivileges: store.permissionsCache.privileges,
    }
};

export default connect(mapStateToProps)(Rights);