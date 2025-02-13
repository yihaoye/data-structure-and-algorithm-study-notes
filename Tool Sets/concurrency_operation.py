import concurrent.futures

def task(n):
    return n * n

with concurrent.futures.ThreadPoolExecutor() as executor:
    results = executor.map(task, [1, 2, 3, 4, 5])
    print(list(results))  # [1, 4, 9, 16, 25]