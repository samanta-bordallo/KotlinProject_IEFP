package com.example.app1

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvMainHeader: TextView
    private lateinit var etMainNome: EditText
    private lateinit var etMainSexo: EditText
    private lateinit var tvMainMsg: TextView
    private lateinit var btnMainSubmit: Button
    private lateinit var menuContainer: LinearLayout

    private val gerente = Gerente(1, "Ana", "123456789", 5000.0)
    private val vendedor = Vendedor(1, "Pedro", "789456123", 3000.0)
    private val cliente = Cliente(1, "Carlos", "456123789")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMainHeader = findViewById(R.id.tvMainHeader)
        btnMainSubmit = findViewById(R.id.btnMainSubmit)
        tvMainMsg = findViewById(R.id.tvMainMsg)
        etMainNome = findViewById(R.id.etMainNome)
        etMainSexo = findViewById(R.id.etMainSexo)
        menuContainer = findViewById(R.id.menuContainer)

        tvMainHeader.text = "Stand de Carros"
        btnMainSubmit.setOnClickListener {
            var nome = "Anonim"
            if (etMainNome.text.isNotEmpty()) {
                nome = etMainNome.text.toString()
            }

            val sexo = etMainSexo.text.toString()
            tvMainMsg.text = when (sexo.lowercase()) {
                "m" -> "Olá, $nome, seja bem vindo"
                "f" -> "Olá, $nome, seja bem vinda"
                else -> "Olá, $nome, saudações!"
            }

            hideInputs()
            hideSoftKeyboard()
            showMenu()
        }

        // Adiciona veículos ao estoque
        val veiculo1 = Veiculo(1, "Toyota Corolla", 2020, 80000.0)
        val veiculo2 = Veiculo(2, "Honda Civic", 2019, 75000.0)
        val veiculo3 = Veiculo(3, "Ford Focus", 2018, 60000.0)
        val veiculo4 = Veiculo(4, "Volkswagen Golf", 2017, 55000.0)

        gerente.adicionarVeiculo(veiculo1)
        gerente.adicionarVeiculo(veiculo2)
        gerente.adicionarVeiculo(veiculo3)
        gerente.adicionarVeiculo(veiculo4)
    }

    private fun hideInputs() {
        etMainNome.visibility = View.GONE
        etMainSexo.visibility = View.GONE
        btnMainSubmit.visibility = View.GONE
    }

    private fun showMenu() {
        menuContainer.visibility = View.VISIBLE
        menuContainer.removeAllViews()

        val options = arrayOf(
            "Listar carros",
            "Comprar carro",
            "Vender carro",
            "Adicionar carro",
            "Verificar salário do funcionário",
            "Atualizar valor do veículo",
            "Editar NIF de uma pessoa",
            "Sair"
        )

        for (option in options) {
            val button = Button(this).apply {
                text = option
                setOnClickListener { handleMenuOption(option) }
            }
            menuContainer.addView(button)
        }
    }

    private fun handleMenuOption(option: String) {
        when (option) {
            "Listar carros" -> listarCarros()
            "Comprar carro" -> comprarCarro()
            "Vender carro" -> venderCarro()
            "Adicionar carro" -> adicionarCarro()
            "Verificar salário do funcionário" -> verificarSalario()
            "Atualizar valor do veículo" -> atualizarValorVeiculo()
            "Editar NIF de uma pessoa" -> editarNif()
            "Sair" -> finish()
            else -> tvMainMsg.text = "Opção inválida!"
        }
    }

    private fun hideSoftKeyboard() {
        etMainNome.clearFocus()
        etMainSexo.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listarCarros() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Veículos em Estoque")
        val veiculos = gerente.veiculosEmEstoque.joinToString("\n") { "${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €" }
        builder.setMessage(veiculos)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    private fun comprarCarro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Comprar Carro")
        val veiculos = gerente.veiculosEmEstoque.joinToString("\n") { "${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €" }
        builder.setMessage("Veículos disponíveis:\n$veiculos\n\nDigite o ID do veículo que deseja comprar:")

        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("Comprar") { _, _ ->
            val idVeiculo = input.text.toString().toIntOrNull()
            if (idVeiculo != null) {
                val veiculoSelecionado = gerente.buscarVeiculoPorId(idVeiculo)
                if (veiculoSelecionado != null) {
                    cliente.comprarVeiculo(veiculoSelecionado)
                    gerente.veiculosEmEstoque.remove(veiculoSelecionado)
                    tvMainMsg.text = "Veículo ${veiculoSelecionado.modelo} comprado por ${cliente.nome}."
                } else {
                    tvMainMsg.text = "Veículo não encontrado."
                }
            } else {
                tvMainMsg.text = "ID inválido."
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun venderCarro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vender Carro")
        val input = EditText(this)
        builder.setView(input)
        builder.setMessage("Digite o ID do veículo que deseja vender:")
        builder.setPositiveButton("Vender") { _, _ ->
            val idVeiculo = input.text.toString().toIntOrNull()
            if (idVeiculo != null) {
                val veiculoSelecionado = gerente.buscarVeiculoPorId(idVeiculo)
                if (veiculoSelecionado != null) {
                    vendedor.vender(veiculoSelecionado, cliente)
                    gerente.veiculosEmEstoque.remove(veiculoSelecionado)
                    tvMainMsg.text = "Veículo ${veiculoSelecionado.modelo} vendido por ${vendedor.nome} para ${cliente.nome}."
                } else {
                    tvMainMsg.text = "Veículo não encontrado."
                }
            } else {
                tvMainMsg.text = "ID inválido."
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
    private fun adicionarCarro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Adicionar Carro")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val idInput = EditText(this).apply { hint = "ID" }
        val modeloInput = EditText(this).apply { hint = "Modelo" }
        val anoInput = EditText(this).apply { hint = "Ano" }
        val precoInput = EditText(this).apply { hint = "Preço" }

        layout.addView(idInput)
        layout.addView(modeloInput)
        layout.addView(anoInput)
        layout.addView(precoInput)

        builder.setView(layout)

        builder.setPositiveButton("Adicionar") { _, _ ->
            val id = idInput.text.toString().toIntOrNull()
            val modelo = modeloInput.text.toString()
            val ano = anoInput.text.toString().toIntOrNull()
            val preco = precoInput.text.toString().toDoubleOrNull()

            if (id != null && ano != null && preco != null) {
                val novoVeiculo = Veiculo(id, modelo, ano, preco)
                gerente.adicionarVeiculo(novoVeiculo)
                tvMainMsg.text = "Veículo $modelo adicionado ao estoque."
            } else {
                tvMainMsg.text = "Detalhes do veículo inválidos."
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun verificarSalario() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Verificar Salário")

        val funcionarios = arrayOf("Gerente", "Vendedor")
        builder.setItems(funcionarios) { _, which ->
            val salario = when (which) {
                0 -> gerente.salario
                1 -> vendedor.salario
                else -> 0.0
            }
            tvMainMsg.text = "Salário do ${funcionarios[which]}: $salario €"
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun atualizarValorVeiculo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atualizar Valor do Veículo")
        val input = EditText(this)
        builder.setView(input)
        builder.setMessage("Digite o ID do veículo que deseja atualizar o preço:")

        builder.setPositiveButton("Atualizar") { _, _ ->
            val idVeiculo = input.text.toString().toIntOrNull()
            if (idVeiculo != null) {
                val veiculo = gerente.buscarVeiculoPorId(idVeiculo)
                if (veiculo != null) {
                    val novoPrecoDialog = AlertDialog.Builder(this)
                    novoPrecoDialog.setTitle("Novo Preço")
                    val novoPrecoInput = EditText(this)
                    novoPrecoInput.hint = "Novo preço"
                    novoPrecoDialog.setView(novoPrecoInput)

                    novoPrecoDialog.setPositiveButton("Confirmar") { _, _ ->
                        val novoPreco = novoPrecoInput.text.toString().toDoubleOrNull()
                        if (novoPreco != null) {
                            veiculo.preco = novoPreco
                            tvMainMsg.text = "Preço do veículo ${veiculo.modelo} atualizado para $novoPreco €"
                        } else {
                            tvMainMsg.text = "Preço inválido."
                        }
                    }
                    novoPrecoDialog.setNegativeButton("Cancelar", null)
                    novoPrecoDialog.show()
                } else {
                    tvMainMsg.text = "Veículo não encontrado."
                }
            } else {
                tvMainMsg.text = "ID inválido."
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun editarNif() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar NIF de uma Pessoa")

        val pessoas = arrayOf("Cliente", "Vendedor", "Gerente")
        builder.setItems(pessoas) { _, which ->
            val tipoPessoa = when (which) {
                0 -> cliente
                1 -> vendedor
                2 -> gerente
                else -> null
            }
            if (tipoPessoa != null) {
                val novoNifDialog = AlertDialog.Builder(this)
                novoNifDialog.setTitle("Novo NIF")
                val novoNifInput = EditText(this)
                novoNifInput.hint = "Novo NIF"
                novoNifDialog.setView(novoNifInput)

                novoNifDialog.setPositiveButton("Confirmar") { _, _ ->
                    val novoNif = novoNifInput.text.toString()
                    tipoPessoa.setNIF(novoNif)
                    tvMainMsg.text = "NIF de ${tipoPessoa.nome} atualizado para $novoNif"
                }
                novoNifDialog.setNegativeButton("Cancelar", null)
                novoNifDialog.show()
            } else {
                tvMainMsg.text = "Opção inválida."
            }
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
}