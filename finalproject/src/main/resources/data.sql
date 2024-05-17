--더미데이터로 관리자, 안전시설 구분은 꼭 미리 박아야 함
--학과서버는 Admin_roles 라서 아래꺼랑 다름
--INSERT INTO admin (adminId, adminName, adminPw) VALUES ('admin1', '관리자', '1234');
--INSERT INTO admin_roles (Admin_adminNum, roles) VALUES ('1', 'ADMIN');

--prediction도 0.9할때는 미리 박아야함

--안전시설 구분은 미리 더미데이터 넣어서 박아야 함
--INSERT INTO safe_thing_type (safeTypeNum, safeName) VALUES ('1','CCTV');
--INSERT INTO safe_thing_type (safeTypeNum, safeName) VALUES ('2','경찰서');
--INSERT INTO safe_thing_type (safeTypeNum, safeName) VALUES ('3','비상벨');

--연습해보는 유동인구 더미데이터
--INSERT INTO data (dataNum, dataDate, dataDong, dataGu, dataPeople, dataArea) VALUES ('1','2024-04-29_23:44:00','Mangwon-dong1','Mapo-gu','26','main_street')
--INSERT INTO data (dataNum, dataDate, dataDong, dataGu, dataPeople, dataArea) VALUES ('2','2024-04-29_23:44:00','Daechi4-dong','Gangnam-gu','77','main_street')
--INSERT INTO data (dataNum, dataDate, dataDong, dataGu, dataPeople, dataArea) VALUES ('3','2024-04-29_23:44:00','Yeoksam1-dong','Gangnam-gu','70','main_street')
--INSERT INTO data (dataNum, dataDate, dataDong, dataGu, dataPeople, dataArea) VALUES ('4','2024-04-29_23:44:00','Yeoksam1-dong','Gangnam-gu','70','main_street')

--공지사항 더미데이터 등록
--INSERT INTO notice (postNum, postContent, postDate, postName, adminNum) VALUES ('1', '첫번쨰 공지사항 내용입니다.','2024.05.01','첫번쨰 공지','1');
--INSERT INTO notice (postNum, postContent, postDate, postName, adminNum) VALUES ('2', '두번째 공지사항 내용입니다.','2024.05.02','두번쨰 공지','1');

--학과 서버에서는 테이블 이름이 Admin roles
--INSERT INTO  Admin_roles (Admin_adminNum, roles) VALUES ('1', 'ADMIN');
--INSERT INTO report (reportNum, reportTime, details, placed, userNum) VALUES (2, '2023.01.23', '길찾기 오류나요', '가천대학교', 2);
--INSERT INTO report (reportNum, reportTime, details, placed, userNum) VALUES (2, '2023.01.23', '길찾기 오류나요', '가천대학교', 2);
--INSERT INTO report (reportNum, reportTime, details, placed, userNum) VALUES (2, '2024.11.12', '유동인구 안나와요', '강남구', 1);
--INSERT INTO report (reportNum, reportTime, details, placed, userNum) VALUES (3, '2024.11.12', '길찾기 오류나요', '도봉구', 6);

--모델에 더미데이터 등록
INSERT INTO ai_model(modelNum, batchSize, epoch, modelName, isActive) VALUES (2, 15, 25, '2024-05-13', false);
