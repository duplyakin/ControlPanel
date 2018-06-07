import React from 'react';
import PermissionsCache from "./permissions/PermissionCache";
import CurrentUserCache from "./currentUser/CurrentUserCache";

/**
 * Компонент, отвечающий за получение всей необходимой информации с сервера.
 */
export const Cache = () => <div>
    <PermissionsCache/>
    <CurrentUserCache/>
</div>;