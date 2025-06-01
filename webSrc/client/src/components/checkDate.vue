<template>
    <div id="inputcheck"> 
        <input type="text" id="giturl" v-model="gitUrl" placeholder="git-url" v-if="!clonecontainerBuild">
        <div v-if="clonecontainerBuild" style="font-weight: 900; font-size: 26px;">非克隆容器模式</div>
        <div v-if="ispassurl && !clonecontainerBuild" id="check_1"></div>
        <input type="text" id="password" v-model="password" placeholder="password">
        <!-- <div id="check_2"></div> -->
        <input type="text" id="port" v-model="destport" placeholder="port">
        <!-- <div id="check_3"></div> -->
        <!-- <button :disabled="ischecking"  id="addcontainer" @click="addcontainer">
            <h2 style="position: absolute; top: -16px;">submit</h2>
        </button> -->
        <el-button :disabled="ischecking" type="info" plain id="addcontainer" @click="addcontainer">
             <h2 style="position: absolute; top: -16px; left: 5px;">submit</h2>
        </el-button>
        <el-button v-if="ispassurl" type="success" plain id="addcontainer_send" @click="sendCreatecontainer">
             <h2 style="position: absolute; top: -16px; left: 15px;">send</h2>
        </el-button>
        <!-- <button v-if="ispassurl" id="addcontainer_send" @click="sendCreatecontainer">
            <h2 style="position: absolute; top: -16px;">send</h2>
        </button> -->
        <h2 v-if="ischecking" style="position: absolute; bottom: -14px;right: 30px; height: 30px; width: 100px;">Checking...</h2>
        <div v-if="isstart">
            <h3 v-if="allOk" style="position: absolute; bottom: -14px;right: 10px; height: 30px; width: 140px;">Pass Check</h3>
            <h3 v-if="!allOk" style="position: absolute; bottom: -14px;right: 30px; height: 30px; width: 100px;">Err Value</h3>
        </div>
        <div id="Fifthwheel">
            <h5 style="margin: 10px; margin-left: 0px;" >如果你没有gitUrl请选择:</h5>
            <el-button  type="success" plain id="clongcontainer" @click="EnableClone">
                 <h2 style="position: absolute; top: -14px; left: 5px;">创建非克隆容器</h2>
            </el-button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            gitUrl: '',
            password: '',
            destport: '',
            user: '',

            ischecking: false,
            ispassurl: false,
            isstart: false,

            //判断所有因素齐全
            allOk: false,

            //是否创建非clone项目容器
            clonecontainerBuild: false,
        }
    },
    methods:{
        async addcontainer() {
            if (!this.clonecontainerBuild) {
                this.isstart = true;

                //判断密码合法性
                let passwordbool = false;
                if (this.password.length > 0 && this.password.length < 20) {
                    passwordbool = true;
                } else {
                    return;
                }

                //判定端口合法[3050 - 3060]
                let portbool = false;
                if (Number(this.destport) <= 10045 && Number(this.destport) >= 10030) {
                    portbool = true;
                } else {
                    return;
                }

                //传递给后端进行处理
                //判断git链接合法性
                let ispass = await this.checkgit(this.gitUrl);
                // let ispass = null;
                // //如果gitUrl是空值则不需要判断，直
                // if (!this.gitUrl) {
                //     ispass = await this.checkgit(this.gitUrl);
                // } else {
                //     ispass = true;
                // }

                if (ispass && portbool && passwordbool) {
                    this.allOk = true;
                }

                this.isstart = true;
            } else {
                this.isstart = true;

                //判断密码合法性
                let passwordbool = false;
                if (this.password.length > 0 && this.password.length < 20) {
                    passwordbool = true;
                    console.log(passwordbool);
                } else {
                    return;
                }

                //判定端口合法[3050 - 3060]
                let portbool = false;
                if (Number(this.destport) <= 10045 && Number(this.destport) >= 10030) {
                    portbool = true;
                    console.log(portbool);

                } else {
                    return;
                }
                this.ispassurl = true;
                if (portbool && passwordbool) {
                    this.allOk = true;
                }

                this.isstart = true;
            }
        },
        //验证git链接是否合法函数
        async checkgit(giturl) {
            try {
                this.isstart = false;
                this.ischecking = true;
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/checkgit', {
                    condition: giturl
                    }
                    , {
                        timeout: 10000
                });
                console.log(respones.data.result);
                if (respones.data.result == true) {
                    this.ispassurl = true;
                    this.ischecking = false;
                    this.isstart = true;
                    return true;
                } else {
                    this.ischecking = false;
                    this.ispassurl = false;
                    this.isstart = true;
                    return false;
                }
            } catch (err) {
                console.log("err", err);
                this.ischecking = false;
                this.ispassurl = false;
                this.isstart = true;
                return false;
            }
            
        },

        //发送创建容器请求
        async sendCreatecontainer() {
            //进行后端沟通
            //两种模式1.克隆模式 2.非克隆模式 //clone//

            //1.克隆模式
            if (!this.clonecontainerBuild) {
                //获取密钥
                const token = localStorage.getItem('token');
                console.log(token);
                try {
                    const userback = await axios.post('http://baoding.dreamsky0822.asia:10000/createcontainer', {
                        user: {
                            password: this.password,
                            port: this.destport,
                            gitUrl: this.gitUrl,
                            Usertoken: token,
                            Project: true,
                        }
                    });
                    if (userback.status == 200) {
                        console.log("userback", userback.data);

                    }
                } catch (err) {
                    console.log(err);
                }


                this.$emit('send');
            } else {
                //2.非克隆模式
                //获取密钥
                const token = localStorage.getItem('token');
                console.log(token);
                try {
                    const userback = await axios.post('http://baoding.dreamsky0822.asia:10000/createcontainer_Enableclone', {
                        user: {
                            password: this.password,
                            port: this.destport,
                            gitUrl: this.gitUrl,
                            Usertoken: token,
                            Project: false,
                        }
                    });
                    if (userback.status == 200) {
                        console.log("userback", userback.data);

                    }
                } catch (err) {
                    console.log(err);
                }


                this.$emit('send');
            }
            
        },

        //创建非克隆容器
        EnableClone() {
            this.clonecontainerBuild = !this.clonecontainerBuild;
        }


    }
}
</script>

