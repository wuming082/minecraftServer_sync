#!/bin/sh

# 目标git链接
git_url=$1
PASSWORD=$2
port=$3 #3042
containertime=$4

echo "git_url:$git_url"
echo "the password is:$PASSWORD"
echo "destination port:$port"

# 默认3041
docker run -d -p $port:8080 -it --name codeserve-$containertime -e git_url_user="$git_url" -e PASSWORD=$PASSWORD codeserver-wuming:0.9