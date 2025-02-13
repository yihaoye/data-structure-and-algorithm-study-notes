from datetime import datetime

now = datetime.now()
print(now.strftime("%Y-%m-%d %H:%M:%S"))

# 计算时间差
future = now + timedelta(days=3)
print(future.strftime("%Y-%m-%d"))