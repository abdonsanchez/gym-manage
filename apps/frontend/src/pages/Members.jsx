import React, { useEffect, useState } from 'react';
import { UserService } from '../services/api';
import { UserPlus, Search } from 'lucide-react';

const Members = () => {
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadMembers();
    }, []);

    const loadMembers = async () => {
        try {
            // In a real scenario, this fetches from the backend
            // const response = await UserService.getAllMembers();
            // setMembers(response.data);

            // Mock data for display until backend is fully 100% reachable via docker network
            setTimeout(() => {
                setMembers([
                    { id: 1, firstName: 'John', lastName: 'Doe', email: 'john@example.com', status: 'ACTIVE' },
                    { id: 2, firstName: 'Jane', lastName: 'Smith', email: 'jane@example.com', status: 'INACTIVE' },
                    { id: 3, firstName: 'Mike', lastName: 'Johnson', email: 'mike@example.com', status: 'ACTIVE' },
                ]);
                setLoading(false);
            }, 800);
        } catch (error) {
            console.error("Failed to load members", error);
            setLoading(false);
        }
    };

    if (loading) return <div style={{ padding: '2rem' }}>Loading Members...</div>;

    return (
        <div className="animate-fade-in">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h1>Members Management</h1>
                <button className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    <UserPlus size={20} />
                    Add Member
                </button>
            </div>

            <div className="card">
                <div style={{ marginBottom: '1.5rem', display: 'flex', gap: '1rem' }}>
                    <div style={{ position: 'relative', flex: 1, maxWidth: '400px' }}>
                        <Search size={20} style={{ position: 'absolute', left: '1rem', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                        <input
                            type="text"
                            placeholder="Search members..."
                            style={{
                                width: '100%',
                                padding: '0.8rem 1rem 0.8rem 3rem',
                                backgroundColor: 'var(--bg-dark)',
                                border: '1px solid var(--border-color)',
                                borderRadius: 'var(--radius-sm)',
                                color: 'white'
                            }}
                        />
                    </div>
                </div>

                <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                    <thead>
                        <tr style={{ borderBottom: '1px solid var(--border-color)', color: 'var(--text-muted)' }}>
                            <th style={{ padding: '1rem' }}>ID</th>
                            <th style={{ padding: '1rem' }}>Name</th>
                            <th style={{ padding: '1rem' }}>Email</th>
                            <th style={{ padding: '1rem' }}>Status</th>
                            <th style={{ padding: '1rem' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {members.map(member => (
                            <tr key={member.id} style={{ borderBottom: '1px solid var(--border-color)' }}>
                                <td style={{ padding: '1rem' }}>#{member.id}</td>
                                <td style={{ padding: '1rem', fontWeight: '500' }}>{member.firstName} {member.lastName}</td>
                                <td style={{ padding: '1rem', color: 'var(--text-muted)' }}>{member.email}</td>
                                <td style={{ padding: '1rem' }}>
                                    <span style={{
                                        padding: '0.25rem 0.75rem',
                                        borderRadius: '20px',
                                        fontSize: '0.85rem',
                                        backgroundColor: member.status === 'ACTIVE' ? 'rgba(0, 255, 157, 0.1)' : 'rgba(255, 68, 68, 0.1)',
                                        color: member.status === 'ACTIVE' ? 'var(--primary)' : '#ff4444'
                                    }}>
                                        {member.status}
                                    </span>
                                </td>
                                <td style={{ padding: '1rem' }}>
                                    <button style={{ background: 'none', border: 'none', color: 'var(--primary)', cursor: 'pointer' }}>Edit</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Members;
