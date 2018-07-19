import React from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {endpoints, executeRequest} from "../../../forms/mainActions";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {SelectBox} from "../../../components/basic/inputs/SelectBox";
import Button from "@material-ui/core/es/Button/Button";
import {TextInput} from "../../../components/basic/inputs/TextInput";

class AddNewEvent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            eventTypes: [],
            values: {},
            selected: "",
            event: {
                type: {},
                operationDateTime: new Date(),
                startDepthInMeters: "",
                endDepthInMeters: "",
                startMaxWeightKilos: "",
                endMaxWeightKilos: "",
                perespuskInMeters: "",
                place: "",
            }

        }
    }

    componentDidMount() {
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            endpoint: endpoints.EVENT_TYPES_GET_ALL,
            errorMessage: "Failed to fetch event types",
            postprocess: e => this.setState({eventTypes: e})
        })
    }

    handleEventTypeChange = (e) => {
        const copy = this.state;
        const newEventTypeName = e.target.value;
        copy["selected"] = newEventTypeName;
        const correctEventType = copy.eventTypes.find(elem => elem.name = newEventTypeName);
        if (!_.isEmpty(correctEventType)) {
            copy.event.type = correctEventType;
        }
        this.setState(copy)
    };

    handleInputChange = (field) => (e) => {
        const {event} = this.state;
        event[field] = e.target.value;
        this.setState({event})
    };

    addUnit = () => {
        const {dispatch, id} = this.props;
        const {event} = this.state;
        executeRequest({
            dispatch,
            method: "PUT",
            endpoint: `${endpoints.EVENT_UNIT_ADD}/${id}`,
            body: event,
        })
    };

    render() {
        const {eventTypes, selected, event} = this.state;
        const {
            operationDateTime,
            startDepthInMeters,
            endDepthInMeters,
            startMaxWeightKilos,
            endMaxWeightKilos,
            perespuskInMeters,
            place
        } = event;
        //2017-05-24T10:30
        const options = eventTypes.map(e => e.name);
        return <React.Fragment>
            <div>Добавить событие</div>
            <UniformGrid>
                <SelectBox options={options}
                           value={selected}
                           onChange={this.handleEventTypeChange}
                           label={"Тип события"}/>
                {/*<DateTime label={"Дата-время операции"}*/}
                {/*value={operationDateTime}*/}
                {/*onChange={this.handleInputChange("operationDateTime")}/>*/}
                <TextInput label={"Глубина начала операции, м"}
                           value={startDepthInMeters}
                           onChange={this.handleInputChange("startDepthInMeters")}/>
                <TextInput label={"Глубина в конце операции, м"}
                           value={endDepthInMeters}
                           onChange={this.handleInputChange("endDepthInMeters")}/>
                <TextInput label={"Вес на крюке макс в начале операции, кг"}
                           value={startMaxWeightKilos}
                           onChange={this.handleInputChange("startMaxWeightKilos")}/>
                <TextInput label={"Вес на крюке макс в конце операции, кг"}
                           value={endMaxWeightKilos}
                           onChange={this.handleInputChange("endMaxWeightKilos")}/>
                <TextInput label={"Переспуск-перетяжка, м"}
                           value={perespuskInMeters}
                           onChange={this.handleInputChange("perespuskInMeters")}/>
                <TextInput label={"Место события"}
                           value={place}
                           onChange={this.handleInputChange("place")}/>
                <Button onClick={this.addUnit}>Добавить событие</Button>
            </UniformGrid>
        </React.Fragment>
    }

}

AddNewEvent.propTypes = {
    id: PropTypes.string.isRequired,
};

AddNewEvent.defaultProps = {
    id: ""
};

export default connect()(AddNewEvent)