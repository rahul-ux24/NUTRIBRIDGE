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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.data.DietPlan
import com.example.data.MealItem
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlannerScreen(
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Plan Generator, 1: Saved Plans

  // Form State
  var fitnessGoal by remember { mutableStateOf("Muscle Growth & Hypertrophy") }
  var activityLevel by remember { mutableStateOf("Moderately Active (3-4x/week)") }
  var dietaryPref by remember { mutableStateOf("High-Protein Omnivore") }
  var foodBudget by remember { mutableStateOf("Moderate") }
  var cookingFacility by remember { mutableStateOf("Full Kitchen Access") }
  var allergy by remember { mutableStateOf("None") }

  var isEditingPreferences by remember { mutableStateOf(false) }

  // Current Plan State
  var currentPlan by remember {
    mutableStateOf(
      NutriBridgeData.generatePersonalizedDietPlan(fitnessGoal, dietaryPref, foodBudget, activityLevel)
    )
  }

  val savedPlans = remember { mutableStateListOf<DietPlan>() }
  val checkedGroceries = remember { mutableStateMapOf<String, Boolean>() }

  val isCurrentPlanSaved = savedPlans.any { it.id == currentPlan.id }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Header
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
      SectionHeader(
        title = "Personalized Diet Planner",
        subtitle = "Whole-food first protocols with complete macro breakdowns"
      )

      Spacer(modifier = Modifier.height(10.dp))

      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = CardWhite,
        contentColor = DeepForestGreen,
        indicator = { tabPositions ->
          SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = DeepForestGreen
          )
        },
        modifier = Modifier.clip(RoundedCornerShape(10.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            selectedTab = 0
          },
          text = { Text("Active Meal Plan", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = {
            onPlayClick()
            onTriggerHaptic()
            selectedTab = 1
          },
          text = { Text("Saved Plans (${savedPlans.size})", fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }
        )
      }
    }

    if (selectedTab == 1) {
      // Saved Plans View
      if (savedPlans.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(48.dp), tint = MutedSage)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "No saved meal plans yet", fontWeight = FontWeight.Bold, color = Charcoal)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Generate and save a plan from the Active Meal Plan tab.", fontSize = 13.sp, color = CharcoalMuted)
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          items(savedPlans) { plan ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = CardWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(text = plan.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Charcoal)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Target: ${plan.targetCalories} kcal • ${plan.proteinGrams}g Protein • ${plan.carbsGrams}g Carbs • ${plan.fatsGrams}g Fats", fontSize = 12.sp, color = DeepForestGreen)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Button(
                    onClick = {
                      onPlayClick()
                      onTriggerHaptic()
                      currentPlan = plan
                      selectedTab = 0
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                  ) {
                    Text("Load This Plan", fontSize = 12.sp, color = CardWhite)
                  }
                  OutlinedButton(
                    onClick = {
                      onPlayClick()
                      onTriggerHaptic()
                      savedPlans.remove(plan)
                    },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                  ) {
                    Text("Delete", fontSize = 12.sp, color = CharcoalSecondary)
                  }
                }
              }
            }
          }
        }
      }
    } else {
      // Active Plan View
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Preference Controls / Collapsible Card
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "Personal Parameters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Charcoal
                  )
                  Text(
                    text = "$fitnessGoal • $dietaryPref",
                    fontSize = 12.sp,
                    color = CharcoalMuted
                  )
                }

                TextButton(
                  onClick = {
                    onPlayClick()
                    onTriggerHaptic()
                    isEditingPreferences = !isEditingPreferences
                  }
                ) {
                  Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp), tint = DeepForestGreen)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = if (isEditingPreferences) "Collapse" else "Edit",
                    color = DeepForestGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                  )
                }
              }

              AnimatedVisibility(visible = isEditingPreferences) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                  // Goals
                  Text("Fitness Goal:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Charcoal)
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                  ) {
                    listOf("Muscle Growth & Hypertrophy", "Fat Loss & Conditioning", "Endurance & Stamina", "Vitality & Longevity").forEach { goalOption ->
                      val isSel = fitnessGoal == goalOption
                      FilterChip(
                        selected = isSel,
                        onClick = { fitnessGoal = goalOption },
                        label = { Text(goalOption, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                          selectedContainerColor = DeepForestGreen,
                          selectedLabelColor = CardWhite
                        )
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(8.dp))

                  // Dietary Pref
                  Text("Dietary Preference:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Charcoal)
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                  ) {
                    listOf("High-Protein Omnivore", "Vegetarian", "Vegan / 100% Plant", "Pescatarian", "Mediterranean").forEach { dietOption ->
                      val isSel = dietaryPref == dietOption
                      FilterChip(
                        selected = isSel,
                        onClick = { dietaryPref = dietOption },
                        label = { Text(dietOption, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                          selectedContainerColor = DeepForestGreen,
                          selectedLabelColor = CardWhite
                        )
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(10.dp))

                  Button(
                    onClick = {
                      onPlayClick()
                      onTriggerHaptic()
                      currentPlan = NutriBridgeData.generatePersonalizedDietPlan(fitnessGoal, dietaryPref, foodBudget, activityLevel)
                      checkedGroceries.clear()
                      isEditingPreferences = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Regenerate Plan with Updated Parameters", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }

        // Plan Header & Nutrition Targets Card
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DeepForestGreen),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = currentPlan.title,
                  style = MaterialTheme.typography.headlineSmall,
                  color = CardWhite,
                  modifier = Modifier.weight(1f)
                )

                IconButton(
                  onClick = {
                    onPlayClick()
                    onTriggerHaptic()
                    if (isCurrentPlanSaved) {
                      savedPlans.removeAll { it.id == currentPlan.id }
                    } else {
                      savedPlans.add(currentPlan)
                    }
                  }
                ) {
                  Icon(
                    imageVector = if (isCurrentPlanSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Save Plan",
                    tint = SoftGold
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Daily Targets Grid
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                TargetPill(label = "Calories", value = "${currentPlan.targetCalories}", unit = "kcal")
                TargetPill(label = "Protein", value = "${currentPlan.proteinGrams}", unit = "g")
                TargetPill(label = "Carbs", value = "${currentPlan.carbsGrams}", unit = "g")
                TargetPill(label = "Fats", value = "${currentPlan.fatsGrams}", unit = "g")
              }
            }
          }
        }

        // Daily Meal Schedule
        item {
          Text(
            text = "Structured Daily Meals",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Charcoal,
            modifier = Modifier.padding(top = 6.dp)
          )
        }

        // Breakfast
        item { MealCard(meal = currentPlan.breakfast) }

        // Lunch
        item { MealCard(meal = currentPlan.lunch) }

        // Snack
        item { MealCard(meal = currentPlan.snack) }

        // Dinner
        item { MealCard(meal = currentPlan.dinner) }

        // Grocery Checklist
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = DeepForestGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Smart Grocery Checklist",
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = Charcoal
                )
              }
              Spacer(modifier = Modifier.height(10.dp))

              currentPlan.groceryList.forEach { item ->
                val checked = checkedGroceries[item] == true
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      onPlayClick()
                      checkedGroceries[item] = !checked
                    }
                    .padding(vertical = 4.dp)
                ) {
                  Checkbox(
                    checked = checked,
                    onCheckedChange = {
                      onPlayClick()
                      checkedGroceries[item] = it
                    },
                    colors = CheckboxDefaults.colors(
                      checkedColor = DeepForestGreen,
                      uncheckedColor = CharcoalSecondary
                    )
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = item,
                    fontSize = 13.sp,
                    color = if (checked) CharcoalMuted else Charcoal,
                    style = if (checked) MaterialTheme.typography.bodyMedium.copy(
                      textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                    ) else MaterialTheme.typography.bodyMedium
                  )
                }
              }
            }
          }
        }

        // Disclaimer requirement
        item {
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = GoldLight),
            border = androidx.compose.foundation.BorderStroke(1.dp, SoftGold.copy(alpha = 0.4f)),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("planner_disclaimer_card")
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.Top
            ) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SoftGold,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "Nutrition Disclaimer: This tool provides general nutrition information and is not a substitute for professional medical or dietary advice.",
                fontSize = 11.sp,
                color = Charcoal,
                lineHeight = 16.sp
              )
            }
          }
        }

        // Action Buttons Row (Save Plan / Regenerate)
        item {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = {
                onPlayClick()
                onTriggerHaptic()
                if (!isCurrentPlanSaved) {
                  savedPlans.add(currentPlan)
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
            ) {
              Icon(imageVector = if (isCurrentPlanSaved) Icons.Default.Check else Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(if (isCurrentPlanSaved) "Plan Saved" else "Save Plan", fontSize = 13.sp)
            }

            OutlinedButton(
              onClick = {
                onPlayClick()
                onTriggerHaptic()
                currentPlan = NutriBridgeData.generatePersonalizedDietPlan(fitnessGoal, dietaryPref, foodBudget, activityLevel)
                checkedGroceries.clear()
              },
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, DeepForestGreen),
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
            ) {
              Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = DeepForestGreen)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Regenerate", fontSize = 13.sp, color = DeepForestGreen)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun TargetPill(
  label: String,
  value: String,
  unit: String
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(10.dp))
      .background(Color.White.copy(alpha = 0.12f))
      .padding(horizontal = 10.dp, vertical = 8.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = label, fontSize = 10.sp, color = SageLight)
      Spacer(modifier = Modifier.height(2.dp))
      Row(verticalAlignment = Alignment.Bottom) {
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = CardWhite)
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = unit, fontSize = 10.sp, color = SoftGold)
      }
    }
  }
}

@Composable
private fun MealCard(meal: MealItem) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = CardWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = meal.mealName,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = DeepForestGreen
        )
        Text(
          text = "${meal.calories} kcal • ${meal.protein}g Protein",
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = CharcoalMuted
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = meal.title,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = Charcoal
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = meal.description,
        fontSize = 12.sp,
        color = CharcoalSecondary,
        lineHeight = 17.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Ingredients chips
      Text(text = "Whole-Food Ingredients:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Charcoal)
      Spacer(modifier = Modifier.height(4.dp))
      meal.ingredients.forEach { ing ->
        Text(
          text = "• $ing",
          fontSize = 11.sp,
          color = CharcoalSecondary,
          modifier = Modifier.padding(vertical = 1.dp)
        )
      }
    }
  }
}
