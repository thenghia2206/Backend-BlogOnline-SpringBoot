package com.programming.techie.springngblog.dto;

public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;

    private String description;

    private String thumnail;

    private Long category_id;
    //  ----------------------------------------------------------------
    private Long numLike;

    public Long getNumLike() {        return numLike;    }

    public void setNumLike(Long numLike) {        this.numLike = numLike;    }
    private Long numComment;

    public Long getNumComment() {
        return numComment;
    }
    public void setNumComment(Long numComment) {
        this.numComment = numComment;
    }
    //  --------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
