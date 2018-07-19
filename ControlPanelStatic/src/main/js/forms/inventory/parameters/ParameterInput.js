import React from 'react';
import _ from "lodash";
import {TextInput} from "../../../components/basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {SelectBox} from "../../../components/basic/inputs/SelectBox";

const TYPES = {
    numeric: "NUMBER",
    string: "ENUMERATION",
    enumeration: "STRING",
};

export class ParameterInput extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            type: _.values(TYPES)[0],
        }
    }

    couldBeSaved = () => {
        const {name, type} = this.state;
        return !_.isEmpty(name) && !_.isEmpty(type);
    };

    handleChange = (field) => (e) => {
        const stateCopy = [...this.state];
        stateCopy[field] = e.target.value;
        this.setState(stateCopy)
    };

    handleSelect = (e) => {
        const stateCopy = [...this.state];
        stateCopy.type = e.target.value;
        this.setState(stateCopy)
    };

    render() {
        const {name, type} = this.state;
        const {onSubmit, onReject} = this.props;
        return <div>
            <TextInput label={"Название параметра"} value={name} onChange={this.handleChange("name")}/>
            <SelectBox label={"Тип параметра"}
                       options={_.values(TYPES)}
                       value={type}
                       onChange={this.handleSelect}/>
            <Button disabled={!this.couldBeSaved()} onClick={() => onSubmit(name, type)}>Сохранить</Button>
            <Button onClick={onReject}>Удалить</Button>
        </div>;
    }
}

ParameterInput.defaultProps = {
    onReject: e => e
};