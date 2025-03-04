<!-- Think of it again about css not working properly with this image Test later for
experimentation purpose only not to display -->
<!-- image="https://www.shutterstock.com/image-photo/cool-water-mens-perfume-davidoff-600nw-2204315471.jpg" -->

<script>
import ProductCard from "./components/ProductCard.vue";

export default {
    data() {
        return {
            cartItemCount: 0,
            cartValue: 0,
            products: [
                {
                    name: "Purity Purfume",
                    price: 2000,
                    image: "https://www.zzzone.co.uk/wp-content/uploads/2021/05/Creative-Product-Photography-2.jpg",
                    discount: 2,
                },
                {
                    name: "Safari Perfume",
                    price: 2000,
                    image: "https://th.bing.com/th/id/OIP.zRuZNau5liSvC7fF1VJc5AHaHa?rs=1&pid=ImgDetMain",
                },
                {
                    name: "Water Perfume",
                    price: 2000,
                    image: "https://www.myperfumeshop.com.au/cdn/shop/files/DAVIDOFF_COOL_WATER_edt_-_Google_Search_-_4.jpg?v=1670593188&width=1500",
                },
            ],
        };
    },
    components: {
        ProductCard,
    },
    methods: {
        receiveCartItemCount(cartItemCount) {
            this.cartItemCount = cartItemCount;
        },
        receiveCartValue(cartValue) {
            this.cartValue = cartValue;
        },
    },
};
</script>

<template>
    <div class="main">
        <h1>Product Cart Assignment - ha ha ha jingle bells</h1>
        <p>Cart Item Count : {{ cartItemCount }}</p>
        <p>Cart Value : {{ cartValue }}</p>
        <ProductCard
            v-for="product in products"
            :key="product.name"
            :name="product.name"
            :price="product.price"
            :image="product.image"
            :discount="product.discount"
            @cart-value="receiveCartValue"
            @cart-item-count="receiveCartItemCount"
        />
    </div>
</template>

<style scoped>
.main {
    /* height: 90vh; */
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 50px;
    scroll-snap-type: y mandatory;
}
</style>
