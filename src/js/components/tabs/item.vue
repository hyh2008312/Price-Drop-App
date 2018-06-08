<template>
    <div class="container" @click="onclickitem">
        <text :style="{ color: titleColor }" class="tab-icon">{{icon}}</text>
        <text :style="{ color: titleColor }" class="tab-text">{{title}}</text>
    </div>
</template>

<style scoped>
    .container {
        flex: 1;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 112;
        border-top-width: 1;
        border-top-style: solid;
        border-color: rgba(0,0,0,.12);
    }

    .tab-icon {
        font-size: 48;
        line-height: 48;
        font-weight: 500;
        font-family: iconfont-socialcommer;
    }

    .tab-text {
        margin-top: 4;
        text-align: center;
        font-size: 24;
    }
</style>

<script>
    export default {
        props: {
            index: { default: 0 },
            title: { default: '' },
            titleColor: { default: '#000000' },
            icon: { default: '' },
            backgroundColor: { default: '#ffffff' }
        },
        methods: {
            onclickitem (e) {
                const params = {
                    index: this.index
                };
                this.$emit('tabItemOnClick', params);
            }
        }
    }
</script>
