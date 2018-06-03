import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import Button from "@material-ui/core/es/Button/Button";
import {endpoints, executeRequest} from "../mainActions";
import {connect} from "react-redux";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import UserValidator from "../../components/basic/security/UserValidator";
import _ from 'lodash';
import {privileges, roles} from "../../components/basic/security/authorities";
import {InputWithButton} from "../../components/user/userBlocks/InputWithButton";

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
        };
        this.getUser = this.getUser.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
    }

    getUser(name) {
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
        const {user} = this.state;
        return <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
            <Well>Hi! It's user edit form!</Well>
            <InputWithButton onClick={this.getUser}/>
            {!_.isEmpty(user) && <div>
                <User user={user} onSubmit={this.updateUser}/>
                <UniformGrid>
                    <Button onClick={this.deleteUser}>Удалить</Button>
                </UniformGrid>
            </div>
            }
        </UserValidator>
    }
}

export default connect()(EditUser)