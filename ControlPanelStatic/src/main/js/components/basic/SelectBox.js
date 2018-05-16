import React from 'react';
import PropTypes from 'prop-types';
import {Col, ControlLabel, FormControl, FormGroup, HelpBlock} from "react-bootstrap";

export class SelectBox extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: ''
        };
    }


    handleChange(e) {

    }

    render() {
        const {onChange, label, value, validate, errorMessage, type, placeholder} = this.props;
        return <FormGroup
            controlId={label}
            validationState={validate(value)}>
            <Col componentClass={ControlLabel} sm={2}>{label}</Col>
            <Col sm={2}>
                <FormControl componentClass={"select" } multiple
                    type={type}
                    value={value}
                    placeholder={placeholder}
                    onChange={onChange}
                />
                <FormControl.Feedback/>
                <HelpBlock>{errorMessage}</HelpBlock>
            </Col>
        </FormGroup>
    }
}

SelectBox.propTypes = {
    onChange: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired,
    value: PropTypes.array,
    validate: PropTypes.func,
    errorMessage: PropTypes.string,
    type: PropTypes.string,
    placeholder: PropTypes.string,
};

SelectBox.defaultProps = {
    validate: value => {
        // const length = value.length;
        // if (length > 10)
        return 'success';
        // else if (length > 5) return 'warning';
        // else if (length > 0) return 'error';
        // return null;
    },
    type: "select",
    placeholder: "Enter text",
    value: [],
    errorMessage: "Invalid!",
};