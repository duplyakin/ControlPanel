import React from 'react';
import _ from "lodash";
import Button from "@material-ui/core/es/Button/Button";
import Input from "@material-ui/core/es/Input/Input";
import FormControl from "@material-ui/core/es/FormControl/FormControl";
import InputLabel, {styles as classes} from "@material-ui/core/es/InputLabel/InputLabel";
import Select from "@material-ui/core/es/Select/Select";
import MenuItem from "@material-ui/core/es/MenuItem/MenuItem";
import Checkbox from "@material-ui/core/es/Checkbox/Checkbox";
import ListItemText from "@material-ui/core/es/ListItemText/ListItemText";

const names = [
    'Oliver Hansen',
    'Van Henry',
    'April Tucker',
    'Ralph Hubbard',
    'Omar Alexander',
    'Carlos Abbott',
    'Miriam Wagner',
    'Bradley Wilkerson',
    'Virginia Andrews',
    'Kelly Snyder',
];

export class EditUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            name: [],
        };
        this.getUser = this.getUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event ) {
        this.setState({ name: event.target.value })
    };

    componentDidMount() {
        this.getUser()
    }

    getUser() {
        // fetch('http://localhost:8090/users/get/user', {
        //     method: "GET",
        //     credentials:"include",
        //     redirect: "follow",
        //     mode: "cors"
        // }).then(response => response.json())
        //     .then(responseJson => {
        //         this.setState({user: responseJson})});
    }

    render() {
        return <div>
            <FormControl className={classes.formControl}>
                <InputLabel htmlFor="select-multiple-checkbox">Tag</InputLabel>
                <Select
                    multiple
                    value={this.state.name}
                    onChange={this.handleChange}
                    input={<Input id="select-multiple-checkbox" />}
                    renderValue={selected => selected.join(', ')}
                >
                    {names.map(name => (
                        <MenuItem key={name} value={name}>
                            <Checkbox checked={this.state.name.indexOf(name) > -1} />
                            <ListItemText primary={name} />
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    }

}