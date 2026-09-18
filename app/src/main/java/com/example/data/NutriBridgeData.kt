package com.example.data

object NutriBridgeData {

  val CATEGORIES = listOf(
    "All",
    "Protein",
    "Creatine",
    "Vitamins & Minerals",
    "Electrolytes",
    "Sports Nutrition",
    "Daily Wellness"
  )

  val SUPPLEMENTS: List<Supplement> = listOf(
    Supplement(
      id = "whey-isolate",
      name = "Whey Protein Isolate",
      category = "Protein",
      shortPurpose = "Fast-digesting protein to help muscles recover after workouts.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "Backed by decades of sports nutrition research. It delivers high-quality protein with almost zero fat or sugar, kicking off muscle recovery quickly.",
      quickVerdict = "Super handy on busy gym days, but 100% optional if you eat enough real food.",
      whoShouldUseIt = "Anyone lifting weights, active athletes, or busy folks struggling to eat enough protein.",
      potentialBenefits = listOf(
        "Sparks quick muscle repair and reduces post-workout soreness",
        "Very fast digestion with minimal bloating or lactose",
        "Super convenient post-gym shake in 30 seconds",
        "Helps keep you full when trying to cut down calories"
      ),
      limitations = listOf(
        "Not magical: chicken, eggs, and tofu build just as much muscle",
        "It's powdered food, so you won't get the chewing fullness of solid meals",
        "Dairy-based; folks with severe milk allergies should choose Pea or Rice protein"
      ),
      safetyConsiderations = listOf(
        "Completely safe for healthy kidneys when used normally (1-2 scoops/day)",
        "Look for NSF for Sport or Informed Choice seals to ensure clean purity"
      ),
      foodAlternatives = listOf(
        "3 large scrambled eggs 🍳 (18g protein)",
        "1 cup Greek yogurt or Skyr 🥣 (20g protein)",
        "Palm-sized chicken breast 🍗 (30g protein)",
        "1 cup edamame or cooked lentils 🌱 (18g protein)"
      ),
      dosageGuidance = "1 scoop (25-30g) mixed with water or milk after your workout, or stirred into morning oatmeal."
    ),
    Supplement(
      id = "creatine-mono",
      name = "Creatine Monohydrate",
      category = "Creatine",
      shortPurpose = "The #1 supplement for explosive gym strength, power, and muscle energy.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "The most thoroughly tested workout supplement in the world. It fuels your muscle cells with instant ATP energy during heavy sets and sprints.",
      quickVerdict = "One of the only supplements that genuinely works and costs less than 50 cents a day.",
      whoShouldUseIt = "Weightlifters, sprinters, HIIT athletes, and vegetarians wanting stronger gym performance.",
      potentialBenefits = listOf(
        "Helps you squeeze out 1 to 2 extra reps on heavy sets",
        "Noticeable jump in bench press, squat, and sprint power within 3 weeks",
        "Pulls water into muscle cells making muscles look fuller and feel stronger",
        "Emerging studies show sharper brain focus during stressful or tiring days"
      ),
      limitations = listOf(
        "Doesn't do much for long slow cardio (like marathon running)",
        "Adds 2-4 lbs of initial water weight inside muscles (which is healthy)",
        "A few people naturally have full creatine stores and feel smaller changes"
      ),
      safetyConsiderations = listOf(
        "Hundreds of long-term human studies prove it is completely safe",
        "No crazy loading needed: just 1 small scoop (5g) once a day does the trick",
        "Drink an extra glass of water each day to stay comfortably hydrated"
      ),
      foodAlternatives = listOf(
        "Fresh Atlantic herring or salmon 🐟 (approx. 1-2g per serving)",
        "Lean beef steak 🥩 (approx. 1g per 8oz)",
        "Note: You'd have to eat 2+ lbs of raw steak daily to match 1 simple scoop!"
      ),
      dosageGuidance = "Just 1 small teaspoon (3-5 grams) once daily with water, juice, or your protein shake. Any time of day is fine!"
    ),
    Supplement(
      id = "vitamin-d3-k2",
      name = "Vitamin D3 + K2",
      category = "Vitamins & Minerals",
      shortPurpose = "The 'Sunshine Vitamin' for bone strength, immunity, and daily mood.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "Vitamin D acts like a master hormone in your body. K2 makes sure calcium goes directly into your bones and teeth, rather than sticking to your blood vessels.",
      quickVerdict = "Essential if you work indoors or live where winters are cloudy.",
      whoShouldUseIt = "Desk workers, indoor fitness buffs, and anyone getting less than 20 minutes of daily sun.",
      potentialBenefits = listOf(
        "Fights off seasonal colds and boosts immune resilience",
        "Protects bone density and joint structure under heavy training",
        "Keeps energy levels, testosterone balance, and mood steady year-round"
      ),
      limitations = listOf(
        "Extra pills won't help if your blood test levels are already in the sweet spot",
        "Must be taken with food (like eggs or avocado) so your body can absorb it"
      ),
      safetyConsiderations = listOf(
        "Safe daily dose is between 1,000 to 2,000 IU for general wellness",
        "Get a simple blood test (25-OH Vitamin D) once a year at your physical"
      ),
      foodAlternatives = listOf(
        "15 minutes of direct sunshine on face, arms, and legs ☀️",
        "Wild-caught canned salmon or sardines 🐟 (600 IU)",
        "Egg yolks from pasture-raised hens 🥚"
      ),
      dosageGuidance = "1 softgel (1,000 - 2,000 IU) taken in the morning with your breakfast."
    ),
    Supplement(
      id = "magnesium-glycinate",
      name = "Magnesium Glycinate",
      category = "Vitamins & Minerals",
      shortPurpose = "Relaxes tight muscles, calms nighttime racing thoughts, and boosts deep sleep.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "Magnesium is used in over 300 body processes. The 'glycinate' form is bound to a soothing amino acid, making it super gentle on the stomach.",
      quickVerdict = "Your secret weapon for waking up feeling actually rested and recovering from sore legs.",
      whoShouldUseIt = "Anyone dealing with leg cramps, restless sleep, or high daytime stress.",
      potentialBenefits = listOf(
        "Helps you drift into restorative deep REM sleep faster",
        "Relieves nighttime calf and hamstring muscle twitches",
        "Calms evening anxiety and lowers physical tension"
      ),
      limitations = listOf(
        "It won't knock you out like a sleeping pill—it gently primes your nervous system",
        "Pills can be slightly bulky"
      ),
      safetyConsiderations = listOf(
        "Stick to 200 - 400 mg at night to avoid any loose digestion",
        "Glycinate is gentle, unlike cheap Magnesium Oxide which acts like a laxative"
      ),
      foodAlternatives = listOf(
        "Handful of roasted pumpkin seeds 🎃 (150mg)",
        "1 cup warm cooked spinach 🥬 (155mg)",
        "1 square 85% dark chocolate 🍫 (65mg)",
        "Half an avocado 🥑 (30mg)"
      ),
      dosageGuidance = "200 - 350 mg elemental magnesium taken 30-45 minutes before turning off the lights."
    ),
    Supplement(
      id = "hydration-electrolytes",
      name = "Daily Electrolyte Hydrator",
      category = "Electrolytes",
      shortPurpose = "Keeps your energy high and stops dehydration headaches during sweaty workouts.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "When you sweat, you lose salt and water together. Replacing both stops muscle cramping, brain fog, and midday fatigue.",
      quickVerdict = "Crucial for hard, sweaty sessions over 45 minutes; plain water is fine for easy walks.",
      whoShouldUseIt = "Runners, cyclists, gym trainees in warm gyms, and sauna lovers.",
      potentialBenefits = listOf(
        "Prevents nasty muscle spasms and exercise headaches",
        "Keeps power output high even when you're sweating buckets",
        "Absorbs into cells much faster than plain tap water"
      ),
      limitations = listOf(
        "Not needed for light 30-minute walks where water is plenty",
        "Watch out for sugary supermarket sports drinks loaded with 30g corn syrup"
      ),
      safetyConsiderations = listOf(
        "Choose zero-sugar formulas with sea salt, potassium, and magnesium"
      ),
      foodAlternatives = listOf(
        "Chilled fresh coconut water 🥥 with a tiny pinch of pink sea salt",
        "Juicy watermelon slices sprinkled with lime and salt 🍉",
        "Warm bone broth or light vegetable soup 🥣"
      ),
      dosageGuidance = "1 stick pack in your 24oz gym water bottle to sip before and during intense training."
    ),
    Supplement(
      id = "omega-3-epa-dha",
      name = "Clean Omega-3 Fish Oil",
      category = "Daily Wellness",
      shortPurpose = "Soothes aching joints, protects heart rhythm, and fights daily inflammation.",
      evidenceLevel = EvidenceLevel.STRONG,
      evidenceSummary = "Essential fatty acids (EPA & DHA) that humans cannot produce on their own. They lubricate joints and speed up muscle tissue repair.",
      quickVerdict = "Essential daily nourishment if you don't eat fatty fish twice a week.",
      whoShouldUseIt = "Anyone with creaky knees, sore joints from heavy lifting, or wanting sharp brain health.",
      potentialBenefits = listOf(
        "Eases morning joint stiffness and post-squat knee discomfort",
        "Protects cardiovascular heart health and healthy triglycerides",
        "Supports long-term memory, mood, and eye comfort"
      ),
      limitations = listOf(
        "Takes 3-4 weeks of daily use to feel noticeable joint relief",
        "Plant seeds (like chia or flax) only convert ~5% into active EPA/DHA"
      ),
      safetyConsiderations = listOf(
        "Check for third-party purity (IFOS 5-star) so there are no fishy burps or heavy metals"
      ),
      foodAlternatives = listOf(
        "2 delicious servings of grilled wild salmon or trout per week 🐟",
        "Canned sardines or mackerel on sourdough toast",
        "Algae oil capsules for 100% vegan diets 🌱"
      ),
      dosageGuidance = "1,000 - 2,000 mg combined EPA + DHA taken with dinner."
    )
  )

