<script>
import ChildComponent from "./ChildComponent.vue";
export default {
    components: {
        ChildComponent,
    },
};
</script>

<template>
    <div class="user">
        <h2>User {{ $route.params.id }}</h2>
        <ChildComponent>
            <template #header>
                <p>It is the slot header</p>
            </template>
            <div>
                <h3>Child Component Content</h3>
                <p>It is passed from Parent to the Child Slot</p>
            </div>
            <template #footer>
                <p>It is the slot footer</p>
            </template>
        </ChildComponent>
        <router-view />
    </div>
</template>
