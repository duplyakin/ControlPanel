import React from 'react';
import PropTypes from 'prop-types';
import _ from "lodash";
import {UniformGrid} from "../../basic/formatters/UniformGrid";
import {TextInput} from "../../basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";

export class InputWithButton extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({name: event.target.value})
    }


    render() {
        const {onClick} = this.props;
        const {name} = this.state;
        return <UniformGrid>
            <TextInput value={name} onChange={this.handleChange} label="Имя пользователя"/>
            <Button disabled={_.isEmpty(name)} onClick={()=>onClick(name)}>Найти</Button>
        </UniformGrid>
    }
}

InputWithButton.propTypes = {
    onClick: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,
    name: PropTypes.string,
};

InputWithButton.defaultProps = {
    onClick: e => e,
    onChange: e => e,
    name: "",
};