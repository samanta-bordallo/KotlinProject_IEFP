package com.example.teste6
// o import abaixo serve para importar a classe Scanner do pacote java.util que serve para ler a entrada do usuário
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`) //essa linha cria um objeto scanner para ler a entrada do usuário que serão as linhas abaixo, criando instâncias das classes
    val gerente = Gerente(1, "Ana", "123456789", 5000.0)
    val vendedor = Vendedor(1, "Pedro", "789456123", 3000.0)
    val cliente = Cliente(1, "Carlos", "456123789")
//abaixo adiciono veículos para abastecer o Stand de Carros
    val veiculo1 = Veiculo(1, "Toyota Corolla", 2020, 80000.0)
    val veiculo2 = Veiculo(2, "Honda Civic", 2019, 75000.0)
    val veiculo3 = Veiculo(3, "Ford Focus", 2018, 60000.0)
    val veiculo4 = Veiculo(4, "Volkswagen Golf", 2017, 55000.0)
    //abaixo adiciono os veículos ao estoque do gerente
    gerente.adicionarVeiculo(veiculo1)
    gerente.adicionarVeiculo(veiculo2)
    gerente.adicionarVeiculo(veiculo3)
    gerente.adicionarVeiculo(veiculo4)


    var escolha: Int //declaro uma variável chamada escolha que vai guardar a escolha do usuário no menu

    do { // implementação do do-while e vai se repetir enquanto a condição for verdadeira.
        println("\nEscolha uma opção:")
        println("1 - Listar carros")
        println("2 - Comprar carro")
        println("3 - Vender carro")
        println("4 - Adicionar carro")
        println("5 - Verificar salário do funcionário")
        println("6 - Atualizar valor do veículo")
        println("7 - Editar NIF de uma pessoa")
        println("0 - Sair")

        escolha = scanner.nextInt() //lê a escolha do usuário e com base nessa escolha, entra no numero correspondente abaixo

        when (escolha) {
            1 -> gerente.listarVeiculosEmEstoque()
            2 -> {
                println("\nVeículos disponíveis:")
                gerente.listarVeiculosEmEstoque()
                println("\nDigite o ID do veículo que deseja comprar:")
                val idVeiculo = scanner.nextInt()
                val veiculoSelecionado = gerente.buscarVeiculoPorId(idVeiculo)
                if (veiculoSelecionado != null) {
                    cliente.comprarVeiculo(veiculoSelecionado)
                } else {
                    println("Veículo não encontrado.")
                }
            }
            3 -> {
                println("\nDigite o ID do veículo que deseja vender:")
                val idVeiculo = scanner.nextInt()
                val veiculoSelecionado = gerente.buscarVeiculoPorId(idVeiculo)
                if (veiculoSelecionado != null) {
                    vendedor.vender(veiculoSelecionado, cliente)
                } else {
                    println("Veículo não encontrado.")
                }
            }
            4 -> {
                println("\nDigite os detalhes do novo veículo:")
                println("ID:")
                val id = scanner.nextInt()
                println("Modelo:")
                scanner.nextLine() // Limpa o buffer do scanner
                val modelo = scanner.nextLine()
                println("Ano:")
                val ano = scanner.nextInt()
                println("Preço:")
                val preco = scanner.nextDouble()
                val novoVeiculo = Veiculo(id, modelo, ano, preco)
                gerente.adicionarVeiculo(novoVeiculo)
            }
            5 -> {
                println("\nEscolha o funcionário:")
                println("1 - Gerente")
                println("2 - Vendedor")
                val opcaoFuncionario = scanner.nextInt()
                when (opcaoFuncionario) {
                    1 -> gerente.verificarSalario()
                    2 -> vendedor.verificarSalario()
                    else -> println("Opção inválida!")
                }
            }
            6 -> {
                println("\nDigite o ID do veículo que deseja atualizar o preço:")
                val idVeiculo = scanner.nextInt()
                println("Digite o novo preço:")
                val novoPreco = scanner.nextDouble()
                gerente.atualizarPrecoVeiculo(idVeiculo, novoPreco)
            }
            7 -> {
                println("\nDigite o tipo de pessoa que deseja editar o NIF:")
                println("1 - Cliente")
                println("2 - Vendedor")
                println("3 - Gerente")
                val tipoPessoa = scanner.nextInt()
                println("\nDigite o novo NIF:")
                val novoNIF = scanner.next()
                when (tipoPessoa) {
                    1 -> cliente.nif = novoNIF
                    2 -> vendedor.nif = novoNIF
                    3 -> gerente.nif = novoNIF
                    else -> println("Opção inválida!")
                }
                println("NIF atualizado com sucesso!")
            }
            0 -> println("Saindo...")
            else -> println("Opção inválida!")
        }
    } while (escolha != 0)

    scanner.close() //fecha o objeto scanner
}
