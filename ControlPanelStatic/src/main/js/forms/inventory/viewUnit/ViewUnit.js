import React from "react";
import _ from "lodash";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../../components/basic/inputs/TextInput";
import {ParameterView} from "../parameters/ParameterView";
import Button from "@material-ui/core/es/Button/Button";
import {endpoints, executeRequest} from "../../mainActions";
import {connect} from "react-redux";
import {EventInput} from "./EventInput"
import AddNewEvent from "./AddNewEvent";

class ViewUnit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: "",
            equipmentUnit: {},
        }
    }

    handleChange = (e) => {
        this.setState({id: e.target.value})
    };

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

    getParameterValueByParameterName = (equipmentUnit, parameter) => {
        const values = _.get(equipmentUnit, "values", []);
        const value = _.find(values, e => e.parameter.name === parameter);
        return _.get(value, "value", "")

    };

    render() {
        const {equipmentUnit, id} = this.state;
        console.log(this.state);
        return <React.Fragment>
            <UniformGrid>
                <TextInput label="Id оборудования" value={id} onChange={this.handleChange}/>
                <Button onClick={this.getUnit}>Найти</Button>
                {!_.isEmpty(equipmentUnit) && <React.Fragment>
                    <div>Единица оборудования:</div>
                    <TextInput label="Id оборудования" value={equipmentUnit.id}/>
                    <TextInput label="Имя оборудования" value={equipmentUnit.type.name}/>
                    {_.get(equipmentUnit, "type.parameters", []).map(e =>
                        <ParameterView name={e.name}
                                       value={this.getParameterValueByParameterName(equipmentUnit, e.name)}
                                       type={e.type}
                                       key={`${e.name}_${e.value}`}/>)}
                    <div>События</div>
                    {
                        _.get(equipmentUnit, "events", [])
                            .map(e => <EventInput event={e}/>)
                    }
                    <AddNewEvent id={id}/>
                </React.Fragment>
                }
            </UniformGrid>
        </React.Fragment>
    }
}

export default connect()(ViewUnit)