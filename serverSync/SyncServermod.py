import os
from pathlib import Path
import json
import asyncio
import requests
import sys
from aiohttp import web
from library import mod_sqlite_handler,init_app



if __name__ == "__main__":

    # 初始化数据库
    mod_sqlite_handler.value_init_setpath()

    # 设置数据服务器
    app = init_app()
    web.run_app(app, host="0.0.0.0", port=6730)
    

    
