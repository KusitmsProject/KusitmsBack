package com.example.demo.src.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMusic is a Querydsl query type for Music
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMusic extends EntityPathBase<Music> {

    private static final long serialVersionUID = 362418046L;

    public static final QMusic music = new QMusic("music");

    public final StringPath artist = createString("artist");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Long> musicIdx = createNumber("musicIdx", Long.class);

    public final ListPath<Post, QPost> posts = this.<Post, QPost>createList("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final StringPath track = createString("track");

    public final StringPath trackIdx = createString("trackIdx");

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public final StringPath videoIdx = createString("videoIdx");

    public QMusic(String variable) {
        super(Music.class, forVariable(variable));
    }

    public QMusic(Path<? extends Music> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMusic(PathMetadata metadata) {
        super(Music.class, metadata);
    }

}

