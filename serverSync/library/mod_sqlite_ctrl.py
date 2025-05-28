import json
import datetime
import os
from pathlib import Path
import sqlite3
import secrets
import string



class ModHandleSqlite:
    def __init__(self):
        self.mod_list_path = ""
        self.sqlite_path = ""

        self.table_list = ["mod_library", "user_account"]
        # self.table_list_information = [
        #     {
        #         ["mod_library"],
        #         ["id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,age INTEGER"]
        #     }
        # ]

        print(self.mod_list_path)

        self.mod_list_path_set = False

    # 检查是存在当前目录的环境
    def _path_check(self,workPath):
        
        # 形成目录地址
        path = Path(workPath)
        
        # 判断是否是目录地址
        if Path(workPath).exists():

            # 检测是否为空
            if workPath == "":
                return False
            return True if path.is_dir() else False
        else:
            return False

    # 检测目录时候合法
    def _mod_list_path_value(self,workPath):

        if self._path_check(workPath):
            return workPath
        else:
            print("ERROR PATH")
            # 获取默认目录环境
            return str(Path.cwd() / "modList")

    # 生成sqlite环境目录
    def _sqlite_path_value(self):
        path = str(Path.cwd() / "data")
        return path
    
    # 进行初始赋值
    def value_init_setpath(self,workPath):
        self.mod_list_path = self._mod_list_path_value(workPath)
        print(self.mod_list_path)
        self.sqlite_path = self._sqlite_path_value()
        self.mod_list_path_set = True

    # 获取mod地址
    def get_mod_path(self):
        if self.mod_list_path == "" or self.mod_list_path_set == False:
            self.value_init_setpath()
            return self.mod_list_path
        else:
            return self.mod_list_path

    # 生成10位id名称文件
    def _generate_file(self):
        # 生成包含大小写字母 + 数字的8字符字符串
        # 如果工作目录没有被创建，则报错
        if self.mod_list_path_set == False:
            raise ValueError("ERROR PATH NOT SET")
        characters = string.ascii_letters + string.digits  # a-zA-Z0-9
        unique_name = ''.join(secrets.choice(characters) for _ in range(15))

        new_path = Path(self.mod_list_path + f'/{unique_name}')

        os.mkdir(new_path)
        print(f"new file:{new_path}")

        return unique_name

    # 创建所有数据表
    def _sqlite_build_init_all(self):
        for table_item in self.table_list:
            # 遍历创建
            self._sqlite_build_init(tableName=table_item)

    def _sqlite_build_init_all_aio(self):

        self._sqlite_build_init_select_modlist(tableName=self.table_list[0])
        self._sqlite_build_init_select_useraccount(tableName=self.table_list[1])

    # 创建mod图书馆列表
    def _sqlite_build_init_select_modlist(self,tableName):
        database_name = "minecraft_sync.db"
        destnation_path = f"{self.sqlite_path}/{database_name}"

        # 连接数据库
        conn = sqlite3.connect(destnation_path)
        cursor = conn.cursor()
        # 执行 SQL 建表语句
        cursor.execute(f'''
            CREATE TABLE IF NOT EXISTS {tableName} (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_manger_id INTEGER NOT NULL,
                mod_library_link TEXT NOT NULL
            )
        ''')
        conn.commit()
        conn.close()

    # 创建用户表列表
    def _sqlite_build_init_select_useraccount(self, tableName):
        database_name = "minecraft_sync.db"
        destnation_path = f"{self.sqlite_path}/{database_name}"

        # 连接数据库
        conn = sqlite3.connect(destnation_path)
        cursor = conn.cursor()
        # 执行 SQL 建表语句
        cursor.execute(f'''
            CREATE TABLE IF NOT EXISTS {tableName} (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                internet_token INTEGER NOT NULL
            )
        ''')
        conn.commit()
        conn.close()

        
mod_sqlite_handler = ModHandleSqlite()


if __name__ == "__main__":

    mod_sqlite_handler.value_init_setpath(workPath="")
    mod_sqlite_handler._sqlite_build_init_all_aio()
    print(f"modListID:{mod_sqlite_handler._generate_file()}")