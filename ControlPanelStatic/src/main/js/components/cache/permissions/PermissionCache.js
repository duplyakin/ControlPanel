import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {endpoints, executeRequest} from "../../../forms/mainActions";

/**
 * Компонент для загрузки всех доступных ролей и прав.
 * Роли и права, загруженные с динамики, используются в качестве опций значений в селекторах.
 */
class PermissionCache extends React.Component {

    loadRoles = () => {
        const {dispatch} = this.props;
        return executeRequest({
            popupIfSuccess: false,
            endpoint: endpoints.GET_ALL_ROLES,
            postprocess: roles => dispatch(actions.merge("permissionsCache.roles", roles)),
            errorMessage: "Не удалось загрузить роли",
            dispatch,
        });
    };

    loadPrivileges = () => {
        const {dispatch} = this.props;
        return executeRequest({
            popupIfSuccess: false,
            endpoint: endpoints.GET_ALL_PRIVILEGES,
            postprocess: privileges => dispatch(actions.merge("permissionsCache.privileges", privileges)),
            errorMessage: "Не удалось загрузить права",
            dispatch,
        });
    };

    componentDidMount() {
        Promise.all([this.loadRoles(), this.loadPrivileges()])
            .then(() => console.log("Roles and privileges are successfully fetched!"))
    }

    render() {
        return null;
    }
}

export default connect()(PermissionCache)