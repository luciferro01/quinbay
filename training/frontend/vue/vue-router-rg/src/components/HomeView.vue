<script>
export default {
    data() {
        console.log(this);
        return {
            param: 0,
        };
    },

    methods: {
        navigate() {
            console.log(this.$router);
            this.$router.push({
                path: "/withPath",
                params: { id: "1324" },
                // query: { user: "Mohil" },
                // hash: "#1234",
            });
        },
    },
};
</script>

<template>
    <div>
        <h1>Welcome to HomeView!</h1>
        <p>{{ $route.params.id }}</p>
    </div>
</template>
