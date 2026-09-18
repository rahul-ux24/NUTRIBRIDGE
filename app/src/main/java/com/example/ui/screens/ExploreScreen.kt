package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EvidenceLevel
import com.example.data.NutriBridgeData
import com.example.data.Supplement
import com.example.ui.components.EvidenceBadge
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

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
  savedSupplementIds: Set<String>,
  onToggleFavorite: (String) -> Unit,
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf("All") }
  var selectedEvidenceFilter by remember { mutableStateOf<EvidenceLevel?>(null) }

  var activeDetailSupplement by remember { mutableStateOf<Supplement?>(null) }
  val comparisonList = remember { mutableStateListOf<Supplement>() }
  var showCompareSheet by remember { mutableStateOf(false) }

  val filteredSupplements by remember(searchQuery, selectedCategory, selectedEvidenceFilter) {
    derivedStateOf {
      NutriBridgeData.SUPPLEMENTS.filter { item ->
        val matchesCategory = (selectedCategory == "All") || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
          item.name.contains(searchQuery, ignoreCase = true) ||
          item.shortPurpose.contains(searchQuery, ignoreCase = true) ||
          item.foodAlternatives.any { it.contains(searchQuery, ignoreCase = true) }
        val matchesEvidence = selectedEvidenceFilter == null || item.evidenceLevel == selectedEvidenceFilter

        matchesCategory && matchesSearch && matchesEvidence
      }
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Header & Search
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      SectionHeader(
        title = "Supplement Explorer",
        subtitle = "Evidence-graded database with whole-food alternatives"
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search supplements, ingredients, foods...", color = CharcoalMuted, fontSize = 14.sp) },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = DeepForestGreen)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = CharcoalSecondary)
            }
          }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = DeepForestGreen,
          unfocusedBorderColor = BorderSubtle,
          focusedContainerColor = CardWhite,
          unfocusedContainerColor = CardWhite
        ),
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("explore_search_input")
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Category Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
      ) {
        items(NutriBridgeData.CATEGORIES) { cat ->
          val isSelected = selectedCategory == cat
          FilterChip(
            selected = isSelected,
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              selectedCategory = cat
            },
            label = {
              Text(
                text = cat,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = DeepForestGreen,
              selectedLabelColor = CardWhite,
              containerColor = CardWhite,
              labelColor = Charcoal
            ),
            border = FilterChipDefaults.filterChipBorder(
              enabled = true,
              selected = isSelected,
              borderColor = if (isSelected) DeepForestGreen else BorderSubtle
            )
          )
        }
      }
    }

    // Compare Bar Banner if items are staged for comparison
    AnimatedVisibility(visible = comparisonList.isNotEmpty()) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SageLight),
        border = androidx.compose.foundation.BorderStroke(1.dp, MutedSage)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.CompareArrows, contentDescription = null, tint = DeepForestGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Comparing ${comparisonList.size}/2 Supplements",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = DeepForestGreen
            )
          }
          Row {
            TextButton(
              onClick = { comparisonList.clear() },
              contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
              Text("Clear", color = CharcoalMuted, fontSize = 12.sp)
            }
            Button(
              onClick = {
                onPlayClick()
                onTriggerHaptic()
                showCompareSheet = true
              },
              enabled = comparisonList.size >= 2,
              colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
              modifier = Modifier.testTag("open_compare_sheet_button")
            ) {
              Text("Compare", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CardWhite)
            }
          }
        }
      }
    }

    // Results List
    if (filteredSupplements.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MutedSage
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "No supplements match your criteria",
            fontWeight = FontWeight.SemiBold,
            color = Charcoal
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Try clearing your search query or selecting 'All' categories.",
            fontSize = 13.sp,
            color = CharcoalMuted
          )
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        items(filteredSupplements, key = { it.id }) { supplement ->
          val isSaved = savedSupplementIds.contains(supplement.id)
          val isCompared = comparisonList.any { it.id == supplement.id }

          SupplementCard(
            supplement = supplement,
            isSaved = isSaved,
            isCompared = isCompared,
            onCardClick = {
              onPlayClick()
              onTriggerHaptic()
              activeDetailSupplement = supplement
            },
            onToggleFavorite = {
              onPlayClick()
              onTriggerHaptic()
              onToggleFavorite(supplement.id)
            },
            onToggleCompare = {
              onPlayClick()
              onTriggerHaptic()
              if (isCompared) {
                comparisonList.removeAll { it.id == supplement.id }
              } else {
                if (comparisonList.size < 2) {
                  comparisonList.add(supplement)
                }
              }
            }
          )
        }
      }
    }
  }

  // Supplement Detail Bottom Sheet
  activeDetailSupplement?.let { supp ->
    val isSaved = savedSupplementIds.contains(supp.id)
    SupplementDetailSheet(
      supplement = supp,
      isSaved = isSaved,
      onToggleFavorite = {
        onPlayClick()
        onTriggerHaptic()
        onToggleFavorite(supp.id)
      },
      onDismiss = { activeDetailSupplement = null }
    )
  }

  // Side-by-Side Comparison Dialog / Sheet
  if (showCompareSheet && comparisonList.size >= 2) {
    ComparisonModalSheet(
      supplementA = comparisonList[0],
      supplementB = comparisonList[1],
      onDismiss = { showCompareSheet = false }
    )
  }
}

