import os
from pathlib import Path
import json
import asyncio
import requests
import sys
from library import mod_sqlite_handler,_start_flask

from aiohttp import web
from flask import Flask, request, jsonify
from flask_cors import CORS

token = "HELLO WORLD"

if __name__ == "__main__":

    # 初始化数据库
    mod_sqlite_handler.value_init_setpath()
    mod_sqlite_handler.set_verify_key(token)
    _start_flask()


    
    

    
