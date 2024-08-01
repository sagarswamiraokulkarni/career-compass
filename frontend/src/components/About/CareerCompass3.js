import React, { useCallback, useEffect, useRef, useState } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import './CareerCompass3.css';
import { useAuth } from "../Context/AuthContext";
import { useNavigate } from "react-router-dom";
import { FaRocket, FaChartLine, FaBriefcase, FaCode, FaServer, FaDatabase } from 'react-icons/fa';
import { SiAmazonapigateway } from "react-icons/si";
import { SiAuthelia } from "react-icons/si";
import { FaBuildingColumns } from "react-icons/fa6";

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
            const visibleHeight = 70;
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
                        <div>
                            <h2><FaChartLine /> <span>Why CareerCompass?</span></h2>
                            <p>In today's competitive job market, managing job applications can be overwhelming.
                                CareerCompass
                                is here to alleviate that stress by offering a powerful tool that organizes and
                                optimizes your
                                job search process.</p>
                        </div>
                    </div>
                    <div className="feature">
                        <div>
                            <h2><FaCode/><span> Robust Architecture</span></h2>
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
                        {[
                            {
                                pattern: 'Singleton Pattern',
                                description: 'Used to ensure a single instance of utility classes, maintaining global state and providing centralized access to common functions.'
                            },
                            {
                                pattern: 'Factory Pattern',
                                description: 'Implemented to create different types of notification strategies (email, SMS, call) dynamically, enhancing flexibility in communication methods.'
                            },
                            {
                                pattern: 'Strategy Pattern',
                                description: 'Applied to select and implement various verification methods, allowing easy switching between different strategies based on user preferences.'
                            },
                            {
                                pattern: 'Facade Pattern',
                                description: 'Utilized to simplify complex subsystems, providing a unified interface for account verification processes and improving code maintainability.'
                            },
                            {
                                pattern: 'Builder Pattern',
                                description: 'Employed for constructing complex objects like job applications, offering a flexible and readable way to create objects with many optional parameters.'
                            }
                        ].map(({pattern, description}, index) => (
                            <div key={index} className="card" ref={el => cardsRef.current[index] = el}>
                                <button className="card-heading">{pattern}:</button>
                                <ul>
                                    <li>{description}</li>
                                </ul>
                            </div>
                        ))}
                    </div>
                </div>
            </section>
            <section className="architecture-section" ref={architectureSectionRef}>
                <div className="architecture-header">
                    <h2><FaBuildingColumns className="section-icon"/> Architecture</h2>
                    <p>Our application architecture ensures high scalability, reliability, and maintainability.</p>
                </div>
                <div className="architecture-content">
                    <div className="architecture-image-container">
                        <div class="architecture-image-wrapper">
                        <img src="https://career-compass-assets.s3.amazonaws.com/CareerCompassArchitecture.png" alt="Architecture Diagram"
                             className="architecture-image"/>
                        </div>
                    </div>
                    <div className="architecture-details">
                        {[
                            {
                                icon: FaBriefcase,
                                title: "Client-Side",
                                description: "Built with React.js, our platform ensures dynamic and responsive user interfaces, enhanced by Amazon CloudFront for fast content delivery from anywhere in the world."
                            },
                            {
                                icon: FaServer,
                                title: "Server-Side",
                                description: "The backend is implemented with AWS Lambda, leveraging a serverless architecture to provide scalable and efficient business logic processing."
                            },
                            {
                                icon: FaDatabase,
                                title: "Database",
                                description: "We use Amazon DynamoDB for data storage, ensuring high availability, scalability, and fast performance for all database operations."
                            },
                            {
                                icon: SiAmazonapigateway,
                                title: "APIs",
                                description: "Our RESTful APIs are managed by Amazon API Gateway, enabling secure and reliable communication between the frontend and backend services."
                            },
                            {
                                icon: SiAuthelia,
                                title: "Authentication",
                                description: "User authentication is handled by Amazon Cognito, providing secure access control and identity management to protect user data."
                            },
                            {
                                icon: FaRocket,
                                title: "Deployment",
                                description: "Leveraging AWS services like EC2, RDS, and S3 for robust hosting and data storage"
                            }
                        ].map((item, index) => (
                            <div key={index} className="architecture-card">
                                <item.icon className="arch-icon"/>
                                <h3>{item.title}</h3>
                                <p>{item.description}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>
        </>
    );
};

export default CareerCompass2;