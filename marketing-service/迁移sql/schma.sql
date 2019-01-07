ALTER TABLE `member_marketing_activity` ADD COLUMN `TENANT_ID` BIGINT (20) NOT NULL AFTER `ID`;

UPDATE member_marketing_activity ma set ma.TENANT_ID = 1;

ALTER TABLE `marketing_activity` ADD COLUMN `TENANT_ID` BIGINT (20) NOT NULL AFTER `ID`;

UPDATE marketing_activity ma set ma.TENANT_ID = 1;

ALTER TABLE `coupon_active_apply` DROP FOREIGN KEY `COUPON_ACTIVE_APPLY_ibfk_1`;
ALTER TABLE `coupon_active_apply` DROP FOREIGN KEY `COUPON_ACTIVE_APPLY_ibfk_2`;
ALTER TABLE `coupon_active_apply` ADD COLUMN `TENANT_ID` BIGINT (20) NOT NULL AFTER `ID`;

UPDATE coupon_active_apply ma set ma.TENANT_ID = 1;

UPDATE rule_base_system_web_service sws set sws.SERVICE_BEAN_NAME = 'taskDataWebServiceImpl' where sws.SERVICE_BEAN_NAME = 'ruleTaskDataWebService';