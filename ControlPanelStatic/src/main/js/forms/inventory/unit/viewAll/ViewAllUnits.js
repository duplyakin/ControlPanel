import React from 'react';
import _ from "lodash";
import {connect} from "react-redux";
import {endpoints, executeRequest} from "../../../mainActions";
import Grid from "react-bootstrap/es/Grid";
import {Col, Row} from "react-bootstrap";
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';

import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import { withRouter } from "react-router-dom";
import Button from "@material-ui/core/es/Button/Button";
import Chip from '@material-ui/core/Chip';


const chipStyle = {
  fontSize: 12,
  fontWeight: 'bold',
};

const styles = theme => ({
  root: {
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 100,
    maxWidth: 800,
  },
  row: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.background.default,
    },
  },
});

const CustomTableCell = withStyles(theme => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
    fontSize: 12,
  },
  body: {
    fontSize: 12,
  },
}))(TableCell);

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

    handleView = (id) => {
        if (!(id=="")){
            this.props.history.push("/viewUnit/"+id);
        }
    }

    handleEdit = (id) => {
        if (!(id=="")){
            this.props.history.push("/updateUnit/"+id);
        }
    }

    handleAddUnit = () => {
        this.props.history.push("/addUnit");
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

    getParameterValueByParameterName = (equipmentUnit, parameter) => {
        const values = _.get(equipmentUnit, "values", []);
        const value = _.find(values, e => e.parameter.name === parameter);
        return _.get(value, "value", "")

    };

    fillData_new = () => {
        var {units} = this.state;
        var pastWorkoutParName = "Предыдущая наработка, т*км";
        var maxWorkoutParName = "Допустимая наработка, т*км";
        for (var q = 0, len = units.length; q < len; q++) {
            var equipmentUnit = units[q];
            var eventData = this.fillData(equipmentUnit);
            units[q].curWorkout = _.get( eventData[eventData.length-1], "workout1", parseInt(this.getParameterValueByParameterName(equipmentUnit, pastWorkoutParName)));
            units[q].maxWorkout = parseInt(this.getParameterValueByParameterName(equipmentUnit, maxWorkoutParName));
       }

       return units
    }

    fillData = (equipmentUnit) => {
        var data = _.get(equipmentUnit, "events", []);

        var blockWeightParName = "Вес талевого блока, т";
        var candelLengthParName = "Длина свечи, м";
        var pastWorkoutParName = "Предыдущая наработка, т*км";

        var blockWeight = parseInt(this.getParameterValueByParameterName(equipmentUnit, blockWeightParName));
        var candelLength = parseInt(this.getParameterValueByParameterName(equipmentUnit, candelLengthParName))/1000;
        var pastWorkout = parseInt(this.getParameterValueByParameterName(equipmentUnit, pastWorkoutParName));
        var distance = 0;
        var weight = 0;
        var mul = 1;

        for (var q = 0, len = data.length; q < len; q++) {
            distance = Math.abs(data[q].endDepthInMeters - data[q].startDepthInMeters)/1000;
            weight = data[q].endMaxWeightKilos;
            mul = data[q].type.operatingRatio;

            if(data[q].type.name == "Переспуск-перетяжка"){
                data[q].workout0 = 0;
                data[q].workout1 = 0;
                data[q].workout2 = 0;
                if(q>0){
                    data[q].workout2 = data[q-1].workout2;
                }
            }else{
                data[q].workout0 = ((distance + candelLength)*weight + 4*distance*blockWeight)*mul;
                if(q>0){
                    data[q].workout1 = data[q].workout0 + data[q-1].workout1;
                    data[q].workout2 = data[q].workout0 + data[q-1].workout2;
                }else{
                    data[q].workout1 = data[q].workout0 + pastWorkout;
                    data[q].workout2 = data[q].workout0;
                }
            }
        }
        return data
    };

    render() {
        const {units, unit} = this.state;
        return (
            <Paper style={{marginTop: "20px"}}>
                <h3>Реестр оборудования</h3>
                <Button onClick={() =>this.handleAddUnit()}>Добавить единицу оборудования</Button>
                <Table>
                    <TableHead>
                     <TableRow>
                       <CustomTableCell> </CustomTableCell>
                       <CustomTableCell> </CustomTableCell>
                       <CustomTableCell>ID</CustomTableCell>
                       <CustomTableCell>Тип оборудования</CustomTableCell>
                       <CustomTableCell>Текущая наработка</CustomTableCell>
                       <CustomTableCell>Связанных событий</CustomTableCell>
                     </TableRow>
                    </TableHead>
                    <TableBody>
                    {this.fillData_new().map(e => {
                         return (
                             <TableRow hover key={_.get(e, "id", "")}>
                                 <CustomTableCell><Button onClick={() =>this.handleView(_.get(e, "id", ""))}>Перейти в журнал операций...</Button></CustomTableCell>
                                 <CustomTableCell><Button onClick={() =>this.handleEdit(_.get(e, "id", ""))}>Редактировать...</Button></CustomTableCell>
                                 <CustomTableCell>{_.get(e, "id")}</CustomTableCell>
                                 <CustomTableCell>{_.get(e, "type.name")}</CustomTableCell>
                                 <CustomTableCell><Chip style={chipStyle} label={e.curWorkout + " / " + e.maxWorkout}/></CustomTableCell>
                                 <CustomTableCell>{_.get(e, "events", []).length}</CustomTableCell>
                                 {/*<CustomTableCell><Link to={"/viewUnit/"+_.get(e, "id")}>Перейти</Link></CustomTableCell>*/}
                             </TableRow>
                         );
                     })}
                    </TableBody>
                </Table>
            </Paper>
        );
    }

};

export default connect()(ViewAllUnits)
