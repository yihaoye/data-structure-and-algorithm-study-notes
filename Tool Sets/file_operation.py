# 读取文件
with open("test.txt", "r") as file:
    content = file.read()
    print(content)

# 写入文件
with open("test.txt", "w") as file:
    file.write("Hello, Python!")

# 逐行读取
with open("test.txt", "r") as file:
    for line in file:
        print(line.strip())
