const express = require('express');
const cors = require('cors');
const app = express();
const { spawn } = require('child_process'); // 确保拼写正确
const port = 10000;
const axios = require('axios');
const { send, uptime } = require('process');
const jwt = require('jsonwebtoken');
const db = require('./dataJs/db');
const { promises, resolve } = require('dns');
const { rejects } = require('assert');
const token_key = '1jhkhofoiejfiuwehf'
const adminUser = 'dreamsky'

// 解析 JSON 请求体
app.use(cors()); // 使用 cors 中间件
app.use(express.json());

// 获取所有 container 记录的函数
function getAllContainers(db, callback) {
    const sql = "SELECT * FROM docker_containers";
    
    db.all(sql, [], (err, rows) => {
        if (err) {
            console.error("Error executing SQL query:", err.message);
            return callback(err);
        }
        // 返回结果给回调函数
        callback(null, rows);
    });
}


//数据库测试函数
//添加用户
function addUser(db, username, password, email, callback) {
    const sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    return new Promise((resolve, reject) => {
        db.run(sql, [username, password, email], (err) => {
            if (err) {
                return reject(err);
            }
            resolve(this.lastID);
        })
    }) 
}

//查找用户
function findUser(db, username) { 
    const sql = "SELECT * FROM users WHERE username = ?";
    return new Promise((resolve, reject) => {
        db.get(sql, [username], (err, row) => {
            if (err) {
              return reject(err); 
            }
            return resolve(row); // 如果用户存在，row 将包含用户信息；如果不存在，row 将为 undefined  
        });
    })
}

//添加容器信息
function addDockerContainer(db, containerId, username, password, project , callback) {
    containerId = containerId.split('\n')[0];
    const sql = "INSERT INTO docker_containers (container_id , username, container_enter_password , project_contaienr) VALUES (?, ?, ? ,?)";
    db.run(sql, [containerId, username, password , project], function(err) {
        if (err) {
            console.log('adderr',username);
            return callback(err);
        }
        callback(null, this.lastID); // 返回插入的容器 ID
        console.log("containerUsername:",username,"build container:->",password);
    });
}
 
//查找容器用户_00
function findContainerUser(db, containerID, callback) {
    const sql = "SELECT username , project_contaienr FROM docker_containers WHERE container_id = ?"; 
    console.log("container_id:",containerID);
    db.get(sql, [containerID], (err, row) => {
        if (err) {
            console.log(err);
            return callback(err);
        }
        callback(null,row ? row.username : null , row.project_contaienr);
        console.log("row.username:", JSON.stringify(row),"row.project",row.project_contaienr);
    });
}

//查找容器用户_01
function findContainerUser_01(db, containerID) {
    return new Promise((resolve, reject) => {
        const sql = "SELECT username FROM docker_containers WHERE container_id = ?";
        console.log("container_id:",containerID);
        db.get(sql, [containerID], (err, row) => {
            if (err) {
                console.log(err);s
                return reject(err);
            }
            console.log("row.username:", JSON.stringify(row));
            resolve(row ? row.username : null );
        });
    })
}

//查找容器密码
function findContainerPassword(db, containerID) {
    return new Promise((resolve, reject) => {
        const sql = "SELECT container_enter_password FROM docker_containers WHERE container_id = ?";
        console.log("containerID:", containerID, 'finding password');
        db.get(sql, [containerID], (err, row) => { 
            if (err) {
                console.log(err);
                return reject(err);
            }
            console.log("row.username:", JSON.stringify(row));
            resolve(row ? row.container_enter_password : null);
        });
    })
}

//验证用户密码
function findUserPassword(db ,username) {
    return new Promise((resolve, reject) => {
        const sql = "SELECT password FROM users WHERE username = ?";
        db.get(sql, [username], (err, row) => { 
            if (err) {
                console.log(err);
                return reject(err); 
            }
            resolve(row ? row.password : null);
        });
    })
}

//检查链接是否合法
app.post('/checkgit', (req, res) => {
    const { condition } = req.body; // 从请求体中获取数据
    console.log(condition);
    const pythonProcess = spawn('python3', ['./python/check.py', condition]);
    pythonProcess.stdout.on('data', (data) => {
        console.log("data",String(data));
        if (data.toString().trim() == 'true') {
            res.status(200).json({ result:true })
        } else {
            res.status(200).json({ result:false })
        }
    })
});

//创建容器规则
function addcontainer(jsondata) {
    //接收一个json对象并返回一个json对象
    //解包json

}

//通过jwt模块获取用户名称
function JwtFindUser(jwttoken) {
    let username = null;
    jwt.verify(jwttoken, token_key, (err, decoded) => {
        if (err) {
            console.error("can not find this usertoken:",jwttoken,":",err);
            return err;
        }
        //返回用户id
        console.log("JwtFindUser:", decoded.id);
        username = decoded.id;
    })
    return username;  
}

