import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import SideNav from "../components/SideNav/SideNav";
import "../App.css";

const NotificationCenter = ({ isSideNavActive, toggleSideNav, closeSideNav }) => {
    const [title, setTitle] = useState("");
    const [message, setMessage] = useState("");
    const [recipient, setRecipient] = useState("");
    const [sendToAll, setSendToAll] = useState(false);
    const [type, setType] = useState("INFO");
    const [notifications, setNotifications] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [user, setUser] = useState(null);

    const BASE_URL = "http://localhost:9090/api/notifications";

    useEffect(() => {
        const storedUser = localStorage.getItem("user");
        const token = localStorage.getItem("token"); // JWT token
        if (!storedUser || !token) {
            navigate("/login");
            return;
        }

        const parsedUser = JSON.parse(storedUser);
        setUser(parsedUser);

        const fetchNotifications = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/getAll`);
                setNotifications(response.data);
            } catch (err) {
                console.error("Error fetching notifications:", err);
                setError("Failed to fetch notifications");
            }
        };

        fetchNotifications();
    }, [navigate]);

    if (!user) {
        return <p>Loading user...</p>;
    }

    const userRole = user.role || "ROLE_ADMIN";

    const handleSend = async () => {
        if (!title || !message || (!recipient && !sendToAll)) {
            setError("Please fill in all fields.");
            return;
        }

        try {
            const token = localStorage.getItem("token");
            const notification = {
                title,
                message,
                recipient: sendToAll ? "ALL" : recipient,
                sender: user.username || user.email || "admin",
                type,
                time: new Date().toLocaleString(),
            };

            const response = await axios.post(`${BASE_URL}/create`, notification);

            // If "ALL", backend returns nothing, so we skip updating state
            if (response.data) setNotifications([response.data, ...notifications]);

            setTitle("");
            setMessage("");
            setRecipient("");
            setSendToAll(false);
            setType("INFO");
            setError(null);
        } catch (err) {
            console.error("Error sending notification:", err);
            setError("Failed to send notification");
        }
    };

    return (
        <div>
            <Navbar toggleSideNav={toggleSideNav} />
            <SideNav isActive={isSideNavActive} closeSideNav={closeSideNav} />

            <div className="main-section">
                <h1>Notification Center</h1>
                <div className="notification-box">
                    <div className="message-history">
                        <h2>{userRole === "ROLE_ADMIN" ? "Sent Messages" : "Received Messages"}</h2>
                        {notifications.length === 0 ? (
                            <p>No messages yet.</p>
                        ) : (
                            <div className="message-list">
                                {notifications.map((n, index) => (
                                    <div
                                        key={index}
                                        className={`message-card ${n.isRead ? "read" : "unread"}`}
                                    >
                                        <h3>{n.title}</h3>
                                        <p>{n.message}</p>
                                        <p>
                                            <strong>Time:</strong> {n.time}
                                        </p>
                                        <p>
                                            <strong>Recipient:</strong> {n.recipient}
                                        </p>
                                        <p>
                                            <strong>Type:</strong> {n.type}
                                        </p>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>

                    <div className="notification-container">
                        <form className="notification-form">
                            <label>Title:</label>
                            <input
                                type="text"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                required
                            />

                            <label>Message:</label>
                            <textarea
                                value={message}
                                onChange={(e) => setMessage(e.target.value)}
                                rows="6"
                                required
                            />

                            <label>Recipient:</label>
                            <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>
                                <input
                                    type="text"
                                    placeholder="Enter username"
                                    value={recipient}
                                    onChange={(e) => setRecipient(e.target.value)}
                                    disabled={sendToAll}
                                />
                                <label>
                                    <input
                                        type="checkbox"
                                        checked={sendToAll}
                                        onChange={(e) => {
                                            setSendToAll(e.target.checked);
                                            if (e.target.checked) setRecipient("ALL");
                                            else setRecipient("");
                                        }}
                                    />{" "}
                                    All Students
                                </label>
                            </div>

                            <label>Type:</label>
                            <select value={type} onChange={(e) => setType(e.target.value)}>
                                <option value="INFO">INFO</option>
                                <option value="ALERT">ALERT</option>
                                <option value="REMINDER">REMINDER</option>
                                <option value="SYSTEM">SYSTEM</option>
                                <option value="MESSAGE">MESSAGE</option>
                                <option value="UPDATE">UPDATE</option>
                            </select>

                            <button type="button" onClick={handleSend} className="btn btn-primary">
                                Send Notification
                            </button>

                            {error && <p className="error">{error}</p>}
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NotificationCenter;
