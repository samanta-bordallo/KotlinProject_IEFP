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
app.delete('/comprar/:idVeiculo', (req, res) => {
    const idVeiculo = req.params.idVeiculo;
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
app.delete('/vender/:idVeiculo', (req, res) => {
    const idVeiculo = req.params.idVeiculo;
    const query = 'DELETE FROM veiculos WHERE id = ?';
    db.query(query, [idVeiculo], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (results.affectedRows === 0) {
            res.status(404).json({ message: 'Veículo não encontrado.' });
        } else {
            res.json({ message: 'Veículo vendido com sucesso!' });
        }
    });
});

// Endpoint para atualizar preço do veículo
app.put('/veiculos/:idVeiculo', (req, res) => {
    const idVeiculo = req.params.idVeiculo;
    const { preco } = req.body;
    const query = 'UPDATE veiculos SET preco = ? WHERE id = ?';
    db.query(query, [preco, idVeiculo], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
        } else if (results.affectedRows === 0) {
            res.status(404).json({ message: 'Veículo não encontrado.' });
        } else {
            res.json({ message: 'Preço do veículo atualizado com sucesso!' });
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