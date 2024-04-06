import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Navbar as BootstrapNavbar, Nav } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import './Navbar.css'; // Import your custom CSS file for styling

function Navbar({ onSignupClick }) {
  const { getUser, userIsAuthenticated, userLogout } = useAuth();

  const logout = () => {
    userLogout();
  };

  const adminPageStyle = () => {
    const user = getUser();
    return user && user.data.rol[0] === 'ADMIN' ? { display: 'block' } : { display: 'none' };
  };

  const enterMenuStyle = () => {
    return userIsAuthenticated() ? { display: 'none' } : { display: 'block' };
  };

  const logoutMenuStyle = () => {
    return userIsAuthenticated() ? { display: 'block' } : { display: 'none' };
  };

  const userPageStyle = () => {
    const user = getUser();
    return user && user.data.rol[0] === 'USER' ? { display: 'block' } : { display: 'none' };
  };

  const getUserName = () => {
    const user = getUser();
    return user ? user.data.name : '';
  };

  return (
      <BootstrapNavbar  expand="lg" className="custom-navbar">
        <Container>
          <BootstrapNavbar.Brand as={Link} to="/">Career-Compass</BootstrapNavbar.Brand>
          <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
          <BootstrapNavbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/" exact>Home</Nav.Link>
              <Nav.Link as={Link} to="/" exact>View Job Applications</Nav.Link>
              <Nav.Link as={Link} to="/" exact>Add Job Application</Nav.Link>
              <Nav.Link as={Link} to="/addTags" exact>Add Tag</Nav.Link>
              <Nav.Link as={Link} to="/adminpage" style={adminPageStyle()}>AdminPage</Nav.Link>
              <Nav.Link as={Link} to="/userpage" style={userPageStyle()}>UserPage</Nav.Link>
            </Nav>
            <Nav>
              {userIsAuthenticated() ? (
                  <>
                    <Nav.Item style={logoutMenuStyle()}>{`Hi ${getUserName()}`}</Nav.Item>
                    <Nav.Link as={Link} to="/" onClick={logout} style={logoutMenuStyle()}>Logout</Nav.Link>
                  </>
              ) : (
                  <>
                    <Nav.Link as={Link} to="/login" onClick={onSignupClick} style={enterMenuStyle()}>Login</Nav.Link>
                    <Nav.Link as={Link} to="/signup" onClick={onSignupClick} style={enterMenuStyle()}>Sign Up</Nav.Link>
                  </>
              )}
            </Nav>
          </BootstrapNavbar.Collapse>
        </Container>
      </BootstrapNavbar>
  );
}

export default Navbar;
