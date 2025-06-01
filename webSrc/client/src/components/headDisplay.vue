<template>
    <div id="header">
        <h1 id="name" style="left: 40px; position: absolute; top: -10px; ">Codeserver-docker-manage</h1>
        <h4 style="position: absolute;left: 43px; bottom: -15px;">https://github.com/wuming082/codeserver-webui.git</h4>
        <img id="dockericon" src="../assets/docker-logo-blue.png" alt="dockericon">
        <div id="exitbutton" @click="logout">
            <h3 style="top:-12px ; left: 13px;; position: absolute; color: #615d5d;">退出登录</h3>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {


    data() {
        return {
            //轮询jwt密钥
            jwtchecktime: null,
        }
    },
    methods: {
        logout() {
            localStorage.clear();
            location.reload();
        },
        async refreshtoken() { 
            console.log("jwtToken", localStorage.getItem('token')); 
            try {
                const token = localStorage.getItem('token');
                //请求剩余时长
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/refreshtoken', { old_token: token });
                //刷新密钥
                const new_token = respones.data.new_token;
                console.log("new_token refresh");
                localStorage.setItem('token',new_token);
            } catch (err) {
                console.error(err); 
                localStorage.clear();
                //导航到登录页面
                this.$router.push('/');
            }
        }
    },
    mounted() {
        //15分钟刷新一次token
        this.jwtchecktime = setInterval(this.refreshtoken, 900000);
    },
    created() {
        const token = localStorage.getItem('token');
        if (!token) {
            //导航到登录页面
            this.$router.push('/');
            return -1;
        }
        //验证token是否时效
        this.refreshtoken();
    }

}

</script>

<style scoped>
#header{
    
    background-color: rgb(255, 244, 230);
    position: absolute;
    left: 0px;
    right: 0;
    top: 0;
    height: 80px;
}
#dockericon{
    max-width: 270px;
    height: auto;
    position: absolute;
    top: 10px;
    left: 480px;
    opacity: 70%;
    -webkit-user-select: none; /* 适用于谷歌浏览器和Safari */ -moz-user-select: none; /* 适用于火狐浏览器 */ -ms-user-select: none; /* 适用于Internet Explorer/Edge */ user-select: none; /* 适用于支持CSS3的浏览器 */

}
#exitbutton{
    -webkit-user-select: none; /* 适用于谷歌浏览器和Safari */ -moz-user-select: none; /* 适用于火狐浏览器 */ -ms-user-select: none; /* 适用于Internet Explorer/Edge */ user-select: none; /* 适用于支持CSS3的浏览器 */

    background-color: rgb(218, 208, 195);
    width: 100px;
    position: absolute;
    right: 30px;
    top: 20px;
    bottom: 20px;
    border-radius: 10px;
    outline: 2px solid rgb(192, 184, 173);
}
#exitbutton:hover{
    background-color: rgb(201, 192, 180);
}
#exitbutton:active{
    background-color: rgb(168, 161, 151);
}
#name{
    -webkit-user-select: none; /* 适用于谷歌浏览器和Safari */ -moz-user-select: none; /* 适用于火狐浏览器 */ -ms-user-select: none; /* 适用于Internet Explorer/Edge */ user-select: none; /* 适用于支持CSS3的浏览器 */

}


</style>