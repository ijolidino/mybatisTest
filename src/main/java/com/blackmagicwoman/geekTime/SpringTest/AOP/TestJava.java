package com.blackmagicwoman.geekTime.SpringTest.AOP;

import com.blackmagicwoman.mybatistest.entity.Dept;
import com.blackmagicwoman.mybatistest.entity.Emp;
import com.sun.xml.internal.ws.util.CompletedFuture;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: mybatisTest
 * @description:
 * @author: heise
 * @create: 2022-05-24 22:03
 **/
public class TestJava {

    @Test
    public void test(){
        System.out.println(Runtime.getRuntime().availableProcessors());
        String s1="`amt$` varchar(32) DEFAULT NULL COMMENT '金额$',";
        char[] charArray = s1.toCharArray();
        for (int i=0;i<20;i++){
            StringBuilder s= new StringBuilder();
            for (char c : charArray) {
                if (c=='$'){
                    s.append(i);
                }else {
                    s.append(c);
                }
            }
            System.out.println(s);
        }
        List<String> strings = new ArrayList<>();
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(32);
        map.put("a","a");
        map.put("b","b");
        String a="10";
        strings.add(a);
        List<List<String>> arrayLists = new ArrayList<>();
        arrayLists.add(strings);
        Integer b=1;
        Integer c=2;
        Integer ccc=3;
        Long d=3L;
        Integer bb=127;
        Long cc=127L;
        Long dd=254L;
        Long ee=254L;
        long ff= 3L;
        int ii=3;
        double j=3d;
        //System.out.println("d==c"+(d==c));编译不通过
        System.out.format("d==(b+c):",d==(b+c));//TRUE
        System.out.format("d.equals(b+c):",ccc.equals(b+c));//TRUE
        System.out.format("dd==(bb+cc):",dd==(bb+cc));//TRUE
        System.out.format("dd.equals(ee):",dd.equals(bb+cc));//TRUE
        System.out.println("ff==i==j"+(ff==ii)+(ii==j));//true true
        //System.out.println("cc==bb"+(cc==bb));//编译不通过
        System.out.println("dd==ee"+(dd==ee));//false
        System.out.println("ff==d"+(ff==d));//TRUE
        ArrayList<Integer> collect = IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> clone = (ArrayList<Integer>) collect.clone();
        for (int i=0;i<clone.size();i++){
            clone.set(i,clone.get(i)+1);
        }
        ThreadLocal<String> objectThreadLocal = new ThreadLocal<>();
        ThreadLocal<String> objectThreadLocal1 = new ThreadLocal<>();
        boolean b1 = objectThreadLocal == objectThreadLocal1;
        System.out.printf("b1:",b1);
        objectThreadLocal.set("123");
        objectThreadLocal1.set("abc");
        System.out.format("11");
        HashMap<String, String> map1 = new HashMap<>();
        Map<String, String> stringStringMap = Collections.synchronizedMap(map1);
        stringStringMap.put("a","a");
        System.out.format("111");
    }
    static class ThreadLocalExample implements Runnable{

        // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
        private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

        public static void main(String[] args) throws InterruptedException {
            ThreadLocalExample obj = new ThreadLocalExample();
            for(int i=0 ; i<10; i++){
                Thread t = new Thread(obj, ""+i);
                Thread.sleep(new Random().nextInt(1000));
                t.start();
            }
        }

        @Override
        public void run() {
            System.out.format("Thread Name= "+Thread.currentThread().getName()+" default Formatter = "+formatter.get().toPattern());
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            formatter.remove();
            //formatter pattern is changed here by thread, but it won't reflect to other threads
            formatter.set(new SimpleDateFormat());

            System.out.format("Thread Name= "+Thread.currentThread().getName()+" formatter = "+formatter.get().toPattern());
        }
    }

    /**
     * 测试克隆
     */
    @Test
    public void TestClone(){
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i+"");
        }
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals("3")) strings.remove("3");
        }
        for (String string : strings) {
            if (string.equals("5")) strings.remove("5");
        }
        Dept dept = new Dept();
        Emp emp = new Emp();
        int i = emp.getClass().getName().compareTo(dept.getClass().getName());
        ArrayList<IntTest> collect = IntStream.range(0, 10).mapToObj(IntTest::new).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<IntTest> clone = (ArrayList<IntTest>) collect.clone();
        clone.forEach(IntTest::incr);
        System.out.format("collect:"+ collect);
        System.out.format("clone:"+clone);
        IntTest a = new IntTest(6, 'a');
        IntTest a1 = a.clone();
        a.increment();
        System.out.format("a1:",a1);
    }

    /**
     * break outer i:0+j:0;
     * break i:0+j:0;i:1+j:0;i:2+j:0;i:3+j:0;i:4+j:0;i:5+j:0;i:6+j:0;i:7+j:0;i:8+j:0;i:9+j:0;
     * continue outer i:0+j:0;i:1+j:0;i:2+j:0;i:3+j:0;i:4+j:0;i:5+j:0;i:6+j:0;i:7+j:0;i:8+j:0;i:9+j:0;
     *continue i:0+j:0;i:1+j:0;i:2+j:0;i:3+j:0;i:4+j:0;i:5+j:0;i:6+j:0;i:7+j:0;i:8+j:0;i:9+j:0;
     */
    @Test
    public void TestLoop(){
        outer:for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(j==0) {
                    System.out.print("i:"+i+"+j:"+j+";");
                }else {
                    continue;   //break;
                }
            }
        }
    }
    @Test
    public void testCompleteFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<Boolean> async = CompletableFuture.supplyAsync(this::complectTest).thenApply(s->s.equals(true));
        CompletableFuture<Boolean> async1 = CompletableFuture.supplyAsync(this::complectTest).thenApply(s->s.equals(true));
        CompletableFuture<Boolean> async2 = CompletableFuture.supplyAsync(this::complectTest).thenApply(s->s.equals(true));
        CompletableFuture<Boolean> async3 = CompletableFuture.supplyAsync(this::complectTest).thenApply(s->s.equals(true));
        Boolean aBoolean = async.get();
        Boolean aBoolean1 = async1.get();
        Boolean aBoolean2 = async2.get();
        Boolean aBoolean3 = async3.get();
        System.out.println("线程都异步完成");
    }

    private String complectTest() {
        String name;
        try {
             name = Thread.currentThread().getName();
            System.out.println(name+"睡眠3秒开始");
            Thread.sleep(3000);
            System.out.println(name+"睡眠3秒结束");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
}
