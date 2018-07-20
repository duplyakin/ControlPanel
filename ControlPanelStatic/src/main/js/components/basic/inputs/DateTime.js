import React from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/es/TextField/TextField";

export const DateTime = ({label, value, onChange}) => <TextField
    label={label}
    type="datetime-local"
    value={value}
    onChange={onChange}
    InputLabelProps={{
        shrink: true,
    }}
/>;

DateTime.propTypes = {
    label: PropTypes.string.isRequired,
    value: PropTypes.string,
    onChange: PropTypes.func,
};


DateTime.defaultProps = {
    value: new Date().toDateString(),
    onChange: e => e,
};