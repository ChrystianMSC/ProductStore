'use client';

import { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';

import { useAuth } from './context/AuthContext';

interface LoginForm {
  username: string;
  password: string;
}

const Login = () => {
  const [form, setForm] = useState<LoginForm>({ username: '', password: '' });
  const { setUser } = useAuth();
  const router = useRouter();

  const handleLogin = async () => {
    try {
      const res = await axios.post('http://localhost:8083/api/clients/login', form);
      const token = res.data.token;
      const id = res.data.id;
      localStorage.setItem('token', token);
      localStorage.setItem('user', id);

      console.log(res.data);

      setUser(res.data);
      router.push('/dashboard');
    } catch (error) {
      alert('Login failed. Please check your credentials.');
    }
  };

  const handleSignup = async () => {
    try {
      router.push('/register');
    } catch (error) {
      alert(error);
    }
  };

  return (
    <div className="h-screen bg-[radial-gradient(circle_at_top_left,_#3b3b3f,_#1a1a1d)] flex justify-center items-center font-['Roboto']">
      <div className="bg-black/90 rounded-2xl p-12 shadow-[0_0_30px_rgba(255,215,165,0.3),_0_0_15px_rgba(255,215,165,0.15)] min-w-[320px] max-w-[400px] flex flex-col gap-6 text-gray-100">
        <h1 className="font-bold text-3xl tracking-wide mb-4 text-center text-[#ffd186]">
          Welcome Back
        </h1>
        <input
          className="bg-[#1f1f23] border-none rounded-xl p-4 text-base text-gray-300 focus:outline-none focus:ring-2 focus:ring-[#ffd186] transition duration-300"
          placeholder="Username"
          value={form.username}
          onChange={e => setForm({ ...form, username: e.target.value })}
          autoFocus
        />
        <input
          className="bg-[#1f1f23] border-none rounded-xl p-4 text-base text-gray-300 focus:outline-none focus:ring-2 focus:ring-[#ffd186] transition duration-300"
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
          onKeyDown={e => e.key === 'Enter' && handleLogin()}
        />
        <button
          className="bg-[#ffd186] rounded-xl p-4 font-semibold text-lg text-[#1a1a1d] hover:bg-[#f5c670] transition duration-300"
          onClick={handleLogin}
        >
          Log In
        </button>
        <button
          className="bg-[#404040] rounded-xl p-4 font-semibold text-lg text-[#ffd186] hover:bg-[#505050] transition duration-300"
          onClick={handleSignup}
        >
          Signup
        </button>
      </div>
    </div>
  );
};

export default Login;
