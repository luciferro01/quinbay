<script>
export default {
    data() {
        return {
            data: {},
        };
    },

    created() {
        try {
            fetch("https://jsonplaceholder.typicode.com/posts")
                .then((res) => {
                    return res.json();
                })
                .then((jsonRes) => {
                    this.data = jsonRes;
                });
        } catch (e) {
            console.log("Something error happened", e);
        }
    },
};
</script>

<template>
    <div v-for="user in data" class="user-block">
        <p><span class="block-heading">UserId : </span> {{ user.userId }}</p>
        <p>
            <span class="block-heading">Id of that User : </span> {{ user.id }}
        </p>
        <p><span class="block-heading">Title : </span> {{ user.title }}</p>
        <p><span class="block-heading">Body : </span> {{ user.body }}</p>
    </div>
</template>

<style scoped>
.user-block {
    background-color: #ccc;
    border-radius: 12px;
    /* padding-top: 8px;
    padding-left: 6px;
    padding-right: 6px;
    padding-bottom: 8px; */
    margin-bottom: 12px;
    padding: 8px 6px 6px 12px;
}
.block-heading {
    font-size: 16px;
    font-weight: bolder;
}
</style>
