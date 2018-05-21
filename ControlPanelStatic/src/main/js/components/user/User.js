import React from 'react';
import _ from 'lodash';
import {TextInput} from "../basic/inputs/TextInput";
import {MultiTagSelector} from "../basic/inputs/MultiTagSelector";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {CheckBox} from "../basic/inputs/CheckBox";
import Grid from "@material-ui/core/es/Grid/Grid";
import Button from "@material-ui/core/es/Button/Button";

const getUserOrEmpty = (user) => {
    return _.isEmpty(user)
        ? {
            username: "",
            password: "",
            roles: [],
            privileges: [],
            accountNonExpired: true,
            accountNonLocked: true,
            credentialsNonExpired: true,
            enabled: true
        }
        : user
};

class User extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: getUserOrEmpty(props.user)
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
        const {user} = this.state;
        user[change] = !user[change];
        this.setState({user});
    }


    render() {
        const {onSubmit, mode} = this.props;
        const {user} = this.state;
        const {username, password, roles, privileges, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled} = user;
        return <div style={{marginLeft: "10px"}}>
            <Grid container spacing={16}>
                <Grid item xs={2}>
                    <TextInput label={'Имя'}
                               value={username}
                               onChange={mode === "CREATE"
                                   ? this.handleInputChange.bind(this, 'username')
                                   : undefined}


                    />
                </Grid>
                {mode === "CREATE" && <Grid item xs={2}>
                    <TextInput label={'Пароль'}
                               type={"password"}
                               value={password}
                               onChange={this.handleInputChange.bind(this, 'password')}/>
                </Grid>}
            </Grid>
            <Grid container>
                <Grid item xs={2}>
                    <MultiTagSelector label={"Роли"}
                                      options={this.props.allRoles}
                                      value={roles}
                                      onChange={this.handleSelectorChange.bind(this, 'roles')}/>
                </Grid>
                <Grid item xs={2}>
                    <MultiTagSelector label={"Права"}
                                      options={this.props.allPrivileges}
                                      value={privileges}
                                      onChange={this.handleSelectorChange.bind(this, 'privileges')}/>
                </Grid>
            </Grid>
            <Grid container>
                <Grid item xs={2}>
                    <CheckBox checked={accountNonExpired}
                              label={"Учетная запись действительна"}
                              onChange={this.handleCheckboxChange.bind(this, 'accountNonExpired')}
                    />
                </Grid>
                <Grid item xs={2}>
                    <CheckBox checked={accountNonLocked}
                              label={"Учетная запись не заблокирована"}
                              onChange={this.handleCheckboxChange.bind(this, 'accountNonLocked')}
                    />
                </Grid>
                <Grid item xs={2}>
                    <CheckBox checked={credentialsNonExpired}
                              label={"Данные пользователя действительны"}
                              onChange={this.handleCheckboxChange.bind(this, 'credentialsNonExpired')}
                    />
                </Grid>
                <Grid item xs={2}>
                    <CheckBox checked={enabled}
                              label={"Включён"}
                              onChange={this.handleCheckboxChange.bind(this, 'enabled')}
                    />
                </Grid>
                <Grid container>
                    <Grid item xs={2}>
                        <Button onClick={() => onSubmit(user)}>{mode === "CREATE"
                            ? "Создать"
                            : "Обновить"
                        }</Button>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    }
}

User.propTypes = {
    user: PropTypes.object,
    onSubmit: PropTypes.func,
    allPrivileges: PropTypes.array,
    allRoles: PropTypes.array,
    mode: PropTypes.string.isRequired,
};

User.defaultProps = {
    user: {},
    allPrivileges: [],
    allRoles: [],
    onSubmit: (e) => {
    },
    mode: "EDIT",
};

const mapStateToProps = (store) => {
    return {
        allRoles: store.permissionsCache.roles,
        allPrivileges: store.permissionsCache.privileges,
    }
};

export default connect(mapStateToProps)(User);