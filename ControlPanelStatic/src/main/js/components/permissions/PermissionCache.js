import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {executeRequest, endpoints} from "../../forms/mainActions";

class PermissionCache extends React.Component {

    constructor(props) {
        super(props);
        this.loadRoles = this.loadRoles.bind(this);
        this.loadPrivileges = this.loadPrivileges.bind(this);
    }

    loadRoles() {
        const {dispatch} = this.props;
        return executeRequest({
            popupIfSuccess: false,
            endpoint: endpoints.GET_ALL_ROLES,
            postprocess: roles => dispatch(actions.merge("permissionsCache.roles", roles)),
            errorMessage: "Не удалось загрузить роли",
            dispatch,
        });
    }

    loadPrivileges() {
        const {dispatch} = this.props;
        return executeRequest({
            popupIfSuccess: false,
            endpoint: endpoints.GET_ALL_PRIVILEGES,
            postprocess: privileges => dispatch(actions.merge("permissionsCache.privileges", privileges)),
            errorMessage: "Не удалось загрузить права",
            dispatch,
        });
    }

    componentDidMount() {
        Promise.all([this.loadRoles(), this.loadPrivileges()])
            .then(() => console.log("Roles and privileges are successfully fetched!"))
    }

    render() {
        return <div/>
    }
}

export default connect()(PermissionCache)