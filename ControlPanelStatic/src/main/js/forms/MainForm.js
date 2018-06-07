import React from 'react';
import {mainReducer} from './mainReducer';
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import {Provider} from 'react-redux';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';

import PermissionsCache from "../components/permissions/PermissionCache"
import CurrentUserCache from "../components/currentUser/CurrentUserCache"
import CreateUser from "./createUser/CreateUser";
import EditUser from "./editUser/EditUser";
import Rights from "./modifyPermissions/Rights";
import UserValidator from "../components/basic/security/UserValidator"
import DialogOnSuccess from "../components/basic/popups/DialogOnSuccess";
import DialogOnError from "../components/basic/popups/DialogOnError";
import ChangePassword from "./changePassword/ChangePassword";
import HomePage from "./homePage/HomePage";
import {privileges, roles} from "../components/basic/security/authorities";

const store = createStore(mainReducer, applyMiddleware(thunk));
/**
 * Основная форма
 * Осуществляет роутинг между компонентами.
 * Предоставляет redux store.
 */
export const MainForm = (props) =>
    <Provider store={store}>
        <div>
            <PermissionsCache/>
            <CurrentUserCache/>
            <DialogOnError/>
            <DialogOnSuccess/>
            <Router>
                <div>
                    <ul>
                        <UserValidator>
                            <li>
                                <Link to="/">Домашняя страница</Link>
                            </li>
                        </UserValidator>
                        <UserValidator>
                            <li>
                                <Link to="/changePassword">Сменить пароль</Link>
                            </li>
                        </UserValidator>
                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                            <li>
                                <Link to="/create">Форма создания</Link>
                            </li>
                        </UserValidator>
                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                            <li>
                                <Link to="/edit">Форма редактирования</Link>
                            </li>
                        </UserValidator>
                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                            <li>
                                <Link to="/modify">Форма изменения прав</Link>
                            </li>
                        </UserValidator>
                    </ul>
                    <Switch>
                        <Route exact path="/" component={HomePage}/>
                        <Route path="/changePassword" component={ChangePassword}/>
                        <Route path="/create" component={CreateUser}/>
                        <Route path="/edit/:username?" component={EditUser}/>
                        <Route path="/modify" component={Rights}/>
                    </Switch>
                </div>
            </Router>
        </div>
    </Provider>;