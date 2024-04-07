import React, {useState} from 'react';
import {NavLink, Navigate, useNavigate} from 'react-router-dom';
import {Modal, Button, Form, Alert} from 'react-bootstrap';
import {useAuth} from '../context/AuthContext';
import {orderApi} from '../misc/OrderApi';
import {parseJwt, handleLogError} from '../misc/Helpers';
import {BsFillEyeFill, BsFillEyeSlashFill} from 'react-icons/bs';
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
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
    // const notify = () => toast("Wow so easy!");
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
            const response = await orderApi.authenticate(email, password);
            localStorage.setItem('userDetails', JSON.stringify({userId:response.data.userId,firstName:response.data.firstName,email:response.data.email}))
            console.log(response)
            const {accessToken} = response.data;
            console.log(accessToken)
            const data = parseJwt(accessToken);
            const authenticatedUser = {data, accessToken};
            console.log(authenticatedUser);
            Auth.userLogin(authenticatedUser);
            const userJson = JSON.parse(localStorage.getItem('user'));
            const storedUser = JSON.parse(localStorage.getItem('userDetails'));
            const getAllTags = await orderApi.getApiCall(userJson,urlPaths.GET_ALL_TAGS + storedUser.userId);
            const tagNames = getAllTags.data.map(tag => tag.name);
            console.log(tagNames);
            localStorage.setItem('allTags', JSON.stringify(tagNames));
            try {
                const unarchivedJobs = await orderApi.getApiCall(userJson,urlPaths.GET_UNARCHIVED_JOB_APPLICATIONS + storedUser.userId);
                localStorage.setItem('unArchivedJobs', JSON.stringify(unarchivedJobs.data));
                const archivedJobs = await orderApi.getApiCall(userJson,urlPaths.GET_ARCHIVED_JOB_APPLICATIONS + storedUser.userId);
                localStorage.setItem('archivedJobs', JSON.stringify(archivedJobs.data));
                console.log('API Response:', response);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
            setEmail('');
            setPassword('');
            setIsError(false);
            navigate('/');
        } catch (error) {
            handleLogError(error);
            setIsError(true);
        }
    };

    if (isLoggedIn) {
        return <Navigate to={'/'}/>;
    }

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
