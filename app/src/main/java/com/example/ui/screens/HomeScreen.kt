package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppTab
import com.example.ui.components.LegalDialog
import com.example.ui.components.SectionHeader
import com.example.ui.components.TrustDisclaimerBanner
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

data class NutritionInsight(
  val title: String,
  val subtitle: String,
  val tag: String,
  val icon: ImageVector,
  val detailedArticle: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  onNavigateTab: (AppTab) -> Unit,
  onPlayClick: () -> Unit,
  onTriggerHaptic: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedInsight by remember { mutableStateOf<NutritionInsight?>(null) }

  val insights = listOf(
    NutritionInsight(
      title = "Protein Made Simple 🍳",
      subtitle = "Real food first! How much you actually need without choking down 5 chalky shakes.",
      tag = "Everyday Staple",
      icon = Icons.Default.FitnessCenter,
      detailedArticle = "💡 Quick Takeaway:\n\n• Most active people only need 0.7 to 1.0 gram of protein per pound of body weight.\n• 3 eggs = 18g. 1 cup Greek yogurt = 20g. Chicken breast = 30g.\n• Protein shakes are great when you're in a rush, but real food keeps you fuller and gives you vital micronutrients like zinc and iron that powder lacks!"
    ),
    NutritionInsight(
      title = "The Creatine Truth ⚡",
      subtitle = "What 30+ years of research says, how to take it safely, and why it works.",
      tag = "Top Gym Pick",
      icon = Icons.Default.AutoAwesome,
      detailedArticle = "💡 Quick Takeaway:\n\n• Creatine is naturally found in steak and fish. It fuels fast ATP energy in your muscles.\n• 1 teaspoon (5g) of simple Creatine Monohydrate daily helps you get 1-2 extra reps on heavy lifts.\n• Completely safe for healthy adults. Drink normal amounts of water and skip expensive fancy formulas!"
    ),
    NutritionInsight(
      title = "Beat the 3PM Crash 💧",
      subtitle = "The secret of electrolytes, simple hydration, and steady afternoon energy.",
      tag = "Daily Energy",
      icon = Icons.Default.LocalDrink,
      detailedArticle = "💡 Quick Takeaway:\n\n• Feeling sluggish around 3 PM? You're probably dehydrated, not tired!\n• Drinking plain water can sometimes flush out salt if you've been sweating. A pinch of sea salt in your water or fresh coconut water restores cellular balance fast.\n• Start your morning with a large glass of water before your first coffee."
    ),
    NutritionInsight(
      title = "Spot Fake Labels 🔍",
      subtitle = "Don't get tricked by 'Proprietary Blends' and mystery powder claims.",
      tag = "Smart Shopping",
      icon = Icons.Default.Shield,
      detailedArticle = "💡 Quick Takeaway:\n\n• If a label says 'Mega Pump Blend: 4,000mg' without listing exact ingredient amounts, put it back!\n• Always look for NSF Certified for Sport or Informed Choice seals.\n• The best supplements have simple, transparent labels with zero artificial food dyes."
    )
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("home_screen_scroll"),
    contentPadding = PaddingValues(bottom = 24.dp)
  ) {

    // ==========================================
    // SECTION 1: WELCOME & HERO
    // ==========================================
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 16.dp)
      ) {
        // Tagline Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(GoldLight)
            .border(0.8.dp, SoftGold.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = SoftGold,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "EVIDENCE-INFORMED DISCOVERY",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp,
              color = DeepForestGreen
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Headline
        Text(
          text = "Smarter Nutrition.\nBetter Fitness Decisions.",
          style = MaterialTheme.typography.displayMedium,
          color = DeepForestGreen,
          lineHeight = 38.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle
        Text(
          text = "Understand your nutritional needs, explore supplements with confidence, and build practical diet plans.",
          style = MaterialTheme.typography.bodyLarge,
          color = CharcoalSecondary,
          lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Hero Graphic Asset
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = CardWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_nutribridge_hero_1789736749655),
            contentDescription = "NutriBridge Wellness and Whole Food Nutrition Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.PLANNER)
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = DeepForestGreen,
              contentColor = CardWhite
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("create_plan_button")
          ) {
            Text(
              text = "Create My Plan",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp
            )
          }

          OutlinedButton(
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.EXPLORE)
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepForestGreen),
            border = androidx.compose.foundation.BorderStroke(1.dp, DeepForestGreen),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("explore_supplements_button")
          ) {
            Text(
              text = "Explore Supplements",
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // ==========================================
    // SECTION 2: QUICK ACTIONS
    // ==========================================
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        SectionHeader(
          title = "Quick Actions",
          subtitle = "Direct access to tools and voice assistant"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Prominent Voice Assistant Feature Banner
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = SageLight),
          border = androidx.compose.foundation.BorderStroke(1.dp, MutedSage.copy(alpha = 0.5f)),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.ASSISTANT)
            }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(DeepForestGreen),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = null,
                tint = SoftGold,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Voice Assistant Ready",
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = DeepForestGreen
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GoldLight)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "AUDIO ENABLED",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Charcoal
                  )
                }
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Tap to ask everyday questions via microphone or hear answers read aloud.",
                fontSize = 12.sp,
                color = CharcoalSecondary,
                lineHeight = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickActionCard(
            title = "Voice AI Assistant",
            caption = "Speak or type in plain English",
            icon = Icons.Default.AutoAwesome,
            accentColor = DeepForestGreen,
            modifier = Modifier.weight(1f),
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.ASSISTANT)
            }
          )
          QuickActionCard(
            title = "Diet Planner",
            caption = "Personalized food-first plans",
            icon = Icons.Default.DateRange,
            accentColor = SoftGold,
            modifier = Modifier.weight(1f),
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.PLANNER)
            }
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          QuickActionCard(
            title = "Supplement Explorer",
            caption = "Compare verified research",
            icon = Icons.Default.Search,
            accentColor = MutedSage,
            modifier = Modifier.weight(1f),
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.EXPLORE)
            }
          )
          QuickActionCard(
            title = "Find Professionals",
            caption = "Accredited sports dietitians",
            icon = Icons.Default.People,
            accentColor = DeepForestGreen,
            modifier = Modifier.weight(1f),
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.CONNECT)
            }
          )
        }
      }
    }

    // ==========================================
    // SECTION 3: NUTRITION INSIGHTS
    // ==========================================
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp)
      ) {
        PaddingValues(horizontal = 20.dp).let {
          Column(modifier = Modifier.padding(it)) {
            SectionHeader(
              title = "Nutrition Insights",
              subtitle = "Bite-sized, peer-reviewed educational topics"
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
          contentPadding = PaddingValues(horizontal = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(insights) { insight ->
            Card(
              modifier = Modifier
                .width(260.dp)
                .clickable {
                  onPlayClick()
                  onTriggerHaptic()
                  selectedInsight = insight
                },
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = CardWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
              elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(SageLight),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = insight.icon,
                      contentDescription = null,
                      tint = DeepForestGreen,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                  Text(
                    text = insight.tag,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SoftGold
                  )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                  text = insight.title,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = Charcoal
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = insight.subtitle,
                  fontSize = 12.sp,
                  color = CharcoalSecondary,
                  lineHeight = 17.sp,
                  maxLines = 3
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Read Guide",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepForestGreen
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = DeepForestGreen,
                    modifier = Modifier.size(12.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // ==========================================
    // SECTION 4: FEATURE HIGHLIGHTS
    // ==========================================
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 14.dp)
      ) {
        SectionHeader(
          title = "Why NutriBridge?",
          subtitle = "Built on scientific rigor, complete transparency, and human connections"
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = CardWhite),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            HighlightRow(
              icon = Icons.Default.Restaurant,
              title = "Personalized Nutrition Guidance",
              description = "Food-first diet frameworks tailored to your actual cooking setup, allergies, and training volume."
            )
            Spacer(modifier = Modifier.height(14.dp))
            HighlightRow(
              icon = Icons.Default.Shield,
              title = "Supplement Education",
              description = "Evidence-level grading on human clinical trials so you never waste money on ineffective trends."
            )
            Spacer(modifier = Modifier.height(14.dp))
            HighlightRow(
              icon = Icons.Default.CompareArrows,
              title = "Ingredient Comparison",
              description = "Side-by-side analysis of bioavailable active forms versus cheap synthetic derivatives."
            )
            Spacer(modifier = Modifier.height(14.dp))
            HighlightRow(
              icon = Icons.Default.Verified,
              title = "Professional Discovery",
              description = "Direct pathways to board-certified Registered Dietitians and accredited sports performance coaches."
            )
          }
        }
      }
    }

    // ==========================================
    // SECTION 5: TRUST SECTION
    // ==========================================
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
      ) {
        TrustDisclaimerBanner()
      }
    }

    // ==========================================
    // SECTION 6: CALL TO ACTION
    // ==========================================
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepForestGreen)
      ) {
        Column(
          modifier = Modifier.padding(22.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Start Your Nutrition Journey",
            style = MaterialTheme.typography.headlineSmall.copy(
              fontFamily = FontFamily.SansSerif,
              fontWeight = FontWeight.Bold
            ),
            color = CardWhite
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Create an evidence-based, food-first blueprint crafted around your training and metabolic goals.",
            style = MaterialTheme.typography.bodyMedium,
            color = SageLight,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 19.sp
          )
          Spacer(modifier = Modifier.height(18.dp))
          Button(
            onClick = {
              onPlayClick()
              onTriggerHaptic()
              onNavigateTab(AppTab.PLANNER)
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = SoftGold,
              contentColor = Charcoal
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("home_cta_create_plan")
          ) {
            Text(
              text = "Create My Plan",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }
  }

  // Insight detail modal
  selectedInsight?.let { insight ->
    LegalDialog(
      title = insight.title,
      content = insight.detailedArticle,
      onDismiss = { selectedInsight = null }
    )
  }
}

@Composable
private fun QuickActionCard(
  title: String,
  caption: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .height(112.dp)
      .clickable { onClick() },
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = CardWhite),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(SageLight),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = DeepForestGreen,
          modifier = Modifier.size(18.dp)
        )
      }
      Column {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = Charcoal
        )
        Text(
          text = caption,
          fontSize = 11.sp,
          color = CharcoalMuted
        )
      }
    }
  }
}

@Composable
private fun HighlightRow(
  icon: ImageVector,
  title: String,
  description: String
) {
  Row(verticalAlignment = Alignment.Top) {
    Box(
      modifier = Modifier
        .size(28.dp)
        .clip(CircleShape)
        .background(SageLight),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = DeepForestGreen,
        modifier = Modifier.size(16.dp)
      )
    }
    Spacer(modifier = Modifier.width(12.dp))
    Column {
      Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = Charcoal
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = description,
        fontSize = 12.sp,
        color = CharcoalSecondary,
        lineHeight = 17.sp
      )
    }
  }
}
