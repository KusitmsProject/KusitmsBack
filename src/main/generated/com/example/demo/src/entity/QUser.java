package com.example.demo.src.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -542262446L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath gender = createString("gender");

    public final StringPath introduction = createString("introduction");

    public final StringPath kakaoEmail = createString("kakaoEmail");

    public final NumberPath<Long> kakaoId = createNumber("kakaoId", Long.class);

    public final StringPath kakaoNickname = createString("kakaoNickname");

    public final StringPath password = createString("password");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final StringPath status = createString("status");

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

