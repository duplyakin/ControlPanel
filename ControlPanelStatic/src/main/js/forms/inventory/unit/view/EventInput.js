import React from "react";
import _ from "lodash";
import PropTypes from "prop-types";
import {UniformGrid} from "../../../../components/basic/formatters/UniformGrid";
import {SelectBox} from "../../../../components/basic/inputs/SelectBox";
import {TextInput} from "../../../../components/basic/inputs/TextInput";

export class EventInput extends React.Component {

    transformEvent = (field) => (e) => {
        const {event, onChange} = this.props;
        event[field] = e.target.value;
        onChange(event)
    };

    transformType = (e) => {
        const {event, eventTypes, onChange} = this.props;
        const newEventTypeName = e.target.value;
        const correctEventType = eventTypes.find(elem => elem.name === newEventTypeName);
        if (!_.isEmpty(correctEventType)) {
            event['type'] = correctEventType;
        }
        onChange(event)
    };

    render() {
        const {event, eventTypes} = this.props;
        const {
            type,
            operationDateTime,
            endDateTime,
            startDepthInMeters,
            endDepthInMeters,
            startMaxWeightKilos,
            endMaxWeightKilos,
            perespuskInMeters,
            place
        } = event;
        const options = eventTypes.map(e => e.name);
        const saved = event.id
        return <React.Fragment>
            <UniformGrid>
                {
                    _.isNil(saved)
                        ? <SelectBox options={options}
                                     value={_.get(type, "name", "")}
                                     onChange={this.transformType}
                                     label={"Тип"}/>
                        : <TextInput label={"Тип"}
                                     value={_.get(type, "name", "")}
                                     onChange={this.transformEvent("type.name")}/>
                }
                <TextInput label={"Начало операции"}
                           type="datetime-local"
                           value={operationDateTime}
                           onChange={this.transformEvent("operationDateTime")}/>
                <TextInput label={"Конец операции"}
                           type="datetime-local"
                           value={endDateTime}
                           onChange={this.transformEvent("endDateTime")}/>
                <TextInput label={"Глубина начала, м"}
                           value={startDepthInMeters}
                           onChange={this.transformEvent("startDepthInMeters")}/>
                <TextInput label={"Глубина в конце, м"}
                           value={endDepthInMeters}
                           onChange={this.transformEvent("endDepthInMeters")}/>
                <TextInput label={"Вес нач макс, т"}
                           value={startMaxWeightKilos}
                           onChange={this.transformEvent("startMaxWeightKilos")}/>
                <TextInput label={"Вес кон макс, т"}
                           value={endMaxWeightKilos}
                           onChange={this.transformEvent("endMaxWeightKilos")}/>
                <TextInput label={"Переспуск-перетяжка, м"}
                           value={perespuskInMeters}
                           onChange={this.transformEvent("perespuskInMeters")}/>
            </UniformGrid>
        </React.Fragment>
    };
}

EventInput.propTypes = {
    event: PropTypes.shape({
        operationDateTime: PropTypes.date,
        endDateTime: PropTypes.date,
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