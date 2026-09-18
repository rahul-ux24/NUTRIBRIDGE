package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class VoiceManager(context: Context) : TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = null
  private var isInitialized = false
  var isSpeaking = false
    private set

  private var onSpeakingStateChanged: ((Boolean) -> Unit)? = null

  init {
    try {
      tts = TextToSpeech(context.applicationContext, this)
    } catch (e: Exception) {
      Log.w("VoiceManager", "Failed to create TTS: ${e.message}")
    }
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.US)
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.w("VoiceManager", "Language US is not supported or missing data")
      } else {
        isInitialized = true
        tts?.setSpeechRate(0.95f)
        tts?.setPitch(1.0f)
      }

      tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
          isSpeaking = true
          onSpeakingStateChanged?.invoke(true)
        }

        override fun onDone(utteranceId: String?) {
          isSpeaking = false
          onSpeakingStateChanged?.invoke(false)
        }

        @Deprecated("Deprecated in Java")
        override fun onError(utteranceId: String?) {
          isSpeaking = false
          onSpeakingStateChanged?.invoke(false)
        }
      })
    }
  }

  fun setSpeakingListener(listener: (Boolean) -> Unit) {
    onSpeakingStateChanged = listener
  }

  fun speak(text: String) {
    if (!isInitialized || tts == null) {
      Log.w("VoiceManager", "TTS not initialized yet")
      return
    }
    stop()
    // Clean markdown symbols like * and # for natural audio reading
    val cleanText = text
      .replace(Regex("[*#_`~]"), "")
      .replace(Regex("•"), " ")
      .trim()

    tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, "AI_RESPONSE_${System.currentTimeMillis()}")
  }

  fun stop() {
    try {
      tts?.stop()
      isSpeaking = false
      onSpeakingStateChanged?.invoke(false)
    } catch (e: Exception) {
      Log.d("VoiceManager", "Stop error: ${e.message}")
    }
  }

  fun release() {
    try {
      tts?.stop()
      tts?.shutdown()
      tts = null
      isInitialized = false
    } catch (e: Exception) {
      // safe cleanup
    }
  }
}
