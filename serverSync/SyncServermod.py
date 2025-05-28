import os
from pathlib import Path
import json
import asyncio
import requests
from library.mod_sqlite_ctrl import mod_sqlite_handler






if __name__ == "__main__":
    # 地址初始化
    mod_sqlite_handler.value_init_setpath(workPath="")
    print(f"modListID:{mod_sqlite_handler._generate_file()}")
