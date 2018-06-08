<template>
    <div class="wrapper">
        <div class="btn yellow  btn-margin" @click="openBottomPopup">
            <text class="btn-txt">Click</text>
        </div>
        <wxc-popup popup-color="rgb(92, 184, 92)"
                   :show="isBottomShow"
                   @wxcPopupOverlayClicked="popupOverlayBottomClick"
                   pos="bottom"
                   height="400">
            <div class="demo-content">
                <!--<image src="https://img.alicdn.com/tfs/TB1ojYvOXXXXXaOXFXXXXXXXXXX-180-41.png" class="demo-image"></image>-->
                <text>Different from a "web app", "HTML5 app", or "hybrid app", you can use Weex to build a real mobile app. The code that you write is relatively simple, because you can build native applications just using HTML, CSS, Javascript.</text>
            </div>
        </wxc-popup>
    </div>
</template>

<script>
    import { WxcPopup } from 'weex-ui';
    export default {
        components: { WxcPopup },
        data: () => ({
            isBottomShow: false,
            height: 400
        }),
        methods: {
            openBottomPopup () {
                this.isBottomShow = true;
            },
            popupOverlayBottomClick () {
                this.isBottomShow = false;
            }
        }
    };
</script>
