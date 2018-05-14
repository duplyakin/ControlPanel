import React from 'react';
import app from './mainReducer'
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {combineReducers, createStore} from 'redux';

import {AdminConsole} from "./adminConsole/AdminConsole";
import {CreateUser} from "./createUser/CreateUser";
import {EditUser} from "./editUser/EditUser";

const mainReducer = combineReducers({
    main: app
});

const store = createStore(mainReducer);

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
                    </ul>
                    <Route path="/create" component={CreateUser}/>
                    {/*<Route path="/edit" component={EditUser}/>*/}
                    <Route path="/edit/:username?" component={EditUser}/>
                </div>
            </Router>
        </div>
    </Provider>;