// EditForm.js
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import Chips from 'react-chips';
import './AddJobApplication.css';

const EditForm = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const rowData = location.state?.rowData;
    const allTags = JSON.parse(localStorage.getItem('allTags'))
    const tagNames = rowData.jobTags.map(tag => tag.name);
    const [tags, setTags] = useState(tagNames || []);
    const validationSchema = Yup.object().shape({
        starred: Yup.boolean(),
        companyName: Yup.string().required('Company Name is required'),
        jobUrl: Yup.string().url('Invalid URL').required('Job URL is required'),
        role: Yup.string().required('Role is required'),
        appliedOn: Yup.date().required('Applied On date is required'),
        status: Yup.string().required('Status is required'),
        notes: Yup.string(),
    });

    const applicationStatus=['YetToApply', 'Applied', 'WaitingToHearBack', 'NeedToFollowUp', 'Accepted', 'Rejected'];

    const handleSubmit = (values) => {
        const updatedData = {
            ...values,
            tags: tags,
        };
        console.log('Updated data:', updatedData);
        navigate('/');
    };

    return (
        <div className="add-job-application-container">
        <div className="add-job-application">
            <h2>Edit Job Application</h2>
            <Formik
                initialValues={{
                    starred: rowData.starred || false,
                    companyName: rowData.company || '',
                    jobUrl: rowData.companyUrl || '',
                    role: rowData.position || '',
                    appliedOn: rowData.applicationDate || '',
                    status: rowData.status || '',
                    notes: rowData.notes || '',
                }}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                {({ errors, touched }) => (
                    <Form>
                        <div className="form-group">
                            <label htmlFor="starred">Starred</label>
                            <Field type="checkbox" id="starred" name="starred"/>
                        </div>
                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="companyName">Company Name</label>
                                <Field type="text" id="companyName" name="companyName"
                                       className={errors.companyName && touched.companyName ? 'error' : ''}/>
                                <ErrorMessage name="companyName" component="div" className="error-message"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="role">Role</label>
                                <Field type="text" id="role" name="role"
                                       className={errors.role && touched.role ? 'error' : ''}/>
                                <ErrorMessage name="role" component="div" className="error-message"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="jobUrl">Job URL</label>
                            <Field type="text" id="jobUrl" name="jobUrl"
                                   className={errors.jobUrl && touched.jobUrl ? 'error' : ''}/>
                            <ErrorMessage name="jobUrl" component="div" className="error-message"/>
                        </div>
                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="appliedOn">Applied On</label>
                                <Field type="date" id="appliedOn" name="appliedOn"
                                       className={errors.appliedOn && touched.appliedOn ? 'error' : ''}/>
                                <ErrorMessage name="appliedOn" component="div" className="error-message"/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="status">Status</label>
                                <Field as="select" id="status" name="status"
                                       className={errors.status && touched.status ? 'error' : ''}>
                                    <option value="">Select Status</option>
                                    {applicationStatus.map((status) => (
                                        <option key={status} value={status}>{status}</option>
                                    ))}
                                </Field>
                                <ErrorMessage name="status" component="div" className="error-message"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="tags">Tags</label>
                            <Chips
                                value={tags}
                                onChange={setTags}
                                suggestions={allTags}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="notes">Notes</label>
                            <Field as="textarea" id="notes" name="notes"/>
                        </div>
                        <div className="form-row">
                            <button type="button" onClick={() => navigate(-1)}>Cancel</button>
                            <button type="submit">Update</button>
                        </div>
                    </Form>
                )}
            </Formik>
        </div>
        </div>
    );
};

export default EditForm;