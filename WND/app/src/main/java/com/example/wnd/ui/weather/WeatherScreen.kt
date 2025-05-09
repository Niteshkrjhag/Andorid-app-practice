package com.example.wnd.ui.weather

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.wnd.data.model.weatherModel.WeatherResponse
import com.example.wnd.data.remote.weatherApi.NetworkResponse

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
){
    val weatherResult = viewModel.weatherResult.observeAsState()
    var city by remember {
        mutableStateOf("")
    }
    val keyWordController = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = {city = it},
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        viewModel.getWeather(city)
                        keyWordController?.hide()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search City"
                    )
                }
            }
            when(val result = weatherResult.value){
                is NetworkResponse.Loading -> CircularProgressIndicator()
                is NetworkResponse.Success -> WeatherDetails(data = result.data)
                is NetworkResponse.Failure -> Text("Unable To Load Data")
                null -> {}
            }

        }
    }

@Composable
private fun WeatherDetails(
    modifier: Modifier = Modifier,
    data: WeatherResponse
){
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "location",
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = data.location.name,
                fontSize = 28.sp,

            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = data.location.country,
                fontSize = 24.sp
            )
        }
        Text(
            text = "${data.current.temp_c} \u2103",
            fontSize = 48.sp
        )
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
        Log.e("HEEEE","${data.current.condition.icon}")
        val imageUrl = if(!(data.current.condition.icon.isNullOrBlank())){"https:${data.current.condition.icon}"}
        else
            "data:image/png;base64,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"
        AsyncImage(
            model = imageUrl,
            contentDescription = "Loaded Image",
            modifier = Modifier.size(160.dp)
        )
            Log.e("Weather","image url is ${data.current.condition.icon}")
            Text(
                text = data.current.condition.text,
                fontSize = 20.sp
            )
//        }
        Card(
            modifier = Modifier.padding(5.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                WeatherDetailsFragment(keys = data.current.humidity.toString(), value = "Humidity")
                WeatherDetailsFragment(keys = data.current.wind_kph.toString(), value = "Wind Speed")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                WeatherDetailsFragment(keys = data.current.dewpoint_c.toString(), value = "Dewpoint")
                WeatherDetailsFragment(keys = data.current.heatindex_c.toString(), value = "Heat Index")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                WeatherDetailsFragment(keys = data.current.uv.toString(), value = "Uv")
                WeatherDetailsFragment(keys = data.current.is_day.toString(), value = "isDay")
            }
        }
    }
}

@Composable
private fun WeatherDetailsFragment(
    keys: String,
    value: String
){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = keys,
            fontSize = 28.sp
        )
        Text(
            text = value
        )
    }
}