import asyncio

async def say_hello():
    await asyncio.sleep(1)
    print("Hello, Async!")

asyncio.run(say_hello())
