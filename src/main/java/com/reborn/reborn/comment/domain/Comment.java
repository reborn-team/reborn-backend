package com.reborn.reborn.comment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> child = new ArrayList<>();

    @Builder
    public Comment(Long id, Member member, Article article, String content) {
        this.id = id;
        this.member = member;
        this.article = article;
        this.content = content;
    }

    public void modifyComment(String content){
        this.content = content;
    }

}