  val PROFESSIONALS: List<Professional> = listOf(
    Professional(
      id = "prof-1",
      name = "Dr. Elena Rostova, RD, CSSD",
      credentials = "Board-Certified Sports Dietitian",
      category = "Dietitian",
      specialty = "Endurance Athletics & Gut-Friendly Fueling",
      location = "Telehealth Nationwide",
      description = "Friendly sports nutrition expert who helps everyday runners and gym-goers fuel for energy without restrictive crazy diets.",
      rating = 4.96,
      reviewCount = 58,
      contactUrl = "https://nutribridge.app/practitioner/elena-rostova"
    ),
    Professional(
      id = "prof-2",
      name = "Marcus Vance, CSCS, CISSN",
      credentials = "Strength & Everyday Nutrition Coach",
      category = "Sports Nutritionist",
      specialty = "Muscle Building & Sustainable Meal Prep",
      location = "Online & Remote Coaching",
      description = "Specializes in realistic, high-protein meal routines for busy working adults who love lifting weights and want real results.",
      rating = 4.92,
      reviewCount = 84,
      contactUrl = "https://nutribridge.app/practitioner/marcus-vance"
    ),
    Professional(
      id = "prof-3",
      name = "Sarah Chen, MS, RD",
      credentials = "Plant-Based Performance Dietitian",
      category = "Dietitian",
      specialty = "Easy High-Protein Plant Meals",
      location = "Virtual Consultations",
      description = "Helps people hit their protein and vitamin targets on vegetarian and plant-forward diets with zero confusion or expensive fads.",
      rating = 4.98,
      reviewCount = 47,
      contactUrl = "https://nutribridge.app/practitioner/sarah-chen"
    )
  )

