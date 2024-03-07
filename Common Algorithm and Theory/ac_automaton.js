// https://zhuanlan.zhihu.com/p/80325757
// by 司徒正美
class Trie {
    constructor() {
        this.root = new Node("root");
    }

    insert(word) {
        var cur = this.root;
        for (var i = 0; i < word.length; i++) {
            var c = word[i];
            var node = cur.children[c];
            if (!node) {
                node = cur.children[c] = new Node(word[i]);
            }
            cur = node;
        }
        cur.pattern = word; // 防止最后收集整个字符串用
        cur.endCount++; // 这个字符串重复添加的次数
    }
}

function createGoto(trie, patterns) {
    for (var i = 0; i < patterns.length; i++) {
        trie.insert(patterns[i]);
    }
}

function createFail(ac) {
    var root = ac.root;
    var queue = [root]; // root 所在层为第 0 层
    while (queue.length) {
        // 广度优先遍历
        var node = queue.shift();
        if (!node) continue;
        // 将其孩子逐个加入列队
        for (var i in node.children) {
            var child = node.children[i];
            if (node === root) {
                child.fail = root; // 第 1 层的节点的 fail 总是指向 root
            } else {
                var p = node.fail; // 第 2 层以下的节点, 其 fail 是在另一个分支上
                while (p) {
                    // 遍历它的孩子，看它们有没与当前孩子相同字符的节点
                    if (p.children[i]) {
                        child.fail = p.children[i];
                        break;
                    }
                    p = p.fail;
                }
                if (!p) {
                    child.fail = root;
                }
            }
            queue.push(child);
        }
    }
}

function match(ac, text) {
    var root = ac.root, p = root, ret = [], unique = {};
    for (var i = 0; i < text.length; i++) {
        var c = text[i];
        while (!p.children[c] && p != root) {
            p = p.fail; // 失配指针发挥作用
        }
        p = p.children[c];
        if (!p) {
            p = root; // 如果没有匹配的，从 root 开始重新匹配
        }
        var node = p;
        while (node != root) {
            // 收集出可以匹配的模式串
            if (node.endCount) {
                var pos = i - node.pattern.length + 1;
                console.log(`匹配模式串 ${node.pattern} 其起始位置在 ${pos}`)
                if (!unique[node.pattern]) {
                    unique[node.pattern] = 1;
                    ret.push(node.pattern);
                }
            }
            node = node.fail;
        }
    }
    return ret;
}

var ac = new Trie();
createGoto(ac, ["she", "shr", "say", "he", "her"]);
createFail(ac);
console.log(match(ac, "one day she say her has eaten many shrimps"));
