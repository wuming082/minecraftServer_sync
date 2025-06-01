<template>
    <div id="env" @mousemove="move" ref="envcolor" :style="{left:  (indexCount) * 36 + 6 + 'px'}" @click="EventClick">
        <img id="imgsize" :src="getImagePath(Imgpathsrc)" alt="">
    </div>
</template>

<script>
export default {
    data() {
        return {
            backgruandcolor: "rgb(109, 220, 183)",
        }
    },
    components:{
        
    },
    methods:{
        move(){

        },
        EventClick() {
            console.log("this env index is:",this.indexCount);
        },
        getImagePath(path) {
            return require('@/assets/' + path); // 确保路径相对于 src 目录
        }
    },
    props: {
        //用于接受第几个环境按钮
        indexCount: Number,

        //用于接收图片地址
        Imgpathsrc:String,
    }
}
</script>

<style scoped>

/*
    dreamsky
    2025/1/23
    envbutton的作用为：在控制面板处，在容器外部，为容器内部添加或者修改环境

    width为30px 
*/
#env{
    -webkit-user-select: none; /* 适用于谷歌浏览器和Safari */ -moz-user-select: none; /* 适用于火狐浏览器 */ -ms-user-select: none; /* 适用于Internet Explorer/Edge */ user-select: none; /* 适用于支持CSS3的浏览器 */

    position: absolute;
    height: 30px;
    width: 30px;
    border-radius: 7px;
    background-color: rgb(253, 255, 250);
    outline: 1px solid rgb(90, 90, 90);

}
#env:hover{
    background-color: rgb(228, 230, 225);
}
#env:active{
    background-color: rgb(213, 215, 209);
}
#slotimg{
    position: absolute;
    left: 4px;
    top: 4px;
    width: 22px;
    height: 22px;
}
#imgsize{
    max-width: 25px;
    height: auto;
    position: absolute;
    left: 3px;
    top: 4px;
}
</style>