import React from 'react';
import {actions} from "react-redux-form";
import {connect} from "react-redux";
import {executeRequest} from "../../forms/mainActions";

class CurrentUserCache extends React.Component {

    constructor(props) {
        super(props);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
    }

    loadCurrentUser() {
        const {dispatch} = this.props;
        return executeRequest({
            popupIfSuccess: false,
            endpoint: "currentUser/get",
            postprocess: user => {
                console.log('user');
                console.log(user);
                dispatch(actions.merge("currentUser", user))},
            errorMessage: "Не удалось загрузить текущего пользователя",
            dispatch,
        });
    }

    componentDidMount() {
        Promise.resolve(this.loadCurrentUser())
            .then(() => console.log("Current user is successfully fetched!"))
    }

    render() {
        return <div/>
    }
}

export default connect()(CurrentUserCache)