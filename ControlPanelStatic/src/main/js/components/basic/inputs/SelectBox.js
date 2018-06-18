import React from 'react';
import PropTypes from 'prop-types';
import InputLabel, {styles} from "@material-ui/core/es/InputLabel/InputLabel";
import Select from "@material-ui/core/es/Select/Select";
import MenuItem from "@material-ui/core/es/MenuItem/MenuItem";
import ListItemText from "@material-ui/core/es/ListItemText/ListItemText";
import FormControl from "@material-ui/core/es/FormControl/FormControl";
import Input from "@material-ui/core/es/Input/Input";

export const SelectBox = (props) => {

    const {label, onChange, value, options, valueFactory} = props;

    const validOptions = options.map(e => valueFactory(e));
    const validValue = valueFactory(value);

    return <FormControl className={styles.formControl}>
        <InputLabel htmlFor="select-single">{label}</InputLabel>
        <Select
            value={validValue}
            onChange={onChange}
            input={<Input id="select-single"/>}
        >
            {validOptions.map(name => (
                <MenuItem key={name} value={name}>
                    {/*<Checkbox checked={value.includes(name)}/>*/}
                    <ListItemText primary={name}/>
                </MenuItem>
            ))}
        </Select>
    </FormControl>
};

SelectBox.propTypes = {
    onChange: PropTypes.func.isRequired,
    valueFactory: PropTypes.func,
    value: PropTypes.oneOfType(PropTypes.string, PropTypes.object),
    options: PropTypes.oneOfType(PropTypes.array, PropTypes.object),
    label: PropTypes.string,
};

SelectBox.defaultProps = {
    value: [],
    label: '',
    valueFactory: e => e,
    options: [],
    onChange: (e) => {
    }
};