//创建容器路由
app.post('/createcontainer',async (req, res) => {
    const condition = req.body;
    console.log("condition", condition);
    const port = condition.user.port;
    const password = condition.user.password;
    const giturl = condition.user.gitUrl;
    const jwttoken = condition.user.Usertoken;
    const project = condition.user.Project;


    //输出项目模式
    if (project) {
        console.log("克隆项目模式");
    } else {
        console.log("非克隆项目模式");
    }


    //接收用户名称
    //查找目标用户jwtUser 
    const username = JwtFindUser(jwttoken);
    console.log(username,"build container",'userToken:',jwttoken); 
    console.log('password:',password,'port:',port,'giturl',giturl);
    //创建容器开始构建
    //docker run -d -p $port:8080 -it --name codeserve-$containertime -e git_url_user="$git_url" -e PASSWORD=$PASSWORD codeserver-wuming:0.9
    //生成时间戳
    const time_point = Math.floor(Date.now() / 1000);
    console.log("time", time_point);
    const dockerctrl = spawn('docker', ['run'
        , '-d', '-p', `${port}:8080`, '-it', '--name', `codeserver-${time_point}`
        , '-e', `git_url_user=${giturl}`, '-e', `PASSWORD=${password}`,
        'codeserver-wuming:0.9'])
    // const dockerctrl = spawn('docker', ['run'
    //     , '-d', '-p', `${port}:8080`, '-it', '--name', `codeserver-${time_point}`
    //     , '-e', `git_url_user=${giturl}`, '-e', `PASSWORD=${password}`,
    //     'codeserver-wuming:0.9'])
    let respones = '';
    
    dockerctrl.stdout.on('data', (data) => {
        console.log("stdout",data.toString()); 
        respones += data.toString();
    })
    dockerctrl.stderr.on('data', (data) => { 
        console.error("stderr", data.toString());
        respones += data.toString();
    })
    dockerctrl.on('close', (code) => {
        console.log('responesID:', respones);  
        //将容器ID与创建用户进行绑定
        //绑定用户
        addDockerContainer(db, respones, username,password, project , (err, userid) => {
            if (err) {
                console.log(err);
            }
            console.log("addDockerContainer:",userid); 
        })  
        console.log("codeContainerInfo",code); 
        res.json({
            time: time_point, 
            responescli: respones,
        });
    })
    
    //addcontainer();
});

//创建非克隆容器路由
app.post('/createcontainer_Enableclone',async (req, res) => { 
    const condition = req.body;
    console.log("condition", condition);
    const port = condition.user.port;
    const password = condition.user.password;
    const giturl = condition.user.gitUrl;
    const jwttoken = condition.user.Usertoken;
    const project = condition.user.Project;


    //输出项目模式
    if (project) {
        console.log("克隆项目模式");
    } else {
        console.log("非克隆项目模式");
    }

    //接收用户名称  
    //查找目标用户jwtUser
    const username = JwtFindUser(jwttoken);
    console.log(username,"build container",'userToken:',jwttoken); 
    console.log('password:',password,'port:',port,'giturl',giturl);
    //创建容器开始构建
    //docker run -d -p $port:8080 -it --name codeserve-$containertime -e git_url_user="$git_url" -e PASSWORD=$PASSWORD codeserver-wuming:0.9
    //生成时间戳
    const time_point = Math.floor(Date.now() / 1000);
    console.log("time", time_point);
    const dockerctrl = spawn('docker', ['run'
        , '-d', '-p', `${port}:8080`, '-it', '--name', `codeserver-${time_point}`
        , '-e', `PASSWORD=${password}`, 
        'codeserver-wuming-base'])
    // const dockerctrl = spawn('docker', ['run'
    //     , '-d', '-p', `${port}:8080`, '-it', '--name', `codeserver-${time_point}`
    //     , '-e', `git_url_user=${giturl}`, '-e', `PASSWORD=${password}`,
    //     'codeserver-wuming:0.9'])
    let respones = '';
    
    dockerctrl.stdout.on('data', (data) => {
        console.log("stdout",data.toString()); 
        respones += data.toString();
    })
    dockerctrl.stderr.on('data', (data) => { 
        console.error("stderr", data.toString());
        respones += data.toString();
    })
    dockerctrl.on('close', (code) => {
        console.log('responesID:', respones);  
        //将容器ID与创建用户进行绑定
        //绑定用户
        addDockerContainer(db, respones, username,password, project, (err, userid) => {  
            if (err) {
                console.log(err);
            }
            console.log("addDockerContainer:",userid); 
        })  
        console.log("codeContainerInfo",code); 
        res.json({
            time: time_point, 
            responescli: respones,
        });
    })
    
    //addcontainer();
});

