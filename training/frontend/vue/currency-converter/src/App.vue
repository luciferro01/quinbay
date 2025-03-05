<script>
export default {
    data() {
        return {
            currency: 0,
        };
    },
    watch: {
        currency() {
            console.log(this.currency);
        },
    },
    computed: {
        convertCurrencyToUSD() {
            return (this.currency * 81.0).toFixed(2);
        },
    },
};
</script>

<template>
    <input type="number" v-model="currency" />
    <h4>
        Converted value to usd is :
        <span>${{ convertCurrencyToUSD }}</span>
    </h4>
</template>

<style></style>
