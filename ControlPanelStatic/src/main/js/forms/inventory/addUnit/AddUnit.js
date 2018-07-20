import React from "react";
import {endpoints, executeRequest} from "../../mainActions";
import {connect} from "react-redux";
import {SelectBox} from "../../../components/basic/inputs/SelectBox";
import {ParametersBlock} from "./ParametersBlock";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {Button} from "react-bootstrap";

class AddUnit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            types: [],
            selected: "",
            values: {}
        }
    }

    componentWillMount() {
        const {dispatch} = this.props;
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

    addUnit = () => {
        const {dispatch} = this.props;
        const {values, types,selected} = this.state;
        executeRequest({
            dispatch,
            method: "PUT",
            endpoint: endpoints.EQUIPMENT_UNIT_ADD,
            body: {type: types.find(e=>e.name === selected), values},
            errorMessage: "Failed to add unit",
            postprocess: e => console.log(e.id)
        })
    };


    render() {
        const {types, selected, values} = this.state;
        console.log("VALUES")
        console.log(values)
        const options = types.map(e => e.name);
        const params = _.get(types.find(e => e.name === selected), "parameters", []);
        return <UniformGrid>
            <SelectBox options={options}
                       value={selected}
                       onChange={this.handleChange("selected")}
                       label={"Тип оборудования"}/>
            <ParametersBlock parameters={params} onChange={(e) => this.setState({values: e})}/>
            <Button onClick={this.addUnit}>Добавить оборудование</Button>
        </UniformGrid>
    }
}

export default connect()(AddUnit)