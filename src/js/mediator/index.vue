<template>
    <div></div>
</template>

<script>
    // 这个vue的实例我们在app启动的时候就执行并常驻app内存，在app关闭时候销毁，所以data中的数据每次重启都会被初始化
    // 而我们在app运行期间都可以任意改变data中的数据，然后推送给订阅者

    // 如果想要持久化存储，可以配合storage来完成，每次启动app时候都从本地取数据，当data改变的时候异步的更新一下即可
    // 住： 不能再app退出的时候来持久化存储，退出时间很短，无法保证存储成功

    // 我们不建议在这里存储大量的数据 这里仅仅希望用作一个中介者 来提供给其他页面实例来通信 他无法替代storage 读取速度也远远慢与storage
    export default {
        data() {
            return {
            }
        },
        methods: {
            bindEvent() {
                this.$event.on('refresh', resData => {
                    this.$router.refresh()
                })
            },
            initPush() {
                const bmPush = weex.requireModule('bmPush');
                bmPush.initPush({
                    appId: 'C9ekVCzRSF8OHu2YQIXxG2',
                    appKey: 'UlOzLeMded8pgj3BjGTfm1',
                    appSecret: 'd9v10tH2A36hJwYM1gQKJ5',
                });
            }
        },

        created () {
            this.bindEvent();
            this.initPush();
        }
    }
</script>
