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
  productId: number;
  quantity: number;
}

const AdminPanel = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [form, setForm] = useState<Omit<Product, 'id'>>({ name: '', description: '', price: 0, stock: 0 });
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [purchaseQuantities, setPurchaseQuantities] = useState<Record<number, number>>({});
  const [purchasedProducts, setPurchasedProducts] = useState<Sale[]>([]);
  const [loading, setLoading] = useState(false);
  const [loadingProductId, setLoadingProductId] = useState<number | null>(null); // for per-product actions


  const clientId = parseInt(localStorage.getItem('user') || '0');

  useEffect(() => {
    axios.get<Product[]>('http://localhost:8081/api/products').then(res => setProducts(res.data));
  }, []);

  const startEditing = (product: Product) => {
    setEditingProduct(product);
    setForm({ name: product.name, description: product.description, price: product.price, stock: product.stock });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const cancelEditing = () => {
    setEditingProduct(null);
    setForm({ name: '', description: '', price: 0, stock: 0 });
  };

  const updateProduct = async () => {
    if (!editingProduct) return;
    setLoading(true);
    try {
      const res = await axios.put<Product>(`http://localhost:8081/api/products/${editingProduct.id}`, form);
      setProducts(products.map(p => (p.id === editingProduct.id ? res.data : p)));
      cancelEditing();
    } finally {
      setLoading(false);
    }
  };

  const createProduct = async () => {
    setLoading(true);
    try {
      const res = await axios.post<Product>('http://localhost:8081/api/products', form);
      setProducts([...products, res.data]);
      setForm({ name: '', description: '', price: 0, stock: 0 });
    } finally {
      setLoading(false);
    }
  };

  const deleteProduct = async (id: number) => {
    setLoadingProductId(id);
    try {
      await axios.delete(`http://localhost:8081/api/products/${id}`);
      setProducts(products.filter(p => p.id !== id));
    } finally {
      setLoadingProductId(null);
    }
  };


  const handleBuy = async (productId: number) => {
    const quantity = purchaseQuantities[productId] || 0;
    if (quantity <= 0) return alert("Enter a valid quantity.");

    const product = products.find(p => p.id === productId);
    if (!product || quantity > product.stock) return alert("Not enough stock.");

    const confirmBuy = window.confirm(`Are you sure you want to buy ${quantity} of "${product.name}"?`);
    if (!confirmBuy) return;

    setLoadingProductId(productId);
    try {
      await axios.post('http://localhost:8082/api/sales', {
        clientId,
        productId,
        quantity
      });

      const updatedProduct = {
        ...product,
        stock: product.stock - quantity
      };

      const res = await axios.put<Product>(
        `http://localhost:8081/api/products/${productId}`,
        updatedProduct
      );

      setProducts(products.map(p => p.id === productId ? res.data : p));
      setPurchasedProducts([...purchasedProducts, { productId, quantity }]);
    } finally {
      setLoadingProductId(null);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-black via-gray-900 to-gray-800 text-white p-10 font-sans flex flex-col md:flex-row gap-6">
      <div className="w-full md:w-2/3 space-y-10">
        {/* Form Section */}
        <section className="bg-gray-900/60 p-6 rounded-2xl shadow-lg backdrop-blur-sm">
          <h2 className="text-2xl font-semibold mb-6 tracking-wide">
            {editingProduct ? 'Edit Product' : 'Create Product'}
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <input
              placeholder="Name"
              value={form.name}
              className="bg-gray-800 text-white placeholder-gray-400 px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-400"
              onChange={e => setForm({ ...form, name: e.target.value })}
            />
            <input
              placeholder="Price"
              type="number"
              value={form.price}
              className="bg-gray-800 text-white placeholder-gray-400 px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-400"
              onChange={e => setForm({ ...form, price: +e.target.value })}
            />
            <input
              placeholder="Stock"
              type="number"
              value={form.stock}
              className="bg-gray-800 text-white placeholder-gray-400 px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-400"
              onChange={e => setForm({ ...form, stock: +e.target.value })}
            />
          </div>
          <input
            placeholder="Description"
            value={form.description}
            className="w-full mt-4 mb-4 bg-gray-800 text-white placeholder-gray-400 px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-orange-400"
            onChange={e => setForm({ ...form, description: e.target.value })}
          />
          <div className="mt-4 flex gap-4">
            <button
              onClick={editingProduct ? updateProduct : createProduct}
              className="bg-orange-500 hover:bg-orange-600 transition-colors px-6 py-2 rounded-xl text-white font-medium disabled:opacity-50"
              disabled={loading}
            >
              {loading ? 'Processing...' : editingProduct ? 'Update Product' : 'Add Product'}
            </button>

            {editingProduct && (
              <button
                onClick={cancelEditing}
                className="bg-gray-500 hover:bg-gray-600 transition-colors px-6 py-2 rounded-xl text-white font-medium"
              >
                Cancel
              </button>
            )}
          </div>
        </section>

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
                      onClick={() => handleBuy(p.id)}
                      disabled={loadingProductId === p.id}
                    >
                      {loadingProductId === p.id ? 'Buying...' : 'Buy'}
                    </button>
                  </div>
                </div>
                <div className="mt-2 md:mt-0 flex gap-4">
                  <button
                    onClick={() => deleteProduct(p.id)}
                    disabled={loadingProductId === p.id}
                    className={`font-medium ${loadingProductId === p.id ? 'text-gray-400' : 'text-red-400 hover:text-red-600'}`}
                  >
                    {loadingProductId === p.id ? 'Deleting...' : 'Delete'}
                  </button>
                  <button
                    onClick={() => startEditing(p)}
                    className="text-blue-400 hover:text-blue-600 font-medium"
                  >
                    Edit
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </section>
      </div>

      <div className="w-full md:w-1/3 bg-gray-900/60 p-6 rounded-2xl shadow-lg backdrop-blur-sm">
        <h2 className="text-xl font-semibold mb-4">Your Purchases</h2>
        <ul className="space-y-2 text-sm text-gray-300">
          {purchasedProducts.map((purchase, i) => {
            const product = products.find(p => p.id === purchase.productId);
            return product ? (
              <li key={i} className="bg-gray-800 p-3 rounded-lg shadow-sm">
                {product.name} — {purchase.quantity} pcs
              </li>
            ) : null;
          })}
        </ul>
      </div>
    </div>
  );
};

export default AdminPanel;
