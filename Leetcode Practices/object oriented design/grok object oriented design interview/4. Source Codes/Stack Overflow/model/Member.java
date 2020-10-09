public class Member {
    private Account account;
    private List<Badge> badges;
  
    public int getReputation();
    public String getEmail();
    public boolean createQuestion(Question question);
    public boolean createTag(Tag tag);
}
  
public class Admin extends Member {
    public boolean blockMember(Member member);
    public boolean unblockMember(Member member);
}
  
public class Moderator extends Member {
    public boolean closeQuestion(Question question);
    public boolean undeleteQuestion(Question question);
}
