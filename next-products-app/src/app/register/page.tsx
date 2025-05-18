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
    <div style={styles.container}>
      <div style={styles.loginBox}>
        <h1 style={styles.title}>Register now</h1>
        <input
          style={styles.input}
          placeholder="Username"
          value={form.username}
          onChange={e => setForm({ ...form, username: e.target.value })}
          autoFocus
        />
        <input
          style={styles.input}
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
          onKeyDown={e => e.key === 'Enter' && handleRegister()}
        />
        <button style={styles.button} onClick={handleRegister}>
          Signup
        </button>
      </div>
    </div>
  );
};


const styles: { [key: string]: React.CSSProperties } = {
  container: {
    height: '100vh',
    background:
      'radial-gradient(circle at top left, #3b3b3f, #1a1a1d)', // dark gradient similar to image
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    fontFamily: "'Roboto', sans-serif",
  },
  loginBox: {
    backgroundColor: 'rgba(20, 20, 20, 0.9)',
    borderRadius: '16px',
    padding: '3rem 4rem',
    boxShadow:
      '0 0 30px rgba(255, 215, 165, 0.3), 0 0 15px rgba(255, 215, 165, 0.15)', // subtle gold glow like red gold from image
    minWidth: '320px',
    maxWidth: '400px',
    display: 'flex',
    flexDirection: 'column',
    gap: '1.5rem',
    color: '#eee',
  },
  title: {
    fontWeight: '700',
    fontSize: '2rem',
    letterSpacing: '1.2px',
    marginBottom: '1rem',
    color: '#ffd186', // gold highlight color
    textAlign: 'center',
  },
  input: {
    backgroundColor: '#1f1f23',
    border: 'none',
    borderRadius: '10px',
    padding: '1rem',
    fontSize: '1rem',
    color: '#ddd',
    outlineColor: '#ffd186',
    transition: 'outline-color 0.3s ease',
  },
  button: {
    backgroundColor: '#ffd186',
    border: 'none',
    borderRadius: '12px',
    padding: '1rem',
    fontWeight: '600',
    fontSize: '1.1rem',
    color: '#1a1a1d',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease',
  },
};


export default Register;
