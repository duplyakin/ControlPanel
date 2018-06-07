import React from "react";
import PropTypes from 'prop-types';
import {connect} from "react-redux";
import _ from 'lodash';
import {privileges, roles} from './authorities'

const containsAll = (authoritiesRequired, authoritiesActual) => {
    if (_.isEmpty(authoritiesRequired)) {
        return true;
    }
    return authoritiesRequired.reduce((prev, curr) => {
        return prev && authoritiesActual.includes(curr)
    }, true)
};
/**Контейнер, скрывающий действия, на которые нет прав у текущего пользователя
 * Выполняет косметическую функцию - права дополнительно проверяются на стороне динамики
 */
const UserValidator = ({user, privilegesRequired, rolesRequired, children}) => {
    return (containsAll(privilegesRequired, _.get(user, 'privileges', []))
        && containsAll(rolesRequired, _.get(user, 'roles', [])))
        ? children
        : <div/>
};

UserValidator.propTypes = {
    privilegesRequired: PropTypes.arrayOf(PropTypes.oneOf(_.values(privileges))),
    rolesRequired: PropTypes.arrayOf(PropTypes.oneOf(_.values(roles))),
    children: PropTypes.oneOfType([PropTypes.node, PropTypes.arrayOf(PropTypes.node)])
};

UserValidator.defaultProps = {
    privilegesRequired: [],
    rolesRequired: [],
};

const mapStateToProps = (store) => {
    return {user: store.currentUserCache}
};

export default connect(mapStateToProps)(UserValidator)