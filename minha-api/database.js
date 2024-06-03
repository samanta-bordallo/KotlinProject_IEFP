const mysql = require('mysql');

// Configurações de conexão com o banco de dados
const connection = mysql.createConnection({
  host: 'localhost',     // Endereço do servidor MySQL
  user: 'root',          // Nome de usuário do banco de dados
  password: '', // Senha do banco de dados
  database: 'stand_carros' // Nome do banco de dados
});

// Estabelece a conexão com o banco de dados
connection.connect((err) => {
  if (err) {
    console.error('Erro ao conectar ao banco de dados:', err);
    return;
  }
  console.log('Conexão bem-sucedida ao banco de dados!');
});

// Exporta a conexão para ser utilizada em outros módulos
module.exports = connection;
