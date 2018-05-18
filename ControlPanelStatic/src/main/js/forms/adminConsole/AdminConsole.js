import React from 'react';
import Well from "react-bootstrap/es/Well";
import PermissionsCache from '../../components/permissions/PermissionCache'
export const AdminConsole = (props) =>
    <div>
        <PermissionsCache/>
        <Well>Hi! It's control panel!</Well>;
    </div>;