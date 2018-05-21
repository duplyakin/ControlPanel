import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import {TextInput} from "../../components/basic/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest} from "../mainActions";
import Grid from "@material-ui/core/es/Grid/Grid";
import DialogTitle from "@material-ui/core/es/DialogTitle/DialogTitle";
import Dialog from "@material-ui/core/es/Dialog/Dialog";

export class EditUser extends React.Component {

    deleteUser() {
        executeRequest({
            endpoint: `users/delete/${this.state.user.username}`,
            method: "DELETE",
            handleError: e => {
                this.setState({hasError: true, errorMessage: "Failed to delete user"})
            }
        })
    }

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            name: "",
            hasError: false,
            errorMessage: "",
        };
        this.getUser = this.getUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleClose() {
        this.setState({hasError: false});
    }

    handleChange(event) {
        const {user} = this.state;
        this.setState({user, name: event.target.value});
    }

    getUser() {
        executeRequest({
            endpoint: `users/get/${this.state.name}`,
            postprocess: (e) => this.setState({user: e}),
            handleError: e => {
                this.setState({hasError: true, errorMessage: "Failed to get user"})
            }
        })
    }

    updateUser(user) {
        executeRequest({
            endpoint: "users/update",
            method: "POST",
            body: user,
            postprocess: (e) => this.setState({user: e}),
            handleError: e => {
                this.setState({hasError: true, errorMessage: "Failed to update user"})
            }
        })
    }

    render() {
        const {user, name, hasError, errorMessage} = this.state;
        return <div>
            <Well>Hi! It's user edit form!</Well>
            <Dialog onClose={this.handleClose} aria-labelledby="simple-dialog-title" open={hasError}>
                <DialogTitle id="simple-dialog-title">{errorMessage}</DialogTitle>
                <Button onClick={this.handleClose}>Ok</Button>
            </Dialog>
            <div style={{marginLeft: "10px"}}>
                <div style={{marginLeft: "10px"}}>
                    <Grid container spacing={16}>
                        <Grid item xs={2}>
                            < TextInput value={name} onChange={this.handleChange} label="Имя пользователя"/>
                        </Grid>
                        <Grid item xs={2}>
                            <Button onClick={this.getUser}>Найти</Button>
                        </Grid>
                    </Grid>
                </div>
                {!_.isEmpty(user) && <User user={user} onSubmit={this.updateUser}/>}
                {!_.isEmpty(user) &&
                <div style={{marginLeft: "10px"}}>
                    <Grid container spacing={16}>
                        <Grid item xs={2}>
                            <Button onClick={this.deleteUser}>Удалить</Button>
                        </Grid>
                    </Grid>
                </div>}
            </div>
        </div>
    }

}