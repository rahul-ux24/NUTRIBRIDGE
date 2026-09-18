package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.EvidenceLevel
import com.example.data.NutriBridgeData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("NutriBridge", appName)
  }

  @Test
  fun `nutribridge data repository has evidence graded supplements`() {
    val supplements = NutriBridgeData.SUPPLEMENTS
    assertTrue("Supplements repository should not be empty", supplements.isNotEmpty())
    assertTrue("Whey isolate exists", supplements.any { it.id == "whey-isolate" })
    assertTrue("Creatine exists with strong evidence", supplements.any { it.id == "creatine-mono" && it.evidenceLevel == EvidenceLevel.STRONG })
  }

  @Test
  fun `generate personalized diet plan produces valid macro allocations`() {
    val plan = NutriBridgeData.generatePersonalizedDietPlan(
      goal = "Muscle Growth & Hypertrophy",
      dietaryPref = "High-Protein Omnivore",
      budget = "Moderate",
      activityLevel = "Moderately Active"
    )

    assertNotNull(plan)
    assertTrue("Target calories should be positive", plan.targetCalories > 2000)
    assertTrue("Protein should be high for muscle growth", plan.proteinGrams >= 150)
    assertTrue("Grocery list should be populated", plan.groceryList.isNotEmpty())
    assertNotNull(plan.breakfast)
    assertNotNull(plan.lunch)
    assertNotNull(plan.dinner)
  }

  @Test
  fun `assistant response handles educational queries safely`() {
    val reply = NutriBridgeData.getAssistantResponse("Do I need protein powder?")
    assertNotNull(reply)
    assertTrue("Assistant response should provide educational guidance", reply.text.contains("Real Food", ignoreCase = true) || reply.text.contains("protein", ignoreCase = true))
  }
}

