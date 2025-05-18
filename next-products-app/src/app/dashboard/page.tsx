"use client";

import { useAuth } from '../context/AuthContext';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import AdminPanel from '../components/AdminPanel';
import UserPanel from '../components/UserPanel';

const Dashboard = () => {
  const { user, setUser } = useAuth();
  const [ userId , setUserId ] = useState<any>(''); 
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem('token');
    setUserId(localStorage.getItem('user'));
    if (!token) {
      router.push('/');
    }
  }, []);

  const handleLogoff = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    router.push('/');
  };

  return (
    <div>
      <button onClick={handleLogoff}>Log Off</button>
      {userId === '1' ? <AdminPanel /> : <UserPanel />}
    </div>
  );
};

export default Dashboard;
