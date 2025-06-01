from datetime import datetime

#获取当前日期
current_time = datetime.now()

def createtimes(timenow):
    return str(timenow.year) + str(timenow.month) + str(timenow.day) + str(timenow.hour) + str(timenow.minute) + str(timenow.second)

print(createtimes(current_time))