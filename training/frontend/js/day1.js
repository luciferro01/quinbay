// function findMax(n1, n2, n3, n4, n5) {
//   return Math.max(n1, n2, n3, n4, n5);
// }
// console.log(findMax(12, 3, 5, 7, 9));

// const findSum = function (n1, n2, n3, n4, n5) {
//   return n1 + n2 + n3 + n4 + n5;
// };

// console.log(findSum(12, 3, 5, 7, 9));

// const multiply = (n1, n2) => n1 * n2;
// console.log(multiply(12, 3));

// const book = {
//   title: "JavaScript",
//   author: "Mohil bansal",
//   price: 2000,
//   isAvailable: false,
// };

// // console.log(book);

// const inventoryList = [];

// const inventory = {
//   addBook: function (book) {
//     return inventoryList.push(book);
//   },
//   getBooks: function () {
//     return inventoryList;
//   },
//   findBookByTitle: function (title) {
//     return inventoryList.find((book) => book.title === title);
//   },

//   removeBook: function (title) {
//     const index = inventoryList.findIndex((book) => book.title === title);
//     return inventoryList.splice(index, 1);
//   },
//   updateBookByTtile: function (title, book) {
//     const index = inventoryList.findIndex((book) => book.title === title);
//     return (inventoryList[index] = book);
//   },
//   listAvailableBooks: function () {
//     return inventoryList.filter((book) => book.isAvailable);
//   },
//   discountToAllBooks: function (discount) {
//     return inventoryList.map((book) => {
//       book.price = book.price - discount;
//       return book;
//     });
//   },
//   sortBooksByPrice: function () {
//     return inventoryList.sort((a, b) => a.price - b.price);
//   },
// };

// inventory.addBook({
//   title: "JavaScript",
//   author: "Mohil bansal",
//   price: 2000,
//   isAvailable: false,
// });

// inventory.addBook({
//   title: "Dart & Flutter",
//   author: "Mohil bansal",
//   price: 2000,
//   isAvailable: false,
// });

// inventory.addBook({
//   title: "Kotlin",
//   author: "Mohil bansal",
//   price: 2000,
//   isAvailable: false,
// });

// inventory.addBook({
//   title: "Swift Os",
//   author: "Mohil bansal",
//   price: 2000,
//   isAvailable: false,
// });

// // console.log(inventory.findBookByTitle("Dart & Flutter"));

// console.log(
//   "------------------------Finding Book by JavaScript------------------------"
// );
// console.log(inventory.findBookByTitle("JavaScript"));

// console.log(
//   "------------------------Removing Dart & Flutter------------------------"
// );
// console.log(inventory.removeBook("Dart & Flutter"));
// console.log(
//   "------------------------Updating JavaScript------------------------"
// );
// console.log(
//   inventory.updateBookByTtile("JavaScript", {
//     title: "JavaScript",
//     author: "Mohil Bansal",
//     price: 2400,
//     isAvailable: true,
//   })
// );

// console.log("------------------------After Update------------------------");
// // inventoryList.pop();
// // inventoryList.pop();
// console.log(inventory.getBooks());

// console.log(
//   "------------------------Listing All Available Books------------------------"
// );
// console.log(inventory.listAvailableBooks());

// console.log(
//   "------------------------Discount on all Books------------------------"
// );
// console.log(inventory.discountToAllBooks(2000));
// console.log("------------------------Sort by Price------------------------");
// console.log(inventory.sortBooksByPrice());

// const student = {
//   name: "Mohil",
//   age: 21,
//   grade: 85,
// };
// const students = [];

// const studentList = {
//   addStudent: function (student) {
//     return students.push(student);
//   },
//   getStudents: function () {
//     return students;
//   },
//   removeLastStudent: function () {
//     return students.pop();
//   },
//   filterByAge: function (age) {
//     return students.filter((student) => student.age > age);
//   },
//   getArrayOfGrades: function () {
//     return students.map((student) => student.grade);
//   },
//   avgGrade: function () {
//     return (
//       students.reduce((acc, curr) => acc + curr.grade, 0) / students.length
//     );
//   },
//   sortByDescendingGrades: function () {
//     return students.sort((a, b) => b.grade - a.grade);
//   },
//   arrayOfStduents: function (noOfStudents) {
//     return students.slice(0, noOfStudents);
//   },
// };

// studentList.addStudent({
//   name: "Mohil",
//   age: 21,
//   grade: 85,
// });

// studentList.addStudent({
//   name: "Sandeep",
//   age: 21,
//   grade: 60,
// });
// studentList.addStudent({
//   name: "Prakshenjay",
//   age: 18,
//   grade: 60,
// });
// console.log(studentList.filterByAge(18));
// console.log(studentList.getArrayOfGrades());
// console.log(studentList.avgGrade());
// console.log(studentList.sortByDescendingGrades());
// console.log(studentList.arrayOfStduents(3));

// const delayExecution = new Promise((resolve, reject) => {
//   setTimeout(() => {}, 3000);
//   resolve("Mohil");
// }).then((res) => console.log(res));
//
// function printMessage(message) {
//   console.log(message);
//   return message;
// }

// function delayExecution(timer, message, printMessage) {
//   const promise = new Promise((resolve, reject) => {
//     setTimeout(() => {
//       resolve(printMessage(message));
//       // resolve("Mohil");
//     }, timer);
//   });
//   promise
//     .then((data) => {
//       console.log("It is after the promise is resolved :", data);
//     })
//     .catch((e) => console.log(e))
//     .finally(() => console.log("It exits finally"));
// }

// function checkAsyncBehaviour(timer) {
//   setTimeout(() => {
//     console.log("Inside the Set Timeout");
//   }, timer);
//   console.log("Outside the Set Timeout");
// }
// delayExecution(5000, "Mohil this side", printMessage);
// checkAsyncBehaviour(5000);
//
//
// // Fetch Example
async function fetchUrlWithPromises(search) {
  fetch(`https://www.themealdb.com/api/json/v1/1/search.php?s=${search}`)
    // .then((response) => {
    //   console.log(response);
    //   response.json();
    // })
    .then((data) => console.log(data))
    .catch((err) => console.log(err))
    .finally(() => console.log("It just worked fine"));
}
async function fetchUrlWithAsync(search) {
  const response = await fetch(
    `https://www.themealdb.com/api/json/v1/1/search.php?s=${search}`
  );
  console.log(response);
  // .then((data) => console.log(data))
  // .catch((err) => console.log(err))
  // .finally(() => console.log("It just worked fine"));
}

// fetchUrlWithPromises("Sushi");
fetchUrlWithAsync("Sushi");
