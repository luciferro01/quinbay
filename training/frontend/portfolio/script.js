// setTimeout(() => {}, 3000);
const emailInput = document.getElementById("email");
const nameInput = document.getElementById("name");
const messageInput = document.getElementById("message");
const submitButton = document.getElementById("submit");

emailInput.placeholder = "mohil.bansal@gdn-commerce.com";
nameInput.placeholder = "Mohil Bansal";
messageInput.placeholder =
  "Hello, I am Mohil Bansal. I am a software engineer with a passion for building scalable and efficient systems.";
submitButton.addEventListener("click", () => {
  console.log("Form submitted");
  console.log("Email:", emailInput.value);
  console.log("Name:", nameInput.value);
  console.log("Message:", messageInput.value);
  const ul = document.createElement("ul");
  document.body.appendChild(ul);
  let skill = prompt("Enter your skill");
  if (skill) {
    addListItem(ul, skill);
  }

  addListItem(ul, emailInput.value);
  addListItem(ul, nameInput.value);
  addListItem(ul, messageInput.value);
  // addListItem(submitButton, submitButton.value);
});

function addListItem(ul, text) {
  if (text != null && text != "") {
    const li = document.createElement("li");
    li.textContent = text;
    ul.appendChild(li);
    return li;
  }
}
