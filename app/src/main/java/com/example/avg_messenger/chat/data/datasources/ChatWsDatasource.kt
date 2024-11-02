package com.example.avg_messenger.chat.data.datasources


import android.util.Log
import com.example.avg_messenger.BuildConfig
import com.example.avg_messenger.chat.data.models.ChatMessageModel
import com.example.avg_messenger.chat.data.models.MessageHistoryModel
import com.example.avg_messenger.chat.data.models.MessageModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class ChatWsDatasource @Inject constructor(private val okHttpClient: OkHttpClient) {

    private var webSocket: WebSocket? = null
    private val _messagesFlow = MutableSharedFlow<MessageModel>(replay = 1)
    val messagesFlow: SharedFlow<MessageModel> = _messagesFlow

    fun connect(chatId: Int) {
        Log.i("WebSocket", "Attempting to connect to chat: $chatId")
        val request =
            Request.Builder().url("${BuildConfig.WS_URL}messenger/connect?chat-id=$chatId").build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                Log.i("WebSocket", "Connected to chat $chatId")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    Log.i("WebSocket onMessage", "Received message: $text")
                    val message = Gson().fromJson(text, ChatMessageModel::class.java)
                    Log.i("WebSocket onMessage", "Parsed message: $message")
                    val emitResult = _messagesFlow.tryEmit(message)
                    Log.i("WebSocket onMessage", "Emit or not emit?: $emitResult")

                } catch (e: Exception) {
                    Log.e("WebSocket onMessage", "Failed to parse message: ${e.message}")
                }
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                Log.e("WebSocket onFailure", "Error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.i("WebSocket onClosed", "Closed with code: $code, reason: $reason")
            }
        })
    }

    fun sendMessage(text: String) {
        val jsonMessage = """{ "text": "$text" }"""
        if (webSocket != null) {
            Log.i("WebSocket sendMessage", "Sending message: $text")
            webSocket?.send(jsonMessage)
        } else {
            Log.e("WebSocket sendMessage", "WebSocket is not connected.")
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "Disconnected")
        Log.i("WebSocket disconnect", "Disconnected from chat")
    }
}
