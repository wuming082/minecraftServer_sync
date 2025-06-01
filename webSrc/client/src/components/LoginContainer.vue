<!-- LoginContainer.vue -->
<template>
    <div id="background">
        <!-- <img  id="background_img" src="../assets/docker-svgrepo-com.svg" alt=""> -->
            <!-- <div id="infomation"></div> -->
            <!-- 登录界面 -->
            <div class="login-container" v-if="login_stauts">
                <input id="userinput" v-model="credentials.username" type="text" placeholder="USERNAME" @keyup.enter="handleLogin">
                <input id="passwordinput" v-model="credentials.password" type="text" placeholder="PASSWORD" @keyup.enter="handleLogin">
                <h2 style="position: absolute; top: -10px;left: 20px; color: rgb(115, 115, 115);">minecraft server sync</h2>
                <p v-if="error" class="error">{{ error }}</p>
                <img id="vscode" src="../assets/20200408094004334.png" alt="">
                <!-- <img id="vscodeimg" src="../assets/loginvscode.png" alt=""> -->
                <el-button type="success" plain id="submit"  @click="handleLogin">
                    <h3 style="position: absolute; top: -5px; left: 33px;">登陆</h3>
                </el-button>
                <img id="github" src="../assets/GitHub-Emblem.png" alt="" @click="githubweb">
            
                <div id="sign_up" style="font-weight: 800;" @click="switch_login_sign">
                    sign up
                </div>
            </div>
            <!-- 注册界面 -->
            <div class="sign_in-container" v-if="!login_stauts">
                <input id="userinput" v-model="sign_inData.username" type="text" :style="{ border: refFindinput[0] }" placeholder="USERNAME" @keyup.enter="Sign_in">
                <input id="passwordinput" v-model="sign_inData.password" type="text" :style="{ border: refFindinput[1] }" placeholder="PASSWORD" @keyup.enter="Sign_in">
                <input id="emailinput" v-model="sign_inData.email" type="text" :style="{ border: refFindinput[2] }" placeholder="EMAIL" @keyup.enter="Sign_in">
                <input id="Checkinput" v-model="sign_inData.check" type="text" :style="{ border: refFindinput[3] }" placeholder="邀请码" @keyup.enter="Sign_in">
                <div id="sussces" style="font-weight: 900; position: absolute; top: 36px; color: #0f9125;" v-if="sussces_sgin">注册成功</div>
                <h2 style="position: absolute; top: -10px;left: 20px; color: rgb(115, 115, 115);">minecraft server sync</h2>
                <p v-if="error" class="error">{{ error }}</p>
                <img id="vscode" src="../assets/20200408094004334.png" alt="">
                <!-- <img id="vscodeimg" src="../assets/loginvscode.png" alt=""> -->
                <el-button type="success" plain id="submit" style=" top: 230px;"  @click="Sign_in">
                    <h3 style="position: absolute; top: -5px; left: 33px;">注册</h3>
                </el-button>
                <img id="github" src="../assets/GitHub-Emblem.png" alt="" @click="githubweb">
                <div id="sign_up"  style="top: 240px; font-weight: 800;" @click="switch_login_sign">
                    login
                </div>
            </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'LoginContainer',
    data() {
        return {
            credentials: {
                username: '',
                password: ''
            },

            sign_inData: {
                username: '',
                password: '',
                email: '',
                check:'',
            },

            error: null,

            //用于切换登录和注册
            login_stauts: true,

            //注册是否成功
            sussces_sgin: false,

            //控制ref元素数组
            //2px solid rgb(241, 150, 150)提示红色号
            refFindinput: [
                'none',
                'none',
                'none',
                'none'
            ],

            test_process:"server.cooode.online:6730"
        };
    },
    methods: {
        async handleLogin() {
            try {
                const response = await axios.post(`http://server.cooode.online:6730/login`, this.credentials);
                const { token } = response.data;
                localStorage.setItem('token', token); // 存储令牌
                this.$router.push('/dashboard'); // 登录成功后重定向到仪表盘
            } catch (error) {
                console.error('Login failed:', error.response ? error.response.data : error.message);
                this.error =  error.response ? error.response.data : error.message;
            }
        },
        //跳转到github
        githubweb() {
            window.open('https://github.com/wuming082/codeserver-webui');
        },

        //注册页面
        switch_login_sign(){
            this.login_stauts = !this.login_stauts;
            this.error = null;
            this.sign_inData = {
                username: '',
                password: '',
                email: '',
                check: '',
            }
        },

        //注册用户函数
        async Sign_in() {
            try {
                const response = await axios.post(`http://${this.test_process}/enrollment`, this.sign_inData);
                //注册成功业务逻辑
                console.log(response);
                this.error = null;
                this.sussces_sgin = true;
            } catch (error) {
                this.error = error.response.data.message;
                //刷新设置
                this.reloadInputboard();
                this.refFindinput[Number(error.response.data.errorkind)] = '2px solid rgb(241, 150, 150)';
                console.log(error.response.data.errorkind);
            }
        },
        reloadInputboard() {
            this.refFindinput[0] = 'none';
            this.refFindinput[1] = 'none';
            this.refFindinput[2] = 'none';
            this.refFindinput[3] = 'none';
        }
    },
    mounted() {

    },
    watch:{
        'sign_inData.username': function(){
            this.sussces_sgin = false;
        },
        'sign_inData.password': function () {
            this.sussces_sgin = false;
        },
        'sign_inData.email': function () {
            this.sussces_sgin = false;
        },
        'sign_inData.check': function () {
            this.sussces_sgin = false;
        }
    }
};
</script>

