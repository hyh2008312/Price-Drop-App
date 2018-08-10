<!-- CopyRight (C) 2017-2022 Alibaba Group Holding Limited. -->
<!-- Created by Tw93 on 16/10/25. -->
<!--A popup box with customized contents.-->

<template>
    <div class="wxc-popup-bg">
        <div class="popup-content" v-if="show" ref="overlay"
             @click="wxcOverlayBodyClicking"></div>
        <div ref="wxc-popup"
             v-if="show"
             :height="_height"
             :hack="isNeedShow"
             @click="()=>{}"
             :class="['wxc-popup', pos]"
             :style="padStyle">
            <slot></slot>
        </div>
    </div>
</template>

<style scoped>
    .wxc-popup-bg{
        position: absolute;
        width: 750px;
        top: 160px;
        overflow: hidden;
        left: 0;
        right: 0;
        bottom: 0;
    }
    .wxc-popup {
        position: absolute;
        width: 750px;
    }
    .top {
        left: 0;
        right: 0;
    }
    .bottom {
        left: 0;
        right: 0;
    }
    .left {
        bottom: 0;
        top: 0;
    }
    .right {
        bottom: 0;
        top: 0;
    }
    .popup-content{
        position: absolute;
        width: 750px;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        background-color: rgba(0,0,0,0.6);
    }
</style>

<script>
    const animation = weex.requireModule('animation');
    export default {
        props: {
            show: {
                type: Boolean,
                default: false
            },
            pos: {
                type: String,
                default: 'bottom'
            },
            popupColor: {
                type: String,
                default: '#FFFFFF'
            },
            overlayCfg: {
                type: Object,
                default: () => ({
                    hasAnimation: true,
                    timingFunction: ['ease-in', 'ease-out'],
                    duration: 300,
                    opacity: 0.6
                })
            },
            height: {
                type: [Number, String],
                default: 840
            },
            standOut: {
                type: [Number, String],
                default: 0
            },
            width: {
                type: [Number, String],
                default: 750
            },
            animation: {
                type: Object,
                default: () => ({
                    timingFunction: 'ease-in'
                })
            }
        },
        data: () => ({
            haveOverlay: true,
            isOverShow: true
        }),
        computed: {
            isNeedShow () {
                setTimeout(() => {
                    this.appearPopup(this.show);
                }, 50);
                return this.show;
            },
            _height () {
                this.appearPopup(this.show, 150);
                return this.height;
            },
            padStyle () {
                const { pos, width, height, popupColor, standOut } = this;
                let style = {
                    width: `${width}px`,
                    backgroundColor: popupColor
                };
                pos === 'top' && (style = {
                    ...style,
                    top: `${-height + standOut}px`,
                    height: `${height}px`
                });
                pos === 'bottom' && (style = {
                    ...style,
                    bottom: `${-height + standOut}px`,
                    height: `${height}px`
                });
                pos === 'left' && (style = {
                    ...style,
                    left: `${-width + standOut}px`
                });
                pos === 'right' && (style = {
                    ...style,
                    right: `${-width + standOut}px`
                });
                return style;
            }
        },
        methods: {
            hide () {
                this.appearPopup(false);
            },
            wxcOverlayBodyClicking () {
                this.isShow && this.appearPopup(false);
            },
            appearPopup (bool, duration = 300) {
                this.isShow = bool;
                const popupEl = this.$refs['wxc-popup'];
                if (!popupEl) {
                    return;
                }
                animation.transition(popupEl, {
                    styles: {
                        transform: this.getTransform(this.pos, this.width, this.height, !bool)
                    },
                    duration,
                    delay: 0,
                    ...this.animation
                }, () => {
                    if (!bool) {
                        this.$emit('wxcPopupOverlayClicked', { pos: this.pos });
                    }
                });
            },
            getTransform (pos, width, height, bool) {
                let _size = pos === 'top' || pos === 'bottom' ? height : width;
                bool && (_size = 0);
                let _transform;
                switch (pos) {
                    case 'top':
                        _transform = `translateY(${_size}px)`;
                        break;
                    case 'bottom':
                        _transform = `translateY(-${_size}px)`;
                        break;
                    case 'left':
                        _transform = `translateX(${_size}px)`;
                        break;
                    case 'right':
                        _transform = `translateX(-${_size}px)`;
                        break;
                }
                return _transform;
            }
        }
    }
</script>
