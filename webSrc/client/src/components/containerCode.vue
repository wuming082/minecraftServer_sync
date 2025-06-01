<template>
    <div>
        <div id="container" :style="{ top: index * (containerboardHeight + 20) + 10 + distandMove + 'px', height: containerboardHeight + 'px', opacity: MyselfContainer ? 100 : 50 + '%' }">
            <div id="icon">
            </div>
            <div id="ID" >
                <h4 style="top: -10px;" >
                    {{ containerID.substring(0, 16) }}
                </h4>
                <div id="usernameboard" :style="{ color: EnableColor[MyselfContainer ? 0 : 1] }">
                    {{ username }}
                </div>
                <!-- <i style="position: absolute; top: 0px; left: 145px; " class="el-icon-collection"></i> -->
            </div>
            <div id="display" >
                <div id="environment" v-if="ProjectEnable_Number == 0 ? false : true">
                    <svg id="docker" width="35px" height="35px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path fill="#0DB7ED" fill-rule="evenodd" d="M6.94221099,14.9002344 C6.9980621,14.9002344 7.05128211,14.9107588 7.10043586,14.9297745 C7.04721586,14.9606302 7.01109801,15.018335 7.01109801,15.0842919 C7.01109801,15.1828984 7.09098782,15.2626686 7.18959432,15.2626686 C7.25710599,15.2626686 7.31570779,15.2251754 7.34608506,15.1698027 C7.36743286,15.2214082 7.37939241,15.2780367 7.37939241,15.3374756 C7.37939241,15.578939 7.18361455,15.774657 6.94221099,15.774657 C6.70080744,15.774657 6.50496978,15.578939 6.50496978,15.3374756 C6.50496978,15.0959525 6.70080744,14.9002344 6.94221099,14.9002344 L6.94221099,14.9002344 Z M6.94221099,16.0853662 C6.52978585,16.0853662 6.19420083,15.7499008 6.19420083,15.3374756 C6.19420083,14.9250505 6.52978585,14.5895253 6.94221099,14.5895253 C7.35457634,14.5895253 7.69010156,14.9250505 7.69010156,15.3374756 C7.69010156,15.7499008 7.35457634,16.0853662 6.94221099,16.0853662 L6.94221099,16.0853662 Z M20.3859431,11.1838037 C18.2619865,16.8117894 13.4653093,19.318631 7.81023526,19.318631 C5.13823222,19.318631 3.00656172,18.3995992 1.64323262,16.8672219 L1.65327865,16.8605843 C2.04609012,16.880497 2.39758135,16.8872541 2.75439457,16.8872541 C3.08065114,16.8872541 3.39979178,16.8838457 3.6953721,16.8672219 C3.72108514,16.8657867 3.75325633,16.8621989 3.77878997,16.8605843 C3.77902916,16.8605245 3.86998155,16.8546046 3.82549202,16.853887 C4.57667146,16.8075437 5.15892224,16.7031368 5.70188589,16.5482008 C5.70200548,16.548141 5.70212508,16.548141 5.70224467,16.5480812 C5.80091098,16.5198567 5.89658739,16.4901372 5.98825735,16.4583846 C6.09081051,16.4228049 6.14510687,16.3108635 6.109587,16.2083104 C6.07406714,16.1056974 5.96218553,16.0512815 5.85957258,16.0869807 C5.16992503,16.3259326 4.26010213,16.4574876 3.14505333,16.4821841 L3.14475434,16.4821841 C2.57739321,16.4947416 1.95717085,16.4797922 1.28450587,16.4365584 L1.28444607,16.4365584 C1.14529669,16.2507668 1.01649231,16.0576798 0.89869073,15.8577161 L0.71248051,15.5172277 C0.149903198,14.4112083 -0.0964037696,13.1191582 0.0343141305,11.7160038 L16.3965356,11.7160038 C17.7407294,11.7160038 19.0534696,11.2143604 19.6764427,10.6609919 C18.5601381,9.75332174 18.670764,7.59731356 19.3822377,6.774616 C19.9997093,7.270758 20.9954018,8.31584342 20.824141,9.64622396 C21.6011531,9.255625 22.9506091,9.06259783 24,9.66816973 C23.3411483,10.9541803 21.8929064,11.3383809 20.3859431,11.1838037 L20.3859431,11.1838037 Z M2.25508329,11.3188869 L4.46771995,11.3188869 L4.46771995,9.1061306 L2.25508329,9.1061306 L2.25508329,11.3188869 Z M4.80808879,11.3188869 L7.02096464,11.3188869 L7.02096464,9.1061306 L4.80808879,9.1061306 L4.80808879,11.3188869 Z M4.80808879,8.76576176 L7.02096464,8.76576176 L7.02096464,6.5530653 L4.80808879,6.5530653 L4.80808879,8.76576176 Z M7.36127369,11.3188869 L9.57402994,11.3188869 L9.57402994,9.1061306 L7.36127369,9.1061306 L7.36127369,11.3188869 Z M7.36127369,8.76576176 L9.57402994,8.76576176 L9.57402994,6.5530653 L7.36127369,6.5530653 L7.36127369,8.76576176 Z M9.91433899,11.3188869 L12.1270952,11.3188869 L12.1270952,9.1061306 L9.91433899,9.1061306 L9.91433899,11.3188869 Z M9.91433899,8.76576176 L12.1270952,8.76576176 L12.1270952,6.5530653 L9.91433899,6.5530653 L9.91433899,8.76576176 Z M9.91433899,6.21275626 L12.1270952,6.21275626 L12.1270952,4 L9.91433899,4 L9.91433899,6.21275626 Z M12.4674043,11.3188869 L14.6801605,11.3188869 L14.6801605,9.1061306 L12.4674043,9.1061306 L12.4674043,11.3188869 Z"/>
                    </svg>
                    <envButtonVue id="env"
                    v-for="(img, index) in ImgPath"
                    :key="index"
                    :indexCount = "index"
                    :Imgpathsrc = "img.src"
                    >
                    </envButtonVue>
                </div>
                <div id="port" @click="Checktoken">
                    <div style="z-index: 0;" id="backport"></div>
                    <div style="color: rgb(48, 48, 48); z-index: 2;">
                        {{ "Port: " + dest_port }}
                    </div>
                </div>
                <div id="botton-tool" >
                    <div id="status_show" :style="{ backgroundColor: color }">
                        <div style="position: absolute; top: 6px; left: 5px; color: aliceblue;">
                            {{ status }}
                        </div>
                    </div>
                    <div id="starttime">
                        {{ startTime }}
                    </div>
                    <el-button type="success" plain :disabled="istrans || !MyselfContainer"  id="stop_start" v-if="!statusbool" @click="start">
                        <i style="position: absolute; top: 7px; left: 13px;" class="el-icon-video-play"></i>
                        <h3 v-if="!istrans" style="position: absolute; top: -10px; left: 33px;">启动容器</h3>
                        <h3 v-if="istrans" style="position: absolute; top: -10px; left: 33px;">正在启动</h3>
                    </el-button>
                    <el-button type="danger" plain :disabled="istrans || !MyselfContainer"  id="stop_start" v-if="statusbool" @click="stop">
                        <i style="position: absolute; top: 7px; left: 13px; " class="el-icon-video-pause"></i>
                        <h3 v-if="!istrans" style="position: absolute; top: -10px; left: 33px;">中止容器</h3>
                        <h3 v-if="istrans" style="position: absolute; top: -10px; left: 33px;">正在中止</h3>
                    </el-button>
                    <el-button :disabled="!statusbool || !MyselfContainer" type="info" plain id="info"  @click="info">
                        <i style="position: absolute; top: 7px; left: 13px; " class="el-icon-s-promotion" ></i>
                        <h3  style="position: absolute; top: -10px; left: 33px;">跳转到容器</h3>
                    </el-button>
                    <el-button :disabled="statusbool && !istrans || !MyselfContainer" type="danger" plain id="delete"  @click="Deletecontainer">
                        <i style="position: absolute; top: 7px; left: 13px; " class="el-icon-delete" ></i>
                        <h3 style="position: absolute; top: -10px; left: 33px;">删除</h3>
                    </el-button>
                    <el-button :disabled="!MyselfContainer" type="info" plain id="info_container"  @click="extendBoard">
                            <i style="position: absolute; top: 7px; left: 13px; " class="el-icon-info" ></i>
                            <h3 style="position: absolute; top: -10px; left: 33px;">Info</h3>
                    </el-button>
                </div>
                <!-- 用于控制容器占用内存 -->
                <!-- <div id="mem">
                    <el-slider
                      v-model="memOption"
                      :show-tooltip="false"
                      :step="25"
                      show-stops>
                    </el-slider>
                </div> -->
            </div>
            <!-- infomation信息面板 -->
        
        </div> 
        <div id="infomationBoard" v-if="displayInfoBoard" :style="{ top: index * (containerboardHeight + 20) + 35 + distandMove + containerboardHeight + 'px'}">
            <div id="containerInfo" style="position: absolute; left: 10px; top: 6px; font-weight: 550; font-size: 20px;">
                容器信息
            </div>
            <div style="position: absolute; left: 10px; right: 10px; bottom: 20px; top: 40px; background-color: rgb(230, 230, 230); border-radius: 5px; text-align: left;"
                :style="{opacity: displayInfoBoard ? 100 : 0 + '%'}"            
            >
                <div v-if="MyselfContainer"  style="position: absolute;left: 8px; right: 0; bottom: 0; top: 5px; color: rgb(105, 105, 105);" ref="copyinfotext">
                    <!-- 文本信息infomation -->
                    容器信息:此容器为{{ ProjectEnable }}<br />
                    容器ID:{{ containerID }}<br />
                    容器创建日期:{{ startTime }}<br />
                    容器网络目标地址:baoding.dreamsky0822.asia:{{ dest_port }}<br />
                    容器管理者:{{ username }}<br />
                </div>
                <div v-if="MyselfContainer" style="position: absolute;left: 8px; right: 0; bottom: 0; top: 5px; color: rgb(105, 105, 105);" ref="copyinfotext">
                        <!-- 文本信息infomation -->
                        容器信息:此容器为{{ ProjectEnable }}<br />
                        容器ID:{{ containerID }}-{{ ProjectEnable }}<br />
                        容器创建日期:{{ startTime }}<br />
                        容器网络目标地址:baoding.dreamsky0822.asia:{{ dest_port }}<br />
                        容器管理者:{{ username }}<br />
                        容器密码: {{ adminPassword }}<br />
                    </div>
                <el-button type="info" plain id="copybutton" style="position: absolute; right: 5px; top: 5px;" 
                    v-clipboard:copy="text" v-clipboard:success="onCopy" v-clipboard:error="onError"
                    @click="Infocopytext"
                >
                    <i style="position: absolute; top: 7px; left: 13px; " class="el-icon-document-copy" ></i>
                </el-button>
            </div>
        </div>
    </div>
