"use client";

import { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';

const Register = () => {
  const [form, setForm] = useState({ username: '', password: '' });
  const router = useRouter();

  const handleRegister = async () => {
    await axios.post('http://localhost:8083/api/clients/register', form);
    router.push('/');
  };

  return (
    <div className="h-screen bg-[radial-gradient(circle_at_top_left,_#3b3b3f,_#1a1a1d)] flex justify-center items-center font-['Roboto']">
      <div className="bg-black/90 rounded-2xl p-12 shadow-[0_0_30px_rgba(255,215,165,0.3),_0_0_15px_rgba(255,215,165,0.15)] min-w-[320px] max-w-[400px] flex flex-col gap-6 text-gray-100">
        <h1 className="font-bold text-3xl tracking-wide mb-4 text-center text-[#ffd186]">
          Register now
        </h1>
        <input
          className="bg-[#1f1f23] border-none rounded-xl p-4 text-base text-gray-300 focus:outline-none focus:ring-2 focus:ring-[#ff6900] transition duration-300"
          placeholder="Username"
          value={form.username}
          onChange={e => setForm({ ...form, username: e.target.value })}
          autoFocus
        />
        <input
          className="bg-[#1f1f23] border-none rounded-xl p-4 text-base text-gray-300 focus:outline-none focus:ring-2 focus:ring-[#ff6900] transition duration-300"
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
          onKeyDown={e => e.key === 'Enter' && handleRegister()}
        />
        <button
          className="bg-[#ff6900] rounded-xl p-4 font-semibold text-lg text-[#1a1a1d] hover:bg-[#f5c670] transition duration-300"
          onClick={handleRegister}
        >
          Signup
        </button>
      </div>
    </div>
  );
};

export default Register;
