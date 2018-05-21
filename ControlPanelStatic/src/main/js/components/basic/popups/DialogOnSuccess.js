import React from 'react';
import {StatelessPopup} from "./StatelessPopup";
import {connect} from "react-redux";
import {actions} from "react-redux-form";

const DialogOnSuccess = ({isOpen, dispatch}) => {
    return <StatelessPopup isOpen={isOpen}
                           handleClose={e => dispatch(actions.merge("callStatus", {success: false}))}
                           title="Успешно"/>;
};

const mapStateToProps = (store) => {
    return {
        isOpen: store.callStatus.success,
    }
};

export default connect(mapStateToProps)(DialogOnSuccess);
