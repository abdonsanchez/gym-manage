import axios from 'axios';

// Create axios instance
const api = axios.create({
    baseURL: '/api', // Proxied to http://api-gateway:8080 in dev
    headers: {
        'Content-Type': 'application/json',
    },
});

// Response interceptor for error handling
api.interceptors.response.use(
    (response) => response,
    (error) => {
        console.error('API Error:', error.response ? error.response.data : error.message);
        return Promise.reject(error);
    }
);

export const UserService = {
    getAllMembers: () => api.get('/users/members'),
    getMemberById: (id) => api.get(`/users/members/${id}`),
    createMember: (data) => api.post('/users/members', data),
};

export const AnalyticsService = {
    getAttendance: (start, end) => api.get(`/analytics/attendance`, { params: { start, end } }),
};

export default api;
