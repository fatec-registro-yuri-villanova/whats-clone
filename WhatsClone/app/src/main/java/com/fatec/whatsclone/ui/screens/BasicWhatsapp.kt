package com.fatec.whatsclone.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.whatsclone.model.ChatMessage
import com.fatec.whatsclone.ui.components.ChatMessageItem
import com.fatec.whatsclone.ui.components.InfoCard
import com.fatec.whatsclone.ui.theme.Green40
import com.fatec.whatsclone.ui.theme.WhatsCloneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppScreen() {
    var showInfo by remember { mutableStateOf(false) }
    // Estado para controlar a rotação
    val isRotated = remember { mutableStateOf(false) }
    // Animação da rotação
    val rotation by animateFloatAsState(
        targetValue = if (isRotated.value) 45f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "rotationAnimation"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("John Doe") },
                actions = {
                    IconButton(onClick = { showInfo = true }) {
                        Icon(Icons.Filled.Info, contentDescription = "Info")
                    }
                    IconButton(onClick = {
                        // Alterna o estado da rotação ao clicar
                        isRotated.value = !isRotated.value
                        // Adicione aqui outras lógicas necessárias

                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Mais opções",
                            modifier = Modifier.rotate(rotation) // Aplica a rotação animada
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            if (showInfo) {
                InfoCard(onDismiss = { showInfo = false }, innerPadding)
            } else {
                WhatsAppChat(innerPadding)
            }
        }
    )
}

@Composable
fun WhatsAppChat(innerPadding: PaddingValues) {
    //= é usado para atribuir um objeto mutableStateListOf à chatMessages, permitindo
    // que você adicione ou remova mensagens da lista.
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }

    //by é usado para delegar a propriedade messageText para um objeto mutableStateOf,
    // que é gerenciado pelo remember. Isso permite que o estado da messageText seja observado
    // e re-renderize a interface do usuário quando houver alterações.
    var messageText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chatMessages) { message ->
                ChatMessageItem(message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message") },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            IconButton(onClick = {
                if (messageText.isNotBlank()) {
                    chatMessages.add(ChatMessage(messageText, isSent = true))
                    messageText = "" // Clear the input field
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send message",
                    tint = Green40
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WhatsAppScreenPreview() {
    WhatsCloneTheme {
        WhatsAppScreen()
    }
}
