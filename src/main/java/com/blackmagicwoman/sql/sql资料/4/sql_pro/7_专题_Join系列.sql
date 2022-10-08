use interview_db;
set hive.exec.mode.local.auto=true;
drop table if exists all_users;
create table all_users(
                          id int comment '用户id',
                          name string comment '用户姓名',
                          sex string comment '性别',
                          age int comment '年龄'
) comment '银行用户信息表';
insert overwrite table all_users values
                                     (1,'张三','男',20),
                                     (2,'李四','男',29),
                                     (3,'王五','男',21),
                                     (4,'赵六','女',28),
                                     (5,'田七','女',22);
select * from all_users;

drop table if exists black_list;
create table black_list(
                           user_id int comment '用户编号',
                           type string comment '风控类型'
)comment '银行黑名单信息表';
insert overwrite table black_list values
                                      (1,'诈骗'),
                                      (2,'逾期'),
                                      (3,'套现');
select * from black_list;

-- 观察inner join 的效果
select *
from all_users a
inner join black_list b
on a.id=b.user_id;

-- 使用left join对所有用户，如果也在黑名单中，则标记为YES，否则标记为NO。
set spark.sql.shuffle.partitions=4;
select a.*,
       case  when b.user_id is not null then 'YES'
             else 'NO'
       end as flag
from all_users a
left join black_list b
on a.id=b.user_id;
--对所有用户，如果也在黑名单中，则标记为YES，否则标记为NO。
--对上面的问题，使用right join再做一次。
--right join 特点是：
select a.*,
       if(b.user_id is not null,'YES','NO') as flag
from black_list b
right join all_users a
on b.user_id=a.id;

--使用left semi join对所有用户，如果也在黑名单中，则挑选出来。
-- left semi join结果只显示左表字段，不会显示右表字段
select *
from all_users a
left semi join black_list b on a.id = b.user_id;
--他跟left join的关系是:
select a.*
from all_users a
left join black_list b on a.id = b.user_id
where b.user_id is not null;
--也等价于
select a.*
from all_users a
join black_list b on a.id = b.user_id;
;


-- 使用left anti join对所有用户，如果不在黑名单中，则挑选出来。
select *
from all_users a
left anti join black_list b on a.id = b.user_id;
--他跟left join的关系是:
select a.*
from all_users a
left join black_list b on a.id = b.user_id
where b.user_id is null;


--用户的存款金额。
drop table if exists deposit;
create table deposit (
 user_id int comment '用户id',
 amount int comment '存款金额'
)comment '用户最新银行存款信息表';
insert overwrite table deposit values
                                   (1,2000),
                                   (2,2900),
                                   (3,2100);
select * from deposit;
--用户的负债金额。
drop table if exists debt;
create table debt (
  user_id int comment '用户id',
  amount int comment '负债金额'
)comment '用户最新银行负债信息表';
insert overwrite table debt values
                                (3,3400),
                                (4,2800),
                                (5,2200);
select * from debt;

--使用full join，展示用户的存款金额和负债金额。
select coalesce(a.user_id,b.user_id) as user_id_new,
       coalesce(a.amount,0) as deposit_amount,
       coalesce(b.amount,0) as debt_amount
from deposit a
full join debt b on a.user_id=b.user_id;

--coalesce返回第一个不为null的值
select coalesce(123,456) as x;
select coalesce(null,456) as x;
select nvl(null,0) as x;
select coalesce(null,null,456) as x;

-- 字节跳动
use interview_db;
set hive.exec.mode.local.auto=true;

create table if not exists dm_paid_buy
(
    `time`    bigint comment '#购买的时间戳',
    server_id string comment '#服务器ID',
    role_id   int comment '#角色ID',
    cost      int comment '#购买对应道具消耗的付费元宝数量',
    item_id   int comment '#购买对应道具的id',
    amount    int comment '#购买对应道具的数量',
    p_date    string comment '#登录日期, yyyy-MM-dd'
) comment '角色使用付费元宝购买商城道具时候记录一条';
insert overwrite table dm_paid_buy values
                                       (1234567,120,10098,2,3,4,'2021-01-01'),
                                       (1234567,120,10098,4,3,5,'2021-01-01'),
                                       (1234567,123,10098,3,3,2,'2021-01-02'),
                                       (1234567,123,10098,2,3,2,'2021-01-02');

-- 查看表结构
desc dm_paid_buy;

create table if not exists dm_free_buy
(
    `time`    bigint comment '#购买的时间戳',
    server_id string comment '#服务器ID',
    role_id   int comment '#角色ID',
    cost      int comment '#购买对应道具消耗的免费元宝数量',
    item_id   int comment '#购买对应道具的id',
    amount    int comment '#购买对应道具的数量',
    p_date    string comment '#登录日期, yyyy-MM-dd'
) comment '角色使用免费元宝购买商城道具时候记录一条';
insert overwrite table dm_free_buy values
(1234567,123,10098,8,3,4,'2021-01-01'),
(1234567,123,10098,5,3,5,'2021-01-01'),
(1234567,120,10098,6,3,4,'2021-01-01'),
(1234567,120,10098,9,3,5,'2021-01-01'),
(1234567,123,10098,18,3,2,'2021-01-02'),
(1234567,123,10098,7,3,2,'2021-01-02');

select * from dm_paid_buy;
select * from dm_free_buy;
select coalesce(a.p_date,b.p_date) as p_date,
       coalesce(a.server_id,b.server_id) as server_id,
       coalesce(a.role_id,b.role_id) as role_id,
       coalesce(a.cost,0) as a_cost,
       coalesce(b.cost,0) as b_cost,
       coalesce(a.cost,0)/coalesce(b.cost,0) as rate
from
    (select p_date,server_id,role_id,
           sum(cost) as cost
    from dm_paid_buy
    where p_date between '2021-01-01' and '2021-01-07'
    group by p_date,server_id,role_id) as a
full join
    (select p_date,server_id,role_id,
           sum(cost) as cost
    from dm_free_buy
    where p_date between '2021-01-01' and '2021-01-07'
    group by p_date,server_id,role_id) as b
on a.p_date=b.p_date and a.server_id=b.server_id and a.role_id=b.role_id
;
--使用with as 来重构上面的逻辑
with a as (
    select p_date,server_id,role_id,
           sum(cost) as cost
    from dm_paid_buy
    where p_date between '2021-01-01' and '2021-01-07'
    group by p_date,server_id,role_id
),
    b as(
        select p_date,server_id,role_id,
               sum(cost) as cost
        from dm_free_buy
        where p_date between '2021-01-01' and '2021-01-07'
        group by p_date,server_id,role_id
    )
select coalesce(a.p_date,b.p_date) as p_date,
       coalesce(a.server_id,b.server_id) as server_id,
       coalesce(a.role_id,b.role_id) as role_id,
       coalesce(a.cost,0) as a_cost,
       coalesce(b.cost,0) as b_cost,
       coalesce(a.cost,0)/coalesce(b.cost,0) as rate
from  a
full join b
on a.p_date=b.p_date and a.server_id=b.server_id and a.role_id=b.role_id
;
