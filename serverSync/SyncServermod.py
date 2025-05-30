import os
from pathlib import Path
import json
import asyncio
import requests
import sys
from library import mod_sqlite_handler


if __name__ == "__main__":


    mod_sqlite_handler.value_init_setpath()
    have =  mod_sqlite_handler._get_all_table_modlist()
    print(have)
    # print(f"modListID:{mod_sqlite_handler._generate_file()}")
