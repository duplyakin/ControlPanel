import React from 'react';
import PropTypes from 'prop-types';
import {Well} from "react-bootstrap";
import _ from 'lodash';
import {connect} from "react-redux";

const welcomeString = (username) =>
    _.isEmpty(username)
        ? "Hello!"
        : "Hello, " + username;
/**
 * Компонент для отображения информации о текущем пользователе.
 * @param username - имя текущего пользователя (загружается автоматически)
 */
const HomePage = ({username}) => <Well>{welcomeString(username)}</Well>;

HomePage.propTypes = {
    username: PropTypes.string,
};

HomePage.defaultProps = {
    username: '',
};

const mapStateToProps = (store) => {
    return {
        username: _.get(store, "currentUserCache.username", "")
    }
};

export default connect(mapStateToProps)(HomePage)