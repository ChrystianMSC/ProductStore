"use client";

import { useEffect, useState } from 'react';
import axios from 'axios';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
}

interface Sale {
  id: number;
  clientId: number
  productId: number;
  productName: string;
  quantity: number;
  saleDate: string;
}

const UserPanel = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [sales, setSales] = useState<Sale[]>([]);
  const [purchaseQuantities, setPurchaseQuantities] = useState<Record<number, number>>({})
  const [loading, setLoading] = useState(false);
  const [loadingProductId, setLoadingProductId] = useState<number | null>(null); // for per-product actions

  const clientId = parseInt(localStorage.getItem('user') || '0');

  useEffect(() => {
    axios.get<Product[]>('http://localhost:8081/api/products').then(res => setProducts(res.data));
  }, []);

  useEffect(() => {
    axios.get<Sale[]>('http://localhost:8082/api/sales')
      .then(res => {
        console.log('All Sales:', res.data); 
        const filteredSales = res.data.filter(sale => sale.clientId === clientId);
        setSales(filteredSales);
      })
      .catch(err => {
        console.error('Error fetching sales:', err);
      });
  }, []);


  const deleteProduct = async (id: number) => {
    setLoadingProductId(id);
    try {
      await axios.delete(`http://localhost:8081/api/products/${id}`);
      setProducts(products.filter(p => p.id !== id));
    } finally {
      setLoadingProductId(null);
    }
  };

  const handleBuy = async (productId: number, productName: string) => {
    const quantity = purchaseQuantities[productId] || 0;
    if (quantity <= 0) return alert("Enter a valid quantity.");
    
    const product = products.find(p => p.id === productId);
    if (!product || quantity > product.stock) return alert("Not enough stock.");

    const confirmBuy = window.confirm(`Are you sure you want to buy ${quantity} of "${product.name}"?`);
    if (!confirmBuy) return;

    setLoadingProductId(productId);
    try {
      const res = await axios.post('http://localhost:8082/api/sales', {
        clientId,
        productId,
        productName,
        quantity
      });
      const updatedProduct = {
        ...product,
        stock: product.stock - quantity
      };
      if (updatedProduct.stock <= 0) {
        deleteProduct(productId);
      }else{
        const resUpdate = await axios.put<Product>(
          `http://localhost:8081/api/products/${productId}`,
          updatedProduct
        );
        setProducts(products.map(p => p.id === productId ? resUpdate.data : p));
      }
      setSales([...sales, res.data]);
    } finally {
      setLoadingProductId(null);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-black via-gray-900 to-gray-800 text-white p-10 font-sans flex flex-col md:flex-row gap-6">
      <div className="w-full md:w-2/3 space-y-10">
        {/* Product List */}
        <section className="bg-gray-900/60 p-6 rounded-2xl shadow-lg backdrop-blur-sm">
          <h2 className="text-2xl font-semibold mb-4 tracking-wide">Product List</h2>
          <ul className="space-y-4">
            {products.map(p => (
              <li
                key={p.id}
                className="flex flex-col md:flex-row md:items-center justify-between bg-gray-800 px-4 py-3 rounded-lg shadow-md hover:bg-gray-700 transition-colors"
              >
                <div>
                  <div className="font-medium">{p.name}</div>
                  <div className="text-sm text-gray-400">€{p.price} — Stock: {p.stock}</div>
                  <div className="text-sm mt-1">{p.description}</div>
                  <div className="flex gap-2 mt-2">
                    <input
                      type="number"
                      min={1}
                      max={p.stock}
                      placeholder="Qty"
                      value={purchaseQuantities[p.id] || ''}
                      className="w-20 px-2 py-1 rounded bg-gray-700 text-white"
                      onChange={e => setPurchaseQuantities({ ...purchaseQuantities, [p.id]: +e.target.value })}
                    />
                    <button
                      className="bg-green-600 px-4 py-1 rounded hover:bg-green-700 disabled:opacity-50"
                      onClick={() => handleBuy(p.id, p.name)}
                      disabled={loadingProductId === p.id}
                    >
                      {loadingProductId === p.id ? 'Buying...' : 'Buy'}
                    </button>
                  </div>
                </div>
                <div className="mt-2 md:mt-0 flex gap-4">
                </div>
              </li>
            ))}
          </ul>
        </section>
      </div>

      <div className="w-full md:w-1/3 bg-gray-900/60 p-6 rounded-2xl shadow-lg backdrop-blur-sm">
        <h2 className="text-xl font-semibold mb-4">Your Purchases</h2>
        <ul className="space-y-2 text-sm text-gray-300">
          {sales.map(sale => {
            const date = new Date(sale.saleDate);
            const formattedDate = date.toLocaleDateString("en-US", {
              year: "numeric",
              month: "long",
              day: "numeric",
              hour: "2-digit",
              minute: "2-digit",
            });

            return (
              <li key={sale.id} className="bg-gray-800 p-3 rounded-lg shadow-sm">
                {sale.productName} — {sale.quantity} pcs — {formattedDate}
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

export default UserPanel;
