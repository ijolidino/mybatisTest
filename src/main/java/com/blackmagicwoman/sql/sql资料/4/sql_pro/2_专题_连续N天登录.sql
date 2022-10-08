show databases ;
create database test_db;
use test_db;
show tables ;


set hive.exec.mode.local.auto=true;
/*--OPPO*/
create table game(name string,`date` string);
insert overwrite table game  values
                       ('张三','2021-01-01'),
                       ('张三','2021-01-02'),
                       ('张三','2021-01-03'),
                       ('张三','2021-01-02'),

                       ('张三','2021-01-07'),
                       ('张三','2021-01-08'),
                       ('张三','2021-01-09'),

                       ('李四','2021-01-01'),
                       ('李四','2021-01-02'),
                       ('王五','2021-01-03'),
                       ('王五','2021-01-02'),
                       ('王五','2021-01-02');

select * from game;
--方案1
with t1 as (
    select distinct name,`date` from game
),
     t2 as (
         select *,
                row_number() over (partition by name order by `date`) as rn
         from t1
     ),
     t3 as (
         select *,
                date_sub(`date`,rn) as temp
         from t2
     ),
     t4 as (
         select name,temp ,
                count(1) as cnt
         from t3
         group by name,temp
         having count(1)>=3
     )
select distinct name from t4;

--方案2
with t1 as (
    select distinct name,`date` from game
),
    t2 as (
        select *,
               date_add(`date`,2) as date2,
               lead(`date`,2) over(partition by name order by `date`) as date3
          from t1
    )
select distinct name from t2 where date2=date3;