</template>
 
<script>
import axios from 'axios';
import envButtonVue from './envbuttoncomponent/envButton.vue';


export default {

    data() {
        return {
            //此容器的全部信息json
            container_info: null,

            //容器所在的端口
            dest_port: '',

            //容器的状态
            status: '',

            //如果是running就是 ture
            //如果是exited就是false
            statusbool: false,

            //是否进入start或者stop进程
            istrans: false,

            color: '#4C585B',

            //删除验证
            deletecheck: false,

            //debug
            count: 3,
            i: 0,

            //为每一个环境图标设定一个合集
            ImgPath: [
                { src: 'logo.png' },
                { src: 'npm.png' },
                { src: 'docker-mark-blue.png' },
                { src: 'python-logo.png'}
            ],

            //用于保存容器的所有json信息
            containerinfojson: '',

            //保存容器启动时间
            startTime: '',

            //保存容器归属的用户名称
            username: '',

            //容器卡片高度值
            containerboardHeight: 90,

            //表示是否可操作 [0]可操作 [1]不可操作
            EnableColor: [
                '#6e89ff',
                '#858585'
            ],
            EnableColorOptionNumber: 0,

            //是否为自己的容器
            MyselfContainer: false, 

            //debug
            text: "",

            //用于控制内存占用行为
            memOption: 0,

            //记录该容器的进入密码
            adminPassword: null,

            //是否为项目克隆容器
            ProjectEnable: null,
            ProjectEnable_Number:null,
        }
    },
    components:{
        envButtonVue,
    },
    methods: {
        async stop() {
            this.status = 'wait';
            this.color = '#4C585B';
            this.istrans = true;
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/stopcontainer', {
                    containerID: this.containerID
                });
                if (respones.status == 204) {
                    this.statusbool = false;
                    this.status = 'exited';
                }
            } catch (err) {
                console.log("err",err);
            }
            this.istrans = false;
        },
        async start() {
            this.status = 'wait';
            this.color = '#4C585B';
            this.istrans = true;
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/startcontainer', {
                    containerID: this.containerID
                });
                if (respones.status == 204) {
                    this.statusbool = true;
                    this.status = 'running';
                }
            } catch (err) {
                console.log("err", err);
            }
            this.istrans = false;
        },
        info() {
            //跳转到目标网页
            window.open(`http://baoding.dreamsky0822.asia:${this.dest_port}`);
        },
        //删除网页
        async Deletecontainer(){
            //删除网页
            //向后端发送删除指令
            //alert("不要手贱，还没做好呢");

            //dubug
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/deletecontainer', {
                    containerID: this.containerID
                });
                if (respones.status == 204) {
                    console.log("delete contaienr:",this.containerID);
                } else if(respones.status == 404) {
                    console.log("delete err contaienr:", this.containerID);
                }
            } catch (err) {
                console.log("delete err",err);
            }
        },

        //更新资源
        async update() {
            console.log("containerID reception", this.containerID);
            //进行post请求
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/reception_container_info', {
                    containerID: this.containerID,
                },
                    { timeout: 3000 },
                );
                //获取端口
                // this.container_info = respones.data.HostConfig.PortBindings["8080/tcp"][0].HostPort;
                //配置环境变量
                this.dest_port = respones.data.HostConfig.PortBindings["8080/tcp"][0].HostPort;
                this.status = respones.data.State.Status;
                const startTime = respones.data.Created.slice(0, 10) + " " + respones.data.Created.slice(11, 19);
                const originalDateStr = startTime;
                this.startTime = this.convertToUtcPlus8(originalDateStr);
                this.containerinfojson = respones.data;

            } catch (err) {
                console.error(err);
            }
        },
        get_containerInfo() {
            // 使用 Clipboard API
            //const client_getInfo = JSON.stringify(this.containerinfojson)
            console.log(this.containerinfojson);
            //运行在https的环境当中
            // navigator.clipboard.writeText(client_getInfo).then(function () {
            //     console.log('文本已经成功复制');
            // }).catch(function (err) {
            //     console.error('未能复制文本: ', err);
            // });
        },

        //更换时间UTC+8
        convertToUtcPlus8(dateStr) {
            // 解析输入的日期字符串
            const date = new Date(dateStr);

            // 检查日期是否有效
            if (isNaN(date.getTime())) {
                console.error('无效的日期格式');
                return;
            }
        
            // 获取当前时间的UTC偏移量（以分钟为单位）
            const utcOffsetMinutes = date.getTimezoneOffset();
        
            // UTC+8的偏移量是-480分钟（东八区相对于UTC的时间差）
            const targetOffset = -480;
        
            // 计算需要调整的总分钟数
            const totalOffsetMinutes = utcOffsetMinutes + targetOffset;
        
            // 创建一个新的Date对象，并应用时差
            const adjustedDate = new Date(date.getTime() - totalOffsetMinutes * 60000);
        
            // 返回格式化后的日期字符串
            return adjustedDate.toISOString().replace('T', ' ').substring(0, 19);
        },

        //debug寻找目标用户通过containerID
        async findcontainerID() {
            //获取当前容器的ID
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/container_user', {
                    containerID: this.containerID
                });
                this.username = respones.data.containerUser;
                const ProjectEnable_in = respones.data.ProjectEnable;
                this.ProjectEnable_Number = ProjectEnable_in;

                if (ProjectEnable_in == 1) {
                    this.ProjectEnable = "项目容器";
                } else if(ProjectEnable_in == 0){
                    this.ProjectEnable = "非项目容器";
                }
            } catch (err) {
                console.log(err);
            }
        },

        //验证用户是否为该容器卡片使用者
        async Checktoken() {
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/container_username', {
                    jwttoken: localStorage.getItem('token'),
                    username_containerUser: this.username
                });
                console.log("respones.data.tokenUsername",respones.data.tokenUsername,"--username",this.username);
                if (respones.data.tokenUsername == this.username) {
                    this.MyselfContainer = true;
                }
            } catch (err) {
                console.log(err);
            }
        },
        extendBoard() {
            this.$emit('extendInfomation', this.index);
        },
        info_distand() {
            //debug
            console.log("MovementStatus:", this.Movement, "Movedistand:", this.distandMove);
        },

        //获取该容器密码
        async getContainerPassoword() {
            const token = localStorage.getItem('token');
            try {
                const respones = await axios.post('http://baoding.dreamsky0822.asia:10000/findcontainer-password', {
                    usertoken: token,
                    containerID: this.containerID
                });
                this.adminPassword = respones.data.containerPassword;
            } catch (err) {
                console.log(err, "message");
            }
        },

        //用于复制文本信息
        Infocopytext() {
            this.text = this.$refs.copyinfotext.innerText;
        },

        //debug
        onCopy() {
           
        },
        onError(e) {
            alert('复制失败',e.text);
        }
        
        
    },
    props:{
        containerID: String,
        containerStatus: String,
        index: Number,

        //用于告知该组件是否要向下移动相应距离
        Movement: Boolean,
        //移动距离
        distandMove: Number,

        //是否要展示info面板
        displayInfoBoard: Boolean,
    },
    async mounted() {
        this.update();
        //验证是否为个人容器卡片
        for (let i = 0; i < 3 ; i++){
            await this.findcontainerID();
            await this.Checktoken();
            await this.getContainerPassoword();
        }
        
    },
    watch:{
        status(newVal){
            if (newVal == 'running') {
                this.color = "#5CB338";
                this.statusbool = true;
            } else if (newVal == 'exited') {
                this.color = "#FB4141";
                this.statusbool = false;
            }
        },
    }
}
</script>
<style scoped>
#container{
    -webkit-user-select: none; /* 适用于谷歌浏览器和Safari */ -moz-user-select: none; /* 适用于火狐浏览器 */ -ms-user-select: none; /* 适用于Internet Explorer/Edge */ user-select: none; /* 适用于支持CSS3的浏览器 */
    overflow: hidden;
    left: 0;
    right: 0;
    background-color: rgb(245, 245, 245);
    position: absolute;
    border-radius: 7px;
    height: 90px;
    margin-left: 30px;
    margin-right: 30px;
    margin-top: 20px; 
    box-shadow: 5px 5px 10px rgba(146, 146, 146, 0.5); /* 外阴影 */
    backdrop-filter: blur(10px); /* 应用模糊效果 */
    transition: top 0.5s ease;

    /* opacity: 50%; */
}
#ID{
    width: 130px;
    height: 30px;
    position: relative;
    top: 25px;
    left: 10px;
    
}
#icon{
    position: relative;
    top: 0px;
    left: 10px;
    width: 200px;
    height: 20px;
}
#docker{
    position: absolute;
    left: 5px;
    width: 30px;
    height: 30px;
}
/* #vue{
    position: absolute;
    left: 4px;
    top: 4px;
    width: 22px;
    height: 22px;
} */
#display{
    /* background-color: aquamarine; */
    left: 0px;
    height: 30px;
    position: relative;
    bottom: 45px;
}
#port{
    width: 100px;
    height: 30px;
    position: absolute;
    right: 1px;
    top: 37px;
    font-size: 14px;
    font-weight: 700; /* 最粗 */
}
#backport{
    width: 90px;
    height: 25px;
    position: absolute;
    border-radius: 5px;
    background-color: rgb(239, 239, 239);
    left: 5px;
    bottom: 10px;
    opacity: 50%;
    outline: 2px solid rgb(178, 178, 178);
}
#botton-tool{
    width: 300px;
    height: 32px;
    border-radius: 5px;
    top: -20px;
    position: absolute;
    right: 10px;
}
#status_show{
    width: 70px;
    height: 30px;

    border-radius: 5px;
    position: absolute;
    right: -5px;
    font-size: 14px;
    font-weight: 800; /* 最粗 */
    opacity: 70%;
}
#stop_start{
    width: 125px;
    height: 30px;
    position: absolute;
    right: 70px;
}
#info{
    width: 140px;
    height: 30px;
    position: absolute;
    right: 295px;
    opacity: 70%;
}
#nodejs{
    width: 25px;
    height: 25px;
    position: absolute;
    left: 70px;
    top: 3px;
}
#delete{
    width: 90px;
    height: 30px;
    position: absolute;
    right: 200px;
    border-radius: 5px;
}
#environment{
    width: 240px;
    height: 40px;
    position: absolute;
    left: 8px;
    bottom: -5px;
    top: -18px;
    background-color: rgb(233, 233, 233);
    opacity: 80%;
    border-radius: 10px;
    outline: 1px solid rgb(178, 178, 178);
}
#env{
    left: 5px;
    top: 5px;
}
#info_container{
    position: absolute;
    left: -240px;
    width: 90px;
    height: 30px;
}
#starttime{
    width: 200px;
    height: 30px;
    position: absolute;
    right: 70px;
    top: 63px;
    font-size: 14px;
    font-weight: 700; /* 最粗 */
}
#usernameboard{
    width: 300px;
    height: 30px;
    position: absolute;
    left: 165px;
    font-size: 50px;
    font-weight: 550;
    top: -17px;
    text-align: left;
    color: #6e89ff;
    /* 可操纵色系 */
    /* color: #9caeff; */

    /* 正在运行色系 */


    /* 不可操纵色系 */
    /* color: #858585; */
    opacity: 40%;
}
#infomationBoard{
    position: absolute;
    left: 0;
    right: 0;
    height: 200px;
    background-color: rgb(245, 245, 245);
    margin-left: 30px;
    margin-right: 30px;
    border-radius: 10px;
    outline: 1px solid rgb(182, 182, 182);
    box-shadow: 5px 5px 10px rgba(146, 146, 146, 0.5); /* 外阴影 */
    transition:  0.5s ease;
    
}
#copybutton{
    width: 30px;
    height: 30px;
}
#mem{
    position: absolute;
    height: 50px;
    left: 600px;
    top: -24px;
    right: 600px;
}
</style>