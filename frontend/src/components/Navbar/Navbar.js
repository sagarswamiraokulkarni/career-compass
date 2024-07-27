import React, {useCallback} from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Container, Navbar as BootstrapNavbar, Nav } from 'react-bootstrap';
import { useAuth } from '../Context/AuthContext';
import './Navbar.css';
import {useCleanup} from "../Utils/CleanupContext";

function Navbar({ onSignupClick }) {
    const { getUser, userIsAuthenticated, userLogout } = useAuth();
    const location = useLocation();
    const navigate = useNavigate();
    const logout = () => {
        cleanup();
        userLogout();
    };
    const { cleanup } = useCleanup();
    const handleNavigation = () => {
        cleanup();
    };
    const enterMenuStyle = () => {
        return userIsAuthenticated() ? { display: 'none' } : { display: 'block' };
    };
    const handleGuestClick = () => {
        cleanup();
        navigate('/login', {
            state: { email: "fireflies186@gmail.com", password: "123456" }
        });
    };
    const logoutMenuStyle = () => {
        return userIsAuthenticated() ? { display: 'block' } : { display: 'none' };
    };

    const getUserName = () => {
        const user = getUser();
        return user ? user.data.name : '';
    };

    return (
        <BootstrapNavbar expand="lg" className="custom-navbar">
            <Container>
                <BootstrapNavbar.Brand as={Link} to="/" className="brand-name">Career-Compass</BootstrapNavbar.Brand>
                <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
                <BootstrapNavbar.Collapse id="basic-navbar-nav">
                    {userIsAuthenticated() && <Nav className="me-auto">
                        <Nav.Link as={Link} to="/jobs" onClick={() => handleNavigation()} className={location.pathname === '/jobs' ? 'active' : ''}>Job Applications</Nav.Link>
                        <Nav.Link as={Link} to="/archivedJobs" onClick={() => handleNavigation()} className={location.pathname === '/archivedJobs' ? 'active' : ''}>Archived Job Applications</Nav.Link>
                        <Nav.Link as={Link} to="/addJobApplication" onClick={() => handleNavigation()} className={location.pathname === '/addJobApplication' ? 'active' : ''}>Add Job Application</Nav.Link>
                        <Nav.Link as={Link} to="/addTags" onClick={() => handleNavigation()} className={location.pathname === '/addTags' ? 'active' : ''}>Add/Update Tags</Nav.Link>
                    </Nav>}
                    <Nav className="ms-auto">
                        {userIsAuthenticated() ? (
                            <>
                                <Nav.Item className="user-name" style={logoutMenuStyle()}>
                                    {`${getUserName()}`}
                                </Nav.Item>
                                <button onClick={logout} style={logoutMenuStyle()} className="logout-button">
                                    Logout
                                </button>
                            </>
                        ) : (
                            <>
                                <button onClick={handleGuestClick} style={enterMenuStyle()} className="login-button">
                                    Guest
                                </button>
                                <Link to="/login" onClick={() => handleNavigation()}  style={{ textDecoration: 'none' }}>
                                    <button onClick={onSignupClick}  style={enterMenuStyle()} className="login-button">
                                        Login
                                    </button>
                                </Link>
                                <Link to="/signup" onClick={() => handleNavigation()} style={{ textDecoration: 'none' }}>
                                    <button onClick={onSignupClick} style={enterMenuStyle()} className="signup-button">
                                        Sign Up
                                    </button>
                                </Link>
                            </>
                        )}
                    </Nav>
                </BootstrapNavbar.Collapse>
            </Container>
        </BootstrapNavbar>
    );
}

export default Navbar;
