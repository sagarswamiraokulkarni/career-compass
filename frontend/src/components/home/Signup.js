import React, {useState} from 'react';
import {NavLink, Navigate, useNavigate} from 'react-router-dom';
import {Modal, Button, Form, Alert} from 'react-bootstrap';
import {useAuth} from '../context/AuthContext';
import {orderApi} from '../misc/OrderApi';
import {parseJwt, handleLogError} from '../misc/Helpers';
import {BsFillEyeFill, BsFillEyeSlashFill} from 'react-icons/bs';

function Signup({show, handleClose, handleLoginShow}) {
    const Auth = useAuth();
    const isLoggedIn = Auth.userIsAuthenticated();
    const navigate = useNavigate();
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [password, setPassword] = useState('');
    const [otp, setOtp] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [verificationMethod, setVerificationMethod] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [isError, setIsError] = useState(false);
    const [showVerification, setshowVerification] = useState(false);
    const [showOtp, setShowOtp] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        if (name === 'firstname') {
            setFirstname(value);
        } else if (name === 'lastname') {
            setLastname(value);
        } else if (name === 'password') {
            setPassword(value);
        } else if (name === 'confirmPassword') {
            setConfirmPassword(value);
        } else if (name === 'email') {
            setEmail(value);
        } else if (name === 'phoneNumber') {
            setPhoneNumber(value);
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const toggleConfirmPasswordVisibility = () => {
        setShowConfirmPassword(!showConfirmPassword);
    };

    function validatePhoneNumber(phoneNumber) {
        const phoneRegex = /^\d{10}$/;
        return phoneRegex.test(phoneNumber);
    }

    const handleVerification = (method) => {
        setVerificationMethod(method);
        setshowVerification(false);
        setShowOtp(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!(firstname && lastname && password && confirmPassword && email)) {
            setIsError(true);
            setErrorMessage('Please, fill in all fields!');
            return;
        }

        if (phoneNumber && !validatePhoneNumber(phoneNumber)) {
            setIsError(true);
            setErrorMessage('Please, enter valid phone number!');
            return;
        }

        if (password !== confirmPassword) {
            setIsError(true);
            setErrorMessage('Passwords do not match!');
            return;
        }
        setIsError(false);
        setErrorMessage('');
        setshowVerification(true);
        const user = {firstname, lastname, password, email, phoneNumber};

        // try {
        //     const response = await orderApi.signup(user);
        //     const {accessToken} = response.data;
        //     const data = parseJwt(accessToken);
        //     const authenticatedUser = {data, accessToken};
        //
        //     Auth.userLogin(authenticatedUser);
        //
        //     setFirstname('');
        //     setLastname('');
        //     setPassword('');
        //     setConfirmPassword('');
        //     setEmail('');
        //     setIsError(false);
        //     setErrorMessage('');
        //     handleClose();
        // } catch (error) {
        //     handleLogError(error);
        //     if (error.response && error.response.data) {
        //         const errorData = error.response.data;
        //         let errorMessage = 'Invalid fields';
        //         if (errorData.status === 409) {
        //             errorMessage = errorData.message;
        //         } else if (errorData.status === 400) {
        //             errorMessage = errorData.errors[0].defaultMessage;
        //         }
        //         setIsError(true);
        //         setErrorMessage(errorMessage);
        //     }
        // }
    };

    if (isLoggedIn) {
        return <Navigate to='/'/>;
    }

    return (
        <Modal show={show} onHide={() => {
            navigate('/')
        }} centered className="login-modal">
            <Modal.Header closeButton>
                <Modal.Title>Signup</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {showVerification ? <div className="verification-options">
                        <Button variant="primary" type="submit" className="btn-block" onClick={() => handleVerification('email')}>Verify by Email</Button>
                        {phoneNumber !== '' && (<><span className="divider-text">or</span>
                                <Button variant="primary" type="submit" className="btn-block" onClick={() => handleVerification('sms')}>Verify by
                                    SMS</Button>
                                <span className="divider-text">or</span>
                                <Button variant="primary" type="submit" className="btn-block" onClick={() => handleVerification('whatsapp')}>Verify by
                                    Whatsapp</Button>
                            </>
                        )}
                    </div> :
                    (showOtp ? (<>
                            <Form.Group className="mb-3">
                                <Form.Label>Otp</Form.Label>
                                <Form.Control type="text" name="otp" value={otp} onChange={handleInputChange}/>
                            </Form.Group>
                            <Button variant="primary" type="submit" className="btn-block">Verify</Button>
                        </>) :
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label>First Name*</Form.Label>
                                <Form.Control type="text" name="firstname" value={firstname}
                                              onChange={handleInputChange}/>
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>Last Name*</Form.Label>
                                <Form.Control type="text" name="lastname" value={lastname}
                                              onChange={handleInputChange}/>
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>Password*</Form.Label>
                                <div className="password-input">
                                    <Form.Control type={showPassword ? "text" : "password"} name="password"
                                                  value={password}
                                                  onChange={handleInputChange}/>
                                    <div className="password-toggle" onClick={togglePasswordVisibility}>
                                        {showPassword ? <BsFillEyeSlashFill/> : <BsFillEyeFill/>}
                                    </div>
                                </div>
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>Confirm Password*</Form.Label>
                                <div className="password-input">
                                    <Form.Control type={showConfirmPassword ? "text" : "password"}
                                                  name="confirmPassword"
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
                                <Form.Label>Phone Number</Form.Label>
                                <Form.Control type="tel" name="phoneNumber" value={phoneNumber}
                                              onChange={handleInputChange}/>
                            </Form.Group>
                            <Button variant="primary" type="submit" className="btn-block">Signup</Button>
                            <div className="mt-3 text-center">Already have an account? <NavLink to="/login"
                                                                                                onClick={handleLoginShow}>Login</NavLink>
                            </div>
                        </Form>)}
                {isError && <Alert variant="danger" className="mt-3">{errorMessage}</Alert>}
            </Modal.Body>
        </Modal>
    );
}

export default Signup;
