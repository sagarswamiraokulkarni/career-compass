import React, {useEffect, useState} from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Verify.css'
import {urlPaths} from "../../Constants";
import {orderApi} from "../misc/OrderApi";
function Verify() {
    const { email, hash } = useParams();
    const notify = (message) => toast(message);
    const navigate = useNavigate();
    const [verificationSuccess, setVerificationSuccess] = useState(null);
    useEffect(() => {
        const handleVerification = async () => {
            try {
                console.log("coming here")
                const response = await orderApi.postApiCallWithoutToken(urlPaths.VALIDATE_VERIFICATION, {
                    email,
                    verificationStrategyType: 'email',
                    verificationChallenge: hash
                });
                if (response.statusCode === 200 && response.data.status==='Success') {
                    notify('Verification is successful. Redirecting to login page..')
                    setVerificationSuccess(true);
                    setTimeout(() => {
                        navigate('/login');
                    }, 2000);
                } else {
                    notify('Verification failed. Do you want to send verification link again?')
                    setVerificationSuccess(false);
                }
            } catch (error) {
                console.error('Error verifying:', error);
                setVerificationSuccess(false);
            }
        };

        handleVerification();
    }, []);

    const handleReVerification = () => {
        navigate('/signup',{ state: { email: email } });
    };
    return (
        <div className="verification-container">
            <ToastContainer/>
            {verificationSuccess === null ? (
                <div className="verification-message verifying">
                    <h2>Verifying your account</h2>
                    <p>Please wait while we verify your account details.</p>
                    <div className="spinner"></div>
                </div>
            ) : verificationSuccess ? (
                <div className="verification-message success">
                    <h2>Verification Successful!</h2>
                    <p>
                        Congratulations! Your account has been successfully verified.
                    </p>
                    <p>
                        You will be redirected to the{' '}
                        <Link to="/login" className="login-link">login page</Link> shortly.
                    </p>
                </div>
            ) : (
                <div className="verification-message failed">
                    <h2>Verification Failed</h2>
                    <p>
                        We apologize, but the verification process has failed. It seems there was an issue with the
                        verification link.
                    </p>
                    <p>
                        Would you like to request a new verification link?
                    </p>
                    <button className="re-verification-button" onClick={handleReVerification}>
                        Request New Link
                    </button>
                </div>
            )}
        </div>
    );
}

export default Verify;
