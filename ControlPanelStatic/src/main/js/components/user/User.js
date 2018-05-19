import React from 'react';
import _ from 'lodash';
import {Button} from "react-bootstrap";
import {TextInput} from "../basic/TextInput";
import {MultiTagSelector} from "../basic/MultiTagSelector";
import Form from "react-bootstrap/es/Form";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {CheckBox} from "../basic/CheckBox";

class User extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: _.isEmpty(props.user)
                ? {
                    username: "",
                    password: "",
                    roles: [],
                    privileges: [],
                    accountNonExpired: false,
                    accountNonLocked: false,
                    credentialsNonExpired: false,
                    enabled: false
                }
                : props.user
        };
        this.handleSelectorChange = this.handleSelectorChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
    }

    handleSelectorChange(change, event) {
        const {user} = this.state;
        user[change] = event.target.value;
        this.setState({user});
    }

    handleInputChange(change, event) {
        const {user} = this.state;
        user[change] = event.target.value;
        this.setState({user});
    }

    handleCheckboxChange(change, event) {
        console.log("AAAAAAAAAA")
        const {user} = this.state;
        user[change] = !user[change];
        this.setState({user});
    }


    render() {
        const {onSubmit} = this.props;
        const {username, password, roles, privileges, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled} = this.state.user;
        return <Form horizontal>
            <TextInput label={'Имя пользователя'}
                       value={username}
                       onChange={this.handleInputChange.bind(this, 'username')}/>
            <TextInput label={'Пароль'}
                       type={"password"}
                       value={password}
                       onChange={this.handleInputChange.bind(this, 'password')}/>
            <MultiTagSelector label={"Роли"}
                              options={this.props.allRoles}
                              value={roles}
                              onChange={this.handleSelectorChange.bind(this, 'roles')}/>
            <MultiTagSelector label={"Права"}
                              options={this.props.allPrivileges}
                              value={privileges}
                              onChange={this.handleSelectorChange.bind(this, 'privileges')}/>
            <CheckBox checked={accountNonExpired}
                      label={"Учетная запись действительна"}
                      onChange={this.handleCheckboxChange.bind(this, 'accountNonExpired')}
            />
            <CheckBox checked={accountNonLocked}
                      label={"Учетная запись не заблокирована"}
                      onChange={this.handleCheckboxChange.bind(this, 'accountNonLocked')}
            />
            <CheckBox checked={credentialsNonExpired}
                      label={"Данные пользователя действительны"}
                      onChange={this.handleCheckboxChange.bind(this, 'credentialsNonExpired')}
            />
            <CheckBox checked={enabled}
                      label={"Включён"}
                      onChange={this.handleCheckboxChange.bind(this, 'enabled')}
            />

            <Button onClick={onSubmit}>Create user</Button>
        </Form>;
    }
}

User.propTypes = {
    user: PropTypes.object,
    onSubmit: PropTypes.func,
    allPrivileges: PropTypes.array,
    allRoles: PropTypes.array,
};

User.defaultProps = {
    user: {},
    allPrivileges: [],
    allRoles: [],
    onSubmit: (e) => {
    }
};

const mapStateToProps = (store) => {
    return {
        allRoles: store.permissionsCache.roles,
        allPrivileges: store.permissionsCache.privileges,
    }
};

export default connect(mapStateToProps)(User);