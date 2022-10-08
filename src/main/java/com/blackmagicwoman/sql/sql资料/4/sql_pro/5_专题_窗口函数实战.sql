use test_db;

set hive.exec.mode.local.auto=true;
create table emp(name string , month string, amt int);
insert overwrite table emp values ('张三', '01', 100),
                                  ('李四', '02', 120),
                                  ('王五', '03', 150),
                                  ('赵六', '04', 500),
                                  ('张三', '05', 400),
                                  ('李四', '06', 350),
                                  ('王五', '07', 180),
                                  ('赵六', '08', 400);
select * from emp;
with t1 as (
    select name,
           sum(amt) as sum_amt
        from emp
        group by name
),
    t2 as (
     select *,
            row_number() over (partition by null order by sum_amt desc) as rn, --排名
            sum(sum_amt) over(partition by null) as sum_all--全体总分
      from t1
    )
select *,
       -- || 两根竖线等价于 concat函数
       round(sum_amt*100/sum_all,2)||'%' as rate-- 占比
from t2;

--跨越速运
drop table if exists emp;
create table emp(empno string ,ename string,hiredate string,sal int ,deptno string);
insert overwrite table emp values
                               ('7521', 'WARD', '1981-2-22', 1250, 30),
                               ('7566', 'JONES', '1981-4-2', 2975, 20),
                               ('7876', 'ADAMS', '1987-7-13', 1100, 20),
                               ('7369', 'SMITH', '1980-12-17', 800, 20),
                               ('7934', 'MILLER', '1982-1-23', 1300, 10),
                               ('7844', 'TURNER', '1981-9-8', 1500, 30),
                               ('7782', 'CLARK', '1981-6-9', 2450, 10),
                               ('7839', 'KING', '1981-11-17', 5000, 10),
                               ('7902', 'FORD', '1981-12-3', 3000, 20),
                               ('7499', 'ALLEN', '1981-2-20', 1600, 30),
                               ('7654', 'MARTIN', '1981-9-28', 1250, 30),
                               ('7900', 'JAMES', '1981-12-3', 950, 30),
                               ('7788', 'SCOTT', '1987-7-13', 3000, 20),
                               ('7698', 'BLAKE', '1981-5-1', 2850, 30);


select *,
       sum(cnt) over(partition by null order by year1) as `累计人数`
from
(select year(hiredate) as year1,
       count(empno) as cnt
from emp
group by year(hiredate)) as t
;

-- 广州银行
select * from goods;

with t1 as (
    select *,
           row_number() over (partition by goods_type order by price) as rn,--每个在组内的排名
           count(1) over(partition by goods_type) as cnt --每个组内有多少个
    from goods
),
    t2 as (
    select *,
           rn/cnt as rate --比例
     from t1
    )
select goods_type,
       goods_name,
       price,
       case when rate<0.3 then '低档'
            when rate>=0.3 and rate<0.85 then '中档'
            when rate>=0.85 then '高档'
       end    as type
 from t2
;

-- 上面的代码可以简化

select goods_type,
       goods_name,
       price,
       case when rate<0.3 then '低档'
            when rate>=0.3 and rate<0.85 then '中档'
            when rate>=0.85 then '高档'
       end    as type
from (
         select *,
                row_number() over (partition by goods_type order by price)
                    /count(1) over(partition by goods_type) as rate --比例
         from goods
     ) as t1
;















