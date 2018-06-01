<template>
    <div>
        <topic-header ref="ref1" :leftBtn="" ></topic-header>
        <div>
            <text>Shipping Cost</text>       <text>Rs.11.00</text>
            <text>Shipping Carrier</text>    <text>Rs.11.00</text>
            <text>Order Processing</text>    <text>Rs.11.00</text>
            <text>Shipping Time</text>       <text>Rs.11.00</text>
            <text>Estimasted Arrival</text>  <text>Rs.11.00</text>
        </div>
    </div>

</template>

<script>
    import header from './header';
    export default {
        components: {
            'topic-header': header
        },
        name: 'ship'

    }
</script>

<style scoped>

</style>
