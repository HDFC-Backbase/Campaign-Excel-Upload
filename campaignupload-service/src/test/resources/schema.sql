CREATE TABLE IF NOT EXISTS `file_master` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(100) DEFAULT NULL,
  `f_type` varchar(100) DEFAULT NULL,
  `f_status` varchar(10) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `system_remarks` varchar(200) DEFAULT NULL,
  `user_remarks` varchar(250) DEFAULT NULL,
  `f_display_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
);
CREATE TABLE IF NOT EXISTS `cmp_staging` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_id` varchar(200) DEFAULT NULL,
  `c_name` varchar(400) DEFAULT NULL,
  `c_imageurl` varchar(400) DEFAULT NULL,
  `c_url` varchar(400) DEFAULT NULL,
  `file_id` int(11) NOT NULL,
  `c_status` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT 'SYSTEM',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `cmp_staging_file_master_fk` FOREIGN KEY (`file_id`) REFERENCES `file_master` (`f_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `corporate_offer_staging` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `logo` varchar(400) DEFAULT NULL,
  `offer_text` varchar(400) DEFAULT NULL,
  `file_id` int(11) NOT NULL,
    `company_id` varchar(200) DEFAULT NULL,
`approval_status` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT 'SYSTEM',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `corporate_offer_staging_file_master_fk` FOREIGN KEY (`file_id`) REFERENCES `file_master` (`f_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `corporate_offer_final` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `logo` varchar(400) DEFAULT NULL,
  `offer_text` varchar(400) DEFAULT NULL,
  `corp_id` int(11) NOT NULL,
  `company_id` varchar(200) DEFAULT NULL,
  `approval_status` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT 'SYSTEM',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `corporate_offer_staging_fk` FOREIGN KEY (`corp_id`) REFERENCES `corporate_offer_staging` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `partner_offer_staging` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `logo` varchar(400) DEFAULT NULL,
  `offer_text` varchar(400) DEFAULT NULL,
  `file_id` int(11) NOT NULL,
  `approval_status` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT 'SYSTEM',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `partner_offer_staging_file_master_fk` FOREIGN KEY (`file_id`) REFERENCES `file_master` (`f_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `partner_offer_final` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `logo` varchar(400) DEFAULT NULL,
  `offer_text` varchar(400) DEFAULT NULL,
  `partoff_id` int(11) NOT NULL,
  `approval_status` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT 'SYSTEM',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(100) DEFAULT 'SYSTEM',
  `updated_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `partner_offer_staging_fk` FOREIGN KEY (`partoff_id`) REFERENCES `partner_offer_staging` (`id`) ON DELETE CASCADE
);
