package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppTab
import com.example.data.EvidenceLevel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriBridgeTopBar(
  soundEnabled: Boolean,
  onToggleSound: () -> Unit,
  onNotificationsClick: () -> Unit,
  onSettingsClick: () -> Unit
) {
  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.testTag("top_bar_title_row")
      ) {
        // Brand Leaf-Bridge Emblem
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DeepForestGreen),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "NB",
            color = SoftGold,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "NutriBridge",
              style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
              ),
              color = Charcoal
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(GoldLight)
                .border(0.5.dp, SoftGold.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "AI",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = DeepForestGreen
              )
            }
          }
          Text(
            text = "Nutrition & Supplement Bridge",
            fontSize = 10.sp,
            color = CharcoalMuted
          )
        }
      }
    },
    actions = {
      IconButton(
        onClick = onToggleSound,
        modifier = Modifier.testTag("sound_toggle_button")
      ) {
        Icon(
          imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
          contentDescription = if (soundEnabled) "Tap sounds active" else "Tap sounds muted",
          tint = if (soundEnabled) DeepForestGreen else CharcoalMuted
        )
      }
      IconButton(
        onClick = onNotificationsClick,
        modifier = Modifier.testTag("notification_button")
      ) {
        BadgedBox(
          badge = {
            Badge(
              containerColor = SoftGold,
              contentColor = Charcoal
            ) {
              Text("1")
            }
          }
        ) {
          Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Charcoal
          )
        }
      }
      IconButton(
        onClick = onSettingsClick,
        modifier = Modifier.testTag("top_settings_button")
      ) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = "User Settings",
          tint = Charcoal
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background
    )
  )
}

@Composable
fun NutriBridgeBottomBar(
  currentTab: AppTab,
  onTabSelected: (AppTab) -> Unit
) {
  NavigationBar(
    containerColor = CardWhite,
    tonalElevation = 8.dp
  ) {
    val items = listOf(
      Triple(AppTab.HOME, Icons.Filled.Home, Icons.Outlined.Home),
      Triple(AppTab.EXPLORE, Icons.Filled.Search, Icons.Outlined.Search),
      Triple(AppTab.PLANNER, Icons.Filled.DateRange, Icons.Outlined.DateRange),
      Triple(AppTab.ASSISTANT, Icons.Filled.Chat, Icons.Outlined.Chat),
      Triple(AppTab.CONNECT, Icons.Filled.People, Icons.Outlined.People),
      Triple(AppTab.PROFILE, Icons.Filled.Person, Icons.Outlined.Person)
    )

    items.forEach { (tab, filledIcon, outlinedIcon) ->
      val selected = currentTab == tab
      NavigationBarItem(
        selected = selected,
        onClick = { onTabSelected(tab) },
        icon = {
          Icon(
            imageVector = if (selected) filledIcon else outlinedIcon,
            contentDescription = tab.title
          )
        },
        label = {
          Text(
            text = tab.title,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = CardWhite,
          selectedTextColor = DeepForestGreen,
          indicatorColor = DeepForestGreen,
          unselectedIconColor = CharcoalSecondary,
          unselectedTextColor = CharcoalMuted
        ),
        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
      )
    }
  }
}

@Composable
fun EvidenceBadge(
  level: EvidenceLevel,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, borderC) = when (level) {
    EvidenceLevel.STRONG -> Triple(SageLight, DeepForestGreen, MutedSage)
    EvidenceLevel.MODERATE -> Triple(GoldLight, DeepForestGreen, SoftGold)
    EvidenceLevel.PRELIMINARY -> Triple(Color(0xFFF2F4F2), CharcoalSecondary, BorderSubtle)
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(6.dp))
      .background(bgColor)
      .border(0.8.dp, borderC, RoundedCornerShape(6.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp)
  ) {
    Text(
      text = level.label,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = textColor
    )
  }
}

@Composable
fun SectionHeader(
  title: String,
  subtitle: String? = null,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = title,
      style = MaterialTheme.typography.headlineSmall.copy(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
      ),
      color = Charcoal
    )
    if (subtitle != null) {
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium,
        color = CharcoalSecondary
      )
    }
  }
}

@Composable
fun TrustDisclaimerBanner(
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("trust_disclaimer_card"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = SageLight),
    border = androidx.compose.foundation.BorderStroke(1.dp, MutedSage.copy(alpha = 0.5f))
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.Top
    ) {
      Icon(
        imageVector = Icons.Default.Info,
        contentDescription = "Independent Trust Notice",
        tint = DeepForestGreen,
        modifier = Modifier
          .size(22.dp)
          .padding(top = 2.dp)
      )
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = "Independent Nutrition Guidance",
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = DeepForestGreen
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "NutriBridge does NOT sell supplements directly. We provide unbiased, evidence-based education, diet planning tools, and verified professional connections so you can make informed decisions.",
          fontSize = 12.sp,
          color = Charcoal,
          lineHeight = 17.sp
        )
      }
    }
  }
}

@Composable
fun LegalDialog(
  title: String,
  content: String,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = title,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        color = DeepForestGreen
      )
    },
    text = {
      Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
          text = content,
          fontSize = 13.sp,
          lineHeight = 19.sp,
          color = Charcoal
        )
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("I Understand", color = DeepForestGreen, fontWeight = FontWeight.Bold)
      }
    },
    shape = RoundedCornerShape(16.dp),
    containerColor = CardWhite
  )
}
