use test_db;

set hive.exec.mode.local.auto=true;

create table score(cid int ,sname string,score int);
insert overwrite table score values
(1,'张三',60),
(1,'李四',70),
(1,'王五',80),
(1,'赵六',90),
(2,'安安',80),
(2,'平平',90);
select * from score;


       -- 第一类、 聚合类的窗口函数
       -- sum() over()
       -- count/avg/max/min
       --让每个学生都知道，班级总分是多少
       --sum(score) over (partition by cid ) as `班级总分`,

       --计算同一个班级内，每个同学和比他分数低的总分是多少
       --sum(score) over (partition by cid order by score )  as `累加分数1`,
       --上句等价于
       --sum(score) over (partition by cid order by score rows between unbounded preceding and current row ) as `累加分数2`,
       --思考：如何计算每个同学，在班级内，包括他自己，和比他低一名的，和比他高1名的，这3个分数求和
       --sum(score) over(partition by cid order by score rows between 1 preceding and 1 following) as `累加分数3`


       -- 第二类、 排序类的窗口函数
       --同一个班内，按分数排序打上序号
       row_number() over (partition by cid order by score)  as `分数序号排名`,
       --考虑并列
       rank()       over (partition by cid order by score)  as `分数序号排名2`,
       dense_rank() over (partition by cid order by score)  as `分数序号排名3`



select *,
       -- 第三类、 偏移类的，跨行的
       -- lag / lead
       --同一班内，考得比自己低1名的分数是多少
       lag(score, 1) over (partition by cid order by score)     as `低一名的分数`,
       --同一班内，考得比自己低1名的分数是多少,如果找不到，则显示为0
       lag(score, 1, 0) over (partition by cid order by score)  as `低一名的分数2`,
       --同一班内，考得比自己低2名的分数是多少
       lag(score, 2) over (partition by cid order by score)     as `低2名的分数`,
       --同一班内，考得比自己高1名的分数是多少
       lead(score, 1) over (partition by cid order by score)    as `高一名的分数`
from score;




