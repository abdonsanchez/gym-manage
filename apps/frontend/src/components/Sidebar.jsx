import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Users, BrainCircuit, Activity, Calendar } from 'lucide-react';

const Sidebar = () => {
    const navItems = [
        { icon: LayoutDashboard, label: "Dashboard", path: "/" },
        { icon: Users, label: "Members", path: "/members" },
        { icon: Calendar, label: "Bookings", path: "/bookings" },
        { icon: BrainCircuit, label: "AI Hub", path: "/ai-hub" },
        { icon: Activity, label: "Analytics", path: "/analytics" },
    ];

    const sidebarStyle = {
        width: '260px',
        height: '100vh',
        backgroundColor: 'var(--bg-sidebar)',
        borderRight: '1px solid var(--border-color)',
        position: 'fixed',
        left: 0,
        top: 0,
        padding: '2rem 1rem',
        display: 'flex',
        flexDirection: 'column'
    };

    const logoStyle = {
        color: 'var(--primary)',
        fontSize: '1.5rem',
        fontWeight: 'bold',
        marginBottom: '3rem',
        paddingLeft: '1rem',
        display: 'flex',
        alignItems: 'center',
        gap: '0.5rem'
    };

    const linkStyle = ({ isActive }) => ({
        display: 'flex',
        alignItems: 'center',
        gap: '1rem',
        padding: '0.8rem 1rem',
        marginBottom: '0.5rem',
        textDecoration: 'none',
        borderRadius: 'var(--radius-sm)',
        color: isActive ? 'var(--text-main)' : 'var(--text-muted)',
        backgroundColor: isActive ? 'rgba(0, 255, 157, 0.1)' : 'transparent',
        borderLeft: isActive ? '3px solid var(--primary)' : '3px solid transparent',
        transition: 'all 0.2s'
    });

    return (
        <aside style={sidebarStyle}>
            <div style={logoStyle}>
                <span style={{ fontSize: '2rem' }}>⚡</span> GestorGYM
            </div>
            <nav>
                {navItems.map((item) => (
                    <NavLink to={item.path} key={item.path} style={linkStyle}>
                        <item.icon size={20} />
                        {item.label}
                    </NavLink>
                ))}
            </nav>
        </aside>
    );
};

export default Sidebar;
