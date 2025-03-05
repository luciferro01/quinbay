<script>
import FetchApp from "./FetchApp.vue";

export default {
    components: {
        FetchApp,
    },
    data() {
        return {
            message: "Hello Vue!",
            overrideCounter: 0,
            show: true,
            students: [
                { name: "John", age: 20 },
                { name: "Jane", age: 22 },
                { name: "Bob", age: 25 },
            ],
        };
    },
    methods: {
        updateMessage(newMessage) {
            this.message = newMessage;
        },
        overrideClicked() {
            console.log(this.message);
            this.overrideCounter++;
        },
        updateMessage(event) {
            this.message = event.target.value;
        },
    },
};
</script>

<template>
    <!-- <div>
        <input v-bind:value="message" @input="updateMessage($event)" />
        <p>{{ message }}</p>
        <input v-model="message" />
        <button @click="overrideClicked">Override</button>
        <div>
            <p>Override Counter: {{ overrideCounter }}</p>
        </div>
        <button @click="show = !show">
            {{ show ? "Hide" : "Show" }}
        </button>
        <h1 v-if="show">Button is clicked.</h1>
        <h1 v-else>Oh no</h1>
        <h1 v-show="show">Button is shown.</h1>

        <div v-for="(student, key, index) of students">
            <p>
                Index : {{ index }}, key {{ student.key }} Name:
                {{ student.name }}
            </p>
            <p>
                Index : {{ index }}, key {{ student.key }} Age:
                {{ student.age }}
            </p>
        </div>
        <span v-for="n in 10">{{ n }}</span>
    </div> -->
    <FetchApp />
</template>

<style scoped></style>
