#!/bin/sh
import sys
import json
import subprocess
import time
from datetime import datetime

# 用户选择
option = sys.argv[1]


# 获取目标文件地址
userfile = "userfile.json"

# 读取文件
with open(userfile, 'r') as file:
    data = json.load(file)

print("当前的codeserver-docker容器有以下用户")
print(data)

print("选择需要对docker容器的操作:\n0.扩增容器\n1.删除指定容器\n2.容器代理订阅链接设置\n3.预估剩余可增加的容器\n4.取消操作")

option_user = input()

cureent_time = datetime.now()

# 生成唯一时间码
def createtimes(timenow):
    return str(timenow.year) + str(timenow.month) + str(timenow.day) + str(timenow.hour) + str(timenow.minute) + str(timenow.second)


# 判断git链接是否有效
def checkgiturl(repo_url):
    try:
        # 运行 git ls-remote 验证仓库链接
        result = subprocess.run(['git', 'ls-remote', repo_url],
                            stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        if result.returncode == 0:
            print("仓库链接有效。")
            return False
        else:
            print("仓库链接无效或没有访问权限。")
            print("错误信息:", result.stderr)
            return True
    except Exception as e:
        print("发生错误:", str(e))
        return False


def add_container():
    print("输入passowrd:")
    passowrd = input()
    print("输入git仓库的目标克隆地址:")

    # debug地址 https://github.com/wuming082/program_web.git
    git_url = input()
    if(checkgiturl(git_url)):
        return 

    print("输入您的预期端口服务[10030 - 10045]")
    port_server = input()
    port_server = int(port_server)

    if (port_server < 10030 or port_server > 10045):
        print("err port")
        return

    print("正在构建容器环境")
    # 运行脚本并传递参数 
    result = subprocess.run(['sh', 'startcodeserver.sh', git_url, passowrd, str(
        port_server), createtimes(cureent_time)], stdout=subprocess.PIPE, text=True)
    print(result)

    debugarg = 0

    inputcontainer = result.stdout.strip()
    inputcontainertrans = result.stdout.strip().split('\n')[-1]
    # inputcontainer = "null"
    # inputcontainertrans = "null"

    print("docker容器已就绪\n\n容器详情:" + inputcontainer +
          "\n\n容器ID:" + inputcontainertrans + '\ncodeserver默认网址 http://baoding.dreamsky0822.asia:3041/\n' + "密码:" + passowrd)

    #将所选容器信息加载到json文件当中
    new_user = {"container_id": inputcontainertrans                
        , "git_url": git_url ,"password": passowrd , "destport": port_server}

    data['users'].append(new_user)

    with open(userfile, 'w') as file:
        json.dump(data, file , indent=4)

# 删除容器
def dismiss_container():
    print("container list:\n")

    check = False
    # 打印目前容器
    for user in data['users']:
        print(f"容器ID:{user['container_id']},容器密码:{user['password']}\n")
        check = True

    if (check == False):
        print("目前没有正在运行的容器")
        return  

    print("选择你要删除的容器 输入容器ID前三位:")
    container_option = input()

    
    subprocess.run(['docker', 'stop', container_option], check=True)

    #检测容器
    while True:
        result = subprocess.run(
            ['docker', 'inspect', '--format="{{.State.Running}}"', container_option], stdout=subprocess.PIPE, text=True)

        if 'false' in result.stdout:
            break

    print(f"正在等待暂停容器{container_option}停止...")
    time.sleep(2)

    print(f"正在删除容器 {container_option}...")
    subprocess.run(['docker', 'rm', container_option], check=True) 
    print(f"容器 {container_option} 已删除。")

    data['users'] = [user for user in data['users']
                     if user['container_id'][:3] != container_option]

    with open(userfile, 'w') as file:
        json.dump(data, file, indent=4)

        

if (option_user == '0'):
    add_container()

if (option_user == '1'):
    dismiss_container()

    

