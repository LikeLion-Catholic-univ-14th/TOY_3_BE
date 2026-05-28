INSERT INTO tag_category(category_id, name)
VALUES (1, '무드');

INSERT INTO tag_category(category_id, name)
VALUES (2, '소재');

INSERT INTO tag_category(category_id, name)
VALUES (3, '가구유형');


-- =========================================
-- EmotionTag 데이터
-- (가구 유형 제외 / 색감 + 분위기 중심)
-- =========================================

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (1, '따뜻한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (2, '포근한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (3, '아늑한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (8, '미니멀한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (9, '깔끔한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (10, '모던한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (11, '감성적인', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (12, '차분한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (15, '밝은', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (16, '화사한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (19, '세련된', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (20, '빈티지', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (21, '러블리한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (23, '심플한', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (24, '고급스러운', 1);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (25, '편안한', 1);

--소재/컬러 태그
INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (4, '우드', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (5, '베이지', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (6, '브라운', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (7, '화이트', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (13, '내추럴', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (14, '원목 감성', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (17, '모노톤', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (18, '블랙', 2);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (22, '파스텔', 2);

--가구 유형 태그
INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (26, '소파', 3);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (27, '테이블', 3);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (28, '의자', 3);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (29, '조명', 3);

INSERT INTO emotion_tag(tag_id, name, category_id)
VALUES (30, '커튼', 3);



-- =========================================
-- 추천 관계 데이터
-- base_tag_id -> recommended_tag_id
-- =========================================

-- 따뜻한 -> 포근한, 우드, 베이지
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (1, 2);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (1, 4);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (1, 5);

-- 포근한 -> 아늑한, 편안한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (2, 3);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (2, 25);

-- 아늑한 -> 차분한, 감성적인
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (3, 12);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (3, 11);

-- 우드 -> 브라운, 원목 감성, 내추럴
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (4, 6);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (4, 14);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (4, 13);

-- 베이지 -> 따뜻한, 내추럴
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (5, 1);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (5, 13);

-- 화이트 -> 미니멀한, 깔끔한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (7, 8);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (7, 9);

-- 미니멀한 -> 심플한, 모던한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (8, 23);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (8, 10);

-- 깔끔한 -> 화이트, 심플한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (9, 7);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (9, 23);

-- 모던한 -> 세련된, 모노톤
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (10, 19);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (10, 17);

-- 감성적인 -> 따뜻한, 빈티지
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (11, 1);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (11, 20);

-- 차분한 -> 모노톤, 편안한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (12, 17);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (12, 25);

-- 내추럴 -> 우드, 베이지
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (13, 4);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (13, 5);

-- 원목 감성 -> 우드, 따뜻한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (14, 4);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (14, 1);

-- 밝은 -> 화사한, 화이트
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (15, 16);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (15, 7);

-- 화사한 -> 밝은, 파스텔
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (16, 15);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (16, 22);

-- 모노톤 -> 블랙, 세련된
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (17, 18);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (17, 19);

-- 블랙 -> 모던한, 모노톤
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (18, 10);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (18, 17);

-- 세련된 -> 모던한, 고급스러운
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (19, 10);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (19, 24);

-- 빈티지 -> 감성적인, 브라운
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (20, 11);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (20, 6);

-- 러블리한 -> 파스텔, 화사한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (21, 22);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (21, 16);

-- 파스텔 -> 러블리한, 밝은
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (22, 21);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (22, 15);

-- 심플한 -> 미니멀한, 깔끔한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (23, 8);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (23, 9);

-- 고급스러운 -> 세련된, 모던한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (24, 19);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (24, 10);

-- 편안한 -> 포근한, 차분한
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (25, 2);
INSERT INTO recommendation_keyword(base_tag_id, recommended_tag_id)
VALUES (25, 12);