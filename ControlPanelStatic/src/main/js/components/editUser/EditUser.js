import React from 'react';
import Button from "react-bootstrap/es/Button";
import Well from "react-bootstrap/es/Well";
import _ from "lodash";

export const EditUser = (props) =>
    <div>
        <Well>Hi! It's user {_.get(props, "match.params.username", "")} edit form!</Well>
        <Button>Edit user</Button>
    </div>;