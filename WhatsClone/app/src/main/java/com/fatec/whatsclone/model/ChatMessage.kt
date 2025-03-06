package com.fatec.whatsclone.model

// Added user field
data class ChatMessage(
    val text: String,
    val isSent: Boolean,
    val user: Int = 1
)