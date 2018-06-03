import React from 'react';
import PropTypes from 'prop-types';
import {UniformGrid} from "../../basic/formatters/UniformGrid";
import {CheckBox} from "../../basic/inputs/CheckBox";

export class UserStatusCheckboxGroup extends React.Component {

    constructor(props) {
        super(props);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
    }

    handleCheckboxChange(change) {
        const ch = {};
        ch[change] = !this.props[change];
        this.props.onChange(ch);
    }

    render() {
        const {accountNonExpired, accountNonLocked, credentialsNonExpired, enabled} = this.props;
        return <UniformGrid>
            <CheckBox checked={accountNonExpired}
                      label={"Учетная запись действительна"}
                      onChange={this.handleCheckboxChange.bind(this, 'accountNonExpired')}
            />
            <CheckBox checked={accountNonLocked}
                      label={"Учетная запись не заблокирована"}
                      onChange={this.handleCheckboxChange.bind(this, 'accountNonLocked')}
            />
            <CheckBox checked={credentialsNonExpired}
                      label={"Данные пользователя действительны"}
                      onChange={this.handleCheckboxChange.bind(this, 'credentialsNonExpired')}
            />
            <CheckBox checked={enabled}
                      label={"Включён"}
                      onChange={this.handleCheckboxChange.bind(this, 'enabled')}
            />
        </UniformGrid>
    }
}

UserStatusCheckboxGroup.propTypes = {
    onChange: PropTypes.func,
    accountNonExpired: PropTypes.bool,
    accountNonLocked: PropTypes.bool,
    credentialsNonExpired: PropTypes.bool,
    enabled: PropTypes.bool,
};

UserStatusCheckboxGroup.defaultProps = {
    onChange: e => e,
    accountNonExpired: true,
    accountNonLocked: true,
    credentialsNonExpired: true,
    enabled: true,
};