@Composable
private fun SupplementCard(
  supplement: Supplement,
  isSaved: Boolean,
  isCompared: Boolean,
  onCardClick: () -> Unit,
  onToggleFavorite: () -> Unit,
  onToggleCompare: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onCardClick() }
      .testTag("supplement_card_${supplement.id}"),
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
          EvidenceBadge(level = supplement.evidenceLevel)
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = supplement.name,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Charcoal
          )
          Text(
            text = supplement.category,
            fontSize = 12.sp,
            color = CharcoalMuted
          )
        }

        Row {
          IconButton(onClick = onToggleFavorite) {
            Icon(
              imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Save to favorites",
              tint = if (isSaved) SoftGold else CharcoalMuted
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = supplement.shortPurpose,
        fontSize = 13.sp,
        color = CharcoalSecondary,
        lineHeight = 18.sp
      )

      if (supplement.quickVerdict.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SageLight)
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "💡", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Verdict: ${supplement.quickVerdict}",
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = DeepForestGreen,
              lineHeight = 15.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Whole food alternative callout pill
      if (supplement.foodAlternatives.isNotEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GoldLight)
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Restaurant,
              contentDescription = null,
              tint = SoftGold,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Food Alternative: ${supplement.foodAlternatives.first()}",
              fontSize = 11.sp,
              color = Charcoal,
              maxLines = 1
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Tap to read clinical evidence →",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = DeepForestGreen
        )

        OutlinedButton(
          onClick = onToggleCompare,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isCompared) SageLight else Color.Transparent
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isCompared) DeepForestGreen else BorderSubtle),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
          modifier = Modifier.height(32.dp)
        ) {
          Text(
            text = if (isCompared) "✓ Staged" else "+ Compare",
            fontSize = 11.sp,
            color = DeepForestGreen,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun SupplementDetailSheet(
  supplement: Supplement,
  isSaved: Boolean,
  onToggleFavorite: () -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = CardWhite,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      contentPadding = PaddingValues(bottom = 36.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          EvidenceBadge(level = supplement.evidenceLevel)
          IconButton(onClick = onToggleFavorite) {
            Icon(
              imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (isSaved) SoftGold else CharcoalSecondary
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = supplement.name,
          style = MaterialTheme.typography.headlineMedium,
          color = Charcoal
        )
        Text(
          text = "Category: ${supplement.category}",
          fontSize = 13.sp,
          color = CharcoalMuted
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Quick Plain English Verdict Callout Card
        if (supplement.quickVerdict.isNotBlank()) {
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = GoldLight),
            border = androidx.compose.foundation.BorderStroke(1.dp, SoftGold.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "💡", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Bottom Line in Plain English",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = Charcoal
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = supplement.quickVerdict,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Charcoal,
                lineHeight = 18.sp
              )
              if (supplement.whoShouldUseIt.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "🎯 Best For: ${supplement.whoShouldUseIt}",
                  fontSize = 12.sp,
                  color = DeepForestGreen,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
        }

        // Clinical Evidence Summary Card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = SageLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Scientific Evidence Summary",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = DeepForestGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = supplement.evidenceSummary,
              fontSize = 13.sp,
              color = Charcoal,
              lineHeight = 18.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Potential Benefits
        Text(
          text = "Potential Benefits",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = Charcoal
        )
        Spacer(modifier = Modifier.height(8.dp))
        supplement.potentialBenefits.forEach { benefit ->
          BulletPointRow(text = benefit, checkmark = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Limitations & Caveats
        Text(
          text = "Limitations & Non-Responders",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = Charcoal
        )
        Spacer(modifier = Modifier.height(8.dp))
        supplement.limitations.forEach { limit ->
          BulletPointRow(text = limit, checkmark = false)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Whole Food Alternatives
        Text(
          text = "Food-First Natural Alternatives",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = Charcoal
        )
        Spacer(modifier = Modifier.height(8.dp))
        supplement.foodAlternatives.forEach { food ->
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 3.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Restaurant,
              contentDescription = null,
              tint = SoftGold,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = food, fontSize = 13.sp, color = Charcoal)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dosage & Safety Considerations
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = GoldLight),
          border = androidx.compose.foundation.BorderStroke(1.dp, SoftGold.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = SoftGold, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Dosage & Safety Precautions",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Charcoal
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = supplement.dosageGuidance,
              fontSize = 12.sp,
              color = Charcoal,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            supplement.safetyConsiderations.forEach { precaution ->
              Text(
                text = "• $precaution",
                fontSize = 12.sp,
                color = CharcoalSecondary,
                lineHeight = 17.sp,
                modifier = Modifier.padding(vertical = 2.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Non-Prescriptive Educational Disclaimer
        Text(
          text = "Educational Notice: NutriBridge does not prescribe supplements or offer medical diagnosis. Always review potential interactions with your physician or Registered Dietitian.",
          fontSize = 11.sp,
          color = CharcoalMuted,
          lineHeight = 15.sp
        )
      }
    }
  }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun ComparisonModalSheet(
  supplementA: Supplement,
  supplementB: Supplement,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = CardWhite,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 36.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Ingredient & Evidence Comparison",
          style = MaterialTheme.typography.titleLarge,
          color = DeepForestGreen
        )
        IconButton(onClick = onDismiss) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Column A
        ComparisonColumn(supplement = supplementA, modifier = Modifier.weight(1f))
        // Column B
        ComparisonColumn(supplement = supplementB, modifier = Modifier.weight(1f))
      }
    }
  }
}

@Composable
private fun ComparisonColumn(
  supplement: Supplement,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = SageLight.copy(alpha = 0.5f)),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      EvidenceBadge(level = supplement.evidenceLevel)
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = supplement.name,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Charcoal
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = supplement.shortPurpose,
        fontSize = 11.sp,
        color = CharcoalSecondary,
        lineHeight = 15.sp
      )

      Spacer(modifier = Modifier.height(10.dp))
      Text(text = "Primary Advantage:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepForestGreen)
      Text(
        text = supplement.potentialBenefits.firstOrNull() ?: "N/A",
        fontSize = 11.sp,
        color = Charcoal,
        lineHeight = 15.sp
      )

      Spacer(modifier = Modifier.height(10.dp))
      Text(text = "Food Alternative:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SoftGold)
      Text(
        text = supplement.foodAlternatives.firstOrNull() ?: "N/A",
        fontSize = 11.sp,
        color = Charcoal,
        lineHeight = 15.sp
      )
    }
  }
}

@Composable
private fun BulletPointRow(text: String, checkmark: Boolean) {
  Row(
    verticalAlignment = Alignment.Top,
    modifier = Modifier.padding(vertical = 3.dp)
  ) {
    Icon(
      imageVector = if (checkmark) Icons.Default.Check else Icons.Default.Info,
      contentDescription = null,
      tint = if (checkmark) DeepForestGreen else CharcoalSecondary,
      modifier = Modifier
        .size(16.dp)
        .padding(top = 2.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = text,
      fontSize = 13.sp,
      color = Charcoal,
      lineHeight = 18.sp
    )
  }
}
