import React from "react";
import _ from "lodash";
import {UniformGrid} from "../../../../components/basic/formatters/UniformGrid";
import {TextInput} from "../../../../components/basic/inputs/TextInput";
import {ParameterView} from "../../parameters/ParameterView";
import Button from "@material-ui/core/es/Button/Button";
import {endpoints, executeRequest} from "../../../mainActions";
import {connect} from "react-redux";
import {EventInput} from "./EventInput"
import AddNewEvent from "./AddNewEvent";
import {withRouter} from 'react-router';

import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelActions from '@material-ui/core/ExpansionPanelActions';
import Typography from '@material-ui/core/Typography';

import Chip from '@material-ui/core/Chip';
import Grid from "react-bootstrap/es/Grid";
import {Col, Row} from "react-bootstrap";

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

const chipStyle = {
  fontSize: 12,
  fontWeight: 'bold',
};

class ViewUnit extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.match.params.id1,
            equipmentUnit: {},
            curWorkout: 0,
        }
    }

    handleChange = (e) => {
        //this.setState({id: e.target.value})
    };

    reFresh = () => {
            this.componentDidMount();
    };

    //getUnit = () => {
    componentDidMount(){
        const {id} = this.state;
        const {dispatch} = this.props;
        executeRequest({
            dispatch,
            endpoint: `${endpoints.EQUIPMENT_UNIT_GET}/${id}`,
            errorMessage: "Не удалось",
            postprocess: e => this.fillWorkout(e)
        })
    };

    getParameterValueByParameterName = (equipmentUnit, parameter) => {
        const values = _.get(equipmentUnit, "values", []);
        const value = _.find(values, e => e.parameter.name === parameter);
        return _.get(value, "value", "")

    };

    fillWorkout = (eu) => {
           if (!_.isEmpty(eu))
           {
                var dat = this.fillData(eu);
                if(dat.length > 0){
                    this.setState({equipmentUnit: eu, curWorkout: dat[dat.length-1].workout1});
                }else
                {
                    var pastWorkoutParName = "Предыдущая наработка, т*км";
                    this.setState({equipmentUnit: eu, curWorkout:this.getParameterValueByParameterName(eu,pastWorkoutParName)});
                }
           }
    };

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

    arrangeParameters = (equipmentUnit) => {
        var data = _.get(equipmentUnit, "type.parameters", []);

        var data_new = [];
        console.log(data);
        for (var q = 0, len = data.length/3; q < len; q++) {
            var i = q*3;
            data_new[q] = {};
            data_new[q].name1 = data[i].name;
            data_new[q].value1 = this.getParameterValueByParameterName(equipmentUnit, data[i].name);
            data_new[q].name2 = data[i+1].name;
            data_new[q].value2 = this.getParameterValueByParameterName(equipmentUnit, data[i+1].name);
            data_new[q].name3 = data[i+2].name;
            data_new[q].value3 = this.getParameterValueByParameterName(equipmentUnit, data[i+2].name);
        }
        console.log(data_new);
        return data_new
    }

    render(props) {
        const {equipmentUnit, id, curWorkout} = this.state;
        const {dispatch} = this.props;
        return <React.Fragment>
        {/*<h2>Просмотр оборудования</h2>*/}
            <UniformGrid>
                {/*<TextInput label="Id оборудования" value={id} onChange={this.handleChange}/>*/}
                {/*<Button onClick={this.getUnit}>Найти</Button>*/}
                <h3>Журнал операций</h3>
                {!_.isEmpty(equipmentUnit) && <React.Fragment>
                    <Button onClick={this.reFresh}>Обновить</Button>
                    <TextInput label="Id" value={_.get(equipmentUnit, "id")}/>
                    <TextInput label="Тип" value={_.get(equipmentUnit, "type.name")}/>
                    <Chip style={chipStyle} label={"Текущая наработка: "+curWorkout}/>
                    {
                        _.isNil(equipmentUnit.id)
                            ? null
                            : <Button
                                onClick={() => executeRequest({
                                    dispatch,
                                    endpoint: `equipmentUnits/blockchainGet/${equipmentUnit.hlId}`,
                                })}>Валидировать
                                в блокчейне</Button>
                    }
                    <h4>Параметры оборудования</h4>
                    <Paper>
                      <Table>
                        <TableHead>
                          <TableRow>
                            <CustomTableCell>Параметр</CustomTableCell>
                            <CustomTableCell>Значение</CustomTableCell>
                            <CustomTableCell></CustomTableCell>
                            <CustomTableCell></CustomTableCell>
                            <CustomTableCell></CustomTableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {_.get(equipmentUnit, "type.parameters", []).map(e => {
                            return (
                              <TableRow key={`${e.name}_${e.value}`}>
                                <CustomTableCell>{e.name}</CustomTableCell>
                                <CustomTableCell>{this.getParameterValueByParameterName(equipmentUnit, e.name)}</CustomTableCell>
                                <CustomTableCell></CustomTableCell>
                                <CustomTableCell></CustomTableCell>
                                <CustomTableCell></CustomTableCell>
                              </TableRow>
                            );
                          })}
                        </TableBody>
                      </Table>
                    </Paper>
                    <h4>Операции</h4>
                    <AddNewEvent
                        id={id}
                        WW={7}
                    />
                    <Paper style={{marginTop: "20px"}}>
                        <Table>
                            <TableHead>
                             <TableRow>
                               <CustomTableCell>Тип операции</CustomTableCell>
                               <CustomTableCell>Коэффициент наработки</CustomTableCell>
                               <CustomTableCell>Начало</CustomTableCell>
                               <CustomTableCell>Конец</CustomTableCell>
                               <CustomTableCell>Глубина нач, м</CustomTableCell>
                               <CustomTableCell>Глубина кон, м</CustomTableCell>
                               <CustomTableCell>Вес макс нач, т</CustomTableCell>
                               <CustomTableCell>Вес макс кон, т</CustomTableCell>
                               <CustomTableCell>Переспуск-перетяжка, м</CustomTableCell>
                               <CustomTableCell>Наработка за операцию, т*км</CustomTableCell>
                               <CustomTableCell>Наработка до переспуска</CustomTableCell>
                               <CustomTableCell>Наработка на бухту</CustomTableCell>
                             </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.fillData(equipmentUnit).map(e => {
                                 return (
                                     <TableRow key={e.id}>
                                         <CustomTableCell>{_.get(e.type, "name", "")}</CustomTableCell>
                                         <CustomTableCell>{_.get(e.type, "operatingRatio", "")}</CustomTableCell>
                                         <CustomTableCell>{(new Date(e.operationDateTime)).toISOString().substring(0,10)+" "+(new Date(e.operationDateTime)).toISOString().substring(11,16)}</CustomTableCell>
                                         <CustomTableCell>{(new Date(e.endDateTime)).toISOString().substring(0,10)+" "+(new Date(e.endDateTime)).toISOString().substring(11,16)}</CustomTableCell>
                                         <CustomTableCell>{e.startDepthInMeters}</CustomTableCell>
                                         <CustomTableCell>{e.endDepthInMeters}</CustomTableCell>
                                         <CustomTableCell>{e.startMaxWeightKilos}</CustomTableCell>
                                         <CustomTableCell>{e.endMaxWeightKilos}</CustomTableCell>
                                         <CustomTableCell>{e.perespuskInMeters}</CustomTableCell>
                                         <CustomTableCell>{e.workout0}</CustomTableCell>
                                         <CustomTableCell>{e.workout1}</CustomTableCell>
                                         <CustomTableCell>{e.workout2}</CustomTableCell>
                                     {
                                        _.isNil(e.id)
                                            ? null
                                            : <Button
                                                onClick={() => executeRequest({dispatch, endpoint: `events/blockchainGet/${e.hlId}`,})}>Валидировать
                                                в блокчейне</Button>
                                     }</TableRow>
                                 );
                             })}
                            </TableBody>
                        </Table>
                    </Paper>
                </React.Fragment>
                }
            </UniformGrid>
        </React.Fragment>
    }
}

export default connect()(ViewUnit);