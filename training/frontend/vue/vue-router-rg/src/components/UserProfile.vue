<script>
export default {};
</script>

<template>
    <div class="user">
        <button @click="$router.replace('/user/123')">User Profile</button>

        <h2>User Profile {{ $route.params.id }}</h2>
    </div>
</template>
