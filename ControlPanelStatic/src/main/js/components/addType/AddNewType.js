import React from 'react';
import PropTypes from 'prop-types';
import _ from "lodash";
import {ParameterView} from "../../forms/inventory/parameters/ParameterView";
import {ParameterInput} from "../../forms/inventory/parameters/ParameterInput";
import {UniformGrid} from "../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../components/basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest} from "../../forms/mainActions";
import {connect} from "react-redux";

class AddNewType extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            //name-value
            parameters: [],
            addParameter: false,
        }
    }

    addNewType = () => {
        const {dispatch, endpoint} = this.props;
        const {parameters, name} = this.state;
        executeRequest({
            dispatch,
            method: "PUT",
            endpoint,
            body: {name, parameters},
            errorMessage: "Не удалось создать!",
        })
    };

    handleChange = (e) => {
        this.setState({name: e.target.value})
    };

    addParameter = (name, type) => {
        if (!_.isEmpty(name)) {
            const newParameters = [...this.state.parameters];
            newParameters.push({name, type});
            console.log(newParameters);
            this.setState({parameters: newParameters, addParameter: false})
        }
    };

    rejectNewParameter = () => {
        this.setState({addParameter: false})
    };

    handleAddParameterClick = () => {
        const {addParameter} = this.state;
        this.setState({addParameter: !addParameter});
    };

    render() {
        const {parameters, addParameter, name} = this.state;
        const {label} = this.props;
        return <div>
            <UniformGrid>
                <TextInput label={label} value={name} onChange={this.handleChange}/>
                {parameters.map(e => <ParameterView name={e.name} type={e.type}
                                                    key={e.name}/>)}
                <Button onClick={this.handleAddParameterClick}>Добавить новый параметр</Button>
                {addParameter && <ParameterInput onSubmit={this.addParameter}
                                                 onReject={this.rejectNewParameter}/>}
            </UniformGrid>
            <UniformGrid>
                <Button onClick={this.addNewType}>Создать</Button>
            </UniformGrid>
        </div>
    }
}

AddNewType.propTypes = {
    label: PropTypes.string.isRequired,
    endpoint: PropTypes.string.isRequired,
};

AddNewType.defaultProps = {
    label: "Добавить новый тип",
};

export default connect()(AddNewType)