package com.example.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

class FeedbackManager(private val context: Context) {

  private var toneGenerator: ToneGenerator? = try {
    ToneGenerator(AudioManager.STREAM_SYSTEM, 25)
  } catch (e: Exception) {
    Log.w("FeedbackManager", "ToneGenerator could not be initialized: ${e.message}")
    null
  }

  private val vibrator: Vibrator? = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
      manager?.defaultVibrator
    } else {
      @Suppress("DEPRECATION")
      context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
  } catch (e: Exception) {
    Log.w("FeedbackManager", "Vibrator could not be initialized: ${e.message}")
    null
  }

  fun playSoftClick(soundEnabled: Boolean) {
    if (!soundEnabled) return
    try {
      toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 35)
    } catch (e: Exception) {
      Log.d("FeedbackManager", "Soft click tone skipped: ${e.message}")
    }
  }

  fun playSuccessTone(soundEnabled: Boolean) {
    if (!soundEnabled) return
    try {
      toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 70)
    } catch (e: Exception) {
      Log.d("FeedbackManager", "Success tone skipped: ${e.message}")
    }
  }

  fun triggerLightHaptic(hapticEnabled: Boolean) {
    if (!hapticEnabled) return
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        vibrator?.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(15)
      }
    } catch (e: Exception) {
      Log.d("FeedbackManager", "Light haptic skipped: ${e.message}")
    }
  }

  fun triggerSuccessHaptic(hapticEnabled: Boolean) {
    if (!hapticEnabled) return
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        vibrator?.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(35)
      }
    } catch (e: Exception) {
      Log.d("FeedbackManager", "Success haptic skipped: ${e.message}")
    }
  }

  fun release() {
    try {
      toneGenerator?.release()
      toneGenerator = null
    } catch (e: Exception) {
      // safe cleanup
    }
  }
}
