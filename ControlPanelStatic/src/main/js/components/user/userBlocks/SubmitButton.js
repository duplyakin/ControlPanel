import React from 'react';
import PropTypes from 'prop-types';
import {UniformGrid} from "../../basic/formatters/UniformGrid";
import Button from "@material-ui/core/es/Button/Button";
import _ from "lodash";

function buttonIsDisabled(user) {
    const {username, password} = user;
    return _.isEmpty(username) || _.isEmpty(password);
}

export const SubmitButton = (props) => {
    const {user, onSubmit, mode} = props;
    return <UniformGrid>
        {mode === "CREATE"
            ? <Button disabled={buttonIsDisabled(user)}
                      onClick={() => {
                          onSubmit(user)
                      }}>Создать</Button>

            : <Button disabled={buttonIsDisabled(user)}
                      onClick={() => {
                          onSubmit(user)
                      }}>Обновить</Button>}
    </UniformGrid>
};

SubmitButton.propTypes = {
    user: PropTypes.object,
    onSubmit: PropTypes.func,
    mode: PropTypes.oneOf(["CREATE", "EDIT"]),
};

SubmitButton.defaultProps = {
    user: {},
    onSubmit: e => e,
    mode: "CREATE",
};