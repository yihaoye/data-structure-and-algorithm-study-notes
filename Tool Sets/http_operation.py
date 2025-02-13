# pip install requests

import requests

response = requests.get("https://jsonplaceholder.typicode.com/todos/1") # GET 请求
print(response.json())  # 解析 JSON 数据

data = {"name": "Alice", "age": 25}
response = requests.post("https://httpbin.org/post", json=data) # POST
print(response.json())

