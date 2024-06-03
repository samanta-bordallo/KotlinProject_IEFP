const express = require('express');
const app = express();
const db = require('./database.js');

app.use(express.json());

// Endpoint para listar veículos
app.get('/veiculos', (req, res) => {
    db.query('SELECT * FROM veiculos', (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json(results);
        }
    });
});

// Endpoint para adicionar veículo
app.post('/veiculos', (req, res) => {
    const { id, modelo, ano, preco } = req.body;
    const query = 'INSERT INTO veiculos (id, modelo, ano, preco) VALUES (?, ?, ?, ?)';
    db.query(query, [id, modelo, ano, preco], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json({ message: 'Veículo adicionado com sucesso!' });
        }
    });
});

// Endpoint para comprar veículo
app.post('/comprar', (req, res) => {
    const { idVeiculo } = req.body;
    const query = 'DELETE FROM veiculos WHERE id = ?';
    db.query(query, [idVeiculo], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (results.affectedRows === 0) {
            res.status(404).json({ message: 'Veículo não encontrado.' });
        } else {
            res.json({ message: 'Veículo comprado com sucesso!' });
        }
    });
});

// Endpoint para vender veículo
app.post('/vender', (req, res) => {
    const { id, modelo, ano, preco } = req.body;
    const query = 'INSERT INTO veiculos (id, modelo, ano, preco) VALUES (?, ?, ?, ?)';
    db.query(query, [id, modelo, ano, preco], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else {
            res.json({ message: 'Veículo vendido com sucesso!' });
        }
    });
});

// Endpoint para atualizar preço do veículo
app.put('/veiculos/:id', (req, res) => {
    const { id } = req.params;
    const { preco } = req.body;
    const query = 'UPDATE veiculos SET preco = ? WHERE id = ?';
    db.query(query, [preco, id], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (results.affectedRows === 0) {
            res.status(404).json({ message: 'Veículo não encontrado.' });
        } else {
            res.json({ message: 'Preço do veículo atualizado com sucesso!' });
        }
    });
});

// Endpoint para editar NIF de uma pessoa
app.put('/pessoas/:tipo', (req, res) => {
    const { tipo } = req.params;
    const { nif, id } = req.body; // Adicionamos 'id' aqui para especificar qual pessoa estamos editando
    let query;
    if (tipo === 'cliente') {
        query = 'UPDATE cliente SET nif = ? WHERE id = ?';
    } else if (tipo === 'vendedor') {
        query = 'UPDATE vendedor SET nif = ? WHERE id = ?';
    } else if (tipo === 'gerente') {
        query = 'UPDATE gerente SET nif = ? WHERE id = ?';
    } else {
        return res.status(400).json({ message: 'Tipo inválido.' });
    }
    db.query(query, [nif, id], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (results.affectedRows === 0) {
            res.status(404).json({ message: 'Pessoa não encontrada.' });
        } else {
            res.json({ message: 'NIF atualizado com sucesso!' });
        }
    });
});

// Endpoint inicial
app.get('/', (req, res) => {
    res.send('Bem-vindo à minha API!');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor rodando na porta ${PORT}`);
});
