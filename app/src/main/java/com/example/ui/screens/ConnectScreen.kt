package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NutriBridgeData
import com.example.data.Professional
import com.example.ui.components.LegalDialog
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

@Composable
fun ConnectScreen(
  savedProfessionalIds: Set<String>,
  onToggleSaveProfessional: (String) -> Unit,
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var selectedCategory by remember { mutableStateOf("All") }
  var activeDialogProvider by remember { mutableStateOf<Professional?>(null) }

  val categories = listOf("All", "Dietitian", "Sports Nutritionist", "Authorized Retailer")

  val filteredProfessionals = remember(selectedCategory) {
    if (selectedCategory == "All") {
      NutriBridgeData.PROFESSIONALS
    } else {
      NutriBridgeData.PROFESSIONALS.filter { it.category == selectedCategory }
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      SectionHeader(
        title = "Professional Connections",
        subtitle = "Discover verified sports dietitians and accredited external providers"
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Direct Sales Disclaimer Banner
      Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SageLight),
        border = androidx.compose.foundation.BorderStroke(1.dp, MutedSage.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = DeepForestGreen,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Notice: NutriBridge facilitates connections to qualified practitioners and certified external retailers. We do not sell products or medications directly.",
            fontSize = 11.sp,
            color = Charcoal,
            lineHeight = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Category filter chips
      LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { cat ->
          val isSel = selectedCategory == cat
          FilterChip(
            selected = isSel,
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              selectedCategory = cat
            },
            label = { Text(cat, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = DeepForestGreen,
              selectedLabelColor = CardWhite,
              containerColor = CardWhite,
              labelColor = Charcoal
            )
          )
        }
      }
    }

    // Provider List
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredProfessionals, key = { it.id }) { prof ->
        val isSaved = savedProfessionalIds.contains(prof.id)

        ProfessionalCard(
          professional = prof,
          isSaved = isSaved,
          onCardClick = {
            onPlayClick()
            onTriggerHaptic()
            activeDialogProvider = prof
          },
          onToggleSave = {
            onPlayClick()
            onTriggerHaptic()
            onToggleSaveProfessional(prof.id)
          },
          onContactClick = {
            onPlayClick()
            onTriggerHaptic()
            activeDialogProvider = prof
          }
        )
      }
    }
  }

  // Provider Detail Modal
  activeDialogProvider?.let { prof ->
    LegalDialog(
      title = prof.name,
      content = "Credentials: ${prof.credentials}\n" +
        "Category: ${prof.category}\n" +
        "Specialty: ${prof.specialty}\n" +
        "Location: ${prof.location}\n" +
        "Rating: ${prof.rating} ★ (${prof.reviewCount} peer reviews)\n\n" +
        "Overview:\n${prof.description}\n\n" +
        "Trust Note: This is an accredited external practitioner profile in NutriBridge's partner registry. Consultations and services are conducted independently with the provider.",
      onDismiss = { activeDialogProvider = null }
    )
  }
}

@Composable
private fun ProfessionalCard(
  professional: Professional,
  isSaved: Boolean,
  onCardClick: () -> Unit,
  onToggleSave: () -> Unit,
  onContactClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onCardClick() }
      .testTag("professional_card_${professional.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = CardWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = professional.name,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = Charcoal
            )
            if (professional.isVerified) {
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified Practitioner",
                tint = DeepForestGreen,
                modifier = Modifier.size(16.dp)
              )
            }
          }
          Text(
            text = professional.credentials,
            fontSize = 12.sp,
            color = SoftGold,
            fontWeight = FontWeight.SemiBold
          )
        }

        IconButton(onClick = onToggleSave) {
          Icon(
            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Bookmark",
            tint = if (isSaved) SoftGold else CharcoalMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = CharcoalMuted, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = professional.location, fontSize = 11.sp, color = CharcoalSecondary)
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = SoftGold, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "${professional.rating} (${professional.reviewCount})", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Charcoal)
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = professional.description,
        fontSize = 12.sp,
        color = CharcoalSecondary,
        lineHeight = 17.sp,
        maxLines = 3
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SageLight)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = professional.category,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = DeepForestGreen
          )
        }

        Button(
          onClick = onContactClick,
          colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
          modifier = Modifier.height(34.dp)
        ) {
          Text("Connect", fontSize = 12.sp, color = CardWhite, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.width(4.dp))
          Icon(imageVector = Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(12.dp))
        }
      }
    }
  }
}
