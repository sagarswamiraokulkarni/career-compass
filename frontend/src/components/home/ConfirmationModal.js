import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const ConfirmationModal = ({show, onHide, onConfirm, rowData, bodyContent}) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Confirmation</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {bodyContent}
                {/*<p>Are you sure you want to delete this job application?</p>*/}
                {/*{rowData && <div>*/}
                {/*    <p><strong>Job URL: </strong>*/}
                {/*        <a href={rowData.companyUrl} target="_blank" rel="noopener noreferrer">*/}
                {/*            {rowData.company}*/}
                {/*        </a>*/}
                {/*    </p>*/}
                {/*    <p><strong>Role: </strong> {rowData.position}</p>*/}
                {/*    <p><strong>Status : </strong> {rowData.status}</p>*/}
                {/*</div>*/}
                {/*}*/}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={onConfirm}>
                    Confirm
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ConfirmationModal;
