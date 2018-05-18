import React from 'react';
import PropTypes from 'prop-types';
import InputLabel, {styles} from "@material-ui/core/es/InputLabel/InputLabel";
import FormControl from "@material-ui/core/es/FormControl/FormControl";
import Input from "@material-ui/core/es/Input/Input";

export const TextInput = (props) => {

    const {label, value, onChange} = props;

    return <FormControl className={styles.formControl}>
        <InputLabel htmlFor="select-multiple-checkbox">{label}</InputLabel>
        <Input value={value} onChange={onChange}/>
    </FormControl>
};

TextInput.propTypes = {
    onChange: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

TextInput.defaultProps = {
    value: "",
    label: "",
    onChange: (e) => {
    }
};