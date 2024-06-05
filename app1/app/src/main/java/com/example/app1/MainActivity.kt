package com.example.app1

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private val apiService by lazy { RetrofitClient.instance.create(ApiService::class.java) }

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
        apiService.getVeiculos().enqueue(object : Callback<List<Veiculo>> {
            override fun onResponse(call: Call<List<Veiculo>>, response: Response<List<Veiculo>>) {
                val veiculos = response.body()
                if (veiculos != null) {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Veículos em Estoque")
                    val veiculosList = veiculos.joinToString("\n") { "${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €" }
                    builder.setMessage(veiculosList)
                    builder.setPositiveButton("OK", null)
                    builder.show()
                } else {
                    tvMainMsg.text = "Erro ao carregar veículos."
                }
            }

            override fun onFailure(call: Call<List<Veiculo>>, t: Throwable) {
                tvMainMsg.text = "Erro ao carregar veículos: ${t.message}"
            }
        })
    }

    private fun comprarCarro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Comprar Carro")
        apiService.getVeiculos().enqueue(object : Callback<List<Veiculo>> {
            override fun onResponse(call: Call<List<Veiculo>>, response: Response<List<Veiculo>>) {
                val veiculos = response.body()
                if (veiculos != null) {
                    val veiculosList = veiculos.joinToString("\n") { "${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €" }
                    builder.setMessage("Veículos disponíveis:\n$veiculosList\n\nDigite o ID do veículo que deseja comprar:")
                }

                val input = EditText(this@MainActivity)
                builder.setView(input)
                builder.setPositiveButton("Comprar") { _, _ ->
                    val idVeiculo = input.text.toString().toIntOrNull()
                    if (idVeiculo != null) {
                        comprarCarro(idVeiculo)
                    } else {
                        tvMainMsg.text = "ID inválido."
                    }
                }
                builder.setNegativeButton("Cancelar", null)
                builder.show()
            }

            override fun onFailure(call: Call<List<Veiculo>>, t: Throwable) {
                tvMainMsg.text = "Erro ao carregar veículos: ${t.message}"
            }
        })
    }

    private fun comprarCarro(idVeiculo: Int) {
        apiService.comprarVeiculo(idVeiculo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    tvMainMsg.text = "Veículo comprado com sucesso!"
                } else {
                    tvMainMsg.text = "Erro ao comprar veículo: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                tvMainMsg.text = "Erro ao comprar veículo: ${t.message}"
            }
        })
    }

    private fun venderCarro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vender Carro")
        apiService.getVeiculos().enqueue(object : Callback<List<Veiculo>> {
            override fun onResponse(call: Call<List<Veiculo>>, response: Response<List<Veiculo>>) {
                val veiculos = response.body()
                if (veiculos != null) {
                    val veiculosList = veiculos.joinToString("\n") { "${it.id} - ${it.modelo} - Ano: ${it.ano}, Preço: ${it.preco} €" }
                    builder.setMessage("Veículos disponíveis:\n$veiculosList\n\nDigite o ID do veículo que deseja vender:")
                }

                val input = EditText(this@MainActivity)
                builder.setView(input)
                builder.setPositiveButton("Vender") { _, _ ->
                    val idVeiculo = input.text.toString().toIntOrNull()
                    if (idVeiculo != null) {
                        venderCarro(idVeiculo)
                    } else {
                        tvMainMsg.text = "ID inválido."
                    }
                }
                builder.setNegativeButton("Cancelar", null)
                builder.show()
            }

            override fun onFailure(call: Call<List<Veiculo>>, t: Throwable) {
                tvMainMsg.text = "Erro ao carregar veículos: ${t.message}"
            }
        })
    }

    private fun venderCarro(idVeiculo: Int) {
        apiService.comprarVeiculo(idVeiculo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    tvMainMsg.text = "Veículo vendido com sucesso!"
                } else {
                    tvMainMsg.text = "Erro ao vender veículo: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                tvMainMsg.text = "Erro ao vender veículo: ${t.message}"
            }
        })
    }
    private fun atualizarValorVeiculo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atualizar Valor do Veículo")
        val inputId = EditText(this)
        val inputPreco = EditText(this)
        inputId.hint = "ID do veículo"
        inputPreco.hint = "Novo preço"
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(inputId)
        layout.addView(inputPreco)
        builder.setView(layout)
        builder.setPositiveButton("Atualizar") { _, _ ->
            val idVeiculo = inputId.text.toString().toIntOrNull()
            val novoPreco = inputPreco.text.toString().toDoubleOrNull()
            if (idVeiculo != null && novoPreco != null) {
                atualizarValorVeiculo(idVeiculo, novoPreco)
            } else {
                tvMainMsg.text = "ID ou preço inválido."
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun atualizarValorVeiculo(idVeiculo: Int, novoPreco: Double) {
        apiService.atualizarPrecoVeiculo(idVeiculo, novoPreco).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    tvMainMsg.text = "Preço do veículo atualizado com sucesso!"
                } else {
                    tvMainMsg.text = "Erro ao atualizar preço do veículo: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                tvMainMsg.text = "Erro ao atualizar preço do veículo: ${t.message}"
            }
        })
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

            if (id != null && modelo.isNotEmpty() && ano != null && preco != null) {
                val novoVeiculo = Veiculo(id, modelo, ano, preco)
                apiService.addVeiculo(novoVeiculo).enqueue(object : Callback<Veiculo> {
                    override fun onResponse(call: Call<Veiculo>, response: Response<Veiculo>) {
                        tvMainMsg.text = "Veículo $modelo adicionado ao estoque."
                    }

                    override fun onFailure(call: Call<Veiculo>, t: Throwable) {
                        tvMainMsg.text = "Erro ao adicionar veículo: ${t.message}"
                    }
                })
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
