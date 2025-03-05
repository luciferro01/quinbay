// meal_details.js
const mealBannerImage = document.getElementById("meal-banner-image");
const mealTitle = document.getElementById("meal-title");
const mealIngredients = document.getElementById("meal-ingredients"); // This will now be a container for the list
const mealInstructions = document.getElementById("meal-instructions");
const mealYoutubeLink = document.getElementById("meal-youtube-link");
const backButton = document.getElementById("back-button");

const API_BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const mealId = urlParams.get("id");
  console.log("Meal ID from URL:", mealId); // Debug log

  if (mealId) {
    fetchMealDetails(mealId);
  } else {
    console.error("Meal ID is missing from URL.");
    alert("Meal ID is missing.");
  }
});

async function fetchMealDetails(mealId) {
  try {
    const response = await fetch(`${API_BASE_URL}lookup.php?i=${mealId}`);
    if (!response.ok) {
      // Check if response status is not in the success range (200-299)
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    console.log("API Response Data:", data); // Debug log

    if (data.meals && data.meals[0]) {
      displayMealDetails(data.meals[0]);
    } else {
      console.warn("No meal details found in API response for ID:", mealId);
      alert("Meal details not found.");
    }
  } catch (error) {
    console.error("Error fetching meal details:", error);
    alert("Failed to load meal details.");
  }
}

function displayMealDetails(meal) {
  console.log("Meal data received for display:", meal); // Debug log
  mealBannerImage.src = meal.strMealThumb;
  mealBannerImage.alt = meal.strMeal;
  mealTitle.textContent = meal.strMeal;

  // Ingredients List (using UL > LI)
  mealIngredients.innerHTML = ""; // Clear any existing content in the ingredients container
  const ingredientsList = document.createElement("ul");
  for (let i = 1; i <= 20; i++) {
    // MealsDB has up to 20 ingredients/measures
    const ingredient = meal[`strIngredient${i}`];
    const measure = meal[`strMeasure${i}`];
    if (ingredient && ingredient.trim() !== "") {
      const listItem = document.createElement("li");
      listItem.textContent = `${ingredient} - ${measure}`;
      ingredientsList.appendChild(listItem);
    }
  }
  if (ingredientsList.children.length === 0) {
    const noIngredientsItem = document.createElement("li");
    noIngredientsItem.textContent = "Ingredients not available.";
    ingredientsList.appendChild(noIngredientsItem);
  }
  mealIngredients.appendChild(ingredientsList); // Append the UL to the ingredients container

  mealInstructions.textContent = meal.strInstructions;
  mealYoutubeLink.href = meal.strYoutube || "#"; // Use '#' if no YouTube link
  mealYoutubeLink.style.display = meal.strYoutube ? "inline-block" : "none"; // Hide if no link
}

backButton.addEventListener("click", () => {
  window.location.href = "index.html"; // Go back to the main page
});
