INSERT INTO `corporate_offer_staging` 
(`title`,`logo`,`offer_text`,`approval_status`,`created_by`,`updated_by`,company_id,file_id)
VALUES
('Travel','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgD514Sz3-HFz9n3C98n4KBlIwlEVjgXABzA&usqp=CAU',
'Enjoy 50% off on purchases above 1,500','Approved','SYSTEM','SYSTEM','bostongroup',(SELECT f_id FROM `file_master` WHERE `f_name` = 'corporate-offer'));

