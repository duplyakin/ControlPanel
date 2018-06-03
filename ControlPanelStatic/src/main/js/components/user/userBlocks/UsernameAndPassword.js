import React from 'react';
import PropTypes from 'prop-types';
import {UniformGrid} from "../../basic/formatters/UniformGrid";
import {TextInput} from "../../basic/inputs/TextInput";

export class UsernameAndPassword extends React.Component {

    constructor(props) {
        super(props);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(change, event) {
        const ch = {};
        ch[change] = event.target.value;
        this.props.onChange(ch)
    }

    render() {
        const {username, password, mode} = this.props;
        return <UniformGrid>
            <TextInput label={'Имя'}
                       value={username}
                       onChange={mode === "CREATE"
                           ? this.handleInputChange.bind(this, 'username')
                           : undefined}/>
            {mode === "CREATE"
            && <TextInput label={'Пароль'}
                          type={"password"}
                          value={password}
                          onChange={this.handleInputChange.bind(this, 'password')}/>
            }

        </UniformGrid>
    }
}

UsernameAndPassword.propTypes = {
    onChange: PropTypes.func,
    username: PropTypes.string,
    password: PropTypes.string,
    mode: PropTypes.string,
};

UsernameAndPassword.defaultProps = {
    onChange: (e) => e,
    mode: "EDIT",
    username: "",
    password: "",
};