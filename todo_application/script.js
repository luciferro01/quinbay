const todoValue = document.getElementById("todoText");
const todoAlert = document.getElementById("Alert");
const listItems = document.getElementById("list-items");
const addUpdate = document.getElementById("AddUpdateClick");

let todo = JSON.parse(localStorage.getItem("todo-list"));
if (!todo) {
  todo = [];
}

function setAlertMessage(message) {
  todoAlert.removeAttribute("class");
  todoAlert.innerText = message;
  setTimeout(() => {
    todoAlert.classList.add("toggleMe");
  }, 1000);
}

function CreateToDoItems() {
  if (todoValue.value === "") {
    todoAlert.innerText = "Please enter your todo text!";
    todoValue.focus();
  } else {
    let IsPresent = false;
    todo.forEach((element) => {
      if (element.item == todoValue.value) {
        IsPresent = true;
      }
    });

    if (IsPresent) {
      setAlertMessage("This item already present in the list!");
      return;
    }

    let li = document.createElement("li");

    const todoItems = `
      <div title="Hit Double Click and Complete" ondblclick="CompletedToDoItems(this)">
        <input type="checkbox" class="todo-checkbox" onclick="toggleCompletion(this)" />
        <span>${todoValue.value}</span>
      </div>
      <div>
        <img class="edit todo-controls" onclick="UpdateToDoItems(this)" src="./images/pencil.png" />
        <img class="delete todo-controls" onclick="DeleteToDoItems(this)" src="./images/delete.png" />
      </div>
    `;
    li.innerHTML = todoItems;
    listItems.appendChild(li);

    if (!todo) {
      todo = [];
    }
    let itemList = { item: todoValue.value, status: false };
    todo.push(itemList);
    setLocalStorage();
  }
  todoValue.value = "";
  setAlertMessage("Todo item Created Successfully!");
}

function ReadToDoItems() {
  todo.forEach((element) => {
    let li = document.createElement("li");

    // Set the checkbox state based on the item's completion status
    const isChecked = element.status ? "checked" : "";

    const todoItems = `
      <div title="Hit Double Click and Complete" ondblclick="CompletedToDoItems(this)">
        <input type="checkbox" class="todo-checkbox" onclick="toggleCompletion(this)" ${isChecked} />
        <span ${element.status ? 'style="text-decoration: line-through;"' : ""}>
          ${element.item}
        </span>
      </div>
      <div>
        ${
          !element.status
            ? '<img class="edit todo-controls" onclick="UpdateToDoItems(this)" src="./images/pencil.png" />'
            : ""
        }
        <img class="delete todo-controls" onclick="DeleteToDoItems(this)" src="./images/delete.png" />
      </div>
    `;

    li.innerHTML = todoItems;
    listItems.appendChild(li);
  });
}
ReadToDoItems();

function UpdateToDoItems(e) {
  console.log("Update todo item");
  if (
    e.parentElement.parentElement.querySelector("div").style.textDecoration ===
    ""
  ) {
    todoValue.value =
      e.parentElement.parentElement.querySelector("div").innerText;
    updateText = e.parentElement.parentElement.querySelector("div");
    addUpdate.setAttribute("onclick", "UpdateOnSelectionItems()");
    addUpdate.setAttribute("src", "./images/reuse.png");
    todoValue.focus();
  }
}

function UpdateOnSelectionItems() {
  let IsPresent = false;
  todo.forEach((element) => {
    if (element.item == todoValue.value) {
      IsPresent = true;
    }
  });

  if (IsPresent) {
    setAlertMessage("This item already present in the list!");
    return;
  }

  todo.forEach((element) => {
    if (element.item == updateText.innerText.trim()) {
      element.item = todoValue.value;
    }
  });
  setLocalStorage();

  updateText.innerText = todoValue.value;
  addUpdate.setAttribute("onclick", "CreateToDoItems()");
  addUpdate.setAttribute("src", "./images/plus-sign.png");
  todoValue.value = "";
  setAlertMessage("Todo item Updated Successfully!");
}

function DeleteToDoItems(e) {
  let deleteValue =
    e.parentElement.parentElement.querySelector("div").innerText;

  if (confirm(`Are you sure. Due you want to delete this ${deleteValue}!`)) {
    e.parentElement.parentElement.setAttribute("class", "deleted-item");
    todoValue.focus();

    todo.forEach((element) => {
      if (element.item == deleteValue.trim()) {
        todo.splice(element, 1);
      }
    });

    setTimeout(() => {
      e.parentElement.parentElement.remove();
    }, 1000);

    setLocalStorage();
  }
}

function CompletedToDoItems(e) {
  console.log("Completed todo item");
  if (e.parentElement.querySelector("div").style.textDecoration === "") {
    const img = document.createElement("img");
    img.src = "./images/check.png";
    img.className = "todo-controls";
    e.parentElement.querySelector("div").style.textDecoration = "line-through";
    e.parentElement.querySelector("div").appendChild(img);
    e.parentElement.querySelector("img.edit").remove();

    todo.forEach((element) => {
      if (
        e.parentElement.querySelector("div").innerText.trim() == element.item
      ) {
        element.status = true;
      }
    });
    setLocalStorage();
    setAlertMessage("Todo item Completed Successfully!");
  }
}

function toggleCompletion(checkbox) {
  const todoItemText = checkbox.parentElement.querySelector("span");
  const taskText = todoItemText.innerText.trim();
  const todoControlsDiv =
    checkbox.parentElement.parentElement.querySelector("div:last-child");

  // Update the status in the todo array
  todo.forEach((element) => {
    if (element.item === taskText) {
      element.status = checkbox.checked;
    }
  });

  if (checkbox.checked) {
    todoItemText.style.textDecoration = "line-through";

    const editIcon = todoControlsDiv.querySelector(".edit");
    if (editIcon) {
      editIcon.remove();
    }

    if (!todoControlsDiv.querySelector(".completed-icon")) {
      const completedIcon = document.createElement("img");
      completedIcon.src = "./images/check.png";
      completedIcon.className = "todo-controls completed-icon";
      todoControlsDiv.appendChild(completedIcon);
    }
  } else {
    todoItemText.style.textDecoration = "none";

    const completedIcon = todoControlsDiv.querySelector(".completed-icon");
    if (completedIcon) {
      completedIcon.remove();
    }

    if (!todoControlsDiv.querySelector(".edit")) {
      const editIcon = document.createElement("img");
      editIcon.src = "./images/pencil.png";
      editIcon.className = "edit todo-controls";
      editIcon.setAttribute("onclick", "UpdateToDoItems(this)");
      todoControlsDiv.insertBefore(editIcon, todoControlsDiv.firstChild);
    }
  }

  setLocalStorage();
  setAlertMessage(
    `Todo item marked as ${checkbox.checked ? "Completed" : "Incomplete"}!`
  );
}

function setLocalStorage() {
  localStorage.setItem("todo-list", JSON.stringify(todo));
}
