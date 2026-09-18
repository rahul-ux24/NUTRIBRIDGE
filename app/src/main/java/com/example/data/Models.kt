package com.example.data

enum class EvidenceLevel(val label: String, val description: String) {
  STRONG("Strong Evidence", "Supported by multiple randomized controlled trials and meta-analyses"),
  MODERATE("Moderate Evidence", "Supported by promising clinical trials; context dependent"),
  PRELIMINARY("Preliminary Evidence", "Emerging research with mechanistic or early clinical data")
}

data class Supplement(
  val id: String,
  val name: String,
  val category: String,
  val shortPurpose: String,
  val evidenceLevel: EvidenceLevel,
  val evidenceSummary: String,
  val potentialBenefits: List<String>,
  val limitations: List<String>,
  val safetyConsiderations: List<String>,
  val foodAlternatives: List<String>,
  val dosageGuidance: String,
  val quickVerdict: String = "",
  val whoShouldUseIt: String = "",
  val isFavorite: Boolean = false
)

data class MealItem(
  val mealName: String,
  val title: String,
  val description: String,
  val calories: Int,
  val protein: Int,
  val carbs: Int,
  val fats: Int,
  val ingredients: List<String>
)

data class DietPlan(
  val id: String,
  val title: String,
  val goal: String,
  val targetCalories: Int,
  val proteinGrams: Int,
  val carbsGrams: Int,
  val fatsGrams: Int,
  val breakfast: MealItem,
  val lunch: MealItem,
  val snack: MealItem,
  val dinner: MealItem,
  val groceryList: List<String>,
  val foodFirstAdvice: String,
  val createdAt: String = "Today"
)

data class Professional(
  val id: String,
  val name: String,
  val credentials: String,
  val category: String,
  val specialty: String,
  val location: String,
  val description: String,
  val isVerified: Boolean = true,
  val rating: Double = 4.9,
  val reviewCount: Int = 42,
  val contactUrl: String = "https://nutribridge.app/partners"
)

data class ChatMessage(
  val id: String,
  val text: String,
  val isUser: Boolean,
  val timestamp: String,
  val categoryTag: String? = null
)

data class UserPreferences(
  val name: String = "Alex Morgan",
  val email: String = "alex.morgan@example.com",
  val fitnessGoal: String = "Muscle Growth & Recovery",
  val activityLevel: String = "Moderately Active (3-4 days/week)",
  val dietaryPreference: String = "High-Protein Balanced",
  val foodBudget: String = "Moderate",
  val soundEnabled: Boolean = true,
  val hapticEnabled: Boolean = true,
  val notificationsEnabled: Boolean = true,
  val savedSupplementIds: Set<String> = setOf("whey-isolate", "creatine-mono"),
  val savedProfessionalIds: Set<String> = setOf("prof-1")
)

enum class AppTab(val title: String) {
  HOME("Home"),
  EXPLORE("Explore"),
  PLANNER("Planner"),
  ASSISTANT("Assistant"),
  CONNECT("Connect"),
  PROFILE("Profile")
}
