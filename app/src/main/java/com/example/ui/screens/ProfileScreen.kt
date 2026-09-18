package com.example.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserPreferences
import com.example.ui.components.LegalDialog
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardWhite
import com.example.ui.theme.Charcoal
import com.example.ui.theme.CharcoalMuted
import com.example.ui.theme.CharcoalSecondary
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldLight
import com.example.ui.theme.MutedSage
import com.example.ui.theme.SageLight
import com.example.ui.theme.SoftGold

@Composable
fun ProfileScreen(
  userPreferences: UserPreferences,
  savedSupplementsCount: Int,
  savedProfessionalsCount: Int,
  onUpdatePreferences: (UserPreferences) -> Unit,
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  var activeLegalDoc by remember { mutableStateOf<Pair<String, String>?>(null) }
  var showEditProfileDialog by remember { mutableStateOf(false) }
  var showDeleteConfirmDialog by remember { mutableStateOf(false) }
  var showSignOutDialog by remember { mutableStateOf(false) }

  var tempName by remember { mutableStateOf(userPreferences.name) }
  var tempEmail by remember { mutableStateOf(userPreferences.email) }

  val nutritionDisclaimerText = """
    NutriBridge is an educational and discovery platform designed to help consumers understand nutritional concepts and peer-reviewed supplement evidence.
    
    1. NO MEDICAL ADVICE: Information provided within this application, including meal plans and AI assistant outputs, is for general educational purposes only. It is not intended to diagnose, treat, cure, or prevent any disease.
    
    2. CONSULT A PROFESSIONAL: Always consult with a qualified physician, registered dietitian, or sports medicine specialist before initiating any significant dietary alteration or supplement regimen, especially if you are pregnant, nursing, or managing pre-existing medical conditions.
  """.trimIndent()

  val supplementDisclaimerText = """
    1. EVIDENCE-BASED GRADING: Supplement summaries reflect aggregated consensus from peer-reviewed clinical studies. Individual biochemical responses may vary significantly based on genetics, baseline diet, training, and sleep.
    
    2. THIRD-PARTY VERIFICATION: We strongly encourage users to choose supplements certified by independent testing bodies (such as NSF Certified for Sport, USP, or Informed Choice) to safeguard against contamination or undeclared substances.
    
    3. NO PRESCRIPTIONS: NutriBridge does not prescribe, sell, or manufacture dietary supplements.
  """.trimIndent()

  val referralDisclosureText = """
    NutriBridge operates with strict editorial independence.
    
    1. INDEPENDENT EDITORIAL POLICY: Product research ratings and evidence evaluations are never influenced by manufacturer sponsorships, paid endorsements, or advertising revenue.
    
    2. EXTERNAL PARTNERS: When users choose to connect with third-party practitioners or certified fulfillment providers through external links, NutriBridge may receive an affiliate referral fee at zero additional expense to the user.
    
    3. NO DIRECT SALES: NutriBridge is not an e-commerce platform and processes no supplement payments directly.
  """.trimIndent()

  val privacyPolicyText = """
    NutriBridge prioritizes user confidentiality and privacy:
    
    1. LOCAL STORAGE: Personal dietary preferences, meal plans, and favorite lists are stored locally on your device by default.
    
    2. ZERO DATA BROKERING: We never sell, rent, or trade your personal wellness parameters or questionnaire answers to third-party advertisers or insurance providers.
    
    3. USER CONTROL: You maintain complete ownership of your data and can purge all cached profiles and history at any time.
  """.trimIndent()

  val termsOfServiceText = """
    By accessing NutriBridge, you agree to:
    
    1. Use the platform for personal, non-commercial educational enrichment.
    2. Acknowledge that the AI assistant operates in an educational capacity and cannot substitute for certified clinical advice.
    3. Verify all external professional credentials and review individual provider service agreements when booking independent consultations.
  """.trimIndent()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    item {
      SectionHeader(
        title = "Profile & Settings",
        subtitle = "Manage personal preferences, feedback, and privacy controls"
      )
    }

    // User Profile Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(52.dp)
                  .clip(CircleShape)
                  .background(DeepForestGreen),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = userPreferences.name.take(2).uppercase(),
                  color = SoftGold,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.SansSerif
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = userPreferences.name,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = Charcoal
                )
                Text(
                  text = userPreferences.email,
                  fontSize = 12.sp,
                  color = CharcoalMuted
                )
              }
            }

            IconButton(
              onClick = {
                onPlayClick()
                onTriggerHaptic()
                tempName = userPreferences.name
                tempEmail = userPreferences.email
                showEditProfileDialog = true
              }
            ) {
              Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile", tint = DeepForestGreen)
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // User Stats Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(SageLight)
              .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            StatItem(count = "$savedSupplementsCount", label = "Saved Supplements")
            StatItem(count = "$savedProfessionalsCount", label = "Saved Experts")
            StatItem(count = "Active", label = "Food-First Plan")
          }
        }
      }
    }

    // App Experience & Feedback Toggles (Tap Sounds & Haptics)
    item {
      Text(
        text = "Interaction Feedback & Experience",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Charcoal
      )
      Spacer(modifier = Modifier.height(8.dp))

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          // Sound Toggle
          SettingToggleRow(
            icon = Icons.Default.VolumeUp,
            title = "Tap Sounds",
            subtitle = "Soft acoustic click on navigation and button actions",
            checked = userPreferences.soundEnabled,
            onCheckedChange = { isChecked ->
              onPlayClick()
              onTriggerHaptic()
              onUpdatePreferences(userPreferences.copy(soundEnabled = isChecked))
            }
          )

          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))

          // Haptic Toggle
          SettingToggleRow(
            icon = Icons.Default.Vibration,
            title = "Haptic Vibration",
            subtitle = "Tactile feedback for button presses and tab changes",
            checked = userPreferences.hapticEnabled,
            onCheckedChange = { isChecked ->
              onTriggerHaptic()
              onUpdatePreferences(userPreferences.copy(hapticEnabled = isChecked))
            }
          )

          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))

          // Notification Toggle
          SettingToggleRow(
            icon = Icons.Default.Notifications,
            title = "Hydration & Meal Reminders",
            subtitle = "Daily notifications to log whole foods and fluids",
            checked = userPreferences.notificationsEnabled,
            onCheckedChange = { isChecked ->
              onPlayClick()
              onTriggerHaptic()
              onUpdatePreferences(userPreferences.copy(notificationsEnabled = isChecked))
            }
          )
        }
      }
    }

    // Legal & Trust Documentation
    item {
      Text(
        text = "Legal, Disclaimers & Disclosures",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Charcoal
      )
      Spacer(modifier = Modifier.height(8.dp))

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          LegalItemRow(title = "Nutrition & Dietary Disclaimer", icon = Icons.Default.Shield) {
            onPlayClick()
            activeLegalDoc = Pair("Nutrition & Dietary Disclaimer", nutritionDisclaimerText)
          }
          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))
          LegalItemRow(title = "Supplement Safety & Testing Statement", icon = Icons.Default.Security) {
            onPlayClick()
            activeLegalDoc = Pair("Supplement Safety Statement", supplementDisclaimerText)
          }
          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))
          LegalItemRow(title = "Referral & Non-Sales Disclosure", icon = Icons.Default.Lock) {
            onPlayClick()
            activeLegalDoc = Pair("Referral & Non-Sales Disclosure", referralDisclosureText)
          }
          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))
          LegalItemRow(title = "Privacy Policy & Data Rights", icon = Icons.Default.Shield) {
            onPlayClick()
            activeLegalDoc = Pair("Privacy Policy", privacyPolicyText)
          }
          HorizontalDivider(color = BorderSubtle.copy(alpha = 0.6f))
          LegalItemRow(title = "Terms of Service", icon = Icons.Default.Security) {
            onPlayClick()
            activeLegalDoc = Pair("Terms of Service", termsOfServiceText)
          }
        }
      }
    }

    // Account Actions
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            showSignOutDialog = true
          },
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CharcoalSecondary),
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
        ) {
          Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(16.dp), tint = CharcoalSecondary)
          Spacer(modifier = Modifier.width(6.dp))
          Text("Sign Out", fontSize = 12.sp, color = CharcoalSecondary)
        }

        OutlinedButton(
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            showDeleteConfirmDialog = true
          },
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.6f)),
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
        ) {
          Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp), tint = ErrorRed)
          Spacer(modifier = Modifier.width(6.dp))
          Text("Delete Account", fontSize = 12.sp, color = ErrorRed)
        }
      }
    }
  }

  // Legal Modal
  activeLegalDoc?.let { (title, content) ->
    LegalDialog(
      title = title,
      content = content,
      onDismiss = { activeLegalDoc = null }
    )
  }

  // Edit Profile Dialog
  if (showEditProfileDialog) {
    AlertDialog(
      onDismissRequest = { showEditProfileDialog = false },
      title = { Text("Edit Profile", fontWeight = FontWeight.Bold, color = DeepForestGreen) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = tempName,
            onValueChange = { tempName = it },
            label = { Text("Display Name") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DeepForestGreen)
          )
          OutlinedTextField(
            value = tempEmail,
            onValueChange = { tempEmail = it },
            label = { Text("Email Address") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DeepForestGreen)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            onUpdatePreferences(userPreferences.copy(name = tempName, email = tempEmail))
            showEditProfileDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen)
        ) {
          Text("Save Changes", color = CardWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { showEditProfileDialog = false }) {
          Text("Cancel", color = CharcoalSecondary)
        }
      },
      containerColor = CardWhite,
      shape = RoundedCornerShape(16.dp)
    )
  }

  // Sign Out Dialog
  if (showSignOutDialog) {
    AlertDialog(
      onDismissRequest = { showSignOutDialog = false },
      title = { Text("Sign Out of NutriBridge", fontWeight = FontWeight.Bold, color = DeepForestGreen) },
      text = { Text("Are you sure you wish to sign out? Your saved meal plans and favorites will remain safe on your device.", fontSize = 13.sp, color = Charcoal) },
      confirmButton = {
        Button(
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            showSignOutDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen)
        ) {
          Text("Sign Out", color = CardWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { showSignOutDialog = false }) {
          Text("Cancel", color = CharcoalSecondary)
        }
      },
      containerColor = CardWhite,
      shape = RoundedCornerShape(16.dp)
    )
  }

  // Delete Account Dialog
  if (showDeleteConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showDeleteConfirmDialog = false },
      title = { Text("Delete Account", fontWeight = FontWeight.Bold, color = ErrorRed) },
      text = { Text("This will permanently clear your profile parameters, saved plans, and local device preferences. This action cannot be reversed.", fontSize = 13.sp, color = Charcoal) },
      confirmButton = {
        Button(
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            onUpdatePreferences(UserPreferences())
            showDeleteConfirmDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
        ) {
          Text("Permanently Delete", color = CardWhite)
        }
      },
      dismissButton = {
        TextButton(onClick = { showDeleteConfirmDialog = false }) {
          Text("Cancel", color = CharcoalSecondary)
        }
      },
      containerColor = CardWhite,
      shape = RoundedCornerShape(16.dp)
    )
  }
}

@Composable
private fun StatItem(count: String, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = count, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepForestGreen)
    Text(text = label, fontSize = 10.sp, color = CharcoalSecondary)
  }
}

@Composable
private fun SettingToggleRow(
  icon: ImageVector,
  title: String,
  subtitle: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 10.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(SageLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = DeepForestGreen, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Charcoal)
        Text(text = subtitle, fontSize = 11.sp, color = CharcoalMuted, lineHeight = 15.sp)
      }
    }
    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = CardWhite,
        checkedTrackColor = DeepForestGreen,
        uncheckedThumbColor = CharcoalMuted,
        uncheckedTrackColor = SageLight
      )
    )
  }
}

@Composable
private fun LegalItemRow(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(imageVector = icon, contentDescription = null, tint = DeepForestGreen, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(12.dp))
      Text(text = title, fontSize = 13.sp, color = Charcoal, fontWeight = FontWeight.Medium)
    }
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
      contentDescription = null,
      tint = CharcoalMuted,
      modifier = Modifier.size(12.dp)
    )
  }
}
