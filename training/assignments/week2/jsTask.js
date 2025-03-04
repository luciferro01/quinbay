const person = {
  name: "John Doe",
  age: 30,
  city: "BTP",
  greet: function () {
    console.log(
      `Hello, my name is ${this.name} and I am ${this.age} years old.`,
    );
    console.log(this);
  },
  greetArrow: () => {
    console.log(this);
  },
};

// person.greet();
person.greetArrow();

// const numbersList = [1, 2, 3, 4, 5, 6];
// numbersList.map((n) => 2 * n).forEach((n) => console.log(n));
// numbersList.filter((n) => n > 10).forEach((n) => console.log(n));
// console.log(
//   "The sum of numbersList is : " +
//     numbersList.reduce((acc, cum) => acc + cum, 0),
// );
