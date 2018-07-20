import React from 'react';
import {endpoints} from "../../mainActions";
import AddNewType from "../../../components/addType/AddNewType";

export default class AddEventType extends React.Component {

    render() {
        return <AddNewType endpoint={endpoints.EVENT_TYPE_ADD} label="Тип события"/>
    }
}