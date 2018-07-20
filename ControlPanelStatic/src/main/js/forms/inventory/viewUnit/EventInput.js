import React from "react";
import _ from "lodash";
import PropTypes from "prop-types";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";
import {SelectBox} from "../../../components/basic/inputs/SelectBox";
import {TextInput} from "../../../components/basic/inputs/TextInput";

const transformEvent = (event) => (field) => (e) => {
    event[field] = e.target.value;
    return event
};

const transformType = (event) => (types) => (e) => {
    const newEventTypeName = e.target.value;
    const correctEventType = types.find(elem => elem.name = newEventTypeName);
    if (!_.isEmpty(correctEventType)) {
        event['type'] = correctEventType;
    }
    return event
};

export const EventInput = (props) => {
    const {event, eventTypes, onChange} = props;
    const {
        type,
        operationDateTime,
        startDepthInMeters,
        endDepthInMeters,
        startMaxWeightKilos,
        endMaxWeightKilos,
        perespuskInMeters,
        place
    } = event;

    const transformEventCurried = transformEvent(event);
    const transformTypeCurried = transformType(event)(eventTypes);
    const onChangeDecoratedForInputs = field => e => onChange(transformEventCurried(field)(e));
    const onChangeDecoratedForSelector = e => onChange(transformTypeCurried(e));
    const options = eventTypes.map(e => e.name);
    return <React.Fragment>
        <UniformGrid>
            <SelectBox options={options}
                       value={_.get(type, "name", "")}
                       onChange={onChangeDecoratedForSelector}
                       label={"Тип события"}/>
            {/*<DateTime label={"Дата-время операции"}*/}
            {/*value={operationDateTime}*/}
            {/*onChange={this.handleInputChange("operationDateTime")}/>*/}
            <TextInput label={"Глубина начала операции, м"}
                       value={startDepthInMeters}
                       onChange={onChangeDecoratedForInputs("startDepthInMeters")}/>
            <TextInput label={"Глубина в конце операции, м"}
                       value={endDepthInMeters}
                       onChange={onChangeDecoratedForInputs("endDepthInMeters")}/>
            <TextInput label={"Вес на крюке макс в начале операции, кг"}
                       value={startMaxWeightKilos}
                       onChange={onChangeDecoratedForInputs("startMaxWeightKilos")}/>
            <TextInput label={"Вес на крюке макс в конце операции, кг"}
                       value={endMaxWeightKilos}
                       onChange={onChangeDecoratedForInputs("endMaxWeightKilos")}/>
            <TextInput label={"Переспуск-перетяжка, м"}
                       value={perespuskInMeters}
                       onChange={onChangeDecoratedForInputs("perespuskInMeters")}/>
            <TextInput label={"Место события"}
                       value={place}
                       onChange={onChangeDecoratedForInputs("place")}/>
        </UniformGrid>
    </React.Fragment>
};

EventInput.propTypes = {
    event: PropTypes.shape({
        operationDateTime: PropTypes.date,
        startDepthInMeters: PropTypes.string,
        endDepthInMeters: PropTypes.string,
        startMaxWeightKilos: PropTypes.string,
        endMaxWeightKilos: PropTypes.string,
        perespuskInMeters: PropTypes.string,
        place: PropTypes.string,
    }),
    eventTypes: PropTypes.array,
    onChange: PropTypes.func
};

EventInput.defaultProps = {
    event: {},
    eventTypes: [],
    onChange: e => e
};