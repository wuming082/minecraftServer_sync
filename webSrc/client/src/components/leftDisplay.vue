<template>
    <div id="leftD">
        <el-button type="info" plain id="addcontainer" @click="addcontainer" >
            <h2 style="position: absolute; text-align: center; top: -8px;">创建容器</h2>
        </el-button>
        <el-button type="info" plain id="dismisscontainer" :style="{top : dismisscontainerTop + (Movedismiss ? 250 : 0 ) + 'px'}" @click="dismisscontainer" >
            <h2 style="position: absolute; text-align: center; top: -8px;">容器默认设置</h2>
        </el-button>
        <checkDate
            :style="{top: 60 + 'px', opacity: dismisscontainerOpacity + '%'}"
            v-if="checkstart"
            @send="send"
        ></checkDate>
    </div>
</template>
<script>
import checkDate from './checkDate.vue'
export default {
    data() {
        return {
            //是否启动申请检查模式
            checkstart: false,

            //dismisscontainer
            //默认高度
            dismisscontainerTop: 60,

            //默认透明度
            dismisscontainerOpacity:0,

            //是否向下移动dismiss按键
            Movedismiss: false,
        }
    },
    methods: {
        addcontainer() {
            //进行合法性验证
            this.Movedismiss = !this.Movedismiss;
            if (!this.checkstart) {
                setTimeout(() => {
                    this.checkstart = !this.checkstart;
                }, 50);
                setTimeout(() => {
                    this.dismisscontainerOpacity = 100;
                }, 100);
            } else {
                this.checkstart = !this.checkstart;
                this.dismisscontainerOpacity = 0;
            }
            
            //this.$emit('addcontainer')
        },
        dismisscontainer() {
            //进行合法性验证
            //选择要删除的容器ID
            //const containerID = null;
            this.$emit('dismisscontainer','')

        },
        send() {
            this.addcontainer();
            //创建容器对象单元
            this.$emit('addcontainer');
        }
    },
    components:{
        checkDate,
    }
}

</script>
<style scoped>
#leftD{
    background-color: rgb(209, 209, 209);
    position: absolute;
    left: 0;
    top: 80px;
    bottom: 0;
    width: 250px;
    /* outline: 1px solid rgb(101, 101, 101); */
}

#addcontainer{
    height: 40px;
    border-radius: 10px;
    position: absolute;
    left: 10px;
    right: 10px;
    top: 10px;

}
#dismisscontainer{
    height: 40px;
    border-radius: 10px;
    position: absolute;
    left: 0px;
    right: 10px;
    transition: all 0.4s ease;  
}
</style>