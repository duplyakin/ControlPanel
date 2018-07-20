import React from 'react';
import {StatelessPopup} from "./StatelessPopup";
import {connect} from "react-redux";
import {actions} from "react-redux-form";

const DialogOnSuccess = ({isOpen, dispatch, message}) => {
    return <StatelessPopup isOpen={isOpen}
                           handleClose={() => dispatch(actions.merge("callStatus", {
                               success: false,
                               successMessage: ""
                           }))}
                           title="Успешно"
                           errorMessage={_.isNil(message) ? "" : `id: ${message}`}/>;
};

const mapStateToProps = (store) => {
    return {
        isOpen: store.callStatus.success,
        message: store.callStatus.successMessage,
    }
};

export default connect(mapStateToProps)(DialogOnSuccess);
