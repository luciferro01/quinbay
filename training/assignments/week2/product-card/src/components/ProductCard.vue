<script>
export default {
    data() {
        return {
            itemCount: 0,
            // Don't redeclare variables that are coming from the props
            // will give problems only remember - sad face
            // cartValue: 0,
            // discountedPrice: 0,
        };
    },
    props: {
        name: { type: [String, null], required: true },
        price: { type: [Number], required: true },
        image: { type: String, required: true },
        discount: { type: [Number, null], required: false },
    },
    methods: {
        addToCart() {
            this.itemCount++;
            this.emitCartUpdates();
        },
        removeFromCart() {
            if (this.itemCount > 0) {
                this.itemCount--;
                this.emitCartUpdates();
            }
        },
        emitCartUpdates() {
            this.$emit("cart-item-count", this.itemCount);
            this.$emit("cart-value", this.cartValue);
        },
    },
    computed: {
        discountedPrice() {
            return this.discount > 0
                ? this.price * (1 - this.discount / 100)
                : this.price;
        },
        cartValue() {
            return this.itemCount * this.discountedPrice;
        },
    },
};
</script>

<template>
    <div class="product-container">
        <img :src="image" alt="Product Card Image" class="product-image" />
        <div>
            <p>Product Name : {{ name }}</p>
            <p v-if="discount && discount > 0">
                Discounted Price :
                <span class="price" style="text-decoration: line-through">
                    {{ price }}
                </span>
                <span class="discount-price">{{
                    discountedPrice.toFixed(2)
                }}</span>
            </p>
            <p v-else>Price : {{ price }}</p>
            <p>Cart Count : {{ itemCount }}</p>
            <div class="cart-buttons">
                <button @click="addToCart" class="button">Add to Cart</button>
                <button @click="removeFromCart" class="button">
                    Remove from Cart
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.product-container {
    width: 40%;
    height: 60vh;
    border-radius: 12px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: 1px solid #ccc;
    padding: 16px;
    /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); */
}

.product-image {
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
    width: 100%;
    height: 40vh;
    object-fit: fill;
}
.price {
    font-weight: lighter;
}
.discount-price {
    padding-left: 10px;
    font-weight: bold;
}
.product-container p {
    color: white;
    font-size: 16px;
    font-weight: 600;
    margin: 8px 0;
}
.cart-buttons {
    display: flex;
    flex-direction: row;
    gap: 20px;
}
.button {
    border-radius: 10px;
    color: black;
    padding: 8px 16px;
    border: 1px solid #ccc;
    background-color: #f0f0f0;
    cursor: pointer;
}
.button:hover {
    background-color: #e0e0e0;
}
</style>
