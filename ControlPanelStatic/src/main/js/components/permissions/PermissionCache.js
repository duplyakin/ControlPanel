import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {executeRequest} from "../../forms/mainActions";
import {DialogWithConfirmation} from "../basic/DialogWithConfirmation";

class PermissionCache extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            errorHappened: false,
            errorMessage: "",
        };
        this.handleClose = this.handleClose.bind(this);
        this.loadRoles = this.loadRoles.bind(this);
        this.loadPrivileges = this.loadPrivileges.bind(this);
    }

    loadRoles() {
        const {dispatch} = this.props;
        return executeRequest({
            endpoint: "roles/getAll",
            postprocess: roles => dispatch(actions.merge("permissionsCache.roles", roles)),
            handleError: e => this.setState({
                errorHappened: true,
                errorMessage: "Не удалось загрузить роли"
            })
        });
    }

    loadPrivileges() {
        const {dispatch} = this.props;
        const b = executeRequest({
            endpoint: "privileges/getAll",
            postprocess: privileges => dispatch(actions.merge("permissionsCache.privileges", privileges)),
            handleError: e => {
                this.setState({
                    errorHappened: true,
                    errorMessage: "Не удалось загрузить права"
                })
            }
        });
    }

    componentDidMount() {
        Promise.all([this.loadRoles(), this.loadPrivileges()])
            .then(() => console.log("Roles and privileges are successfully fetched!"))
    }

    handleClose() {
        this.setState({errorHappened: false})
    }

    render() {
        const {errorHappened, errorMessage} = this.state;

        return <div>
            {(errorHappened)
            && <DialogWithConfirmation errorMessage={errorMessage}
                                       handleClose={this.handleClose}
                                       isOpen={errorHappened}
                                       title="Ошибка загрузки ролей и прав"/>
            }
        </div>
    }
}

export default connect()(PermissionCache)