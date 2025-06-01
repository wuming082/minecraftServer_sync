import subprocess
import sys

repo_url = sys.argv[1]

try:
    # 运行 git ls-remote 验证仓库链接
    result = subprocess.run(['git', 'ls-remote', repo_url],
                            stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
    if result.returncode == 0 and not result.stderr:
        print("true")
    else:
        print("false")
except Exception as e:
    print("404 ERR", str(e))

