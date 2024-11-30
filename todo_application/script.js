// DOM Elements
const todoValue = document.getElementById("todoText");
const todoAlert = document.getElementById("Alert");
const listItems = document.getElementById("list-items");
const addUpdate = document.getElementById("AddUpdateClick");
const totalTasks = document.getElementById("total-tasks");
const completedTasks = document.getElementById("completed-tasks");

const url = "http://localhost:8080";
let updateId = null;
let todo = [];

// Initializing and Fetching To-Do List
function initializeToDoList() {
  fetch(`${url}/api/todo`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to fetch To-Do list");
      }
      return response.json();
    })
    .then((data) => {
      todo = Array.isArray(data) ? data : [];
      renderToDoList();
    })
    .catch((error) => {
      console.error("Error fetching todos:", error);
      setAlertMessage("Unable to fetch To-Do list.");
    });
}

// Rendering To-Do List
function renderToDoList() {
  listItems.innerHTML = ""; // Clear the list
  todo.forEach((item) => {
    const li = document.createElement("li");
    li.innerHTML = `
      <div title="Double-click to complete">
        <input 
          type="checkbox" 
          class="todo-checkbox" 
          onclick="toggleCompletion(this)" 
          data-id="${item.id}"
          ${item.completed ? "checked" : ""} 
        />
        <span ${item.completed ? 'style="text-decoration: line-through;"' : ""}>
          ${item.task || ""}
        </span>
      </div>
      <div>
        ${
          !item.completed
            ? `<img class="edit todo-controls" onclick="updateToDoItem(this)" data-id="${item.id}" src="./images/pencil.png" />`
            : ""
        }
        <img class="delete todo-controls" onclick="deleteToDoItem(this)" data-id="${
          item.id
        }" src="./images/delete.png" />
      </div>
    `;
    listItems.appendChild(li);
  });
  updateTaskCounts();
}

// Creating  Alert Message
function setAlertMessage(message) {
  todoAlert.innerText = message;
  todoAlert.classList.remove("toggleMe");
  setTimeout(() => {
    todoAlert.classList.add("toggleMe");
  }, 2000);
}

// Creating To-Do Item
function createToDoItem() {
  console.log("Create To-Do Item Called...");
  const text = todoValue.value.trim();

  // Input validation
  if (!text) {
    setAlertMessage("Please enter your To-Do item!");
    return;
  }

  if (text.length > 255) {
    // Add max length validation
    setAlertMessage("To-Do item is too long!");
    return;
  }

  // Check for duplicates
  if (todo.some((item) => item.task.toLowerCase() === text.toLowerCase())) {
    setAlertMessage("This item already exists!");
    return;
  }

  // Disabling input during API call
  todoValue.disabled = true;
  const addButton = document.getElementById("AddUpdateClick");
  addButton.style.opacity = "0.5";

  const newItem = {
    task: text,
    completed: false,
  };

  fetch(`${url}/api/todo`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify(newItem),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((err) => Promise.reject(err));
      }
      return response.json();
    })
    .then((data) => {
      todo.push(data);
      renderToDoList();
      todoValue.value = "";
      todoValue.focus();
      setAlertMessage("To-Do item created successfully!");
    })
    .catch((error) => {
      console.error("Error creating To-Do:", error);
      setAlertMessage(error.message || "Unable to create To-Do.");
    })
    .finally(() => {
      // Re-enable input
      todoValue.disabled = false;
      addButton.style.opacity = "1";
    });
}

// Updating To-Do Item
function updateToDoItem(e) {
  const id = e.dataset.id;
  const currentText =
    e.parentElement.parentElement.querySelector("span").innerText;

  todoValue.value = currentText;
  updateId = id;
  addUpdate.setAttribute("onclick", "saveUpdatedToDoItem()");
  addUpdate.setAttribute("src", "./images/reuse.png");
  todoValue.focus();
}
function saveUpdatedToDoItem() {
  const updatedText = todoValue.value.trim();

  // Validation checks
  if (!updatedText) {
    setAlertMessage("Please enter updated text!");
    return;
  }

  // Check for duplicates excluding current item
  if (todo.some((item) => item.task === updatedText && item.id !== updateId)) {
    setAlertMessage("This updated text already exists!");
    return;
  }

  const itemToUpdate = todo.find((item) => item.id === updateId);
  if (itemToUpdate) {
    // Only send required updates
    const updates = {
      task: updatedText,
      completed: itemToUpdate.completed,
    };

    fetch(`${url}/api/todo/${updateId}`, {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updates),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to update To-Do item");
        }
        return response.json();
      })
      .then((updatedItem) => {
        // Update local state with response data
        const index = todo.findIndex((item) => item.id === updateId);
        if (index !== -1) {
          todo[index] = updatedItem;
        }
        resetUpdateForm();
        renderToDoList();
        setAlertMessage("To-Do item updated successfully!");
      })
      .catch((error) => {
        console.error("Error updating To-Do:", error);
        setAlertMessage("Unable to update To-Do.");
      });
  }
}

// Adding helper function to reset form
function resetUpdateForm() {
  updateId = null;
  todoValue.value = "";
  addUpdate.setAttribute("onclick", "createToDoItem()");
  addUpdate.setAttribute("src", "./images/plus-sign.png");
}

// Deleting To-Do Item
function deleteToDoItem(e) {
  const id = e.dataset.id;

  // Find the task text to show in confirmation
  const taskText =
    e.parentElement.parentElement.querySelector("span").innerText;

  const isConfirmed = window.confirm(
    `Are you sure you want to delete "${taskText}"?`
  );

  // Only proceed if user confirmed
  if (isConfirmed) {
    fetch(`${url}/api/todo/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to delete To-Do item");
        }
        todo = todo.filter((item) => item.id !== id);
        renderToDoList();
        setAlertMessage("To-Do item deleted successfully!");
      })
      .catch((error) => {
        console.error("Error deleting To-Do:", error);
        setAlertMessage("Unable to delete To-Do.");
      });
  }
}

// Toggle Completed Status
function toggleCompletion(checkbox) {
  const id = checkbox.dataset.id;
  const itemToToggle = todo.find((item) => item.id === id);

  if (itemToToggle) {
    itemToToggle.completed = checkbox.checked;

    fetch(`${url}/api/todo/${id}/status`, {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ completed: checkbox.checked }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to toggle status");
        }
        renderToDoList();
        setAlertMessage(
          `To-Do item marked as ${
            checkbox.checked ? "completed" : "incomplete"
          }!`
        );
      })
      .catch((error) => {
        console.error("Error toggling status:", error);
        setAlertMessage("Unable to update status.");
      });
  }
}

// Updating Task Counts
function updateTaskCounts() {
  totalTasks.innerText = `Total Tasks: ${todo.length}`;
  completedTasks.innerText = `Completed: ${
    todo.filter((item) => item.completed).length
  }`;
}

// Initializing the DOM to get the list of tasks on the initial page load.
document.addEventListener("DOMContentLoaded", () => {
  initializeToDoList();
});