//删除目标容器
app.post('/deletecontainer', async (req, res) => {
    try {
        const { containerID } = req.body;
        const respones = await axios.delete(`http://localhost:2375/containers/${containerID}`);
        if (respones.status == 204) {
            return res.status(204).send(`container${containerID} be delete in docker server`);
        } else { 
            return res.status(404).send(`container${containerID} is Unormal`)
        }
    } catch (err) {
        console.log("deletecontainer err", err);  
        return res.status(404).send("ERR",err);    
    }
});

//检测目标容器status
app.post('/getcontainerstatus', async (req, res) => { 
    try {
        const { containerImage } = req.body;
        const containerimagse = containerImage
        const respones = await axios.get(`http://localhost:2375/containers/json?all=true&filters={"ancestor":["${containerimagse}"]}`);
        //将打包好的json文件发送
        //回送
        res.json(respones.data);
    } catch (err) {
        console.log("search-container err", err);
        res.status(500).send("NOT-FOUND");
    }
});

//查找目标容器使用者
app.post('/container_user', async (req, res) => {
    // getAllContainers(db, (err, row) => {
    //     if (err) {
    //         console.log(err);
    //     }
    //     console.log(row);
    // })
    try {
        const { containerID } = req.body;
        console.log("containerID -> user finding...");
        let username = null;
        findContainerUser(db, containerID, (err, user , project) => {
            if (err) {
                console.log(err, "container_user Error");
                return res.status(500).json({ err: err });
            }
            username = user;
            projectEnbale = project;
            return res.json({
                containerUser: username,
                ProjectEnable: projectEnbale, 
            });
        })
        
    } catch (err) {
        console.err(err);
        return res.status(404).json({ err: err });
    }
})
//查找容器密码规则
app.post('/findcontainer-password',async (req, res) => {
    const { usertoken, containerID } = req.body;
    console.log("start check");
    //首先验证usertoken所解析的用户组是否与相应containerID容器管理者相符合
    let token_user = null;
    try {
        token_user = JwtFindUser(usertoken);
    } catch (err) {
        console.error(err);
        return res.status(404), json({ message: "404 NOT FOUND" });
    }
    console.log("usertoken -> ",token_user);

    //获取该容器管理员名称
    let admincontainerUser = await findContainerUser_01(db, containerID);

    //进行名称判断
    if (token_user != admincontainerUser) {
        console.log("user id diffient");
        return res.status(500).json({message : "user id diffient"});
    }

    //获取相应container的密码
    let containerAdminPassword = await findContainerPassword(db, containerID);
    
    res.json({ containerPassword : containerAdminPassword});
});
app.get('/dreamsky', (req, res) => {
    res.send("你看你码呢");
});


//查看单个容器的全部信息
app.post('/reception_container_info', async (req, res) => {
    const { containerID } = req.body;  
    console.log("condition-id", containerID);
    try {
        const respones = await axios.get(`http://localhost:2375/containers/${containerID}/json`);
        return res.json(respones.data);  
    } catch (err) { 
        console.error(`condition not effective:${containerID}`, err) ;
        return res.status(500), send(`condition not effective:${containerID}`, err);
    } 
    //res.send(containerID); 
    
});

//停止容器
app.post('/stopcontainer', async (req, res) => {
    const { containerID } = req.body;
    console.log("stop container",containerID);
    try {
        ///v1.47/containers/{id}/stop
        const respones = await axios.post(`http://localhost:2375/containers/${containerID}/stop`)
        if (respones.status == 204) {
            console.log(`container:${containerID} stop`);
            return res.status(204).send("stop container");
        } else if (respones.status == 304) {  
            return res.status(304).send("the container is be stop");
        } 
        return res.send(respones.data);
    } catch (err) {
        console.log("err",err);
    }
});

//启动容器
app.post('/startcontainer', async (req, res) => { 
    const { containerID } = req.body;
    console.log("start container",containerID); 
    try {
        ///v1.47/containers/{id}/stop 
        const respones = await axios.post(`http://localhost:2375/containers/${containerID}/start`); 
        if (respones.status == 204) {
            console.log(`container:${containerID} start`);
            return res.status(204).send("start container");
        } else if (respones.status == 304) {  
            return res.status(304).send("the container is be start");
        } 
        return res.send(respones.data);
    } catch (err) {
        console.log("err",err);
    }
});


//创建登陆规则
app.post('/login', async (req, res) => {
    const { username, password } = req.body;
    
    let user = null;
    try {
        user = await findUser(db, username); 
    } catch (err) {
        console.error(err);
    }

    //验证是否存在该用户
    console.log("findUser:",user);
    if (!user) {
        console.log("Not find dest user");
        return res.status(404).json({ message: "Not find dest user" });
    }
 
    let password_check = null;
    try { 
        password_check = await findUserPassword(db, username);
    } catch (err) {
        console.error(err);
    }

    //验证密码是否为用户密码
    if (password != password_check) {
        return res.status(404).json({message : "密码错误"}); 
    }

    const token = jwt.sign({ id: username }, token_key, { expiresIn: '1h' });
    res.json({ token });  

});
// return res.status(404).json({message : "密码错误"});

