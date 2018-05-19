import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {executeRequest} from "../../forms/mainActions";

class PermissionCache extends React.Component {

    componentDidMount() {
        const {dispatch} = this.props;
        const a = executeRequest({
            endpoint: "roles/getAll",
            postprocess: roles => {
                dispatch(actions.merge("permissionsCache.roles", roles))
            }
        });

        const b = executeRequest({
            endpoint: "privileges/getAll", postprocess: privileges => {
                dispatch(actions.merge("permissionsCache.privileges", privileges))
            }
        });
        Promise.all([a, b]).then(() => console.log("Roles and privileges are successfully fetched!"))

    }

    render() {
        return <div/>
    }
}

export default connect()(PermissionCache)