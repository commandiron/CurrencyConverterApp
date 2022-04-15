package com.commandiron.currencyconverter_app_by_command

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.currencyconverter_app_by_command.ui.theme.CurrencyConverter_app_by_commandTheme
import com.commandiron.currencyconverter_app_by_command.ui.theme.myBackGround
import com.commandiron.currencyconverter_app_by_command.util.Constants
import com.commandiron.currencyconverter_app_by_command.util.Constants.APP_NAME
import com.commandiron.currencyconverter_app_by_command.viewmodel.CurrencyEvent
import com.commandiron.currencyconverter_app_by_command.viewmodel.MainActivityViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.TextStyle


@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CurrencyConverter_app_by_commandTheme {
                SetUi()
            }
        }
    }
}

@Composable
fun SetUi(viewModel: MainActivityViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = myBackGround,
        darkIcons = true
    )

    val allCurrencyList by remember {viewModel.allCurrencyList}
    val resultForCurrency by remember { viewModel.multipledResult}
    
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
            modifier = Modifier.padding(bottom = 100.dp)){

            var baseCurrency by remember { mutableStateOf("")}
            var selectedCurrencyForConvert by remember { mutableStateOf("")}
            var multiplierForConvert by remember { mutableStateOf("1.00")}
            
            Text(text = APP_NAME,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary)

            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                DropdownDemo(allCurrencyList){
                    if(it != Constants.SELECT_CURRENCY_STRING){
                        baseCurrency = it
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier
                    .border(1.dp, Color.Gray)) {
                    BasicTextField(
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .width(160.dp)
                            .background(Color.White)
                            .padding(10.dp),
                        value = multiplierForConvert,
                        onValueChange = {
                            multiplierForConvert = it},
                        maxLines = 1,
                        singleLine = true)
                }
            }

            Button(
                onClick = {
                    viewModel.getMultipledResultForQuery(
                        baseCurrency,
                        selectedCurrencyForConvert,
                        multiplierForConvert.toDouble())
                          },
                modifier = Modifier.width(100.dp)) {
                Text(text = "Swap")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                DropdownDemo(allCurrencyList){
                    if(it != Constants.SELECT_CURRENCY_STRING){
                        selectedCurrencyForConvert = it
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(160.dp)
                        .background(MaterialTheme.colors.primary)
                        .padding(10.dp)
                ) {
                    BasicTextField(
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        value = resultForCurrency,
                        onValueChange = {
                            multiplierForConvert = it},
                        enabled = false)
                }
            }

            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

        }
    }
}

@Composable
fun DropdownDemo(items:List<String>, onSelect: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(160.dp)
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = { expanded = true })
            .padding(10.dp)
            ) {
        Text(items[selectedIndex],
            textAlign =  TextAlign.Center,
            color = Color.White)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .height(300.dp)
                .background(MaterialTheme.colors.primary)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                    onSelect(s)
                    selectedIndex = index
                    expanded = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConverter_app_by_commandTheme {
        SetUi()
    }
}