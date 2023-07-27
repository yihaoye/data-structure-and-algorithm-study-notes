import java.util.*;
import java.util.concurrent.*;

class Solution {
    public static void main(String[] args) {
        // ...
    }
}

class DesignServiceImpl implements DesignService {
    private AtomicInteger designId; // UUID is another solution, AtomicInteger
    private Map<String, Set<String>> userToDesignIds; // user - content: 1 to many, access
    private Map<String, Design> designs; // id to design 1 to 1

    public DesignServiceImpl() {
        designId = 0;
        userToDesignIds = new ConcurrentHashMap<>();
        designs = new ConcurrentHashMap<>();
    }

    public String createDesign(AuthContext ctx, String designContent) { // O(1)
        String designIdStr = Integer.toString(designId.incrementAndGet());
        userToDesignIds.computeIfAbsent(ctx.userId, v -> new CopyOnWriteArraySet<>()).add(designIdStr);
        designs.put(designIdStr, new Design(designIdStr, ctx.userId, designContent));

        return designIdStr;
    }

    public String getDesign(AuthContext ctx, String designId) { // O(1)
        if (!userToDesignIds.containsKey(ctx.userId)) {
            throw new RuntimeException("User not exist");
        }
        if (!userToDesignIds.get(ctx.userId).contains(designId)) { //  || !designs.containsKey(designId)
            throw new RuntimeException("DesignId not accessable for user");
        }
        return designs.get(designId).content;
    }

    public List<String> findDesigns(AuthContext ctx) {
        List<String> res = new ArrayList<>();
        if (!userToDesignIds.containsKey(ctx.userId)) {
            System.out.println("User not exist");
            return res;
        }
        res.addAll(userToDesignIds.get(ctx.userId));
        return res;
    }

    public void shareDesign(AuthContext ctx, String designId, String targetUserId) {
        if (!userToDesignIds.containsKey(ctx.userId)) {
            throw new RuntimeException("User not exist");
        }
        if (!designs.containsKey(designId) || !designs.get(designId).ownerId.equals(ctx.userId)) {
            throw new RuntimeException("DesignId not owned for user");
        }
        if (ctx.userId.equals(targetUserId)) {
            throw new RuntimeException("Target user is source user");
        }
        userToDesignIds.computeIfAbsent(targetUserId, v -> new CopyOnWriteArraySet<>()).add(designId);
    }
}

/** 标识服务方法的调用者 */
class AuthContext {
    public final String userId;

    public AuthContext(String userId) {
        this.userId = userId;
    }
}

interface DesignService {
    /** 创建一个 design 并返回它的 design id. */
    String createDesign(AuthContext ctx, String designContent);

    /** 如果用户有访问 designId 的权限，返回该 design 的内容 */
    String getDesign(AuthContext ctx, String designId);

    /** 返回给定的 context 可访问的 designId 的列表 */
    List<String> findDesigns(AuthContext ctx);

    /** 把访问权限分享给一个给定的用户 */
    void shareDesign(AuthContext ctx, String designId, String targetUserId);
}

class Design {
    public String designId;
    public String ownerId;
    public String content;

    Design(String designId, String ownerId, String content) {
        this.designId = designId;
        this.ownerId = ownerId;
        this.content = content;
    }
}
