import React from 'react';
import {TextInput} from "../../components/basic/inputs/TextInput";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";

export class ParameterView extends React.Component {

    render() {
        const {name, value, type} = this.props;
        return <UniformGrid>
            <TextInput label={"Название параметра"} value={name}/>
            <TextInput label={"Значение параметра"} value={value}/>
            <TextInput label={"Тип параметра"} value={type}/>
        </UniformGrid>;
    }
}