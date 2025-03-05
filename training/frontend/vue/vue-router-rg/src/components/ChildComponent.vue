<script>
export default {};
</script>

<template>
    <div class="child">
        <h1>Child Slot</h1>
        <slot name="header" />
        <div class="slot-container">
            <slot />
        </div>
        <slot name="footer" />
    </div>
</template>

<style scoped>
.child {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.slot-container {
    padding: 10px;
    border: 1px dashed #ccc;
    border-radius: 5px;
}
</style>
