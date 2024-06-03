package pt.iefp.appnavegation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun infoActivity(pessoa: Pessoa) {

    /*
    var nome by remember {
        mutableStateOf(nome)
    }
*/

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Nome: ${pessoa.nome}", fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 15.dp))
        Text(text = "Idade: ${pessoa.idade}", fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 15.dp))
    }
}