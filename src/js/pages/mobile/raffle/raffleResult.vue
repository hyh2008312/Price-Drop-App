<template>
    <div class="wrapper">
        <topic-header title="Prize Winners" leftBtn="icon"  ref="ref1" ></topic-header>
        <div class="blackheader"></div>
            <div class="content">

            </div>
    </div>
</template>

<script>
    import header from './header';
    export default {
        name: "reffleResult",
        components: {
            'topic-header': header,
        },
        data () {
            return{

            }
        }
    }
</script>

<style scoped>
    .blackheader{
        position: fixed;
        top: 0;
        left: 0;
        width: 750px;
        height: 48px;
        background-color: black;
    }

</style>
