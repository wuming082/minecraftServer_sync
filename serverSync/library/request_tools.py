from flask import Flask, request, jsonify
from flask import send_from_directory
from flask_cors import CORS
import jwt
import datetime
import os
import asyncio

# 假设这是你的数据库模块
from .mod_sqlite_ctrl import mod_sqlite_handler
from .user_manger import UserAccountManger

app = Flask(__name__)
CORS(app)  # 自动允许所有跨域请求，生产环境建议指定 origin

def _start_flask():
    app.run(host="0.0.0.0", port=6730, debug=True)

# JWT 密钥和生成函数
SECRET_KEY = "Dreamsky_wuming_web"

def generate_jwt():
    payload = {
        "username": "admin",
        "exp": datetime.datetime.utcnow() + datetime.timedelta(hours=1)
    }
    token = jwt.encode(payload, SECRET_KEY, algorithm="HS256")
    return token

# 获取服务器状态
@app.route("/server_status", methods=["GET"])
def get_snycserver_status():
    return jsonify({
        "status": "health",
        "log_engine": "health"
    })

# 开发环境测试函数(下载mod)
@app.route("/mod_request_snyc/<mod_id>/<mod_name>", methods=["GET"])
def get_snyc_mod_list(mod_id,mod_name):
    print(mod_id,mod_name)
    # 寻找下载途径
    file_path = mod_sqlite_handler._get_mod_path() + '/' + mod_id
    try:
        return send_from_directory(file_path, path=mod_name, as_attachment=True)
    except Exception as e:
        print(str(e))
        return str(e), 404

# 获取所有mod仓库列表
@app.route("/modlist", methods=["GET"])
def get_mod_list():
    try:
        mod_have = mod_sqlite_handler._get_all_table_modlist()
        return jsonify({
            "status": "success",
            "mod_list": mod_have
        })
    except Exception as e:
        return jsonify({
            "status": "error",
            "message": str(e)
        }), 500

# 获取对应mod仓库的内部列表
@app.route("/modlist_id/<mod_id>", methods=["GET"])
def get_mod_list_id(mod_id):
    try:
        mod_have,check = mod_sqlite_handler._information_collection(select_mod_library=mod_id)
        if check:
            return jsonify({
                "status": "success",
                "mod_id": mod_id,
                "mod_list": mod_have
            })
        else:
            return jsonify({
                "status": "error",
                "message": str("NOT FOUND MOD ID")
            }), 500
    except Exception as e:
        return jsonify({
            "status": "error",
            "message": str(e)
        }), 500

# 用户登录接口
@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    username = data.get("username")
    password = data.get("password")

    # 这里你可以添加真正的验证逻辑
    token = generate_jwt()

    return jsonify({
        "token": token
    })

@app.route("/enrollment", methods=["POST"])
def enrollment():
    data = request.get_json()
    username = data.get("username")
    password = data.get("password")
    token = data.get("check")

    if token != mod_sqlite_handler.get_verify_key():
        return jsonify({
            "message": "enrollment account error: verify key error"
        }),500

    user = UserAccountManger()
    # 数据安全认证
    user_exisit = user.user_exisit(username=username)
    if user_exisit == False:
        return jsonify({
            "message": "enrollment account error: exisit this username"
        }),500
    # 进行注册
    verify = user.enrollment_newuser_database(username=username,token=0,password=password)
    if verify:
        return jsonify({
            "message": "enrollment account succues"
        }),200
    else:
        return jsonify({
            "message": "enrollment account error"
        }),500
    

if __name__ == "__main__":
    mod_sqlite_handler.value_init_setpath()

    

    