//创建注册规则
app.post('/sign_up', async (req, res) => {
    const { username, password, email, check } = req.body;
    console.log(check);
    
    //用于匹配非法字符的正则
    const regex = /[^a-zA-Z0-9-_]/;

    //检测用户是否存在
    let user = null;
    try {
        user = await findUser(db, username);
    } catch (err) {
        console.error(err);
    }

    if (check != "dreamskyWZP") {
        return res.status(404).json({ message: "邀请码错误" , errorkind : 3}); 
    }
    if (user) {
        return res.status(404).json({ message : "用户已经存在" , errorkind : 0});
    }
    if(username == '' || username.length < 5){
        return res.status(404).json({ message: "名称过短" , errorkind : 0});
    }
    if (password) {
        if (password.length < 8) {
            return res.status(404).json({ message: "密码应大于8", errorkind: 1 });
        }
        if (regex.test(password)) {
            return res.status(404).json({ message: "应输入有效密码", errorkind: 1 });
        }
    }else {
        return res.status(404).json({ message: "未设置密码" , errorkind : 1});
    }

    // findUser(db, username, (err, user) => {
    //     if (check != "dreamskyWZP") {
    //         return res.status(404).json({ message: "邀请码错误" , errorkind : 3});
    //     }
    //     if (user) {
    //         return res.status(404).json({ message : "用户已经存在" , errorkind : 0});
    //     }
        
    //     if(username == '' || username.length < 5){
    //         return res.status(404).json({ message: "名称过短" , errorkind : 0});
    //     }
    //     if (password) {
    //         if (password.length < 8) {
    //             return res.status(404).json({ message: "密码应大于8" , errorkind : 1});  
    //         }
    //         if (regex.test(password)) {
    //             return res.status(404).json({ message: "应输入有效密码" , errorkind : 1});  
    //         }
    //     } else {
    //         return res.status(404).json({ message: "未设置密码" , errorkind : 1});
    //     }
    const user_add = addUser(db, username, password, email);
    return res.json({ message : "注册成功"});

    // if (err) {
    //     return res.status(500).json({ message: err.message });
    // }
    // console.log(password);
    // console.log("注册成功",user);
})

//通过jwt获取时限规则
app.post('/jsonwebTokenCheck', async (req, res) => {
    const { token } = req.body;
    console.log(token);
    jwt.verify(token, token_key, (err, decoded) => {
        if (err) {
            return res.status(403).json({ message: 'Invalid token' }); 
        }
        // 提取用户名
        console.log('decoded',decoded);
        const username = decoded.id; 
        const jwtEndtime = decoded.exp - Math.floor(Date.now() / 1000);
        console.log(jwtEndtime);
        if (jwtEndtime < 0) {  
            res.status(404);
        } else {
            res.json({message : "token_pass"})
        }
    }); 
}) 
 

//通过jwt获取用户名称
app.post('/container_username', async (req, res) => {
    //获取用户密钥
    const { jwttoken , username_containerUser } = req.body;
    try {
        let user = JwtFindUser(jwttoken);
        if (user == adminUser) {
            user = username_containerUser;
        }
        return res.json({tokenUsername: user});
    } catch (err) {
        res.status(404).json({ message: "NOT FIND" });
    }
})

//刷新jwt签证
app.post('/refreshtoken', (req, res) => {
    //获取用户密钥
    const { old_token } = req.body;
    //验证在时间之内
    try {
        const username = JwtFindUser(old_token);
        if (!username) {
            return res.status(404).json({ errormessage: "token_error" });
        }
        //创建新的token并返回
        const new_token = jwt.sign({ id: username }, token_key, { expiresIn: '1h' });
        return res.json({ new_token: new_token });
    } catch (err) {
        return res.status(404).json({ errormessage: "token_last" });
    }

});

// 启动服务器
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`); 
});


//创建容器函数



//测试dockerapi
app.get('/docker-934814193419837', async (req, res) => {
    const respones = await axios.get(`http://localhost:2375/containers/json?all=true&filters={"ancestor":["${containerimagse}"]}`);
    res.send(respones.data);
});
//测试docker单个容器测试
app.get('/docker-wadawdawd7', async (req, res) => {
    const respones = await axios.get(`http://localhost:2375/containers/fcf11f7091300967137794d74a4c5309bdf9620b5f48f10bc8abc79efefdcf21/json`);
    res.send(respones.data);
});


