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



INSERT INTO furniture (furniture_id, title, price, description, image_url) VALUES
(1, '베이지 패브릭 1인 소파', 45000, '포근한 패브릭 소재와 베이지 컬러가 돋보이는 소파입니다.', 'sofa1.jpg'),
(2, '원목 미니 수납 선반', 22000, '우드 감성과 미니멀한 분위기를 살릴 수 있는 선반입니다.', 'shelf1.jpg'),
(3, '따뜻한 간접 무드등', 13000, '공간을 아늑하게 만들어주는 감성 조명입니다.', 'lamp1.jpg'),
(4, '내추럴 원목 테이블', 35000, '원목 소재로 따뜻한 분위기를 연출할 수 있습니다.', 'table1.jpg'),
(5, '화이트 미니 협탁', 18000, '깔끔하고 밝은 공간에 잘 어울리는 협탁입니다.', 'side1.jpg'),
(6, '브라운 패브릭 암체어', 40000, '편안한 착석감과 브라운 컬러가 특징입니다.', 'chair1.jpg'),
(7, '모던 스탠드 조명', 27000, '심플하고 세련된 디자인의 스탠드 조명입니다.', 'lamp2.jpg'),
(8, '빈티지 원목 서랍장', 55000, '빈티지 감성과 우드 소재가 조화를 이루는 서랍장입니다.', 'drawer1.jpg'),
(9, '파스텔 커튼', 15000, '화사한 분위기를 연출하는 커튼입니다.', 'curtain1.jpg'),
(10, '미니멀 책상', 32000, '깔끔한 디자인으로 어느 공간에나 잘 어울립니다.', 'desk1.jpg');


INSERT INTO furniture_tag (furniture_tag_id, furniture_id, tag_id) VALUES

-- 소파
(1, 1, 1),   -- 따뜻한
(2, 1, 2),   -- 포근한
(3, 1, 5),   -- 베이지
(4, 1, 26),  -- 소파

-- 선반
(5, 2, 4),   -- 우드
(6, 2, 8),   -- 미니멀한
(7, 2, 14),  -- 원목 감성

-- 무드등
(8, 3, 1),   -- 따뜻한
(9, 3, 12),  -- 차분한
(10, 3, 29), -- 조명

-- 테이블
(11, 4, 4),  -- 우드
(12, 4, 13), -- 내추럴
(13, 4, 27), -- 테이블

-- 협탁
(14, 5, 7),  -- 화이트
(15, 5, 9),  -- 깔끔한
(16, 5, 23), -- 심플한

-- 암체어
(17, 6, 6),  -- 브라운
(18, 6, 25), -- 편안한
(19, 6, 28), -- 의자

-- 스탠드 조명
(20, 7, 10), -- 모던한
(21, 7, 19), -- 세련된
(22, 7, 29), -- 조명

-- 서랍장
(23, 8, 20), -- 빈티지
(24, 8, 4),  -- 우드
(25, 8, 14), -- 원목 감성

-- 커튼
(26, 9, 22), -- 파스텔
(27, 9, 16), -- 화사한
(28, 9, 30), -- 커튼

-- 책상
(29, 10, 8), -- 미니멀한
(30, 10, 9), -- 깔끔한
(31, 10, 27); -- 테이블