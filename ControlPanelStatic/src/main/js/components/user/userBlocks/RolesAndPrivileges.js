import React from 'react';
import PropTypes from 'prop-types';
import {UniformGrid} from "../../basic/formatters/UniformGrid";
import {MultiTagSelector} from "../../basic/inputs/MultiTagSelector";
import {connect} from "react-redux";

class RolesAndPrivileges extends React.Component {

    constructor(props) {
        super(props);
        this.handleSelectorChange = this.handleSelectorChange.bind(this);
    }

    handleSelectorChange(change, event) {
        const ch = {};
        ch[change] = event.target.value;
        this.props.onChange(ch);
    }

    render() {
        const {allRoles, allPrivileges, roles, privileges} = this.props;
        return <UniformGrid>
            <MultiTagSelector label={"Роли"}
                              options={allRoles}
                              value={roles}
                              onChange={this.handleSelectorChange.bind(this, 'roles')}/>
            <MultiTagSelector label={"Права"}
                              options={allPrivileges}
                              value={privileges}
                              onChange={this.handleSelectorChange.bind(this, 'privileges')}/>
        </UniformGrid>
    }

}

RolesAndPrivileges.propTypes = {
    onChange: PropTypes.func,
    roles: PropTypes.array,
    privileges: PropTypes.array,
    allRoles: PropTypes.array,
    allPrivileges: PropTypes.array,
};

RolesAndPrivileges.defaultProps = {
    onChange: e => e,
    roles: [],
    privileges: [],
    allRoles: [],
    allPrivileges: []
};

const mapStateToProps = (store) => {
    return {
        allRoles: store.permissionsCache.roles,
        allPrivileges: store.permissionsCache.privileges,
    }
};

export default connect(mapStateToProps)(RolesAndPrivileges);

