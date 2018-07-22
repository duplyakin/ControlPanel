import React from "react";
import {endpoints, executeRequest} from "../../../mainActions";
import {connect} from "react-redux";
import InventoryUnitInput from "./../InventoryUnitInput";

class AddUnit extends React.Component {

    addUnit = (e) => {
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            method: "PUT",
            endpoint: endpoints.EQUIPMENT_UNIT_ADD,
            body: e,
            errorMessage: "Failed to add unit",
            postprocess: e => console.log(e.id)
        })
    };


    render() {
        return <InventoryUnitInput onSubmit={this.addUnit}
                                   buttonLabel={"Добавить оборудование"}
                                   readonly={false}/>
    }
}

export default connect()(AddUnit)