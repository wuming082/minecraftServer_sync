from .mod_sqlite_ctrl import mod_sqlite_handler
import os
import asyncio
from aiohttp import web

def init_app():
    app = web.Application()
    setup_routes(app)
    return app

def setup_routes(app):
    app.router.add_get("/modlist", request_mod_list_get)

# 获取mod列表
async def request_mod_list_get(request):
    try:
        loop = asyncio.get_event_loop()
        mod_have = await loop.run_in_executor(None, mod_sqlite_handler._get_all_table_modlist)
        return web.json_response({
            "status": "success",
            "mod_list": mod_have
            })
    except Exception as e:
        return web.json_response({
            "status": "error",
            "message": str(e)
        })
    
if __name__ == "__main__":
    mod_sqlite_handler.value_init_setpath()
    mod_sqlite_handler._get_all_table_modlist()