<style scoped>
.login-container {
    width: 400px;
    height: 300px;
    margin: auto;
    padding: 20px;
    /* border: 2px solid #b7b7b7; */
    background-color: rgb(251, 251, 251);
    border-radius: 15px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%); /* 使用 transform 来进行微调 */
    box-shadow: 0px 0px 30px rgba(186, 186, 186, 0.5); /* 外阴影 */
    overflow: hidden;
}
.sign_in-container{
    width: 400px;
    height: 300px;
    margin: auto;
    padding: 20px;
    /* border: 2px solid #b7b7b7; */
    background-color: rgb(251, 251, 251);
    border-radius: 15px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%); /* 使用 transform 来进行微调 */
    box-shadow: 0px 0px 30px rgba(186, 186, 186, 0.5); /* 外阴影 */
    overflow: hidden;
}

.error {
    color: red;
}
#background{
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0;
    bottom: 0;
    background-color: rgb(236, 236, 236);
}
#background_img{
    width: 700px;
    height: 700px;
    position: absolute;
    left: -100px;
    top: -40px;
    opacity: 40%;
}
#userinput{
    position: absolute;
    width: 200px;
    height: 40px;
    left: 20px;
    top: 60px;
    border-radius: 10px;
    border: none;
    box-shadow: 0px 0px 5px rgba(186, 186, 186, 0.5); /* 外阴影 */

}
#passwordinput{
    position: absolute;
    width: 200px;
    height: 40px;
    left: 20px;
    top: 120px;
    border-radius: 10px;
    border: none;
    box-shadow: 0px 0px 5px rgba(186, 186, 186, 0.5); /* 外阴影 */

}
input:focus {
    outline: none;
}
#vscode{
    position: absolute;
    opacity: 40%;
    bottom: -90px;
    right: -70px;
    width: 300px;
    height: 300px;
    z-index: -1;
}
#submit{
    position: absolute;
    left: 20px;
    top: 180px;
    width: 100px;
    height: 40px;
    border-radius: 10px;
}
#github{
    position: absolute;
    left: 20px;
    bottom: 0;
    width: 120px;
    height: 64px;
}
#sign_up{

    width: 100px;
    height: 40px;
    position: absolute;
    top: 186px;
    left: 140px;
}
#emailinput{
    position: absolute;
    width: 200px;
    height: 40px;
    left: 20px;
    top: 180px;
    border-radius: 10px;
    border: none;
    box-shadow: 0px 0px 5px rgba(186, 186, 186, 0.5); /* 外阴影 */
}
#Checkinput{
    position: absolute;
    width: 160px;
    height: 40px;
    right: 30px;
    top: 60px;
    border-radius: 10px;
    border: none;
    box-shadow: 0px 0px 5px rgba(186, 186, 186, 0.5); /* 外阴影 */
}
#vscodeimg{
    height: 230px;
    width: auto;
    position: absolute;
    border-radius: 5px;
    right: -110px;
    bottom: -10px;

}
#infomation{
    width: 400px;
    height: 300px;
    margin: auto;
    padding: 20px;
    background-color: rgb(247, 247, 247);
    position: absolute;
    border-radius: 10px;
    top: 50%;
    left: 50%;
    transform: translate(-5%, -50%); /* 使用 transform 来进行微调 */
    box-shadow: 0px 0px 30px rgba(186, 186, 186, 0.5); /* 外阴影 */

}
</style>