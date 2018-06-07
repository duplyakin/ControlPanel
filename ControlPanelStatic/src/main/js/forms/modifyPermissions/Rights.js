import React from 'react';
import {TextInput} from "../../components/basic/inputs/TextInput";
import {MultiTagSelector} from "../../components/basic/inputs/MultiTagSelector";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import Button from "@material-ui/core/es/Button/Button";
import {endpoints, executeRequest} from "../../forms/mainActions";
import _ from 'lodash';
import Well from "react-bootstrap/es/Well";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import UserValidator from "../../components/basic/security/UserValidator";
import {privileges as p, roles as r} from "../../components/basic/security/authorities";

/**
 * Компонент для изменения ролей и прав текущего пользователя.
 */
class Rights extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            roles: [],
            privileges: [],
            user: {},

        };
    }

    updateRoles = () => {
        const {dispatch} = this.props;
        const {user, roles} = this.state;
        executeRequest({
            endpoint: endpoints.SET_ROLES,
            method: "POST",
            body: {name: user.username, authorities: roles},
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось изменить роли пользователя",
            dispatch
        })
    };

    updateAuthorities = () => {
        const {user, privileges} = this.state;
        const {dispatch} = this.props;
        executeRequest({
            endpoint: endpoints.SET_PRIVILEGES,
            method: "POST",
            body: {name: user.username, authorities: privileges},
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось изменить права пользователя",
            dispatch
        })
    };

    getUser = () => {
        const {username} = this.state;
        const {dispatch} = this.props;
        this.setState({
            user: {},
            roles: [],
            privileges: [],
        });
        executeRequest({
            popupIfSuccess: false,
            endpoint: `${endpoints.GET}/${username}`,
            postprocess: (e) => this.setState({user: e, roles: e.roles, privileges: e.privileges}),
            errorMessage: "Не удалось загрузить пользователя",
            dispatch
        })
    };

    handleSelectorChange = (change, event) => {
        const {state} = this;
        state[change] = event.target.value;
        this.setState({state});
    };

    handleClose = () => {
        this.setState({errorHappened: false});
    };

    handleInputChange = (event) => {
        this.setState({username: event.target.value});
    };

    render() {
        const {user, username, roles, privileges} = this.state;
        return <UserValidator privilegesRequired={[p.WRITE]} rolesRequired={[r.ADMIN]}>
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