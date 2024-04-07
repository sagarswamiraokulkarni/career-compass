// RowDetails.js
import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './RowDetails.css';

const RowDetails = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const rowData = location.state?.rowData;
    const tagNames = rowData.jobTags.map(tag => tag.name);

    const handleEditClick = () => {
        navigate('/edit', { state: { rowData } });
    };

    return (
        <div className="row-details">
            <h2>Job Application Details</h2>
            <div className="form-group">
                <label>Starred:</label>
                <span>{rowData.starred ? 'Yes' : 'No'}</span>
            </div>
            <div className="form-row">
                <div className="form-group">
                    <label>Company Name:</label>
                    <span>{rowData.company}</span>
                </div>
                <div className="form-group">
                    <label>Role:</label>
                    <span>{rowData.position}</span>
                </div>
            </div>
            <div className="form-group">
                <label>Job URL:</label>
                <span>{rowData.companyUrl}</span>
            </div>
            <div className="form-row">
                <div className="form-group">
                    <label>Applied On:</label>
                    <span>{rowData.applicationDate}</span>
                </div>
                <div className="form-group">
                    <label>Status:</label>
                    <span>{rowData.status}</span>
                </div>
            </div>
            <div className="form-group">
                <label>Tags:</label>
                <div className="tags-container">
                    {tagNames.map((tag, index) => (
                        <span key={index} className="tag">{tag}</span>
                    ))}
                </div>
            </div>
            <div className="form-group">
                <label>Notes:</label>
                <p>{rowData.notes}</p>
            </div>
            <button className="edit-button" onClick={handleEditClick}>Edit</button>
        </div>
    );
};

export default RowDetails;