# pip install requests # 比 python 内置的标准库 http.client 简单易用

import requests

response = requests.get("https://jsonplaceholder.typicode.com/todos/1") # GET 请求
data = response.json() # 解析 JSON 数据
print(data)
print(data["id"])

data = {"name": "Alice", "age": 25}
response = requests.post("https://httpbin.org/post", json=data) # POST
print(response.json())

