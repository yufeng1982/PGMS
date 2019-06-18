update role set created_by = null,modified_by =null where code = 'CommonRole';
-- 维修订单号
CREATE SEQUENCE project.repairorder_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE project.repairorder_sequence
  OWNER TO postgres;
-- 小维修配置初始化  
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('s1', 'T', 0, 'S000001', '维修工程师审核', 'Small', 1);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('s2', 'T', 0, 'S000002', '区域经理/维修技师审核', 'Small', 2);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('s3', 'T', 0, 'S000003', '油站经理/维修技师响应', 'Small', 3);
---INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
---    VALUES ('s4', 'T', 0, 'S000004', '油站经理确认', 'Small', 4);
-- 普通加急维修配置初始化 
delete from project.repair_approve_setup where pat = 'General' or pat = 'SettleAccount';
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g1', 'T', 0, 'G000001', '维修工程师响应', 'General', 1);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g2', 'T', 0, 'G000002', '维修工程师审核', 'General', 2);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g3', 'T', 0, 'G000003', '区域经理/维修技师审核', 'General', 3);
    INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g4', 'T', 0, 'G0000041', '运营经理审核', 'General', 4);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g5', 'T', 0, 'G000004', '部门经理审核', 'General', 5);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g6', 'T', 0, 'G000005', 'HSE审核', 'General', 6);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g7', 'T', 0, 'G000006', '分管副总审核', 'General', 7);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g8', 'T', 0, 'G000007', '总经理审核', 'General', 8);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g9', 'T', 0, 'G000008', '财务审核', 'General', 9);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g10', 'T', 0, 'G000009', '维修工程师派单', 'General', 10);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g11', 'T', 0, 'G000010', '工程师/技师响应', 'General', 11);
---INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
---    VALUES ('g12', 'T', 0, 'G000011', '油站经理确认', 'General', 12);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g13', 'T', 0, 'G000012', '维修工程师确认', 'General', 13);
-- 维修结算配置初始化    
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g14', 'T', 0, 'G000013', '结算-部门经理审核', 'General', 14);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g15', 'T', 0, 'G000014', '结算-分管副总审核', 'General', 15);
INSERT INTO project.repair_approve_setup(id, active, version, code, name, pat, seq)
    VALUES ('g16', 'T', 0, 'G000015', '结算-总经理审核', 'General', 16);
    
----@date 2015-02-02 finished
update organization set code='ZH';   
ALTER TABLE project.asset DROP COLUMN vendor;
ALTER TABLE project.asset ADD COLUMN vendor character varying(40);

----@date 2015-03-09
insert into project.petrol_station (id, project, version,active,creation_date, modification_date, created_by, modified_by, corporation) 
   select id ,id,0, 'T', creation_date, modification_date, created_by, modified_by,corporation from project.project;
update project.contract set petrol_station = project;
update project.pay_item set petrol_station = project;
update project.repair_order set petrol_station = project;
update project.user_project set petrol_station = project;
update project.asset set petrol_station = project;
update role set function_node_ids = 'TK01,TK02,TK03,TK04,TK05,TK06,KF01,KF02,GC0,GC10,GC11,GC12,GC20,GC21,GC30,GC31,GC40,GC41,GC42,GC43,GC5,XZ10,XZ11,XZ12,ZC1,ZC2,CW1,VD1,VD2,RT01,RT02,HR01,SEC01,SEC02,SEC03,' where code ='admin_P1';

