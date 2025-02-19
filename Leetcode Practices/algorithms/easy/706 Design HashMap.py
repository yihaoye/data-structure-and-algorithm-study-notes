class MyHashMap:
    def __init__(self):
        self.nodes = [None] * 10000

    def get(self, key: int) -> int:
        index = self.get_index(key)
        prev = self.find_element(index, key)
        return -1 if prev.next is None else prev.next.val

    def put(self, key: int, value: int) -> None:
        index = self.get_index(key)
        prev = self.find_element(index, key)
        if prev.next is None:
            prev.next = ListNode(key, value)
        else:
            prev.next.val = value

    def remove(self, key: int) -> None:
        index = self.get_index(key)
        prev = self.find_element(index, key)
        if prev.next is not None:
            prev.next = prev.next.next

    def get_index(self, key: int) -> int:
        return hash(key) % len(self.nodes)

    def find_element(self, index: int, key: int) -> int:
        if self.nodes[index] is None:
            self.nodes[index] = ListNode(-1, -1)
        prev = self.nodes[index]
        while prev.next and prev.next.key != key:
            prev = prev.next
        return prev

class ListNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.next = None


# Your MyHashMap object will be instantiated and called as such:
# obj = MyHashMap()
# obj.put(key,value)
# param_2 = obj.get(key)
# obj.remove(key)