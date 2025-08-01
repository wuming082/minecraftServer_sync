import json
import os
import sqlite3
from .mod_sqlite_ctrl import mod_sqlite_handler




class UserAccountManger:
    def __init__(self):

        self.user_init_status = False
        self.user_database_path,self.user_database_name = mod_sqlite_handler.get_sqlite_path_list()

        # 初始化数据库
        self._init_user_circumstance()
    
    # 设置

    # 用户存在认证
    def user_exisit(self,username):
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()

            process.execute(f"SELECT * FROM {mod_sqlite_handler.get_table_list()[1]} WHERE username = ?;",(username))
            username = process.fetchall()

            if len(username) > 0:
                return False # 表示不能注册
            else:
                return True # 表示可以注册
        except Exception as e:
            print(f"SELECT USER ACCOUNT EXISIT ERROR{e}")
            return False

    # 初始化数据库函数
    def _init_user_circumstance(self):
        self._sqlite_build_init_table_user_link()

    # 注册新用户函数
    def enrollment_newuser_database(self,username,token,password):

        if username == "" or password == "":
            return False

        # 用户名称以及密码合法性检测
        self._insert_useraccount_sqlite(username=username,token=token,passowrd=password)
        return True

    def _transfer_username_to_id(self,username):
        """
        _transfer_username_to_id 将username通过数据库转化为相应的id
        username:数据库存在的相应用户名称
        """
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()
            process.execute(f" SELECT * FROM {mod_sqlite_handler.get_table_list()[1]} WHERE username = ?;",(username,))
            row = process.fetchall()
            return row[0][0]
        except Exception as e:
            print(f"SELECT TRANSFER USERNAME TO ID ERROR:{e}")
            return None

    def _transfer_id_to_username(self,userid):
        """
        _transfer_id_to_username 将userid通过数据库转化为相应的username
        userid:数据库存在的相应用户id
        """
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()
            process.execute(f" SELECT * FROM {mod_sqlite_handler.get_table_list()[1]} WHERE id = ?;",(userid,))
            username = process.fetchall()[0][1]
            return username
        
        except Exception as e:
            print(f"SELECT TRANSFER ID TO USERNAME ERROR:{e}")
            return None

    # 创建数据函数
    def _sqlite_build_init_table_user_link(self):
        
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()

            process.execute(f"""
                CREATE TABLE IF NOT EXISTS {mod_sqlite_handler.get_table_list()[2]}(
                    userid INTEGER NOT NULL,
                    mod_library TEXT NOT NULL
                )
            """)

            conn.commit()
            conn.close()
        
        except Exception as e:
            print(f"BUILD INIT SQLITE USER LINK ERROR:{e}")

    # 用户名单与mod数据连接函数
    def link_user_to_mod_library(self,userid,mod_library):
        """
        link_user_to_mod_library 设置相应mod仓库的归属权
        userid:int
        mod_library:string
        LINK userid <-----> mod_library
        """
        # 首先检查用户名称是否存在
        # 检查mod库是否存在
        if mod_sqlite_handler.mod_library_exisit(mod_library) == False:
            print(f"NOT FOUND EXISIT MOD LIBRARY:{mod_library}")
            return False

        # user名称与mod库进行连接
        self._insert_userlink_sqlite(userid=userid,mod_library_name=mod_library)
    
    def _insert_userlink_sqlite(self,userid,mod_library_name):
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()

            # 插入用户linkmod表
            process.execute(f"""
                INSERT INTO {mod_sqlite_handler.get_table_list()[2]} (userid,mod_library) VALUES (?, ?)
            """,(userid,mod_library_name))

            conn.commit()
            conn.close()

        except Exception as e:
            print(f"INSERT USERLINK TABLE ERROR:{e}")

    # 插入用户表单数据函数
    def _insert_useraccount_sqlite(self,username,token,passowrd):
        try:
            conn = sqlite3.connect(self.user_database_path + '/' + self.user_database_name)
            process = conn.cursor()

            process.execute(f"""
                INSERT INTO {mod_sqlite_handler.get_table_list()[1]} (username,internet_token,password) VALUES (?, ?, ?)
            """,(username,token,passowrd))

            conn.commit()
            conn.close()
            return True,None

        except Exception as e:
            print(f"ERROR INSERT USER:{e}")
            return False,e



if __name__ == "__main__":
    mod_sqlite_handler.value_init_setpath()
    user = UserAccountManger()

    # user.enrollment_newuser_database('dreamsky',12,'WZP8460121')
    print(user._transfer_username_to_id(username="dreamsky"))
    print(user._transfer_id_to_username(userid=1))
    # user.link_user_to_mod_library(12,'DuU3gSXL1IDk5SF')

    


# 创建用户表列表

# def _sqlite_build_init_select_useraccount(self, tableName):
#     destnation_path = f"{self.sqlite_path}/{self.database_name}"
#     # 连接数据库
#     conn = sqlite3.connect(destnation_path)
#     cursor = conn.cursor()
#     # 执行 SQL 建表语句
#     cursor.execute(f'''
#         CREATE TABLE IF NOT EXISTS {tableName} (
#             id INTEGER PRIMARY KEY AUTOINCREMENT,
#             username TEXT NOT NULL,
#             internet_token INTEGER NOT NULL,
#             password TEXT NOT NULL
#         )
#     ''')
#     conn.commit()
#     conn.close()