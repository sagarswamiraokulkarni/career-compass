import React, {useEffect, useState} from 'react';
import {NavLink, Navigate, useNavigate, useLocation} from 'react-router-dom';
import {Modal, Button, Form, Alert} from 'react-bootstrap';
import {useAuth} from '../context/AuthContext';
import {orderApi} from '../misc/OrderApi';
import {parseJwt, handleLogError} from '../misc/Helpers';
import {BsFillEyeFill, BsFillEyeSlashFill} from 'react-icons/bs';
import './Signup.css';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {urlPaths} from '../../Constants'

function Signup() {
    const Auth = useAuth();
    const isLoggedIn = Auth.userIsAuthenticated();
    const navigate = useNavigate();
    const [firstName, setFirstname] = useState('');
    const [lastName, setLastname] = useState('');
    const [password, setPassword] = useState('');
    const [otp, setOtp] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [verificationMethod, setVerificationMethod] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [isError, setIsError] = useState(false);
    const [showForm, setShowForm] = useState(true);
    const [showVerification, setshowVerification] = useState(false);
    const [showNothing, setShowNothing] = useState(false);
    const [showOtp, setShowOtp] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [toastMsg, setToastMsg] = useState('');
    const notify = (message) => toast(message);
    const location = useLocation();
    const [wrongOtpAttempts, setWrongOtpAttempts] = useState(0);
    const message = location.state?.email;

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        if (name === 'firstName') {
            setFirstname(value);
        } else if (name === 'lastName') {
            setLastname(value);
        } else if (name === 'password') {
            setPassword(value);
        } else if (name === 'confirmPassword') {
            setConfirmPassword(value);
        } else if (name === 'email') {
            setEmail(value);
        } else if (name === 'phoneNumber') {
            setPhoneNumber(value);
        }else if(name==='otp'){
            setOtp(value);
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const toggleConfirmPasswordVisibility = () => {
        setShowConfirmPassword(!showConfirmPassword);
    };
    useEffect(() => {
        console.log(message)
        if (message) {
            setEmail(message);
            handleVerification('email',message);
        }
    }, [message]);


    function validatePhoneNumber(phoneNumber) {
        const phoneRegex = /^\d{10}$/;
        return phoneRegex.test(phoneNumber);
    }

    const handleVerification = async (method,email) => {
        setVerificationMethod(method);
        console.log(email)
        const response = await orderApi.postApiCall(urlPaths.SEND_VERIFICATION, {
            email,
            verificationStrategyType: method
        });
        let errorMsg;
        if (response.statusCode != 200) {
            errorMsg = response.message;
            setToastMsg(errorMsg);
            notify(errorMsg);
            setShowForm(false);
            setshowVerification(false);
            setShowOtp(false);
            setShowNothing(true);
        } else {
            if (method === 'email') {
                let message = 'Verification mail has been sent to your email.';
                setToastMsg(message);
                notify(message)
                setShowForm(false);
                setshowVerification(false);
                setShowOtp(false);
                setShowNothing(true);
            } else if (method === 'whatsapp') {
                let message = 'OTP has been sent to your whatsapp.';
                setToastMsg(message);
                notify(message)
                setShowForm(false);
                setshowVerification(false);
                setShowOtp(true);
            } else {
                let message = 'OTP has been sent to your mobile.';
                setToastMsg(message);
                notify(message)
                setShowForm(false);
                setshowVerification(false);
                setShowOtp(true);
            }
        }
    };
    const handleOtpSubmit = async (e) => {
        e.preventDefault();
            const response = await orderApi.postApiCall(urlPaths.VALIDATE_VERIFICATION, {
                email,
                verificationStrategyType: verificationMethod,
                verificationChallenge: otp
            });

            if (response.statusCode === 200 && response.data.status==='Success') {
                notify('Verification is successful. Redirecting to login page..');
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
                return;
            } else {
                setWrongOtpAttempts(prevAttempts => prevAttempts + 1);
                if (wrongOtpAttempts < 2) {
                    notify('Oops, you have entered the wrong OTP. Please enter a valid OTP.');
                } else {
                    notify("You have reached the maximum limit of OTP's. Please try again using below verification methods.");
                    setOtp('');
                    setWrongOtpAttempts(0);
                    setShowForm(false);
                    setShowOtp(false);
                    setshowVerification(true);
                    // return;
                }
            }

    }
    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        if (!(firstName && lastName && password && confirmPassword && email)) {
            setIsError(true);
            setErrorMessage('Please, fill in all fields!');
            return;
        }

        if (phoneNumber && !validatePhoneNumber(phoneNumber)) {
            setIsError(true);
            setErrorMessage('Please, enter valid phone number without extension code!');
            return;
        }

        if (password !== confirmPassword) {
            setIsError(true);
            setErrorMessage('Passwords do not match!');
            notify();
            return;
        }
        setIsError(false);
        setErrorMessage('');

        const user = {firstName, lastName, password, email, phoneNumber: '+1' + phoneNumber, verifyByPhoneNumber: true};

        try {
            const response = await orderApi.getApiCall(urlPaths.CHECK_USER_REGISTRATION_STATUS + email);
            if (response.statusCode == 200) {
                if (response.data.userAccountPresent && response.data.accountVerified) {
                    notify(`Account with email:${email} already present, redirecting to Login page..`)
                    setTimeout(() => {
                        navigate('/login');
                    }, 2000);
                } else if (!response.data.userAccountPresent && !response.data.accountVerified) {
                    const response = await orderApi.postApiCall(urlPaths.SIGNUP, user);
                } else {
                    notify(`Account with the email:${email} was already present but hasn't verified yet. Please verify`)
                }
            } else {
                notify("Something went wrong. Please try again later.")
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            }
            setShowOtp(false);
            setShowForm(false);
            setshowVerification(true);
            setIsError(false);
            setErrorMessage('');
        } catch (error) {
            handleLogError(error);
            if (error.response && error.response.data) {
                const errorData = error.response.data;
                let errorMessage = 'Invalid fields';
                if (errorData.status === 409) {
                    errorMessage = errorData.message;
                } else if (errorData.status === 400) {
                    errorMessage = errorData.errors[0].defaultMessage;
                }
                setIsError(true);
                setErrorMessage(errorMessage);
            }
        }
    };

    if (isLoggedIn) {
        return <Navigate to='/'/>;
    }

    return (
        <div className="signup-container">
            <ToastContainer/>
            {showVerification && (
                <div className="verification-options">
                    <Button variant="primary" type="submit" className="btn-block"
                            onClick={() =>
                                handleVerification('email',email)
                            }>
                        Verify by Email
                    </Button>
                    <span className="divider-text">or</span>
                    <Button variant="primary" type="submit" className="btn-block"
                            onClick={() => handleVerification('sms',email)}>
                        Verify by SMS
                    </Button>
                    <span className="divider-text">or</span>
                    <Button variant="primary" type="submit" className="btn-block"
                            onClick={() => handleVerification('whatsapp',email)}>
                        Verify by Whatsapp
                    </Button>
                </div>
            )}
            {showOtp && (
                <Form onSubmit={handleOtpSubmit} className="otp-form">
                    <Form.Group className="mb-3">
                        <Form.Label>Otp</Form.Label>
                        <Form.Control type="text" name="otp" value={otp} onChange={handleInputChange}/>
                    </Form.Group>
                    <Button variant="primary" type="submit" className="btn-block">
                        Verify
                    </Button>
                </Form>
            )}
            {showForm && (
                <Form onSubmit={handleRegisterSubmit} className="signup-form">
                    <Form.Group className="mb-3">
                        <Form.Label>First Name*</Form.Label>
                        <Form.Control type="text" name="firstName" value={firstName} onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Last Name*</Form.Label>
                        <Form.Control type="text" name="lastName" value={lastName} onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Password*</Form.Label>
                        <div className="password-input">
                            <Form.Control type={showPassword ? "text" : "password"} name="password" value={password}
                                          onChange={handleInputChange}/>
                            <div className="password-toggle" onClick={togglePasswordVisibility}>
                                {showPassword ? <BsFillEyeSlashFill/> : <BsFillEyeFill/>}
                            </div>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Confirm Password*</Form.Label>
                        <div className="password-input">
                            <Form.Control type={showConfirmPassword ? "text" : "password"} name="confirmPassword"
                                          value={confirmPassword} onChange={handleInputChange}/>
                            <div className="password-toggle" onClick={toggleConfirmPasswordVisibility}>
                                {showConfirmPassword ? <BsFillEyeSlashFill/> : <BsFillEyeFill/>}
                            </div>
                        </div>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Email*</Form.Label>
                        <Form.Control type="email" name="email" value={email} onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Phone Number*</Form.Label>
                        <Form.Control type="tel" name="phoneNumber" value={phoneNumber} onChange={handleInputChange}/>
                    </Form.Group>
                    <Button variant="primary" type="submit" className="btn-block">
                        Signup
                    </Button>
                    <div className="mt-3 text-center">
                        Already have an account?{' '}
                        <NavLink to="/login">
                            Login
                        </NavLink>
                    </div>
                </Form>
            )}
            {showNothing && <div className="email-toast">
                {toastMsg}</div>}
            {isError && <Alert variant="danger" className="mt-3">{errorMessage}</Alert>}
        </div>
    );
}

export default Signup;
