package bean;

import java.sql.Timestamp;

public class PostBean {
    
    private int postId;
    private int authorId;
    private String content;
    private Timestamp postTime;
    
    public int getPostId() {
        return postId;
    }
    public int getAuthorId() {
        return authorId;
    }
    public String getContent() {
        return content;
    }
    public Timestamp getPostTime() {
        return postTime;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
    
    

}
