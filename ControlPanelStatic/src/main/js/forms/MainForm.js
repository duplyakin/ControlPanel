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
import ViewUnit from "./inventory/viewUnit/ViewUnit";
import "bootstrap/dist/css/bootstrap.css";
import { Navbar, NavItem, Nav, Grid, Row, Col } from "react-bootstrap";

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
                            <Navbar.Brand>
                                <a href="#home">Super App</a>
                            </Navbar.Brand>
                            <Navbar.Toggle />
                        </Navbar.Header>
                    </Navbar>

                    <Grid>
                        <Row>
                            <Col md={4} sm={4}>
                                <Nav>
                                    <NavItem>
                                        <UserValidator>
                                            <Link to="/">Домашняя страница</Link>
                                        </UserValidator>
                                    </NavItem>
                                    <NavItem>
                                        <UserValidator>
                                                <Link to="/changePassword">Сменить пароль</Link>
                                        </UserValidator>
                                    </NavItem>
                                    <NavItem>
                                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                                            <Link to="/create">Форма создания</Link>
                                        </UserValidator>
                                    </NavItem>
                                    <NavItem>
                                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                                            <Link to="/edit">Форма редактирования</Link>
                                        </UserValidator>
                                    </NavItem>
                                    <NavItem>
                                        <UserValidator privilegesRequired={[privileges.WRITE]} rolesRequired={[roles.ADMIN]}>
                                            <Link to="/modify">Форма изменения прав</Link>
                                        </UserValidator>
                                    </NavItem>

                                    <NavItem>
                                        <UserValidator>
                                            <Link to="/addInventory">Добавить тип оборудования</Link>
                                        </UserValidator>
                                    </NavItem>

                                    <NavItem>
                                        <UserValidator>
                                            <Link to="/viewInventory">Посмотреть оборудование</Link>
                                        </UserValidator>
                                    </NavItem>

                                    <NavItem>
                                        <UserValidator>
                                            <Link to="/addUnit">Добавить единицу оборудования</Link>
                                        </UserValidator>
                                    </NavItem>

                                    <NavItem>
                                        <UserValidator>
                                            <Link to="/viewUnit">Посмотреть единицу оборудования</Link>
                                        </UserValidator>
                                    </NavItem>

                                </Nav>
                            </Col>

                            <Col md={8} sm={8}>
                                <Switch>
                                    <Route exact path="/" component={HomePage}/>
                                    <Route path="/changePassword" component={ChangePassword}/>
                                    <Route path="/create" component={CreateUser}/>
                                    <Route path="/edit/:username?" component={EditUser}/>
                                    <Route path="/modify" component={Rights}/>
                                    <Route path="/addInventory" component={AddInventory}/>
                                    <Route path="/viewInventory" component={ViewInventory}/>
                                    <Route path="/addUnit" component={AddUnit}/>
                                    <Route path="/addUnit" component={AddUnit}/>
                                    <Route path="/viewUnit" component={ViewUnit}/>
                                </Switch>
                            </Col>
                        </Row>
                    </Grid>
                </div>
            </Router>
        </div>
    </Provider>;