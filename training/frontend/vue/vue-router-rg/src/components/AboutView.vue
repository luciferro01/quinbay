<script>
export default {
    data() {
        // console.log(this);
        return {
            param: 0,
            tryParam: this.$route.params.id,
        };
    },
    watch: {
        param(val) {
            if (val == 5) {
                console.log("Hey's it's 5");
            }
        },
    },
    beforeCreate() {
        // console.log(this.$route.params.id);
        // console.log(tryParam);
        // this.param = this.$route.params.id;
    },

    // mounted() {
    //     console.log(this.$route);
    //     this.param = this.$route.params.id;
    // },
    methods: {
        navigate() {
            console.log(this.$router);
            this.$router.push({
                path: "/withPath",
                // params: { id: "1324" },
                query: { user: "Mohil" },
                // hash: "#1234",
            });
        },
    },
};
</script>

<template>
    <div>
        <h1>Welcome to AboutView!</h1>
        <p>{{ JSON.stringify($route.params) }}</p>
        <!-- <Router> -->
        <!-- <p></p> -->
        <button @click="navigate">Navigate to HomeView</button>
    </div>
</template>
