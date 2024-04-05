// RowDetails.js
import React from 'react';
import { useLocation } from 'react-router-dom';
import { Container, Row, Col, Card } from 'react-bootstrap';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import './RowDetails.css';

const RowDetails = () => {
    const location = useLocation();
    const rowData = location.state?.rowData;

    return (
        <Container className="row-details-container">
            <Row className="justify-content-center">
                <Col xs={12} md={8} lg={6}>
                    <Card className="row-details-card">
                        <Card.Body>
                            <Card.Title className="row-details-title">Row Details</Card.Title>
                            <Card.Text>
                                <div className="row-details-item">
                                    <span className="row-details-label">Starred:</span>
                                    {rowData.star ? <AiFillStar className="star-icon"/> :
                                        <AiOutlineStar className="star-icon"/>}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Company Name:</span>
                                    {rowData.companyName}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Role:</span>
                                    {rowData.role}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Applied On:</span>
                                    {rowData.appliedOn}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Status:</span>
                                    {rowData.status}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Field1:</span>
                                    {rowData.field1}
                                </div>
                                <div className="row-details-item">
                                    <span className="row-details-label">Field2:</span>
                                    {rowData.field2}
                                </div>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default RowDetails;