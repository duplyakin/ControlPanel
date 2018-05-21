import React from 'react';
import {mainReducer} from './mainReducer';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';

import PermissionsCache from "../components/permissions/PermissionCache"
import CreateUser from "../forms/createUser/CreateUser";
import EditUser from "../forms/editUser/EditUser";
import Rights from "../forms/modifyPermissions/Rights";
import DialogOnSuccess from "../components/basic/popups/DialogOnSuccess";
import DialogOnError from "../components/basic/popups/DialogOnError";

const store = createStore(mainReducer, applyMiddleware(thunk));

export const MainForm = (props) =>
    <Provider store={store}>
        <div>
            <PermissionsCache/>
            <DialogOnError/>
            <DialogOnSuccess/>
            <Router>
                <div>
                    <ul>
                        <li>
                            <Link to="/">Домашняя страница</Link>
                        </li>
                        <li>
                            <Link to="/create">Форма создания</Link>
                        </li>
                        <li>
                            <Link to="/edit">Форма редактирования</Link>
                        </li>
                        <li>
                            <Link to="/modify">Форма изменения прав</Link>
                        </li>
                    </ul>
                    <Route path="/create" component={CreateUser}/>
                    <Route path="/edit/:username?" component={EditUser}/>
                    <Route path="/modify" component={Rights}/>
                </div>
            </Router>
        </div>
    </Provider>;