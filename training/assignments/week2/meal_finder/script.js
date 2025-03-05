const searchInput = document.getElementById("search-input");
const categoriesContainer = document.getElementById("categories-container");
const mealsContainer = document.getElementById("meals-container");
const categoryDropdown = document.getElementById("category-dropdown");

const API_BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

let selectedCategoryItem = null;

async function fetchCategories() {
  try {
    const response = await fetch(API_BASE_URL + "categories.php");
    const data = await response.json();
    displayCategories(data.categories);

    fetchMealsByCategory("Miscellaneous");

    highlightInitialCategory("Miscellaneous", data.categories);
  } catch (error) {
    console.error("Error fetching categories:", error);
  }
}

function highlightInitialCategory(initialCategoryName, categories) {
  const initialCategory = categories.find(
    (cat) => cat.strCategory === initialCategoryName
  );
  if (initialCategory) {
    const categoryElements =
      categoriesContainer.querySelectorAll(".category-item");
    categoryElements.forEach((element) => {
      if (element.querySelector("span").textContent === initialCategoryName) {
        highlightCategory(element);
      }
    });
  }
}

function displayCategories(categories) {
  categoriesContainer.innerHTML = "";
  categoryDropdown.innerHTML =
    '<option value="">-- Select Category --</option>';

  categories.forEach((category) => {
    const categoryItem = document.createElement("div");
    categoryItem.classList.add("category-item");
    categoryItem.innerHTML = `
            <img src="${category.strCategoryThumb}" alt="${category.strCategory}">
            <span>${category.strCategory}</span>
        `;
    categoryItem.addEventListener("click", () => {
      fetchMealsByCategory(category.strCategory);
      highlightCategory(categoryItem);
      categoryDropdown.value = "";
    });
    categoriesContainer.appendChild(categoryItem);

    const dropdownOption = document.createElement("option");
    dropdownOption.value = category.strCategory;
    dropdownOption.textContent = category.strCategory;
    categoryDropdown.appendChild(dropdownOption);
  });
}

function highlightCategory(categoryElement) {
  if (selectedCategoryItem) {
    selectedCategoryItem.classList.remove("selected");
  }
  categoryElement.classList.add("selected");
  selectedCategoryItem = categoryElement;
}

async function fetchMealsByCategory(category) {
  try {
    const response = await fetch(`${API_BASE_URL}filter.php?c=${category}`);
    const data = await response.json();
    displayMeals(data.meals);
  } catch (error) {
    console.error("Error fetching meals by category:", error);
  }
}

async function searchMeals(searchTerm) {
  try {
    const response = await fetch(`${API_BASE_URL}search.php?s=${searchTerm}`);
    const data = await response.json();
    displayMeals(data.meals);
  } catch (error) {
    console.error("Error searching meals:", error);
  }
}

function displayMeals(meals) {
  mealsContainer.innerHTML = "";
  if (!meals || meals.length === 0) {
    mealsContainer.innerHTML = "<p>No meals found in this category.</p>";
    return;
  }
  meals.forEach((meal) => {
    const mealCard = document.createElement("div");
    mealCard.classList.add("meal-card");
    mealCard.innerHTML = `
            <img src="${meal.strMealThumb}" alt="${meal.strMeal}">
            <h3>${meal.strMeal}</h3>
        `;
    mealCard.addEventListener("click", () => {
      window.location.href = `meal_details.html?id=${meal.idMeal}`;
    });
    mealsContainer.appendChild(mealCard);
  });
}

searchInput.addEventListener("input", (e) => {
  const searchTerm = e.target.value.trim();
  if (searchTerm) {
    searchMeals(searchTerm);
    if (selectedCategoryItem) {
      selectedCategoryItem.classList.remove("selected");
      selectedCategoryItem = null;
    }
    categoryDropdown.value = "";
  } else {
    fetchCategories();
    mealsContainer.innerHTML = "";
    if (selectedCategoryItem) {
    }
  }
});

categoryDropdown.addEventListener("change", (e) => {
  const selectedCategoryName = e.target.value;
  if (selectedCategoryName) {
    fetchMealsByCategory(selectedCategoryName);

    const categoryElements =
      categoriesContainer.querySelectorAll(".category-item");
    categoryElements.forEach((element) => {
      if (element.querySelector("span").textContent === selectedCategoryName) {
        highlightCategory(element);
      } else {
        element.classList.remove("selected");
      }
    });
    if (!categoryElements || categoryElements.length === 0) {
      if (selectedCategoryItem) {
        selectedCategoryItem.classList.remove("selected");
        selectedCategoryItem = null;
      }
    }
    searchInput.value = "";
  } else {
    fetchMealsByCategory("Miscellaneous");
    if (selectedCategoryItem) {
      selectedCategoryItem.classList.remove("selected");
      selectedCategoryItem = null;
    }
  }
});

fetchCategories();
