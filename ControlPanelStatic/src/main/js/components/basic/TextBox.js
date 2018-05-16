import React from 'react';
import PropTypes from 'prop-types';
import {Col, ControlLabel, FormControl, FormGroup, HelpBlock} from "react-bootstrap";

export class TextBox extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: ''
        };
    }

    render() {
        const {onChange, label, value, validate, errorMessage, type, placeholder} = this.props;
        return <FormGroup
            controlId={label}
            validationState={validate(value)}>
            <Col componentClass={ControlLabel} sm={2}>{label}</Col>
            <Col sm={2}>
                <FormControl
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

TextBox.propTypes = {
    onChange: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    validate: PropTypes.func,
    errorMessage: PropTypes.string,
    type: PropTypes.string,
    placeholder: PropTypes.string,
};

TextBox.defaultProps = {
    validate: value => {
        // const length = value.length;
        // if (length > 10)
        return 'success';
        // else if (length > 5) return 'warning';
        // else if (length > 0) return 'error';
        // return null;
    },
    type: "text",
    placeholder: "Enter text",
    value: "",
    errorMessage: "Invalid!",
};