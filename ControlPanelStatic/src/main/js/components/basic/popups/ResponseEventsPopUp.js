import React from 'react';
import DialogOnSuccess from "./DialogOnSuccess";
import DialogOnError from "./DialogOnError";

export const ResponseEventsPopUp = () => <div>
    <DialogOnError/>
    <DialogOnSuccess/>
</div>;