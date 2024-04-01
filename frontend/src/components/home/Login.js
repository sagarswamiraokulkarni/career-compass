import React, { useState } from 'react';
import { NavLink, Navigate, useNavigate } from 'react-router-dom';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { orderApi } from '../misc/OrderApi';
import { parseJwt, handleLogError } from '../misc/Helpers';
import { BsFillEyeFill, BsFillEyeSlashFill } from 'react-icons/bs';
import './LoginModal.css';

function Login({ show, handleClose, handleSignupShow }) {
  const Auth = useAuth();
  const isLoggedIn = Auth.userIsAuthenticated();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isError, setIsError] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
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
      const { accessToken } = response.data;
      const data = parseJwt(accessToken);
      const authenticatedUser = { data, accessToken };

      Auth.userLogin(authenticatedUser);

      setEmail('');
      setPassword('');
      setIsError(false);
      navigate('/');
      handleClose();
    } catch (error) {
      handleLogError(error);
      setIsError(true);
    }
  };

  if (isLoggedIn) {
    return <Navigate to={'/'} />;
  }

  return (
      <Modal show={show} onHide={()=>{navigate('/') }} centered className="login-modal">
        <Modal.Header closeButton>
          <Modal.Title>Login</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" name="email" value={email} onChange={handleInputChange} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <div className="password-input">
                <Form.Control type={showPassword ? "text" : "password"} name="password" value={password} onChange={handleInputChange} />
                <div className="password-toggle" onClick={togglePasswordVisibility}>
                  {showPassword ? <BsFillEyeSlashFill /> : <BsFillEyeFill />}
                </div>
              </div>
            </Form.Group>
            <Button variant="primary" type="submit" className="btn-block">Login</Button>
          </Form>
          {isError && <Alert variant="danger" className="mt-3">The email or password provided is incorrect!</Alert>}
          <div className="mt-3 text-center">Don't have an account? <NavLink to="/signup" onClick={handleSignupShow}>Sign Up</NavLink></div>
        </Modal.Body>
      </Modal>
  );
}

export default Login;
