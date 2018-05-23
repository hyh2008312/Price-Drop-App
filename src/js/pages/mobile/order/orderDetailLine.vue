<template>
    <div class="wrapper">
        <image class="image" src="bmlocal://assets/rectangle-line.png" v-for="i in list"></image>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                list: [1, 2, 3, 4, 5, 6, 7, 8]
            }
        }
    }
</script>
<style scoped>

    .wrapper {
        background-color: #fff;
        width: 750px;
        height: 12px;
        flex-direction: row;
    }

    .image{
        width: 104px;
        height: 12px;
    }


</style>
