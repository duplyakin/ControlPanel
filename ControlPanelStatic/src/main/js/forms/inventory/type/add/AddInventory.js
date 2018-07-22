import React from "react";
import _ from "lodash";
import {UniformGrid} from "../../../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../../../components/basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {ParameterInput} from "../../parameters/ParameterInput";
import {ParameterView} from "../../parameters/ParameterView";
import {endpoints, executeRequest} from "../../../mainActions";
import {connect} from "react-redux";

class AddInventory extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            //name-value
            parameters: [],
            addParameter: false,
        }
    }

    addParameter = () => {
        const {dispatch} = this.props;
        const {name, parameters} = this.state;
        executeRequest({
            dispatch,
            method: "PUT",
            endpoint: endpoints.ADD_EQUIPMENT,
            body: {name, parameters},
            postprocess: (e) => this.setState({kid: e}),
            errorMessage: "Не удалось",
        })
    };

    handleChange = (e) => {
        this.setState({name: e.target.value})
    };

    addEquipment = (name, type) => {
        if (!_.isEmpty(name)) {
            const newParameters = [...this.state.parameters];
            newParameters.push({name, type});
            console.log(newParameters);
            this.setState({parameters: newParameters, addParameter: false})
        }
    };

    handleAddParameterClick = () => {
        const {addParameter} = this.state;
        this.setState({addParameter: !addParameter});
    };

    render() {
        const {parameters, addParameter, name} = this.state;
        return <div>
            <UniformGrid>
                <TextInput label="Название оборудования" value={name} onChange={this.handleChange}/>
                {parameters.map(e => <ParameterView name={e.name} type={e.type}
                                                    key={e.name}/>)}
                <Button onClick={this.handleAddParameterClick}>Добавить параметр</Button>
                {addParameter && <ParameterInput onSubmit={this.addEquipment}/>}
            </UniformGrid>
            <UniformGrid>
                <Button onClick={this.addParameter}>Добавить оборудование</Button>
            </UniformGrid>
        </div>
    }
}

export default connect()(AddInventory)