use test_db;

set hive.exec.mode.local.auto=true;
create table if not exists tb_cuid_1d
(
    cuid         string comment '用户的唯一标识',
    os           string comment '平台',
    soft_version string comment '版本',
    event_day    string comment '日期',
    visit_time    int comment '用户访问时间戳',
    duration     decimal comment '用户访问时长',
    ext          array<string> comment '扩展字段'
);
insert overwrite table tb_cuid_1d values
                                      (1,'android',1,'2020-04-01',1234567,100,`array`('')),
                                      (1,'android',1,'2020-04-02',1234567,100,`array`('')),
                                      (1,'android',1,'2020-04-08',1234567,100,`array`('')),
                                      (2,'android',1,'2020-04-01',1234567,100,`array`('')),
                                      (3,'android',1,'2020-04-02',1234567,100,`array`(''));
select * from tb_cuid_1d;
--写出用户表 tb_cuid_1d的 20200401 的次日、次7日留存的具体SQL ：
--一条sql统计出以下指标 （4.1号uv，4.1号在4.2号的留存uv，4.1号在4.8号的留存uv）;
--uv就是不同的用户数

--方案1 ，性能快，步骤稍微复杂
select '2020-04-01的留存情况' as type,
       count(cuid) as uv_4_1,
       count(case when cnt_4_2>0 then 1 else null end) as uv_4_2,
       count(case when cnt_4_8>0 then 1 else null end) as uv_4_8
from
(select cuid,
       count(case when event_day='2020-04-01' then 1 else null end) as cnt_4_1,
       count(case when event_day='2020-04-02' then 1 else null end) as cnt_4_2,
       count(case when event_day='2020-04-08' then 1 else null end) as cnt_4_8
from tb_cuid_1d
where event_day in ('2020-04-01','2020-04-02','2020-04-08')
group by cuid
having cnt_4_1>0) as t;
--方案2 ，性能慢，步骤比较简单
select count(a.cuid) as uv_4_1,
       count(b.cuid) as uv_4_2,
       count(c.cuid) as uv_4_8,
       count(b.cuid)/count(a.cuid) as rate_4_2,
       count(c.cuid)/count(a.cuid) as rate_4_8
from (select distinct cuid from tb_cuid_1d where event_day='2020-04-01') a
left join (select distinct cuid from tb_cuid_1d where event_day='2020-04-02') b on a.cuid=b.cuid
left join (select distinct cuid from tb_cuid_1d where event_day='2020-04-08') c on a.cuid=c.cuid




