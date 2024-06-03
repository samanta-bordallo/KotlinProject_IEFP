package com.example.app1

open class Pessoa(
    val id: Int,
    val nome: String,
    internal var nif: String) {
    // Getter para NIF
    fun getNIF(): String {
        return nif
    }

    // Setter para NIF
    fun setNIF(novoNIF: String) {
        nif = novoNIF
    }
}

// Vendedor.kt
class Vendedor(id: Int, nome: String, nif: String, var salario: Double) : Pessoa(id, nome, nif) {
    fun vender(veiculo: Veiculo, cliente: Cliente) {
        println("Vendedor $nome vendeu o veículo ${veiculo.modelo} para o cliente ${cliente.nome}")
    }

    // Método para verificar o salário do vendedor
    fun verificarSalario() {
        println("Salário do vendedor $nome: $salario €")
    }
}

// Cliente.kt
class Cliente(id: Int, nome: String, nif: String) : Pessoa(id, nome, nif) {
    fun comprarVeiculo(veiculo: Veiculo) {
        println("Cliente $nome comprou o veículo ${veiculo.modelo}")
    }
}

// Gerente.kt
class Gerente(id: Int, nome: String, nif: String, var salario: Double) : Pessoa(id, nome, nif) {
    val veiculosEmEstoque = mutableListOf<Veiculo>()

    fun adicionarVeiculo(veiculo: Veiculo) {
        veiculosEmEstoque.add(veiculo)
        println("Gerente $nome adicionou o veículo ${veiculo.modelo} ao estoque.")
    }

    fun listarVeiculosEmEstoque() {
        println("Veículos em estoque:")
        veiculosEmEstoque.forEach { println("${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €") }
    }

    fun buscarVeiculoPorId(id: Int): Veiculo? {
        return veiculosEmEstoque.find { it.id == id }
    }

    // Método para verificar o salário do gerente
    fun verificarSalario() {
        println("Salário do gerente $nome: $salario €")
    }

    // Método para atualizar o preço do veículo
    fun atualizarPrecoVeiculo(veiculoId: Int, novoPreco: Double) {
        val veiculo = buscarVeiculoPorId(veiculoId)
        if (veiculo != null) {
            veiculo.preco = novoPreco
            println("Preço do veículo ${veiculo.modelo} atualizado para $novoPreco €")
        } else {
            println("Veículo não encontrado.")
        }
    }
}
// Veiculo.kt
open class Veiculo(val id: Int, val modelo: String, val ano: Int, preco: Double) {
    // Propriedade preco com acesso interno (default)
    var preco: Double = preco
        // Getter para preco
        get() = field
        // Setter para preco
        set(value) {
            field = value
        }
}
