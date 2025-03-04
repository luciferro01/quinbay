<script>
export default {
    data() {
        return {
            count: 0,
        };
    },
    methods: {
        increment() {
            this.count++;
        },
        decrement() {
            this.count--;
        },
        isPrime(num) {
            if (num <= 1) return false;
            if (num === 2) return true;
            for (let i = 2; i <= Math.sqrt(num); i++) {
                console.log(i);
                if (num % i === 0) return false;
            }
            return true;
        },
    },
};
</script>

<template class="main">
    <div class="header">
        <h1>Counter Application</h1>
        <div class="counter">
            <button @click="increment" class="button">Increment (+)</button>
            <span :style="{ color: count % 2 === 0 ? 'green' : 'red' }">{{
                count
            }}</span>
            <button @click="decrement" class="button">Decrement (-)</button>
        </div>
        <div class="count-message">
            <p v-if="isPrime(count)">Count is Prime</p>
            <p v-else-if="count % 2 === 0">Count is Even</p>
            <p v-else>Count is Odd</p>
        </div>
    </div>
</template>

<style scoped>
.main {
    width: 100%;
    height: 100%;
}
.header {
    /* line-height: 1.5; */
    display: flex;
    flex-direction: column;
    align-content: center;
    justify-content: center;
}
.counter {
    display: flex;
    padding: 20px;
}

.counter * {
    padding: 20px;
    font-weight: bold;
}
.counter button {
    cursor: pointer;
}
</style>
