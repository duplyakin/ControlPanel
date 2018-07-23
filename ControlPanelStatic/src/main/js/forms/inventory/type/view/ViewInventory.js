import React from "react";
import _ from "lodash";
import {UniformGrid} from "../../../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../../../components/basic/inputs/TextInput";
import {ParameterView} from "../../parameters/ParameterView";
import Button from "@material-ui/core/es/Button/Button";
import {endpoints, executeRequest} from "../../../mainActions";
import {connect} from "react-redux";

class ViewInventory extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            equipmentUnit: {},
        }
    }

    handleChange = (e) => {
        this.setState({name: e.target.value})
    };

    getInventory = () => {
        const {name} = this.state;
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            endpoint: `${endpoints.EQUIPMENT_GET}/${name}`,
            errorMessage: "Не удалось",
            postprocess: e => this.setState({equipmentUnit: e})
        })
    };

    render() {
        const {equipmentUnit, name} = this.state;
        return <React.Fragment>
            <UniformGrid>
                <TextInput label="Название оборудования" value={name} onChange={this.handleChange}/>
                <Button onClick={this.getInventory}>Найти</Button>
                {!_.isEmpty(equipmentUnit) && <React.Fragment>
                    <div><b>Параметры:</b></div>
                    <TextInput label="Название оборудования" value={equipmentUnit.name}/>
                    {!_.isEmpty(equipmentUnit.parameters) && equipmentUnit.parameters.map(e =>
                        <ParameterView name={e.name}
                                       value={e.value}
                                       type={e.type}
                                       key={`${e.name}_${e.value}`}/>)}
                </React.Fragment>
                }
            </UniformGrid>
        </React.Fragment>
    }
}

export default connect()(ViewInventory)