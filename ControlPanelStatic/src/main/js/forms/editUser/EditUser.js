import React from 'react';
import User from "../../components/user/User";
import Well from "react-bootstrap/es/Well";
import {TextInput} from "../../components/basic/inputs/TextInput";
import Button from "@material-ui/core/es/Button/Button";
import {executeRequest} from "../mainActions";
import Grid from "@material-ui/core/es/Grid/Grid";
import {connect} from "react-redux";

export class EditUser extends React.Component {

    deleteUser() {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: `users/delete/${this.state.user.username}`,
            method: "DELETE",
            errorMessage: "Не удалось удалить пользователя",
            dispatch
        })
    }

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            name: "",
        };
        this.getUser = this.getUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
    }

    handleChange(event) {
        const {user} = this.state;
        this.setState({user, name: event.target.value});
    }

    getUser() {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: `users/get/${this.state.name}`,
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось найти пользователя",
            dispatch
        })
    }

    updateUser(user) {
        const {dispatch} = this.props;
        executeRequest({
            endpoint: "users/update",
            method: "POST",
            body: user,
            postprocess: (e) => this.setState({user: e}),
            errorMessage: "Не удалось обновить пользователя",
            dispatch
        })
    };

    render() {
        const {user, name} = this.state;
        return <div>
            <Well>Hi! It's user edit form!</Well>
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
    }
}

export default connect()(EditUser)