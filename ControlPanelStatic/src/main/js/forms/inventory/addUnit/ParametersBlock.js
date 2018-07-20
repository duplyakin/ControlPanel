import React from 'react';
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../../components/basic/inputs/TextInput";

export class ParametersBlock extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            values: {}
        }
    }

    generateParamValues = (a, name) => {
        const {values} = this.state;
        values[name] = a.target.value;
        const {parameters} = this.props;
        return parameters.map(e => {
            return {parameter: e, value: values[e.name]}
        })
    };

    onChange = (index) => (e) => {
        const {values} = this.state;
        values[index] = e.target.value;
        this.setState({values})
    };

    render() {
        const {parameters, onChange} = this.props;
        const {values} = this.state;
        console.log(values)
        console.log(parameters)
        return <UniformGrid>
            <div>Параметры оборудования</div>
            {parameters.map((e, index) =>
                <TextInput key={index}
                           label={e.name}
                           value={values[e.name]}
                           onChange={a => {
                               onChange(this.generateParamValues(a, e.name));
                               this.onChange(e.name)(a)
                           }}>
                    {`${e.name}_${index}`}
                </TextInput>)
            }
        </UniformGrid>
    }
}