  fun generatePersonalizedDietPlan(
    goal: String,
    dietaryPref: String,
    budget: String,
    activityLevel: String
  ): DietPlan {
    val isMuscle = goal.contains("Muscle", ignoreCase = true)
    val isFatLoss = goal.contains("Fat", ignoreCase = true) || goal.contains("Loss", ignoreCase = true)
    val isVegan = dietaryPref.contains("Vegan", ignoreCase = true) || dietaryPref.contains("Plant", ignoreCase = true)

    val calories = when {
      isMuscle -> 2550
      isFatLoss -> 1950
      else -> 2250
    }

    val protein = when {
      isMuscle -> 165
      isFatLoss -> 155
      else -> 135
    }

    val carbs = when {
      isMuscle -> 295
      isFatLoss -> 180
      else -> 245
    }

    val fats = when {
      isMuscle -> 70
      isFatLoss -> 58
      else -> 68
    }

    val breakfast = if (isVegan) {
      MealItem(
        mealName = "Morning Fuel (7:30 AM)",
        title = "Power Berry Oatmeal Bowl 🍓",
        description = "Creamy rolled oats loaded with blueberries, chia seeds, and plant protein for 4 hours of steady morning energy.",
        calories = 480,
        protein = 25,
        carbs = 70,
        fats = 12,
        ingredients = listOf("1 cup warm rolled oats", "3 tbsp hemp hearts", "1 cup wild blueberries", "1 scoop plant protein or peanut butter", "Pinch of cinnamon")
      )
    } else {
      MealItem(
        mealName = "Morning Fuel (7:30 AM)",
        title = "Avocado Scramble & Golden Toast 🍳",
        description = "Fluffy scrambled eggs, sliced creamy avocado, and toasted sourdough to jumpstart muscle repair.",
        calories = 510,
        protein = 32,
        carbs = 38,
        fats = 24,
        ingredients = listOf("3 farm eggs", "1 slice sourdough toast", "1/2 ripe avocado", "Handful of baby spinach", "Dash of sea salt & pepper")
      )
    }

    val lunch = if (isVegan) {
      MealItem(
        mealName = "Power Lunch (12:30 PM)",
        title = "Rainbow Edamame & Quinoa Crunch Bowl 🥗",
        description = "Crisp, colorful lunch bowl with garlic tahini dressing, packed with iron and magnesium.",
        calories = 610,
        protein = 28,
        carbs = 76,
        fats = 20,
        ingredients = listOf("1 cup fluffy quinoa", "1 cup steamed edamame", "Chopped cucumbers & cherry tomatoes", "2 tbsp lemon tahini dressing")
      )
    } else {
      MealItem(
        mealName = "Power Lunch (12:30 PM)",
        title = "Honey-Garlic Chicken Sweet Potato Bowl 🍗",
        description = "Juicy seared chicken breast over roasted sweet potatoes with tender broccoli.",
        calories = 620,
        protein = 44,
        carbs = 58,
        fats = 22,
        ingredients = listOf("170g grilled chicken breast", "1 roasted Japanese sweet potato", "Steamed garlic broccoli", "1 tsp olive oil drizzle")
      )
    }

    val snack = MealItem(
      mealName = "Afternoon Recharge (4:00 PM)",
      title = "Greek Yogurt Crunch & Honey 🥣",
      description = "Rich, thick Greek yogurt topped with crunchy walnuts and a golden honey drizzle.",
      calories = 290,
      protein = 23,
      carbs = 24,
      fats = 11,
      ingredients = listOf("1 cup Greek yogurt (or almond skyr)", "Small handful walnuts", "1 tsp raw honey", "Dash of cinnamon")
    )

    val dinner = if (isVegan) {
      MealItem(
        mealName = "Evening Recovery (7:30 PM)",
        title = "Warm Coconut Lentil Curry & Jasmine Rice 🍛",
        description = "Cozy spiced red lentils and crispy seared tempeh that digests easily before bed.",
        calories = 650,
        protein = 34,
        carbs = 82,
        fats = 20,
        ingredients = listOf("1.5 cups yellow lentil curry", "100g seared tempeh strips", "1 cup fluffy jasmine rice", "Fresh cilantro")
      )
    } else {
      MealItem(
        mealName = "Evening Recovery (7:30 PM)",
        title = "Pan-Seared Salmon with Crispy Potatoes 🐟",
        description = "Omega-3 rich salmon with golden baby potatoes and asparagus spears for deep overnight recovery.",
        calories = 670,
        protein = 42,
        carbs = 54,
        fats = 26,
        ingredients = listOf("180g wild salmon fillet", "Handful of roasted baby potatoes", "Grilled asparagus spears", "Fresh lemon wedge")
      )
    }

    val groceries = listOf(
      "Eggs or Organic Firm Tofu 🍳",
      "Rolled Oats & Sourdough Bread 🍞",
      "Chicken Breast or Salmon Fillets 🍗",
      "Greek Yogurt or Almond Skyr 🥣",
      "Sweet Potatoes & Baby Potatoes 🥔",
      "Fresh Spinach & Broccoli 🥦",
      "Blueberries & Ripe Avocados 🥑",
      "Chia Seeds & Walnuts 🥜"
    )

    return DietPlan(
      id = "plan-${System.currentTimeMillis()}",
      title = "Real-Food $goal Plan",
      goal = goal,
      targetCalories = calories,
      proteinGrams = protein,
      carbsGrams = carbs,
      fatsGrams = fats,
      breakfast = breakfast,
      lunch = lunch,
      snack = snack,
      dinner = dinner,
      groceryList = groceries,
      foodFirstAdvice = "Real food does 90% of the heavy lifting. Nail these 3 simple meals and you won't need expensive pills!"
    )
  }

