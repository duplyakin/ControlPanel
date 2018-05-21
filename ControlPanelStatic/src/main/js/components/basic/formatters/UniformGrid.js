import React from 'react';
import PropTypes from 'prop-types';
import Grid from "@material-ui/core/es/Grid/Grid";
import _ from 'lodash';

export const UniformGrid = ({children}) => {
    const arrayChildren = _.isArray(children)
        ? children
        : [children];
    return <div style={{marginLeft: "10px"}}>
        <Grid container spacing={16}>
            {
                arrayChildren.map(item => <Grid item xs={2}>{item}</Grid>)
            }
        </Grid>
    </div>
};

UniformGrid.propTypes = {
    children: PropTypes.oneOfType([PropTypes.node,
        PropTypes.arrayOf(PropTypes.node)])
};