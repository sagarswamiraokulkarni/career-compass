import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import Chips from 'react-chips';
import './AddJobApplication.css';

const AddJobApplication = () => {
    const [tags, setTags] = useState([]);
    const applicationStatus=['YetToApply', 'Applied', 'WaitingToHearBack', 'NeedToFollowUp', 'Accepted', 'Rejected'];

    const validationSchema = Yup.object().shape({
        starred: Yup.boolean(),
        companyName: Yup.string().required('Company Name is required'),
        jobUrl: Yup.string().url('Invalid URL').required('Job URL is required'),
        role: Yup.string().required('Role is required'),
        appliedOn: Yup.date().required('Applied On date is required'),
        status: Yup.string().required('Status is required'),
        notes: Yup.string(),
    });

    const handleSubmit = (values, { resetForm }) => {
        const jobApplication = {
            ...values,
            tags: tags,
        };
        console.log(jobApplication);
        resetForm();
        setTags([]);
    };

    return (
        <div className="add-job-application">
            <h2>Add Job Application</h2>
            <Formik
                initialValues={{
                    starred: false,
                    companyName: '',
                    jobUrl: '',
                    role: '',
                    appliedOn: '',
                    status: '',
                    notes: '',
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
                                <Chips value={tags} onChange={setTags} suggestions={['tag1', 'tag2', 'tag3', 'tag4']}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="notes">Notes</label>
                                <Field as="textarea" id="notes" name="notes"/>
                            </div>
                            <button type="submit">Submit</button>
                    </Form>
                    )}
            </Formik>
        </div>
    );
};

export default AddJobApplication;