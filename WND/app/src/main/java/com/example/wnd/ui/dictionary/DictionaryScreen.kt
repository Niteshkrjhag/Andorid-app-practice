package com.example.wnd.ui.dictionary

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wnd.data.model.dictionaryModel.Definition
import com.example.wnd.data.model.dictionaryModel.DictionaryResponse
import com.example.wnd.data.model.dictionaryModel.Meaning
import com.example.wnd.data.remote.weatherApi.NetworkResponse
import kotlin.text.isNotBlank


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel
) {
        DictionaryDetailScreen(
            modifier = Modifier,
            viewModel = viewModel
        )
}

@Composable
fun DictionaryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryViewModel
) {
    val defination = viewModel.defination.observeAsState()
    var word by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = word,
                    onValueChange = { word = it },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        viewModel.getDefination(word)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = "Search", modifier = Modifier.padding(4.dp))
                }
            }
        }

        item {
            when (val result = defination.value) {
                is NetworkResponse.Success -> DictionaryContent(result.data)
                is NetworkResponse.Failure -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Failed To Load Data")
                }

                NetworkResponse.Loading -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                null -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Search Word to find meaning")
                }
            }
        }
    }
}

@Composable
fun DictionaryContent(
    datas: DictionaryResponse
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = datas[0].word ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Blue
        )
        Text(
            text = datas[0].phonetic ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray
        )

        datas.forEach { data ->
            data.meanings.forEach { meaning ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = meaning.partOfSpeech ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Magenta
                        )
                        Text(
                            text = "Definitions:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        meaning.definitions.forEachIndexed { index, item ->
                            Text(
                                text = "${index + 1}. ${item.definition ?: ""}",
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Text(
                            text = "Synonyms:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        val synonymsText = remember(meaning.definitions) {
                            derivedStateOf {
                                meaning.definitions.flatMap { it.synonyms }
                                    .joinToString(", ")
                            }
                        }

                        Text(
                            text = if (synonymsText.value.isNotBlank()) synonymsText.value else "No synonyms found",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}