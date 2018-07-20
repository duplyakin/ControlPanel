import React from 'react';
import PropTypes from 'prop-types';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {StatelessPopup} from "./StatelessPopup";

const DialogOnError = ({isOpen, title, errorMessage, dispatch}) => {

    return <StatelessPopup
        handleClose={e => dispatch(actions.merge("callStatus", {error: false, errorMessage:""}))}
        isOpen={isOpen}
        errorMessage={errorMessage}/>
};

DialogOnError.propTypes = {
    isOpen: PropTypes.bool,
    errorMessage: PropTypes.string,
};

DialogOnError.defaultProps = {
    errorMessage: "",
};

const mapStateToProps = (store) => {
    return {
        isOpen: store.callStatus.error,
        errorMessage: store.callStatus.errorMessage,
    }
};

export default connect(mapStateToProps)(DialogOnError)