import React, { useCallback, useEffect, useRef, useState } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import './CareerCompass3.css';
import { useAuth } from "../Context/AuthContext";
import { useNavigate } from "react-router-dom";
import { FaRocket, FaChartLine, FaBriefcase, FaCode, FaServer } from 'react-icons/fa';

gsap.registerPlugin(ScrollTrigger);

const CareerCompass2 = () => {
    const wrapperRef = useRef(null);
    const cardsRef = useRef([]);
    const leftContentRef = useRef(null);
    const sectionRef = useRef(null);
    const architectureSectionRef = useRef(null);
    const { userIsAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [isMobile, setIsMobile] = useState(window.innerWidth <= 1024);

    const handleGetStarted = () => {
        gsap.killTweensOf(cardsRef.current);
        ScrollTrigger.getAll().forEach(trigger => trigger.kill());
        navigate('/login');
    }

    const enterMenuStyle = () => {
        return userIsAuthenticated() ? { display: 'none' } : { display: 'block' };
    };

    const handleScrollToArchitecture = () => {
        if (architectureSectionRef.current) {
            architectureSectionRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    };

    const handleResize = useCallback(() => {
        setIsMobile(window.innerWidth <= 1024);
    }, []);

    useEffect(() => {
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, [handleResize]);

    useEffect(() => {
        if (wrapperRef.current && cardsRef.current.length > 0 && leftContentRef.current && !isMobile) {
            const cards = cardsRef.current;
            const numCards = cards.length;
            const cardHeight = 200;
            const cardGap = 30;
            const visibleHeight = 50;
            const totalHeight = (cardHeight + cardGap) * numCards;

            gsap.set(cards, {
                y: (i) => i * (cardHeight + cardGap),
                zIndex: (i) => i + 1,
            });

            const tl = gsap.timeline({
                scrollTrigger: {
                    trigger: sectionRef.current,
                    start: "top top",
                    end: `+=${totalHeight}`,
                    pin: true,
                    anticipatePin: 1,
                    scrub: 0.5,
                }
            });

            cards.forEach((card, index) => {
                if (index > 0) {
                    tl.to(card, {
                        y: index * visibleHeight,
                        duration: 1,
                        ease: "power1.inOut",
                    }, (index - 1) / (numCards - 1));
                }
            });

            ScrollTrigger.create({
                trigger: sectionRef.current,
                start: "top top",
                end: `bottom bottom+=${totalHeight}`,
                pin: leftContentRef.current,
                pinSpacing: false
            });

            ScrollTrigger.create({
                trigger: sectionRef.current,
                start: `bottom bottom-=${window.innerHeight / 2}`,
                onEnter: handleScrollToArchitecture,
                once: true
            });
        } else if (isMobile) {
            // Reset styles and kill ScrollTrigger for mobile view
            gsap.set(cardsRef.current, { clearProps: "all" });
            ScrollTrigger.getAll().forEach(trigger => trigger.kill());
        }
    }, [isMobile]);

    return (
        <>
            <section className="intro-section">
                <h1>Your Job Search Organized</h1>
                <p>Keep track of your job applications easily and effortlessly. Try it now.</p>
                <div className="buttons">
                    <button className="start-now-btn" onClick={handleGetStarted}>Start Now</button>
                    <button className="architecture-btn" onClick={handleScrollToArchitecture}>Architecture</button>
                </div>
            </section>

            <section className={`stackable-cards-section ${isMobile ? 'mobile' : ''}`} ref={sectionRef}>
                <div className="left-content" ref={leftContentRef}>
                    <h1><FaRocket/> CareerCompass</h1>
                    <p>CareerCompass is a cutting-edge web application designed to streamline and simplify your job
                        search journey. Our platform empowers job seekers with a user-friendly interface to manage their
                        applications
                        effortlessly.</p>
                    <div className="feature">
                        <FaChartLine className="feature-icon"/>
                        <div>
                            <h2>Why CareerCompass?</h2>
                            <p>In today's competitive job market, managing job applications can be overwhelming.
                                CareerCompass
                                is here to alleviate that stress by offering a powerful tool that organizes and
                                optimizes your
                                job search process.</p>
                        </div>
                    </div>
                    <div className="feature">
                        <FaCode className="feature-icon"/>
                        <div>
                            <h2>Robust Architecture</h2>
                            <p>Our backend is built with industry-standard practices, incorporating key Object-Oriented
                                Design
                                Patterns to enhance performance and scalability. We leverage Singleton, Factory,
                                Strategy,
                                Facade, and Builder patterns within our Spring Boot application, ensuring a robust and
                                efficient
                                system that supports our REST APIs.</p>
                        </div>
                    </div>
                    <div className="cta">
                        <h2>Start Your Journey Today!</h2>
                        <p>Experience a smarter, more organized approach to job searching with CareerCompass.</p>
                    </div>
                </div>
                <div className="right-content" ref={wrapperRef}>
                    <div className="cards">
                        {['Singleton Pattern', 'Factory Pattern', 'Strategy Pattern', 'Facade Pattern', 'Builder Pattern'].map((text, index) => (
                            <div key={index} className="card" ref={el => cardsRef.current[index] = el}>
                                <button className="card-heading">{text}:</button>
                                <ul>
                                    <li>{text}</li>
                                </ul>
                            </div>
                        ))}
                    </div>
                </div>
            </section>
            <section className="architecture-section" ref={architectureSectionRef}>
                <h2><FaServer/> Architecture</h2>
                <img src="CareerCompassArchitecture.png" alt="Architecture Diagram" className="architecture-image"/>
                <p>Our application architecture ensures high scalability, reliability, and maintainability. Here's an
                    overview of the key components:</p>
                <div className="architecture-details">
                    <div className="architecture-card">
                        <FaBriefcase className="arch-icon"/>
                        <h3>Client-Side</h3>
                        <p>Dynamic and responsive user interface built with React.js</p>
                    </div>
                    <div className="architecture-card">
                        <FaServer className="arch-icon"/>
                        <h3>Server-Side</h3>
                        <p>Robust backend implemented with Spring Boot for efficient business logic handling</p>
                    </div>
                    <div className="architecture-card">
                        <FaCode className="arch-icon"/>
                        <h3>Database</h3>
                        <p>MySQL for persistent data storage, ensuring data integrity and efficient querying</p>
                    </div>
                    <div className="architecture-card">
                        <FaRocket className="arch-icon"/>
                        <h3>APIs</h3>
                        <p>RESTful APIs facilitating seamless communication between frontend and backend services</p>
                    </div>
                    <div className="architecture-card">
                        <FaChartLine className="arch-icon"/>
                        <h3>Authentication</h3>
                        <p>Secure mechanisms implemented to protect user data and ensure privacy</p>
                    </div>
                    <div className="architecture-card">
                        <FaServer className="arch-icon"/>
                        <h3>Deployment</h3>
                        <p>Leveraging AWS services like EC2, RDS, and S3 for robust hosting and data storage</p>
                    </div>
                </div>
            </section>
        </>
    );
};

export default CareerCompass2;