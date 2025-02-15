import copy

s1 = "hello"
s2 = s1[:]  # 复制字符串，和直接赋值一样
print(s1 is s2)  # True （指向同一个对象）


t1 = (1, [2, 3])
t2 = copy.deepcopy(t1)  # 深拷贝
t2[1].append(4)
print(t1)  # (1, [2, 3])  （原数据没变）
print(t2)  # (1, [2, 3, 4])


lst1 = [1, 2, [3, 4]]
lst2 = lst1[:]  # 浅拷贝（和 lst1.copy() 等价）

lst2[0] = 100
lst2[2].append(5)  # 修改嵌套列表

print(lst1)  # [1, 2, [3, 4, 5]]  （嵌套列表被修改！）
print(lst2)  # [100, 2, [3, 4, 5]]


lst1 = [1, 2, [3, 4]]
lst2 = copy.deepcopy(lst1)  # 深拷贝

lst2[2].append(5)
print(lst1)  # [1, 2, [3, 4]]  （原数据不变）
print(lst2)  # [1, 2, [3, 4, 5]]


d1 = {"a": 1, "b": [2, 3]}
d2 = d1.copy()  # 或者 d2 = dict(d1)

d2["b"].append(4)  # 修改嵌套列表
print(d1)  # {'a': 1, 'b': [2, 3, 4]}  （原字典的嵌套列表被修改）


d1 = {"a": 1, "b": [2, 3]}
d2 = copy.deepcopy(d1)

d2["b"].append(4)
print(d1)  # {'a': 1, 'b': [2, 3]}  （原数据不变）
print(d2)  # {'a': 1, 'b': [2, 3, 4]}


s1 = {1, 2, 3}
s2 = s1.copy()

s2.add(4)
print(s1)  # {1, 2, 3}  （不受影响）
print(s2)  # {1, 2, 3, 4}


# 对象直接赋值只是引用，所以要拷贝
p1 = Person("Alice", 30, ["reading", "running"])
p2 = copy.deepcopy(p1)  # 深拷贝

p2.hobbies.append("swimming")  # 仅修改 p2 的 hobbies

print(p1.hobbies)  # ['reading', 'running']  ✅ 原对象不受影响
print(p2.hobbies)  # ['reading', 'running', 'swimming']
