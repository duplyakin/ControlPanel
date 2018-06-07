import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import {UserStatusCheckboxGroup} from "./userBlocks/UserStatusCheckboxGroup";
import RolesAndPrivileges from "./userBlocks/RolesAndPrivileges";
import {UsernameAndPassword} from "./userBlocks/UsernameAndPassword";
import {connect} from "react-redux";
import {SubmitButton} from "./userBlocks/SubmitButton";

const getUserOrDefault = (user) => {
    return !_.isEmpty(user)
        ? user
        : defaultUser()
};

const defaultUser = () => {
    return {
        username: "",
        password: "",
        roles: [],
        privileges: [],
        accountNonExpired: true,
        accountNonLocked: true,
        credentialsNonExpired: true,
        enabled: true,
    }
};

class User extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: getUserOrDefault(props.user),
        };
        this.handleGroupChange = this.handleGroupChange.bind(this);
        this.buttonIsDisabled = this.buttonIsDisabled.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (!_.isEqual(this.state.user, nextProps.user)) {
            this.setState({user: nextProps.user})
        }
    }

    buttonIsDisabled() {
        const {username, password} = this.state.user;
        return _.isEmpty(username) || _.isEmpty(password);
    }

    handleGroupChange(changes) {
        const {user} = this.state;
        _.keys(changes).forEach(e => {
            user[e] = changes[e]
        });
        this.setState({user});
    }

    handleSubmit(param) {
        const {onSubmit, mode} = this.props;
        if (mode === "CREATE") {
            this.setState({user: defaultUser()})
        }
        return onSubmit(param);
    }


    render() {
        const {mode} = this.props;
        const {user} = this.state;
        return <div>
            <UsernameAndPassword onChange={this.handleGroupChange}
                                 mode={mode}
                                 username={user.username}
                                 password={user.password}/>
            <RolesAndPrivileges onChange={this.handleGroupChange}
                                privileges={user.privileges}
                                roles={user.roles}/>
            <UserStatusCheckboxGroup onChange={this.handleGroupChange}
                                     accountNonExpired={user.accountNonExpired}
                                     accountNonLocked={user.accountNonLocked}
                                     credentialsNonExpired={user.credentialsNonExpired}
                                     enabled={user.enabled}
            />
            <SubmitButton user={user} mode={mode} onSubmit={this.handleSubmit}/>
        </div>
    }
}

User.propTypes = {
    user: PropTypes.object,
    onSubmit: PropTypes.func,
    mode: PropTypes.string.isRequired,
};

User.defaultProps = {
    user: {},
    onSubmit: (e) => e,
    mode: "EDIT",
};


export default connect()(User);