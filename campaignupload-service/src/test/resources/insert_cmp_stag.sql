INSERT INTO `cmp_staging` 
(`c_id`,`c_name`,`c_imageurl`,`c_url`,`file_id`,`c_status`,`created_by`,`updated_by`)
VALUES
('bostongroup','the boston consulting group',null,'www.hdfcbank.com/bostongroup',(SELECT f_id FROM `file_master` WHERE `f_name` = 'companydata'),'Approved','SYSTEM','SYSTEM');