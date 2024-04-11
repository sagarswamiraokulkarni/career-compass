import React, {useState} from 'react';
import {NavLink, Navigate, useNavigate} from 'react-router-dom';
import {Modal, Button, Form, Alert} from 'react-bootstrap';
import {useAuth} from '../context/AuthContext';
import {careerCompassApi} from '../Utils/CareerCompassApi';
import {parseJwt, handleLogError} from '../Utils/Helpers';
import {BsFillEyeFill, BsFillEyeSlashFill} from 'react-icons/bs';
import './Login.css';
import {urlPaths} from "../../Constants";

function Login() {
    const Auth = useAuth();
    const isLoggedIn = Auth.userIsAuthenticated();
    const navigate = useNavigate();
    const [email, setEmail] = useState('fireflies186@gmail.com');
    const [password, setPassword] = useState('Admin@123');
    const [isError, setIsError] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const handleInputChange = (e) => {
        const {name, value} = e.target;
        if (name === 'email') {
            setEmail(value);
        } else if (name === 'password') {
            setPassword(value);
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!(email && password)) {
            setIsError(true);
            return;
        }
        try {
            const response = await careerCompassApi.postApiCallWithoutToken(urlPaths.AUTHENTICATE,{username:email, password});
            localStorage.setItem('userDetails', JSON.stringify({
                userId: response.data.userId,
                firstName: response.data.firstName,
                email: response.data.email
            }))
            const {accessToken} = response.data;
            const data = parseJwt(accessToken);
            const authenticatedUser = {data, accessToken};
            Auth.userLogin(authenticatedUser);
            const userJson = JSON.parse(localStorage.getItem('user'));
            const storedUser = JSON.parse(localStorage.getItem('userDetails'));
            const [getAllTags, unarchivedJobs, archivedJobs] = await Promise.all([
                careerCompassApi.getApiCall(userJson, urlPaths.GET_ALL_TAGS + storedUser.userId),
                careerCompassApi.getApiCall(userJson, urlPaths.GET_UNARCHIVED_JOB_APPLICATIONS + storedUser.userId),
                careerCompassApi.getApiCall(userJson, urlPaths.GET_ARCHIVED_JOB_APPLICATIONS + storedUser.userId)
            ]);
            localStorage.setItem('allTags', JSON.stringify(getAllTags.data));
            localStorage.setItem('unArchivedJobs', JSON.stringify(unarchivedJobs.data));
            localStorage.setItem('archivedJobs', JSON.stringify(archivedJobs.data));
            navigate('/');


        } catch (error) {
            handleLogError(error);
            setIsError(true);
        }
    };

    return (
        <div className="login-container">
            <Form onSubmit={handleSubmit} className="login-form">
                <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control type="email" name="email" value={email} onChange={handleInputChange}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Password</Form.Label>
                    <div className="password-input">
                        <Form.Control type={showPassword ? "text" : "password"} name="password" value={password}
                                      onChange={handleInputChange}/>
                        <div className="password-toggle" onClick={togglePasswordVisibility}>
                            {showPassword ? <BsFillEyeSlashFill/> : <BsFillEyeFill/>}
                        </div>
                    </div>
                </Form.Group>
                <Button variant="primary" type="submit" className="btn-block">Login</Button>
                <div className="mt-3 text-center">
                    Don't have an account?{' '}
                    <NavLink to="/signup">
                        Sign Up
                    </NavLink>
                </div>
            </Form>
            {isError && <Alert variant="danger" className="mt-3">The email or password provided is incorrect!</Alert>}
        </div>
    );
}

export default Login;
