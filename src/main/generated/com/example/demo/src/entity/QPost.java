package com.example.demo.src.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -542414809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> createdPost = createDate("createdPost", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath emotion = createString("emotion");

    public final ListPath<String, StringPath> friendList = this.<String, StringPath>createList("friendList", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath lyrics = createString("lyrics");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QMusic music;

    public final NumberPath<Integer> options = createNumber("options", Integer.class);

    public final StringPath place = createString("place");

    public final StringPath placeNickname = createString("placeNickname");

    public final NumberPath<Long> postIdx = createNumber("postIdx", Long.class);

    public final StringPath record = createString("record");

    public final StringPath season = createString("season");

    public final QUser user;

    public final ListPath<String, StringPath> weather = this.<String, StringPath>createList("weather", String.class, StringPath.class, PathInits.DIRECT2);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.music = inits.isInitialized("music") ? new QMusic(forProperty("music")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

