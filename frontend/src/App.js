import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './components/context/AuthContext';
import PrivateRoute from './components/Utils/PrivateRoute';
import Navbar from './components/Navbar/Navbar';
import Login from './components/Login/Login';
import Signup from './components/Register/Signup';
import 'bootstrap/dist/css/bootstrap.min.css';
import Verify from "./components/Register/Verify";
import TableContainer from "./components/TableContainer/TableContainer";
import ViewJobApplication from "./components/AddJobApplication/ViewJobApplication";
import EditJobApplication from "./components/AddJobApplication/EditJobApplication";
import AddTags from "./components/AddTags/AddTags";
import AddJobApplication from "./components/AddJobApplication/AddJobApplication";
import ArchivedTableContainer from "./components/TableContainer/ArchivedTableContainer";

function App() {
    const [signupKey, setSignupKey] = useState(0);
    const handleSignupClick = () => {
        setSignupKey((prevKey) => prevKey + 1);
    };

    return (
        <AuthProvider>
            <Router>
                <Navbar onSignupClick={handleSignupClick} />
                <Routes>
                    <Route path='/' element={<PrivateRoute><TableContainer /></PrivateRoute>} />
                    <Route path='/archivedJobs' element={<PrivateRoute><ArchivedTableContainer /></PrivateRoute>} />
                    <Route path='/login' element={<Login key={signupKey} />} />
                    <Route path='/signup' element={<Signup key={signupKey} />} />
                    <Route path="/details"  element={<PrivateRoute><ViewJobApplication /></PrivateRoute>} />
                    <Route path="/edit"  element={<PrivateRoute><EditJobApplication /></PrivateRoute>} />
                    <Route path="/addTags"  element={<PrivateRoute><AddTags /></PrivateRoute>} />
                    <Route path="/addJobApplication"  element={<PrivateRoute><AddJobApplication /></PrivateRoute>} />
                    <Route path='/verify/:email/:hash' element={<Verify />} />
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;