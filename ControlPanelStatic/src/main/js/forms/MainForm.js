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
import AddInventory from "./inventory/type/add/AddInventory";
import ViewInventory from "./inventory/type/view/ViewInventory";
import AddUnit from "./inventory/unit/add/AddUnit";
import ViewUnit from "./inventory/unit/view/ViewUnit";
import {MenuItem, Nav, Navbar, NavDropdown, NavItem} from "react-bootstrap";
import AddEventType from "../forms/events/addType/AddEventType";
import UpdateUnit from "../forms/inventory/unit/update/UpdateUnit";
import ViewAllUnits from "../forms/inventory/unit/viewAll/ViewAllUnits";

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
                    <Navbar inverse collapseOnSelect>
                        <Navbar.Header>
                            <Navbar.Brand><Link to="/">Домашняя страница</Link></Navbar.Brand>
                        </Navbar.Header>

                        <Nav>
                            <NavDropdown eventKey={1} title="Текущий пользователь" id="current-user-nav-dropdown">
                                <MenuItem eventKey={1.1}>
                                    <UserValidator>
                                        <Link to="/changePassword">Сменить пароль</Link>
                                    </UserValidator>
                                </MenuItem>
                            </NavDropdown>
                            <NavDropdown eventKey={2} title="Операции с пользователями" id="user-nav-dropdown">
                                <MenuItem eventKey={2.1}>
                                    <UserValidator privilegesRequired={[privileges.WRITE]}
                                                   rolesRequired={[roles.ADMIN]}>
                                        <Link to="/create">Форма создания</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={2.2}>
                                    <UserValidator privilegesRequired={[privileges.WRITE]}
                                                   rolesRequired={[roles.ADMIN]}>
                                        <Link to="/edit">Форма редактирования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={2.3}>
                                    <UserValidator privilegesRequired={[privileges.WRITE]}
                                                   rolesRequired={[roles.ADMIN]}>
                                        <Link to="/modify">Форма изменения прав</Link>
                                    </UserValidator>
                                </MenuItem>
                            </NavDropdown>
                            <NavDropdown eventKey={3} title="Оборудование" id="inventory-nav-dropdown">
                                <MenuItem eventKey={3.1}>
                                    <UserValidator>
                                        <Link to="/addInventory">Добавить тип оборудования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={3.2}>
                                    <UserValidator>
                                        <Link to="/viewInventory">Посмотреть тип оборудования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={3.3}>
                                    <UserValidator>
                                        <Link to="/addUnit">Добавить единицу оборудования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={3.4}>
                                    <UserValidator>
                                        <Link to="/viewUnit">Посмотреть единицу оборудования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={3.5}>
                                    <UserValidator>
                                        <Link to="/updateUnit">Изменить единицу оборудования</Link>
                                    </UserValidator>
                                </MenuItem>
                                <MenuItem eventKey={3.5}>
                                    <UserValidator>
                                        <Link to="/viewAllUnits">Посмотреть всё оборудование</Link>
                                    </UserValidator>
                                </MenuItem>
                            </NavDropdown>

                            <NavDropdown eventKey={4} title="События" id="events-nav-dropdown">
                                <MenuItem eventKey={4.1}>
                                    <UserValidator>
                                        <Link to="/addEventType">Добавить тип события</Link>
                                    </UserValidator>
                                </MenuItem>
                            </NavDropdown>
                            <NavItem eventKey={5} href={`${SERVER_PATH}/logout`}>
                                Выйти из системы
                            </NavItem>
                        </Nav>

                    </Navbar>

                    <Switch>
                        <Route exact path="/" component={HomePage}/>
                        <Route path="/changePassword" component={ChangePassword}/>
                        <Route path="/create" component={CreateUser}/>
                        <Route path="/edit/:username?" component={EditUser}/>
                        <Route path="/modify" component={Rights}/>
                        <Route path="/addInventory" component={AddInventory}/>
                        <Route path="/viewInventory" component={ViewInventory}/>
                        <Route path="/addUnit" component={AddUnit}/>
                        <Route path="/updateUnit" component={UpdateUnit}/>
                        <Route path="/viewUnit" component={ViewUnit}/>
                        <Route path="/viewUnit/:id" component={ViewUnit}/>
                        <Route path="/viewAllUnits" component={ViewAllUnits}/>
                        <Route path="/addEventType" component={AddEventType}/>
                    </Switch>
                </div>
            </Router>
        </div>
    </Provider>;