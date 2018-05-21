import React from 'react';
import PropTypes from 'prop-types';
import InputLabel, {styles} from "@material-ui/core/es/InputLabel/InputLabel";
import Select from "@material-ui/core/es/Select/Select";
import MenuItem from "@material-ui/core/es/MenuItem/MenuItem";
import Checkbox from "@material-ui/core/es/Checkbox/Checkbox";
import ListItemText from "@material-ui/core/es/ListItemText/ListItemText";
import FormControl from "@material-ui/core/es/FormControl/FormControl";
import Input from "@material-ui/core/es/Input/Input";

export const MultiTagSelector = (props) => {

    const {label, onChange, value, options} = props;

    return <FormControl className={styles.formControl}>
        <InputLabel htmlFor="select-multiple-checkbox">{label}</InputLabel>
        <Select
            multiple
            value={value}
            onChange={onChange}
            input={<Input id="select-multiple-checkbox"/>}
            renderValue={selected => selected.join(', ')}
        >
            {options.map(name => (
                <MenuItem key={name} value={name}>
                    <Checkbox checked={value.includes(name)}/>
                    <ListItemText primary={name}/>
                </MenuItem>
            ))}
        </Select>
    </FormControl>
};

MultiTagSelector.propTypes = {
    onChange: PropTypes.func.isRequired,
    value: PropTypes.array,
    options: PropTypes.array,
    label: PropTypes.string,
};

MultiTagSelector.defaultProps = {
    value: [],
    label: '',
    options: [],
    onChange: (e) => {
    }
};