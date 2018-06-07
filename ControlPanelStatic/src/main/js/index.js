import React from 'react';
import ReactDOM from 'react-dom';
import {MainForm} from "./forms/MainForm";

/**
 * Точка входа в приложение.
 */
export default ReactDOM.render(
    <MainForm/>,
    document.getElementById('react')
);