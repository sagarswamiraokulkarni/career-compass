import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import { AuthProvider } from './components/context/AuthContext';
import PrivateRoute from './components/misc/PrivateRoute';
import Navbar from './components/misc/Navbar';
import Home from './components/home/Home';
import Login from './components/home/Login';
import Signup from './components/home/Signup';
import AdminPage from './components/admin/AdminPage';
import UserPage from './components/user/UserPage';
import 'bootstrap/dist/css/bootstrap.min.css';
import Verify from "./components/home/Verify";

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
                    <Route path='/' element={<Home/>}/>
                    <Route path='/login' element={<Login key={signupKey}/>}/>
                    <Route path='/signup' element={<Signup key={signupKey}/>}/>
                    <Route path='/verify/:email/:hash' element={<Verify/>}/>
                    <Route path="/adminpage" element={<PrivateRoute><AdminPage/></PrivateRoute>}/>
                    <Route path="/userpage" element={<PrivateRoute><UserPage/></PrivateRoute>}/>
                    <Route path="*" element={<Navigate to="/"/>}/>
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
