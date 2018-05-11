<template>
    <div class="wrapper">
        <!-- <div class="status-bar"></div> -->
        <home-header></home-header>
        <!-- <bmmask animation="center" position="center" class="mask" :duration="300" ref="channel" disableMaskEvent="true"> -->
        <top-channel @change="onchange" ref="topChannel" :activeIndex="activeIndex"></top-channel>
        <!-- </bmmask> -->
        <suggest></suggest>
    </div>
</template>
<script>
import header from './header';
import suggest from './suggest';
import topChannel from './topChannel';

export default {
    components: {
        'home-header': header,
        'top-channel': topChannel,
        'suggest': suggest
    },
    created () {
        this.init()
    },
    data () {
        return {
            activeIndex: 0
        }
    },
    methods: {}
}

</script>
<style lang="sass" src="./index.scss"></style>
