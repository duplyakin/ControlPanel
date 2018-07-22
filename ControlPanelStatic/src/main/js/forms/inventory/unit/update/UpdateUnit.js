import React from "react";
import _ from "lodash";
import {endpoints, executeRequest} from "../../../mainActions";
import {connect} from "react-redux";
import InventoryUnitInput from "./../InventoryUnitInput";
import {UniformGrid} from "../../../../components/basic/formatters/UniformGrid";
import Button from "@material-ui/core/es/Button/Button";
import {TextInput} from "../../../../components/basic/inputs/TextInput";

class UpdateUnit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: "",
            equipmentUnit: {},
        }
    }

    getUnit = () => {
        const {id} = this.state;
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            endpoint: `${endpoints.EQUIPMENT_UNIT_GET}/${id}`,
            errorMessage: "Не удалось",
            postprocess: e => this.setState({equipmentUnit: e})
        })
    };

    static getAppropriateValue = (e, newValues) => {
        const good = _.find(newValues, newVal => newVal.parameter.name === e.parameter.name);
        return _.isEmpty(good) ? e.value : good.value
    };

    static mergeValues = (persisted, newValues) => {
        persisted.forEach(
            e => e.value = UpdateUnit.getAppropriateValue(e, newValues)
        );
        return persisted;
    }

    updateUnit = (valuesAndType) => {
        const {dispatch} = this.props;
        const {equipmentUnit} = this.state;
        const {values} = valuesAndType;
        equipmentUnit.values = UpdateUnit.mergeValues(equipmentUnit.values, values);
        executeRequest({
            dispatch,
            method: "POST",
            endpoint: endpoints.EQUIPMENT_UNIT_UPDATE,
            body: equipmentUnit,
            errorMessage: "Failed to update unit",
            postprocess: e => console.log(e.id)
        })
    };

    handleChange = (e) => {
        this.setState({id: e.target.value})
    };

    render() {
        const {equipmentUnit, id} = this.state;
        return <UniformGrid>
            <TextInput label="Id оборудования" value={id} onChange={this.handleChange}/>
            <Button onClick={this.getUnit}>Найти</Button>
            {!_.isEmpty(equipmentUnit) && <InventoryUnitInput readonly={false}
                                                              onSubmit={this.updateUnit}
                                                              buttonLabel={"Обновить оборудование"}
                                                              inventory={equipmentUnit}/>
            }
        </UniformGrid>
    }
}

export default connect()(UpdateUnit)