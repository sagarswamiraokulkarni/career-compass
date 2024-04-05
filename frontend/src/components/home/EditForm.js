// EditForm.js
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import './EditForm.css';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const EditForm = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const rowData = location.state?.rowData;

    const [formData, setFormData] = useState({
        companyName: rowData.companyName,
        role: rowData.role,
        appliedOn: new Date(rowData.appliedOn),
        status: rowData.status,
        field1: rowData.field1,
        field2: rowData.field2,
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleDateChange = (date) => {
        setFormData((prevData) => ({
            ...prevData,
            appliedOn: date,
        }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Updated data:', formData);
        navigate('/');
    };

    const handleCancel = () => {
        navigate(-1);
    };

    return (
        <Container className="edit-form-container">
            <Row className="justify-content-center">
                <Col xs={12} md={8} lg={6}>
                    <h2>Edit Details</h2>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="companyName">
                            <Form.Label>Company Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="companyName"
                                value={formData.companyName}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="role">
                            <Form.Label>Role</Form.Label>
                            <Form.Control
                                type="text"
                                name="role"
                                value={formData.role}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="appliedOn">
                            <Form.Label className="date-label">Applied On</Form.Label>
                            <DatePicker
                                selected={formData.appliedOn}
                                onChange={handleDateChange}
                                dateFormat="yyyy-MM-dd"
                                className="form-control date-picker"
                                showYearDropdown
                                scrollableYearDropdown
                                yearDropdownItemNumber={15}
                            />
                        </Form.Group>
                        <Form.Group controlId="status">
                            <Form.Label>Status</Form.Label>
                            <Form.Control
                                type="text"
                                name="status"
                                value={formData.status}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="field1">
                            <Form.Label>Field 1</Form.Label>
                            <Form.Control
                                type="text"
                                name="field1"
                                value={formData.field1}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="field2">
                            <Form.Label>Field 2</Form.Label>
                            <Form.Control
                                type="text"
                                name="field2"
                                value={formData.field2}
                                onChange={handleInputChange}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" className="mr-2">
                            Submit
                        </Button>
                        <Button variant="secondary" onClick={handleCancel}>
                            Cancel
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default EditForm;