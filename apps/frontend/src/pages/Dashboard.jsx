import React from 'react';
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer } from 'recharts';

const data = [
    { name: 'Mon', visits: 400 },
    { name: 'Tue', visits: 300 },
    { name: 'Wed', visits: 550 },
    { name: 'Thu', visits: 450 },
    { name: 'Fri', visits: 600 },
    { name: 'Sat', visits: 800 },
    { name: 'Sun', visits: 700 },
];

const StatCard = ({ title, value, change }) => (
    <div className="card">
        <h3 style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '0.5rem' }}>{title}</h3>
        <div style={{ display: 'flex', alignItems: 'end', gap: '1rem' }}>
            <span style={{ fontSize: '2rem', fontWeight: 'bold' }}>{value}</span>
            <span style={{ color: change.startsWith('+') ? 'var(--primary)' : '#ff4444', fontWeight: '600' }}>
                {change}
            </span>
        </div>
    </div>
);

const Dashboard = () => {
    return (
        <div className="animate-fade-in">
            <h1>Dashboard Overview</h1>

            <div className="grid-cols-3" style={{ marginBottom: '2rem' }}>
                <StatCard title="Total Members" value="1,234" change="+12%" />
                <StatCard title="Active Now" value="45" change="+5%" />
                <StatCard title="Monthly Revenue" value="$45,200" change="+8.5%" />
            </div>

            <div className="card" style={{ height: '400px' }}>
                <h3 style={{ marginBottom: '1.5rem' }}>Weekly Attendance</h3>
                <ResponsiveContainer width="100%" height="100%">
                    <LineChart data={data}>
                        <CartesianGrid strokeDasharray="3 3" stroke="#2a2e37" />
                        <XAxis dataKey="name" stroke="#8b9bb4" />
                        <YAxis stroke="#8b9bb4" />
                        <Tooltip
                            contentStyle={{ backgroundColor: '#181b21', borderColor: '#2a2e37', color: '#fff' }}
                        />
                        <Line type="monotone" dataKey="visits" stroke="var(--primary)" strokeWidth={3} dot={{ r: 6 }} />
                    </LineChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};

export default Dashboard;
