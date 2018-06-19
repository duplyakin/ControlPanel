import React from 'react';
import {mainReducer} from './mainReducer';
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import {Provider} from 'react-redux';
import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';
import {Cache} from "../components/cache/Cache"
import CreateUser from "./createUser/CreateUser";
import EditUser from "./editUser/EditUser";
import Rights from "./modifyPermissions/Rights";
import UserValidator from "../components/basic/security/UserValidator"
import {ResponseEventsPopUp} from "../components/basic/popups/ResponseEventsPopUp";
import ChangePassword from "./changePassword/ChangePassword";
import HomePage from "./homePage/HomePage";
import {privileges, roles} from "../components/basic/security/authorities";
import AddInventory from "./inventory/add/AddInventory";
import ViewInventory from "./inventory/view/ViewInventory";
import AddUnit from "./inventory/addUnit/AddUnit";

const store = createStore(mainReducer, applyMiddleware(thunk));
/**
 * Основная форма
 * Осуществляет роутинг между формами.
 * Предоставляет redux store.
 */
export const MainForm = () =>
    <Provider store={store}>
        <div>
            <Cache/>
            <ResponseEventsPopUp/>
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
                        <UserValidator>
                            <li>
                                <Link to="/addInventory">Добавить тип оборудования</Link>
                            </li>
                        </UserValidator>
                        <UserValidator>
                            <li>
                                <Link to="/viewInventory">Посмотреть оборудование</Link>
                            </li>
                        </UserValidator>
                        <UserValidator>
                            <li>
                                <Link to="/addUnit">Добавить единицу оборудования</Link>
                            </li>
                        </UserValidator>
                    </ul>
                    <Switch>
                        <Route exact path="/" component={HomePage}/>
                        <Route path="/changePassword" component={ChangePassword}/>
                        <Route path="/create" component={CreateUser}/>
                        <Route path="/edit/:username?" component={EditUser}/>
                        <Route path="/modify" component={Rights}/>
                        <Route path="/addInventory" component={AddInventory}/>
                        <Route path="/viewInventory" component={ViewInventory}/>
                        <Route path="/addUnit" component={AddUnit}/>
                    </Switch>
                </div>
            </Router>
        </div>
    </Provider>;