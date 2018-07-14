<template>
    <div class="container">
        <wxc-overlay v-if="show" :show="true" :hasAnimation="false"></wxc-overlay>
        <div class="dialog-box" v-if="show" :style="{top:top+'px'}">
            <div class="dialog-content">
                <slot name="title">
                    <text class="content-title">{{title}}</text>
                </slot>
                <slot name="content">
                    <text class="content-subtext">{{content}}</text>
                </slot>
                <div class="no-prompt"
                     v-if="showNoPrompt"
                     @click="noPromptClicked">
                    <image :src="noPromptIcon" class="no-prompt-icon"></image>
                    <text class="no-prompt-text">{{noPromptText}}</text>
                </div>
            </div>
            <div class="dialog-footer">
                <div class="overflow-btn">
                    <div class="footer-btn confirm" @click="primaryClicked">
                        <text class="btn-text" :style="{ color: mainBtnColor }">{{confirmText}}</text>
                    </div>
                    <div class="footer-btn1 cancel"
                         v-if="!single"
                         @click="secondaryClicked">
                        <text class="btn-text" :style="{ color: secondBtnColor }">{{cancelText}}</text>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .container {
        position: fixed;
        width: 750px;
        /*兼容H5异常*/
        z-index: 99999;
    }

    .dialog-box {
        position: fixed;
        left: 77px;
        width: 592px;
        height: 314px;
        background-color: #FFFFFF;
        border-radius: 8px;
    }

    .dialog-content {
        padding-top: 36px;
        padding-bottom: 36px;
        padding-left: 36px;
        padding-right: 36px;
    }

    .content-title {
        color: #333333;
        font-size: 36px;
        text-align: center;
        margin-bottom: 24px;
    }

    .content-subtext {
        color: black;
        font-size: 28px;
        line-height: 42px;
        text-align: left;
    }

    .dialog-footer {
        flex-direction: row;
        align-items: center;

    }
    .overflow-btn{
        flex-direction: row;
        align-items: center;
        justify-content: flex-end;
        width: 550px;
        height: 90px;
    }
    .footer-btn1 {
        margin-left: 32px;
    }

    .btn-text {
        font-size: 28px;
        color: #666666;
        font-weight: 700;
    }

    .no-prompt {
        width: 486px;
        align-items: center;
        justify-content: center;
        flex-direction: row;
        margin-top: 24px;
    }

    .no-prompt-icon {
        width: 24px;
        height: 24px;
        margin-right: 12px;
    }

    .no-prompt-text {
        font-size: 24px;
        color: #A5A5A5;
    }
</style>

<script>
    import { WxcOverlay } from 'weex-ui';
    export const CHECKED = 'https://gw.alicdn.com/tfs/TB1UT3VpgMPMeJjy1XdXXasrXXa-42-42.png';
    export const UN_CHECKED = 'https://gw.alicdn.com/tfs/TB1hE3VpgMPMeJjy1XdXXasrXXa-42-42.png';
    export default {
        components: { WxcOverlay },
        props: {
            show: {
                type: Boolean,
                default: false
            },
            single: {
                type: Boolean,
                default: false
            },
            title: {
                type: String,
                default: ''
            },
            content: {
                type: String,
                default: ''
            },
            top: {
                type: Number,
                default: 400
            },
            cancelText: {
                type: String,
                default: 'CANCEL'
            },
            confirmText: {
                type: String,
                default: 'CONFIRM'
            },
            mainBtnColor: {
                type: String,
                default: '#FD9900'
            },
            secondBtnColor: {
                type: String,
                default: '#666666'
            },
            showNoPrompt: {
                type: Boolean,
                default: false
            },
            noPromptText: {
                type: String,
                default: '不再提示'
            },
            isChecked: {
                type: Boolean,
                default: false
            }
        },
        data: () => ({
            noPromptIcon: UN_CHECKED,
            // pageHeight: 1334
        }),
        created () {
            // const { env: { deviceHeight, deviceWidth }} = weex.config;
            // this.pageHeight = deviceHeight / deviceWidth * 750;
        },
        methods: {
            secondaryClicked () {
                this.$emit('wxcDialogCancelBtnClicked', {
                    type: 'cancel'
                });
            },
            primaryClicked (e) {
                this.$emit('wxcDialogConfirmBtnClicked', {
                    type: 'confirm'
                });
            },
            noPromptClicked (e) {
                const isChecked = !this.isChecked;
                this.noPromptIcon = isChecked ? CHECKED : UN_CHECKED;
                this.$emit('wxcDialogNoPromptClicked', { isChecked });
            }
        }
    };
</script>
