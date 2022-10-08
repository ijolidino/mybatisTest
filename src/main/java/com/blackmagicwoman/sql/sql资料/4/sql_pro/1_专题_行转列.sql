show databases ;
show tables ;

use test_sql;
set hive.exec.mode.local.auto=true;
create table table2(year int,month int ,amount double) ;
insert overwrite table table2 values
                                  (1991,1,1.1),
                                  (1991,2,1.2),
                                  (1991,3,1.3),
                                  (1991,4,1.4),
                                  (1992,1,2.1),
                                  (1992,2,2.2),
                                  (1992,3,2.3),
                                  (1992,4,2.4);
select * from table2;
-- 扩展，当case when里面的分支只有2个，并且是互斥，则等价于if语句
-- 也就是 【case when month=1 then amount else 0 end】 等价于【if(month=1,amount,0)】
--行转列
--常规做法是，group by+sum(if())
--原始写法
select year,
       sum(if(month=1,amount,0)) as m1,
       sum(if(month=2,amount,0)) as m2,
       sum(if(month=3,amount,0)) as m3,
       sum(if(month=4,amount,0)) as m4
from table2
group by year;
-- 上面的语句其实是由2步合为1步，而来的。
select year,
       sum(a1) as m1,
       sum(a2) as m2,
       sum(a3) as m3,
       sum(a4) as m4
from
    (select *,
            case when month=1 then amount else 0 end as a1,
            case when month=2 then amount else 0 end as a2,
            case when month=3 then amount else 0 end as a3,
            case when month=4 then amount else 0 end as a4
     from table2) as t
group by year
;
-- 把2步合为一步
select year,
       sum(case when month=1 then amount else 0 end) as m1,
       sum(case when month=2 then amount else 0 end) as m2,
       sum(case when month=3 then amount else 0 end) as m3,
       sum(case when month=4 then amount else 0 end) as m4
from table2 as t
group by year
;
-- 腾讯游戏
--建表
create table table1(DDate string, shengfu string) ;
insert overwrite table table1 values ('2015-05-09', "胜"),
                                     ('2015-05-09', "胜"),
                                     ('2015-05-09', "负"),
                                     ('2015-05-09', "负"),
                                     ('2015-05-10', "胜"),
                                     ('2015-05-10', "负"),
                                     ('2015-05-10', "负");
select * from table1;

select DDate,
       count(case when shengfu='胜' then 1 else null end) as `胜`

from table1
group by DDate;
-- 拆成2步来写
select DDate,
       sum(x) as `胜`,
       sum(y) as `负`
from
    (select *,
            case when shengfu='胜' then 1 else null end as x,
            case when shengfu='负' then 1 else null end as y
     from table1) as t
group by DDate;
--上面2步合为1步：
select DDate,
       sum(case when shengfu='胜' then 1 else null end) as `胜`,
       sum(case when shengfu='负' then 1 else null end) as `负`
from  table1 as t
group by DDate;


-- 腾讯QQ
create table tableA(qq string, game string);
insert overwrite table tableA values
                                  (10000, 'a'),
                                  (10000, 'b'),
                                  (10000, 'c'),
                                  (20000, 'c'),
                                  (20000, 'd');

create table tableB(qq string, game string) ;
insert overwrite table tableB values
                                  (10000, 'a_b_c'),
                                  (20000, 'c_d');

select * from tableA;
select * from tableB;

-- 行转列
select qq,
       concat_ws( '_', collect_list(game)) arr
from tableA group by qq;
-- 列转行 需要借助【侧视图+explode函数】
select qq,
       game2
from tableb lateral view explode(split(game,'_')) v1 as game2;