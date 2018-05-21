import React from 'react';
import {mainReducer} from './mainReducer';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {createStore, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';

import {AdminConsole} from "./adminConsole/AdminConsole";
import {CreateUser} from "../forms/createUser/CreateUser";
import {EditUser} from "../forms/editUser/EditUser";
import {DeleteUser} from "../forms/deleteUser/DeleteUser";

const store = createStore(mainReducer, applyMiddleware(thunk));

export const MainForm = (props) =>
    <Provider store={store}>
        <div>
            <AdminConsole/>
            <Router>
                <div>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/create">Create</Link>
                        </li>
                        <li>
                            <Link to="/edit">Edit</Link>
                        </li>
                        <li>
                            <Link to="/delete">Delete</Link>
                        </li>
                    </ul>
                    <Route path="/create" component={CreateUser}/>
                    <Route path="/edit/:username?" component={EditUser}/>
                    <Route path="/delete" component={DeleteUser}/>
                </div>
            </Router>
        </div>
    </Provider>;