<style scoped>
#inputcheck{
    background-color: rgb(238, 238, 238);
    position: absolute;
    left: 10px;
    right: 10px;
    height: 150px;
    border-radius: 5px;
    transition: opacity 0.4s ease;  
    
}
#giturl{
    position: absolute;
    border-radius: 6px;
    width: 160px;
    height: 20px;
    top: 10px;
    left: 10px;
}
#password{
    position: absolute;
    border-radius: 6px;
    width: 160px;
    height: 20px;
    top: 45px;
    left: 10px;
}
#port{
    position: absolute;
    border-radius: 6px;
    width: 160px;
    height: 20px;
    top: 80px;
    left: 10px;
}
#addcontainer{
    position: absolute;
    width: 80px;
    height: 25px;
    bottom: 10px;
    left: 10px;
    border-radius: 5px;
}
#clongcontainer{
    position: absolute;
    width: 160px;
    height: 30px;
    bottom:20px;
    left: 30px;
    border-radius: 5px;
}
#addcontainer_send{
    position: absolute;
    width: 80px;
    height: 25px;
    bottom: 10px;
    left: 0px;
    border-radius: 5px;
}
#check_1{
    position: absolute;
    width: 20px;
    height: 20px;
    right: 20px;
    top: 13px;
    border-radius: 10px;
    outline: 2px solid rgb(58, 151, 105);
    background-color: rgb(99, 246, 173);
}
#check_2{
    position: absolute;
    width: 20px;
    height: 20px;
    right: 20px;
    top: 48px;
    border-radius: 10px;
    outline: 2px solid rgb(58, 151, 105);
    background-color: rgb(99, 246, 173);
}
#check_3{
    position: absolute;
    width: 20px;
    height: 20px;
    right: 20px;
    top: 83px;
    border-radius: 10px;
    outline: 2px solid rgb(58, 151, 105);
    background-color: rgb(99, 246, 173);
}
#Fifthwheel{
    position: absolute;
    height: 400px;
    right: 0px;
    left: 0px;
    top: 160px;
    height: 80px;
    border-radius: 10px;
    outline: 3px solid rgb(200, 200, 200);
    background-color: rgb(212, 212, 212);
}
</style>