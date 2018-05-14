import React from 'react';
import Button from "react-bootstrap/es/Button";
import Well from "react-bootstrap/es/Well";
import {Col, ControlLabel, Form, FormControl, FormGroup, HelpBlock} from "react-bootstrap";

export class CreateUser extends React.Component {

    render() {
        return <div>
            <Well>Hi! It's user create form!</Well>
            <Form horizontal>
                <FormGroup controlId="formHorizontalEmail">
                    <Col componentClass={ControlLabel} sm={1}>Username</Col>
                    <Col sm={2}>
                        <FormControl type="text" placeholder="Username" />
                    </Col>
                </FormGroup>

                <FormGroup controlId="formHorizontalPassword">
                    <Col componentClass={ControlLabel} sm={1}>Password</Col>
                    <Col sm={2}>
                        <FormControl type="password" placeholder="Password" />
                    </Col>
                </FormGroup>

                <FormGroup controlId="formHorizontalRoles">
                    <Col componentClass={ControlLabel} sm={1}>Roles</Col>
                    <Col sm={2}>
                        <FormControl componentClass="select" multiple>
                            <option value="select">select roles</option>
                            <option value="other">...</option>
                        </FormControl>
                    </Col>
                </FormGroup>

                <FormGroup controlId="formHorizontalPrivileges">
                    <Col componentClass={ControlLabel} sm={1}>Privileges</Col>
                    <Col sm={2}>
                        <FormControl componentClass="select" multiple>
                            <option value="select">select privileges</option>
                            <option value="other">...</option>
                        </FormControl>
                    </Col>
                </FormGroup>

                <FormGroup controlId="formHorizontalPrivileges">
                    <Col sm={1}/>
                    <Col componentClass={Button} sm={1}>Create user</Col>
                </FormGroup>
            </Form>
        </div>
    }

}