import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";

class PermissionCache extends React.Component {

    componentDidMount() {
        const {dispatch} = this.props;
        const a = fetch('http://localhost:8090/roles/getAll', {
            method: "GET",
            credentials: "include",
            redirect: "follow",
            mode: "cors"
        }).then(response => response.json())
            .then(roles => {
                dispatch(actions.merge("permissionsCache.roles", roles))
            });

        const b = fetch('http://localhost:8090/privileges/getAll', {
            method: "GET",
            credentials: "include",
            redirect: "follow",
            mode: "cors"
        }).then(response => response.json())
            .then(roles => {
                dispatch(actions.merge("permissionsCache.privileges", roles))
            });

        Promise.all([a, b]).then(() => console.log("Roles and privileges are successfully fetched!"))

    }

    render() {
        return <div/>
    }
}

export default connect()(PermissionCache)