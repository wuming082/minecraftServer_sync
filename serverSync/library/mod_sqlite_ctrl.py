import json
import datetime
import os
from pathlib import Path
import sqlite3
import secrets
import string
import threading
import shutil


class ModHandleSqlite:
    def __init__(self):
        self.mod_list_path = ""
        self.sqlite_path = ""

        self.table_list = ["mod_library", "user_account"]
        self.database_name = "minecraft_sync.db"

        print(self.mod_list_path)

        self.mod_list_path_set = False
        self.mod_sqlite_set = False

    # 检查是存在当前目录的环境
    def _path_check(self, workPath):

        if workPath == None:
            return False
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
    def _mod_list_path_value(self, workPath):

        if self._path_check(workPath):
            return workPath
        else:
            print("ERROR PATH")
            # 获取默认目录环境
            path = Path(__file__).parent.parent / "modList"
            return str(path)

    # 生成sqlite环境目录
    def _sqlite_path_value(self):

        # 获取当前模块目录
        path = str(Path(__file__).parent.parent / "data")
        return path

    # 进行初始赋值
    def value_init_setpath(self, workPath=None):
        self.mod_list_path = self._mod_list_path_value(workPath)
        print(self.mod_list_path)
        self.sqlite_path = self._sqlite_path_value()
        self.mod_list_path_set = True

        # 创建数据
        self._sqlite_build_init_all_aio()

    # 获取存放mod文件夹的工作地址
    def get_mod_path(self):
        if self.mod_list_path == "" or self.mod_list_path_set == False:
            self.value_init_setpath()
            return self.mod_list_path
        else:
            return self.mod_list_path

    # 生成相应的mod列表
    def _insert_modlist_sqlite(self, user_mangerID, mod_libraryLink, summary_mod_library=None):

        if summary_mod_library == None:
            try:
                conn = sqlite3.connect(self.sqlite_path + "/" + self.database_name)
                process = conn.cursor()
                process.execute(f"""
                    INSERT INTO {self.table_list[0]} (user_manger_id, mod_library_link)
                    VALUES (?, ?)  
                """, (user_mangerID, mod_libraryLink))
                conn.commit()
                conn.close()
            except Exception as e:
                print(f"INSERT ERROR SQLITE:{e}")
        else:
            try:
                conn = sqlite3.connect(
                    self.sqlite_path + "/" + self.database_name)
                process = conn.cursor()
                process.execute(f"""
                    INSERT INTO {self.table_list[0]} (user_manger_id, mod_library_link,information_mod_library)
                    VALUES (?, ?, ?)  
                """, (user_mangerID, mod_libraryLink,summary_mod_library))
                conn.commit()
                conn.close()
            except Exception as e:
                print(f"INSERT ERROR SQLITE:{e}")


    # 生成10位id名称文件

    def _generate_file(self,remark=None):
        # 生成包含大小写字母 + 数字的8字符字符串
        # 如果工作目录没有被创建，则报错
        if self.mod_list_path_set == False:
            raise ValueError("ERROR PATH NOT SET")
        characters = string.ascii_letters + string.digits  # a-zA-Z0-9
        unique_name = ''.join(secrets.choice(characters) for _ in range(15))
        new_path = Path(self.mod_list_path + f'/{unique_name}')
        os.mkdir(new_path)
        if remark == None:
            self._insert_modlist_sqlite(0, unique_name)
        else:
            self._insert_modlist_sqlite(0, unique_name,remark)
        print(f"new file:{new_path}")
        return unique_name

    # 创建所有数据表
    def _sqlite_build_init_all(self):
        for table_item in self.table_list:
            # 遍历创建
            self._sqlite_build_init(tableName=table_item)

    def _sqlite_build_init_all_aio(self):

        # 异步执行任务创建数据库
        thread1 = threading.Thread(
            self._sqlite_build_init_select_modlist(tableName=self.table_list[0]))
        thread2 = threading.Thread(
            self._sqlite_build_init_select_useraccount(tableName=self.table_list[1]))
        thread1.start()
        thread2.start()

    # 创建mod图书馆列表
    def _sqlite_build_init_select_modlist(self, tableName):

        destnation_path = f"{self.sqlite_path}/{self.database_name}"
        try:
            # 连接数据库
            conn = sqlite3.connect(destnation_path)
            cursor = conn.cursor()
            # 执行 SQL 建表语句
            cursor.execute(f'''
                CREATE TABLE IF NOT EXISTS {tableName} (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_manger_id INTEGER,
                    mod_library_link TEXT NOT NULL,
                    information_mod_library TEXT
                )
            ''')
            conn.commit()
            conn.close()

            # 添加创建标记
            self.mod_sqlite_set = True

        except Exception as e:
            print(f"BUILD SQLITE ERROR{e}")

    # 创建用户表列表

    def _sqlite_build_init_select_useraccount(self, tableName):

        destnation_path = f"{self.sqlite_path}/{self.database_name}"

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

    def _show_all_table_modlist(self):
        try:
            conn = sqlite3.connect(self.sqlite_path + "/" + self.database_name)
            process = conn.cursor()

            process.execute(f"""
                SELECT* FROM {self.table_list[0]};
            """)
            rows = process.fetchall()
            mod_have = []
            for row in rows:
                mod_have.append(row)
            
            print(mod_have)

        except Exception as e:
            print(f"SELECT ERROR FROM{self.table_list[0]} ERROR:{e}")

    def _get_all_table_modlist(self):
        try:
            conn = sqlite3.connect(self.sqlite_path + "/" + self.database_name)
            process = conn.cursor()

            process.execute(f"""
                SELECT* FROM {self.table_list[0]};
            """)
            rows = process.fetchall()
            mod_have = []
            for row in rows:
                mod_have.append(row[2])
            return mod_have

        except Exception as e:
            print(f"SELECT ERROR FROM{self.table_list[0]} ERROR:{e}")

    # 删除modlist表当中的一条mod库
    def _delete_sqlite_table_modlist_one(self, modlink_name):
        
        try:
            # 删除数据库元素
            conn = sqlite3.connect(self.sqlite_path + "/" + self.database_name)
            process = conn.cursor()

            process.execute(f"""DELETE FROM {self.table_list[0]} WHERE mod_library_link = ?;"""
            ,(modlink_name,))

            conn.commit()
            conn.close()

            # 删除相应的实体库
            delete_mod_path = self.get_mod_path() + "/" + modlink_name
            if Path(delete_mod_path).exists():
                shutil.rmtree(delete_mod_path)
                print(f"delete file:{modlink_name}")
            else:
                print(f"{modlink_name} not exists")


        except Exception as e:
            print(f"ERROR DELETE MOD_LIST INFORMATION:{e}")

    # 删除modlist表当中的所有mod库
    def _delete_sqlite_table_modlist_all(self):
        # 获取所有内部mod库清单
        mod_list = self._get_all_table_modlist()

        for mod in mod_list:
            self._delete_sqlite_table_modlist_one(modlink_name=str(mod))



mod_sqlite_handler = ModHandleSqlite()

if __name__ == "__main__":

    # 初始化mod_manger （必要）
    mod_sqlite_handler.value_init_setpath()

    # 删除单独相应的mod库
    # mod_sqlite_handler._delete_sqlite_table_modlist_one(
    #     modlink_name="4OZ1nvQVtCBwxyu")

    # 注册生成相应的mod库 有备注
    remark = "暮色森林存档"
    print(f"modListID:{mod_sqlite_handler._generate_file(remark=remark)}")

    # 注册生成相应的mod库 无备注
    # print(f"modListID:{mod_sqlite_handler._generate_file()}")

    # 打印所有的mod库名单
    # mod_sqlite_handler._show_all_table_modlist()

    # 删除所有的mod库
    # mod_sqlite_handler._delete_sqlite_table_modlist_all()
