/*
实现餐厅等候名单数据结构。 它应该支持以下功能：

1. 一组顾客可以加入候补名单。
2. 候补名单中的一方可以随时退出候补名单。
3. 餐厅为顺序等待的第一组（其人数需严格等于给定空桌子大小）顾客提供服务。

自由设计实现方式，请最佳化 serve() 的时空复杂度
*/

import java.util.*;

class RestaurantWaitlist {
    private Map<Integer, Set<Integer>> waitlist; // <party_size, <party_id, ...>>

    public RestaurantWaitlist() {
        waitlist = new HashMap<>();
    }

    public void joinWaitlist(int id, int size) {
        waitlist.computeIfAbsent(size, k -> new LinkedHashSet<>()).add(id);
    }

    public void leaveWaitlist(int id, int size) {
        waitlist.get(size).remove(id);
    }

    public boolean serve(int size) {
        Set<Integer> parties =  waitlist.getOrDefault(size, new LinkedHashSet<>());
        if (parties.isEmpty()) return false;
        int partyId = parties.iterator().next();
        leaveWaitlist(partyId, size);
        return true;
    }
}
