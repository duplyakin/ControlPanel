import React from 'react';
import _ from "lodash";
import {connect} from "react-redux";
import {endpoints, executeRequest} from "../../../mainActions";
import Grid from "react-bootstrap/es/Grid";
import {Col, Row} from "react-bootstrap";
import {Link} from "react-router";
import {browserHistory} from "react-router";

class ViewAllUnits extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            units: [],
            unit: {},
        }
    }

    componentDidMount() {
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            endpoint: endpoints.EQUIPMENT_UNIT_GET_ALL,
            postprocess: e => this.setState({units: e}),
            errorMessage: "Не удалось загрузить экземпляры оборудования",
        })
    }

    setUnit = (unit) => () => this.setState({unit});

    representSelectedUnit = () => {
        const {unit} = this.state;
        return _.isEmpty(unit)
            ? <div/>
            : <React.Fragment>
                <div><b>Оборудование</b></div>
                <div>{`id: ${_.get(unit, "id")}`}</div>
                <div>{`Тип: ${_.get(unit, "type.name")}`}</div>
                <div><b>Параметры:</b></div>
                {_.get(unit, "values", []).map(unit => <div
                    id={unit.id}>{`Имя: ${_.get(unit, "parameter.name")}; Значение: ${_.get(unit, "value")}`}</div>)}
                <div><b>События</b></div>
                {_.get(unit, "events", []).map(event => <div
                    id={event.id}>{`Тип: ${_.get(event, "type.name")}; id: ${_.get(event, "id")}`}</div>)}
            </React.Fragment>
    }

    render() {
        const {units, unit} = this.state;
        return <Grid>
            <Col xs={6}>
                <Row>
                    <Col xs={2}><b>id</b></Col>
                    <Col xs={4}><b>Тип</b></Col>
                    <Col xs={2}><b>Связанных событий</b></Col>
                </Row>
                {units.map(unit =>
                    <Row id={_.get(unit, "id", "")} onClick={this.setUnit(unit)}>
                        <Col xs={2}>{_.get(unit, "id")}</Col>
                        <Col xs={4}>{_.get(unit, "type.name")}</Col>
                        <Col xs={2}>{_.get(unit, "events", []).length}</Col>
                    </Row>
                )}
            </Col>
        </Grid>
    }

};

export default connect()(ViewAllUnits)
