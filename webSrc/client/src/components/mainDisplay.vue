<template>
    <el-container id="MaincontainerBoard">
      <el-main id="scrollboard">
        <div>
            <container-code-vue
            v-for="(component, index) in container_create"
            :key="component[0][0]"
            :index="index"
            :Movement="component[1]"
            :distandMove="component[2]"
            :containerID="component[0][0]"
            :containerStatus="component[0][1]"
            :displayInfoBoard="component[3]"
            @extendInfomation="extendBoard"
            ></container-code-vue>
        
        </div>
      </el-main>
    </el-container>
    
</template>

<script>
import axios from 'axios';

import containerCodeVue from './containerCode.vue'
export default {
    data() {
        return {
            intervalId: null,

            //容器数组验证数组
            container_get: [],

            //生成容器数组
            container_create: [],

            //容器索引id
            containercountID: 0,

            //刷新锚点
            refreshPoint: null,
        }
    },
    components: {
        containerCodeVue,
    },
    mounted() {
        this.traversalContainer();
        
        //刷新周期为15分钟
        this.intervalId = setInterval(this.traversalContainer, 900000);
    },
    methods: {
        //查找容器汇总
        async traversalContainer() {
            //清空container_get变量重新接收
            this.container_get = [];
            await this.checkcontainer("codeserver-wuming:0.9");
            await this.checkcontainer("codeserver-wuming-base");

            //刷新容器卡片
            this.updatecontainer();
        },

        //查找相应images的容器
        async checkcontainer(containerImage) {
            try {
                // containerimage 为索引的镜像名称 后期可修改
                const containerimage = containerImage;
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/getcontainerstatus', {
                    containerImage: containerimage,
                });
 
                //进行数组接收
                this.container_get.push(...await this.transgetcontainer(respones.data)); 
                console.log("container:", this.container_get); 

                //整合数组，json文件转化成数组
            } catch (err) {
                console.error(err);
                console.log("网络连接错误")
            }
        },

        async transgetcontainer(jsondata) {
            //二维数组
            let responestrans = [];

            //遍历json文本
            jsondata.forEach(container => {
                responestrans.push(
                    [container.Id, container.State, container.Status]
                    //容器ID ,容器状态 ,容器整体状态
                )
            });

            //console.log("responestrans",responestrans);
            return responestrans;
        },

        //更新容器container
        updatecontainer() {
            const containerNewval = this.container_get;
            console.log("update-container", containerNewval);

            //初始化格式
            this.container_create = [];
            
            //重新开始推入
            for (let i = 0; i < containerNewval.length; i++ ){
                this.container_create.push(
                    [ containerNewval[i] , false , 0 , false]
                )
            }
            console.log(this.container_create);
        },

        //用于移动相应index的DOM元素
        async extendBoard(index) {
            
            //防止超出范围
            const limited_index = index + 1 >= this.container_create.length ? index : index + 1; 
            console.log("index:",index);
            //超出目标范围后进行给更改
            if (this.refreshPoint != index) {
                //刷新所有卡片位置distandMove归零
                for (let ctrl = 0; ctrl < this.container_create.length; ctrl++) {
                    this.$set(this.container_create[ctrl], 1, false);
                    this.$set(this.container_create[ctrl], 2, 0);
                    this.$set(this.container_create[ctrl], 3, false);
                }
                this.refreshPoint = index;
            }

            console.log("debug:indexEmit", index, "newVal", this.container_get,"this.container_create.length", this.container_create.length);

            //刷新当前index后面的数组元素
            //查看数组index后的第一个元素
            //console.log("this.container_create[limited_index][1]->", this.container_create[limited_index][1]);
            if (!this.container_create[limited_index][1]) {
                //向下移动模式
                // this.$set(this.container_create[index], 0, newValue);
                if(index < this.container_create.length - 1){
                    for (let ctrl = limited_index; ctrl < this.container_create.length; ctrl++) {
                        this.$set(this.container_create[ctrl], 1, true);
                        this.$set(this.container_create[ctrl], 2, 200);
                    }
                }
                setTimeout(() => {
                    this.$set(this.container_create[index], 3, true);
                }, 300);
            } else if(this.container_create.length - 1 == index || this.container_create[limited_index][1]){
                //恢复原样模式
                console.log("back");
                for (let ctrl = limited_index; ctrl < this.container_create.length; ctrl++) {
                    this.$set(this.container_create[ctrl], 1, false);
                    this.$set(this.container_create[ctrl], 2, 0);
                }
                this.$set(this.container_create[index], 3, false);
            }


        }
    },
    props:{
        containerID: String,
        containerCount:Number,
    },
    watch:{
        containerID(newVal){
            console.log(newVal);
        },
        //debug
        // container_get(newVal, oldVal) {
        //     if (newVal.length == oldVal.length){
        //         // console.log("node",newVal,oldVal.length);
        //         //进行遍历
        //         let check = false;
        //         for (let i = 0; i < oldVal.length ; i++){
        //             if (newVal[i][0] != oldVal[i][0]) {
        //                 //正明有值不同
        //                 check = true;
        //             }
        //         }
        //         if (check) {
        //             //更新容器
        //             console.log("update");
        //             this.updatecontainer(newVal);
        //         }
                
        //     } else {
        //         //更新容器
        //         console.log("update");
        //         this.updatecontainer(newVal);
        //     }
        // }

    },

    
}
</script>

<style scoped>
#MaincontainerBoard{
    position: absolute;
    right: 0px;
    bottom: 0;
    top: 80px;
    left: 250px;
    background-color: #ededed;
    /* display: flex; */
    flex-direction: column; 
    padding-right: 15px; /* 调整右边距以补偿滚动条的空间 */
}
#scrollboard{
    max-height: 100%; /* 设置最大高度 */
    top: 0;
    bottom: 0;
    /* overflow-y: auto;  */
    position: absolute;
    left: 0;
    right: 0;
    overflow-y: scroll; /* 启用垂直方向的滚动条 */
    padding: 10px;
}
</style>
<!-- background-color: #ededed; -->