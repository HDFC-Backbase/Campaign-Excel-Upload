ALTER TABLE `corporate_offer_staging` DROP CONSTRAINT `corporate_offer_staging_file_master_fk`;
ALTER TABLE `corporate_offer_final` DROP CONSTRAINT `corporate_offer_staging_fk`;

ALTER TABLE `cmp_staging` DROP CONSTRAINT `CMP_STAGING_FILE_MASTER_FK`;

DROP TABLE IF EXISTS `file_master`;

DROP TABLE IF EXISTS `corporate_offer_staging`;

DROP TABLE IF EXISTS `corporate_offer_final`;
