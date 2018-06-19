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

    onChange = (index) => (e) => {
        const {values} = this.state;
        values[index] = e.target.value;
        this.setState({values})
    };

    render() {
        const {parameters} = this.props;
        const {values} = this.state;
        console.log(values)
        return <UniformGrid>
            <div>Параметры оборудования</div>
            {parameters.map((e, index) =>
                <TextInput key={index}
                           label={e.name}
                           value={values[e.name]}
                           onChange={this.onChange(e.name)}>{`${e.name}_${index}`}</TextInput>)
            }
        </UniformGrid>
    }
}