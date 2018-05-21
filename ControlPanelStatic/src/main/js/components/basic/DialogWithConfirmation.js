import React from 'react';
import PropTypes from 'prop-types';
import Dialog from "@material-ui/core/es/Dialog/Dialog";
import DialogTitle from "@material-ui/core/es/DialogTitle/DialogTitle";
import Button from "@material-ui/core/es/Button/Button";
import Slide from "@material-ui/core/es/Slide/Slide";
import DialogActions from "@material-ui/core/es/DialogActions/DialogActions";
import DialogContentText from "@material-ui/core/es/DialogContentText/DialogContentText";

function Transition(props) {
    return <Slide direction="up" {...props} />;
}

export const DialogWithConfirmation = ({handleClose, handleRetry, isOpen, title, errorMessage}) =>
    <Dialog
        onClose={handleClose}
        aria-labelledby="simple-dialog-title"
        aria-describedby="alert-dialog-slide-description"
        open={isOpen}
        TransitionComponent={Transition}>
        <DialogTitle id="simple-dialog-title">{title}</DialogTitle>
        <DialogContentText id="alert-dialog-slide-description"
                           style={{marginLeft: "10px"}}>
            {errorMessage}
        </DialogContentText>
        <DialogActions>
            <Button onClick={handleClose}>Закрыть</Button>
            {handleRetry && <Button onClick={e => {
                handleClose(e);
                handleRetry(e);
            }}>Повторить</Button>}
        </DialogActions>
    </Dialog>;

DialogWithConfirmation.propTypes = {
    handleClose: PropTypes.func.isRequired,
    handleRetry: PropTypes.func,
    isOpen: PropTypes.bool,
    title: PropTypes.string,
    errorMessage: PropTypes.string,
};

DialogWithConfirmation.defaultProps = {
    isOpen: false,
    title: "Error!",
    errorMessage: "",
};