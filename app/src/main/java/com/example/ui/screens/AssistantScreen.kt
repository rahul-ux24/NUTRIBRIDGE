package com.example.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.ChatMessage
import com.example.data.NutriBridgeData
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardWhite
import com.example.ui.theme.Charcoal
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalSecondary
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.GoldLight
import com.example.ui.theme.MutedSage
import com.example.ui.theme.SageLight
import com.example.ui.theme.SoftGold
import com.example.util.VoiceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun AssistantScreen(
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var userInput by remember { mutableStateOf("") }
  var isTyping by remember { mutableStateOf(false) }
  var currentlySpeakingMsgId by remember { mutableStateOf<String?>(null) }
  var isListeningAudio by remember { mutableStateOf(false) }

  val scope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  val voiceManager = remember { VoiceManager(context) }

  DisposableEffect(Unit) {
    voiceManager.setSpeakingListener { isSpeaking ->
      if (!isSpeaking) {
        currentlySpeakingMsgId = null
      }
    }
    onDispose {
      voiceManager.release()
    }
  }

  val starterPrompts = listOf(
    "Pre-workout snack 🍌",
    "Creatine in 30s ⚡",
    "Do I need protein? 🥛",
    "Smart late night snack 🥣",
    "Spot fake labels 🔍",
    "Vegetarian protein 🌱"
  )

  val messages = remember {
    mutableStateListOf(
      ChatMessage(
        id = "intro-1",
        text = "👋 **Hi there! I'm your everyday NutriBridge AI assistant.**\n\nAsk me anything about daily eating, gym fuel, or supplement basics in plain English!\n\n🎙️ **Tip:** You can type below or tap the **microphone icon** to speak your question directly.",
        isUser = false,
        timestamp = "Just now",
        categoryTag = "Daily Helper"
      )
    )
  }

  fun handleSend(promptText: String) {
    if (promptText.isBlank() || isTyping) return

    onPlayClick()
    onTriggerHaptic()

    val userMsg = ChatMessage(
      id = "user-${System.currentTimeMillis()}",
      text = promptText.trim(),
      isUser = true,
      timestamp = "Just now"
    )
    messages.add(userMsg)
    userInput = ""
    isTyping = true

    scope.launch {
      delay(200)
      listState.animateScrollToItem(messages.size - 1)
      delay(600) // realistic instant reasoning
      val reply = NutriBridgeData.getAssistantResponse(promptText)
      messages.add(reply)
      isTyping = false
      onPlayClick()
      onTriggerHaptic()
      delay(200)
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  // Voice speech recognizer launcher
  val speechLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
  ) { result ->
    isListeningAudio = false
    if (result.resultCode == Activity.RESULT_OK) {
      val spokenText = result.data
        ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        ?.firstOrNull()
      if (!spokenText.isNullOrBlank()) {
        handleSend(spokenText)
      }
    }
  }

  // Permission launcher for audio recording
  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask your nutrition question...")
      }
      try {
        isListeningAudio = true
        speechLauncher.launch(intent)
      } catch (e: Exception) {
        isListeningAudio = false
        Toast.makeText(context, "Voice input not available on this device", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, "Microphone permission is required for voice input", Toast.LENGTH_SHORT).show()
    }
  }

  fun startListening() {
    onPlayClick()
    onTriggerHaptic()
    val hasPermission = ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    if (hasPermission) {
      val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask your nutrition question...")
      }
      try {
        isListeningAudio = true
        speechLauncher.launch(intent)
      } catch (e: Exception) {
        isListeningAudio = false
        Toast.makeText(context, "Voice input not available on this device", Toast.LENGTH_SHORT).show()
      }
    } else {
      permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .imePadding()
  ) {
    // Header
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
      SectionHeader(
        title = "AI Nutrition Assistant 🎙️",
        subtitle = "Everyday food tips, gym snacks & clear answers"
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Attentive Audio Feature Badge
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SageLight),
        border = androidx.compose.foundation.BorderStroke(1.dp, MutedSage.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(DeepForestGreen),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Mic,
              contentDescription = null,
              tint = SoftGold,
              modifier = Modifier.size(16.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Voice Ready: Tap mic to speak or listen to answers",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = DeepForestGreen
            )
            Text(
              text = "Clear, practical answers without confusing textbook jargon",
              fontSize = 11.sp,
              color = CharcoalSecondary
            )
          }
        }
      }
    }

    // Message List
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      contentPadding = PaddingValues(vertical = 10.dp)
    ) {
      items(messages, key = { it.id }) { msg ->
        val isSpeakingThis = currentlySpeakingMsgId == msg.id

        AttentiveChatBubble(
          message = msg,
          isSpeaking = isSpeakingThis,
          onSpeakClick = {
            if (isSpeakingThis) {
              voiceManager.stop()
              currentlySpeakingMsgId = null
            } else {
              onPlayClick()
              onTriggerHaptic()
              currentlySpeakingMsgId = msg.id
              voiceManager.speak(msg.text)
            }
          }
        )
      }

      if (isTyping) {
        item {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp, top = 6.dp)
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(18.dp),
              color = DeepForestGreen,
              strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Thinking of the best real-food answer...",
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              color = CharcoalSecondary
            )
          }
        }
      }
    }

    // Quick Starter Chips
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {
      items(starterPrompts) { prompt ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardWhite)
            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
            .clickable { handleSend(prompt) }
            .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
          Text(
            text = prompt,
            fontSize = 12.sp,
            color = DeepForestGreen,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }

    // Bottom Input Bar with Audio Mic & Send Button
    Surface(
      color = CardWhite,
      tonalElevation = 6.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        if (isListeningAudio) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(GoldLight)
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              CircularProgressIndicator(
                modifier = Modifier.size(14.dp),
                strokeWidth = 2.dp,
                color = DeepForestGreen
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Listening... Speak your question now 🎙️",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DeepForestGreen
              )
            }
          }
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Dedicated Voice Mic Button
          val infiniteTransition = rememberInfiniteTransition(label = "pulse")
          val micScale by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = if (isListeningAudio) 1.25f else 1.0f,
            animationSpec = infiniteRepeatable(
              animation = tween(600, easing = FastOutSlowInEasing),
              repeatMode = RepeatMode.Reverse
            ),
            label = "mic_pulse"
          )

          Box(
            modifier = Modifier
              .size(46.dp)
              .scale(micScale)
              .clip(CircleShape)
              .background(if (isListeningAudio) SoftGold else SageLight)
              .clickable { startListening() }
              .testTag("voice_input_mic_button"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isListeningAudio) Icons.Default.Mic else Icons.Default.MicNone,
              contentDescription = "Voice Input: Tap to speak",
              tint = if (isListeningAudio) Charcoal else DeepForestGreen,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          // Text Field
          OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            placeholder = { Text("Ask a question or tap mic 🎙️", fontSize = 13.sp, color = CharcoalMuted) },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = DeepForestGreen,
              unfocusedBorderColor = BorderSubtle,
              focusedContainerColor = CardWhite,
              unfocusedContainerColor = CardWhite
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("assistant_text_input"),
            maxLines = 3
          )

          Spacer(modifier = Modifier.width(10.dp))

          // Send Action
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(if (userInput.isNotBlank() && !isTyping) DeepForestGreen else SageLight)
              .clickable(enabled = userInput.isNotBlank() && !isTyping) {
                handleSend(userInput)
              },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Send,
              contentDescription = "Send Message",
              tint = if (userInput.isNotBlank() && !isTyping) CardWhite else CharcoalMuted,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun AttentiveChatBubble(
  message: ChatMessage,
  isSpeaking: Boolean,
  onSpeakClick: () -> Unit
) {
  val isUser = message.isUser

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
  ) {
    if (!isUser) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth(0.92f)
          .padding(start = 4.dp, bottom = 4.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(DeepForestGreen),
            contentAlignment = Alignment.Center
          ) {
            Text("AI", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SoftGold)
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = message.categoryTag ?: "NutriBridge Guide",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = DeepForestGreen
          )
        }

        // Voice Read Out Button
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSpeaking) GoldLight else SageLight)
            .clickable { onSpeakClick() }
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Icon(
            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
            contentDescription = if (isSpeaking) "Stop Speaking" else "Listen Aloud",
            tint = DeepForestGreen,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (isSpeaking) "Stop" else "Listen 🔊",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = DeepForestGreen
          )
        }
      }
    }

    Box(
      modifier = Modifier
        .clip(
          RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp,
            bottomStart = if (isUser) 18.dp else 4.dp,
            bottomEnd = if (isUser) 4.dp else 18.dp
          )
        )
        .background(if (isUser) DeepForestGreen else CardWhite)
        .border(1.dp, if (isUser) DeepForestGreen else BorderSubtle, RoundedCornerShape(18.dp))
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .fillMaxWidth(if (isUser) 0.82f else 0.94f)
    ) {
      Text(
        text = message.text,
        fontSize = 14.sp,
        color = if (isUser) CardWhite else Charcoal,
        lineHeight = 21.sp
      )
    }

    Text(
      text = message.timestamp,
      fontSize = 10.sp,
      color = CharcoalMuted,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
    )
  }
}
