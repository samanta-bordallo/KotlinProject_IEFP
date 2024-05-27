package pt.iefp.app2_composerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iefp.app2_composerdemo.ui.theme.App2_composerDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var nome = remember {
                mutableStateOf("Samanta")
            }
            Column {//serve para passar para a linha debaixo
                Text(
                    text = "Hello" ,
                    color = Color.Red
                )
                Text(
                    text = "Hello" ,
                    color = Color.Red
                )
            Row {//serve para deixar na mesma linha
                Text(
                    text = "Hello" ,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "Hello" ,
                    color = Color.Red
                )



            }
            linha("Nome 1")
            linha("Nome 2")
            linha("Nome 3")

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = nome.value,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Button(onClick =  {
                nome.value = "Foo" //troca o valor da variavel -> de 'Samanta' para 'Foo'
                println(nome)

            }) {
                Text(text = "my btn") // é o texto escrito dentro do botão
            }

        }
    }
}
@Composable
fun linha(nome: String){
    Row (modifier = Modifier.padding(all = 10.dp)){
        Image(painter = painterResource(id = R.drawable.images),
            contentDescription = "imagem aaaa",
            alpha = 1f, // quanto mais proximo de 0, mais deixa a imagem indo para o transparente (vai de 0 a 1)
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp) //altera o tamanho da imagem
                .clip(CircleShape) // deixa a imagem em forma de circulo
                .border(1.5.dp, Color.Red, CircleShape) //coloca uma borda na imagem
        )
        Text(text = nome)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App2_composerDemoTheme {
    }
}
}