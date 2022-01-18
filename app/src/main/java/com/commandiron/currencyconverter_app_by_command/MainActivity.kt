package com.commandiron.currencyconverter_app_by_command

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.foundation.background
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
import com.commandiron.currencyconverter_app_by_command.util.Constants
import com.commandiron.currencyconverter_app_by_command.util.Constants.APP_NAME
import com.commandiron.currencyconverter_app_by_command.viewmodel.CurrencyEvent
import com.commandiron.currencyconverter_app_by_command.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


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

    val allCurrencyList by remember {viewModel.allCurrencyList}
    val resultForCurrency by remember { viewModel.multipledResult}
    
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){

            var baseCurrency by remember { mutableStateOf("")}
            var selectedCurrencyForConvert by remember { mutableStateOf("")}
            var multiplierForConvert by remember { mutableStateOf("1.00")}
            
            Text(text = APP_NAME,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary)

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(10.dp),
                contentAlignment = Alignment.Center) {
                Row() {
                    DropdownDemo(allCurrencyList){
                        if(it != Constants.SELECT_CURRENCY_STRING){
                            baseCurrency = it
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = multiplierForConvert,
                        onValueChange = {
                            multiplierForConvert = it},
                        maxLines = 1,
                        singleLine = true)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.getMultipledResultForQuery(
                        baseCurrency,
                        selectedCurrencyForConvert,
                        multiplierForConvert.toDouble())
                          },

                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Swap")
            }

            /*
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val forSave = baseCurrency
                    baseCurrency = selectedCurrencyForConvert
                    selectedCurrencyForConvert = forSave
                },

                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Swap Currency")
            }
             */

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(10.dp),
                contentAlignment = Alignment.Center) {
                Row() {
                    DropdownDemo(allCurrencyList){
                        if(it != Constants.SELECT_CURRENCY_STRING){
                            selectedCurrencyForConvert = it
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    BasicTextField(
                        value = resultForCurrency,
                        onValueChange = {
                            multiplierForConvert = it},
                        maxLines = 1,
                        singleLine = true,
                        enabled = false)
                }
            }
        }
    }
}

@Composable
fun DropdownDemo(items:List<String>, onSelect: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize(Alignment.Center)) {
        Text(items[selectedIndex],
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .clickable(onClick = { expanded = true })
                .background(MaterialTheme.colors.primary),
            textAlign =  TextAlign.Center,
            color = Color.White)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentSize()
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