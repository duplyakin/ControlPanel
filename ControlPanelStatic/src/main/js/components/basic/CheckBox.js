import React from 'react';
import {styles} from "@material-ui/core/es/InputLabel/InputLabel";
import FormControlLabel from "@material-ui/core/es/FormControlLabel/FormControlLabel";
import Checkbox from "@material-ui/core/es/Checkbox/Checkbox";
import PropTypes from "prop-types";

export const CheckBox = (props) => {

    const {label, onChange, checked} = props;
    return  <FormControlLabel control={<Checkbox
                checked={checked}
                onChange={onChange}/>
        }
        label={label}
    />
};

CheckBox.propTypes = {
    onChange: PropTypes.func.isRequired,
    checked: PropTypes.bool,
    label: PropTypes.string,
};

CheckBox.defaultProps = {
    value: false,
    checked: '',
    onChange: (e) => {}
};