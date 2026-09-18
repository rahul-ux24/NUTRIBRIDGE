package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppTab
import com.example.data.UserPreferences
import com.example.ui.components.NutriBridgeBottomBar
import com.example.ui.components.NutriBridgeTopBar
import com.example.ui.screens.AssistantScreen
import com.example.ui.screens.ConnectScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PlannerScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.CardWhite
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.NutriBridgeTheme
import com.example.util.FeedbackManager

class MainActivity : ComponentActivity() {

  private lateinit var feedbackManager: FeedbackManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    feedbackManager = FeedbackManager(this)

    setContent {
      NutriBridgeTheme {
        NutriBridgeApp(feedbackManager = feedbackManager)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    feedbackManager.release()
  }
}

@Composable
fun NutriBridgeApp(
  feedbackManager: FeedbackManager,
  modifier: Modifier = Modifier
) {
  var currentTab by remember { mutableStateOf(AppTab.HOME) }
  var userPreferences by remember { mutableStateOf(UserPreferences()) }
  var savedSupplementIds by remember { mutableStateOf(setOf("whey-isolate", "creatine-mono")) }
  var savedProfessionalIds by remember { mutableStateOf(setOf("prof-1")) }
  var showNotificationsDialog by remember { mutableStateOf(false) }

  fun playSoftClick() {
    feedbackManager.playSoftClick(userPreferences.soundEnabled)
  }

  fun triggerLightHaptic() {
    feedbackManager.triggerLightHaptic(userPreferences.hapticEnabled)
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("nutribridge_scaffold"),
    topBar = {
      NutriBridgeTopBar(
        soundEnabled = userPreferences.soundEnabled,
        onToggleSound = {
          val newSound = !userPreferences.soundEnabled
          userPreferences = userPreferences.copy(soundEnabled = newSound)
          if (newSound) feedbackManager.playSoftClick(true)
          feedbackManager.triggerLightHaptic(userPreferences.hapticEnabled)
        },
        onNotificationsClick = {
          playSoftClick()
          triggerLightHaptic()
          showNotificationsDialog = true
        },
        onSettingsClick = {
          playSoftClick()
          triggerLightHaptic()
          currentTab = AppTab.PROFILE
        }
      )
    },
    bottomBar = {
      NutriBridgeBottomBar(
        currentTab = currentTab,
        onTabSelected = { tab ->
          playSoftClick()
          triggerLightHaptic()
          currentTab = tab
        }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = currentTab,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_transition"
      ) { tab ->
        when (tab) {
          AppTab.HOME -> HomeScreen(
            onNavigateTab = { targetTab ->
              currentTab = targetTab
            },
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )

          AppTab.EXPLORE -> ExploreScreen(
            savedSupplementIds = savedSupplementIds,
            onToggleFavorite = { id ->
              savedSupplementIds = if (savedSupplementIds.contains(id)) {
                savedSupplementIds - id
              } else {
                savedSupplementIds + id
              }
            },
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )

          AppTab.PLANNER -> PlannerScreen(
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )

          AppTab.ASSISTANT -> AssistantScreen(
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )

          AppTab.CONNECT -> ConnectScreen(
            savedProfessionalIds = savedProfessionalIds,
            onToggleSaveProfessional = { id ->
              savedProfessionalIds = if (savedProfessionalIds.contains(id)) {
                savedProfessionalIds - id
              } else {
                savedProfessionalIds + id
              }
            },
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )

          AppTab.PROFILE -> ProfileScreen(
            userPreferences = userPreferences,
            savedSupplementsCount = savedSupplementIds.size,
            savedProfessionalsCount = savedProfessionalIds.size,
            onUpdatePreferences = { updated ->
              userPreferences = updated
            },
            onPlayClick = { playSoftClick() },
            onTriggerHaptic = { triggerLightHaptic() }
          )
        }
      }
    }
  }

  if (showNotificationsDialog) {
    AlertDialog(
      onDismissRequest = { showNotificationsDialog = false },
      title = {
        Text(
          text = "Nutrition Notifications",
          fontFamily = FontFamily.SansSerif,
          fontWeight = FontWeight.Bold,
          color = DeepForestGreen
        )
      },
      text = {
        Text(
          text = "• Food-First Tip: Prioritize 25-35g whole-food protein within 2 hours of strength training.\n\n• Hydration Reminder: Replenish fluids with electrolytes if training in warm conditions.\n\n• Science Bulletin: New peer-reviewed study reaffirms Creatine Monohydrate safety and efficacy across adult age groups.",
          fontSize = 13.sp,
          lineHeight = 18.sp,
          color = Charcoal
        )
      },
      confirmButton = {
        TextButton(
          onClick = {
            playSoftClick()
            showNotificationsDialog = false
          }
        ) {
          Text("Got It", color = DeepForestGreen, fontWeight = FontWeight.Bold)
        }
      },
      shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
      containerColor = CardWhite
    )
  }
}