  fun getAssistantResponse(prompt: String): ChatMessage {
    val p = prompt.trim().lowercase()

    val (reply, tag) = when {
      p.contains("protein") || p.contains("powder") || p.contains("shake") -> {
        Pair(
          "🥛 **Do you actually need protein powder?**\n\n" +
          "**Short Answer:** No! But it is super convenient.\n\n" +
          "• **Real Food Wins:** 3 eggs gives you 18g protein. 1 cup Greek yogurt gives you 20g. A palm-sized chicken breast gives you 30g.\n" +
          "• **When Powder Helps:** If you're running late, traveling, or find it tough to eat 120-160g of protein from food alone, a single scoop after your workout is cheap and easy.\n" +
          "• **Quick Rule:** Aim to get 80% of your protein from real plates of food, and use powder only as a backup convenience.",
          "Protein Essentials"
        )
      }
      p.contains("creatine") -> {
        Pair(
          "⚡ **Creatine in 30 Seconds:**\n\n" +
          "• **What it is:** A natural fuel found in beef and fish that recharges your muscle energy batteries.\n" +
          "• **What you will feel:** In 2-3 weeks, you will be able to squeeze out 1 or 2 extra reps on your bench press, squat, or sprint.\n" +
          "• **How to take it:** 1 small scoop (5g) of Creatine Monohydrate daily with water. You don't need fancy loading phases!\n" +
          "• **Bonus:** It is the safest, most scientifically proven supplement on the planet.",
          "Creatine Guide"
        )
      }
      p.contains("pre-workout") || p.contains("preworkout") || p.contains("before workout") || p.contains("before gym") -> {
        Pair(
          "🍌 **Best Pre-Workout Food (30-60 mins before):**\n\n" +
          "• **Top Choice:** 1 banana with 1 tbsp peanut butter or rice cake with honey 🍯\n" +
          "• **Why it works:** Gives you fast, clean carbs that your muscles burn immediately for energy without feeling heavy in your stomach.\n" +
          "• **Hydration Check:** Drink a large glass of water with a tiny pinch of salt for instant gym pumps!",
          "Workout Fuel"
        )
      }
      p.contains("water") || p.contains("hydrate") || p.contains("cramp") -> {
        Pair(
          "💧 **Daily Hydration Made Easy:**\n\n" +
          "• **The Goal:** Drink roughly half your bodyweight in ounces of water per day (e.g., 160 lbs = ~80 oz / 2.5 liters).\n" +
          "• **Cramping?** If you sweat heavily, plain water isn't enough. Add a pinch of sea salt or sip coconut water to replenish sodium.\n" +
          "• **Attentive Tip:** Drink 1 full glass of water first thing when you wake up—it beats caffeine for clearing morning fog!",
          "Hydration Tip"
        )
      }
      p.contains("snack") || p.contains("night") || p.contains("sweet") || p.contains("caving") -> {
        Pair(
          "🥣 **Quick, Guilt-Free Night Snacks:**\n\n" +
          "1. **Greek Yogurt + Berries + Honey:** Rich in slow-digesting casein protein that feeds your muscles while you sleep.\n" +
          "2. **Apple slices + Almond butter:** Crunchy, satisfying sweet fiber with healthy fats.\n" +
          "3. **Chamomile tea + 1 square dark chocolate:** Helps lower evening cortisol and preps you for deep rest.",
          "Smart Snacks"
        )
      }
      p.contains("vegetarian") || p.contains("vegan") || p.contains("plant") -> {
        Pair(
          "🌱 **High-Protein Vegetarian Hacks:**\n\n" +
          "• **Tofu & Tempeh:** 20-25g protein per block, super versatile in stir fries!\n" +
          "• **Dry Lentils & Chickpeas:** Dirt cheap, loaded with muscle-building amino acids and gut-healthy fiber.\n" +
          "• **Edamame snack bowls:** 18g protein per cup with zero prep—just microwave and sprinkle with sea salt.\n" +
          "• **Hemp Hearts:** Sprinkle 3 tablespoons on your oats or salads for an effortless 10g of complete protein.",
          "Plant Protein"
        )
      }
      p.contains("label") || p.contains("read") || p.contains("ingredient") -> {
        Pair(
          "🔍 **How to Spot a Fake Supplement Label:**\n\n" +
          "1. **Beware 'Proprietary Blends':** If the bottle says 'Energy Matrix 5000mg' and hides the actual caffeine or beta-alanine amount, DO NOT BUY IT.\n" +
          "2. **Look for Badges:** Certified seals like 'NSF for Sport' or 'Informed Choice' guarantee zero hidden drugs or lead.\n" +
          "3. **Short is Sweet:** The fewer artificial food dyes and mystery chemicals, the better your stomach will feel.",
          "Label Detective"
        )
      }
      else -> {
        Pair(
          "👋 **Hey there! What can I help you with today?**\n\n" +
          "You can type or tap the **microphone 🎙️** to ask me anything in plain English, such as:\n\n" +
          "• \"What should I eat before my workout?\"\n" +
          "• \"Do I need protein powder or creatine?\"\n" +
          "• \"Healthy late night snack ideas?\"\n" +
          "• \"How to read a supplement bottle label?\"\n\n" +
          "I'm here to give you straightforward, food-first answers with zero confusing textbook jargon!",
          "Everyday Assistant"
        )
      }
    }

    return ChatMessage(
      id = "msg-${System.currentTimeMillis()}",
      text = reply,
      isUser = false,
      timestamp = "Just now",
      categoryTag = tag
    )
  }
}
