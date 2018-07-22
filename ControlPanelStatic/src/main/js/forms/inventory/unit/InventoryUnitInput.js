import React from "react";
import PropTypes from "prop-types";
import {endpoints, executeRequest} from "../../../forms/mainActions";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {SelectBox} from "../../../components/basic/inputs/SelectBox";
import {ParametersBlock} from "../../../forms/inventory/unit/ParametersBlock";
import Button from "@material-ui/core/es/Button/Button";
import {TextInput} from "../../../components/basic/inputs/TextInput";
import {connect} from "react-redux";

class InventoryUnitInput extends React.Component {

    constructor(props) {
        super(props);
        const {inventory} = props;
        this.state = {
            types: [],
            selected: "",
            values: _.get(inventory, "values", [])
        };
    }

    componentDidMount() {
        const {dispatch, readonly} = this.props;
        if (readonly) {
            return;
        }
        executeRequest({
            dispatch,
            endpoint: endpoints.EQUIPMENT_GET_ALL,
            postprocess: (e) => this.setState({types: e}),
            errorMessage: "Не удалось получить типы оборудования",
        })
    }

    handleChange = (field) => (e) => {
        const copy = this.state;
        copy[field] = e.target.value;
        this.setState(copy)
    };

    wrappedOnSubmit = () => {
        const {onSubmit} = this.props;
        const {values, types, selected} = this.state;
        const type = types.find(e => e.name === selected);
        onSubmit({values, type})
    };


    render() {

        const {types, selected} = this.state;
        const {readonly, buttonLabel, inventory} = this.props;

        const params = !_.isEmpty(_.get(inventory, "type", {}))
            ? _.get(inventory, "type.parameters", [])
            : _.get(types.find(e => e.name === selected), "parameters", []);

        return <UniformGrid>
            {!_.isEmpty(inventory)
                ? <TextInput label={"Тип оборудования"} value={_.get(inventory, "type.name")}/>
                : <SelectBox options={types.map(e => e.name)}
                             value={selected}
                             onChange={this.handleChange("selected")}
                             label={"Тип оборудования"}/>
            }
            <ParametersBlock parameters={params} onChange={(e) => !readonly && this.setState({values: e})}/>
            {!readonly && <Button onClick={this.wrappedOnSubmit}>{buttonLabel}</Button>}
        </UniformGrid>
    }
}

InventoryUnitInput.propTypes = {
    readonly: PropTypes.bool,
    inventory: PropTypes.object,
    buttonLabel: PropTypes.string,
    onSubmit: PropTypes.func.isRequired
};

InventoryUnitInput.defaultProps = {
    readonly: true,
    inventory: {},
    buttonLabel: "Добавить оборудование",
    onSubmit: console.log,
};

export default connect()(InventoryUnitInput)