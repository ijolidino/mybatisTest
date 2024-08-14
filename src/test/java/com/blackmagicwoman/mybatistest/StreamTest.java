package com.blackmagicwoman.mybatistest;

import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: mybatisTest
 * @description: java8的流测试
 * @author: heise
 * @create: 2022-09-17 11:29
 **/
public class StreamTest {

    @Test
    public void testHashMap(){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a","a");
    }
    @Test
    public void TestStream() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 5);
        integers.stream().filter(i -> i == 5).findAny().ifPresent(StreamTest::testSprint);
        Optional<Integer> max = integers.stream().max(Comparator.comparingInt(Integer::intValue));
        Integer integer = max.orElse(1);
        System.out.println("最大值" + integer);
        int[] ints = IntStream.range(1, 100).toArray();
    }

    private static void testSprint(Object objects) {
        System.out.println(objects);
    }

    @Test
    public void TestReduce() {
        List<BigDecimal> bigDecimals = Arrays.asList(new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5"), new BigDecimal("6"), new BigDecimal("7"), new BigDecimal("8"));
        BigDecimal reduce = bigDecimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(reduce);
        List<List<Integer>> collect = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 1000).boxed()
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0 && Math.sqrt(a * a + b * b) < 1000)
                        .map(b -> Arrays.asList(a, b, (int) Math.sqrt(a * a + b * b)))).collect(Collectors.toList());
        System.out.println("1-100的三元g个数为:" + collect.size());
        System.out.println("1-100的三元数为:" + collect);
        /**
         * 1-100的三元g个数为:222
         * 1-100的三元数为:[[3, 4, 5], [5, 12, 13], [6, 8, 10], [7, 24, 25], [8, 15, 17], [9, 12, 15], [9, 40, 41], [10, 24, 26], [11, 60, 61], [12, 16, 20], [12, 35, 37], [13, 84, 85],
         * [14, 48, 50], [15, 20, 25], [15, 36, 39], [15, 112, 113], [16, 30, 34], [16, 63, 65], [17, 144, 145], [18, 24, 30], [18, 80, 82], [19, 180, 181], [20, 21, 29], [20, 48, 52],
         * [20, 99, 101], [21, 28, 35], [21, 72, 75], [21, 220, 221], [22, 120, 122], [23, 264, 265], [24, 32, 40], [24, 45, 51], [24, 70, 74], [24, 143, 145], [25, 60, 65], [25, 312, 313],
         * [26, 168, 170], [27, 36, 45], [27, 120, 123], [27, 364, 365], [28, 45, 53], [28, 96, 100], [28, 195, 197], [29, 420, 421], [30, 40, 50], [30, 72, 78], [30, 224, 226],
         * [31, 480, 481], [32, 60, 68], [32, 126, 130], [32, 255, 257], [33, 44, 55], [33, 56, 65], [33, 180, 183], [33, 544, 545], [34, 288, 290], [35, 84, 91], [35, 120, 125],
         * [35, 612, 613], [36, 48, 60], [36, 77, 85], [36, 105, 111], [36, 160, 164], [36, 323, 325], [37, 684, 685], [38, 360, 362], [39, 52, 65], [39, 80, 89], [39, 252, 255],
         * [39, 760, 761], [40, 42, 58], [40, 75, 85], [40, 96, 104], [40, 198, 202], [40, 399, 401], [41, 840, 841], [42, 56, 70], [42, 144, 150], [42, 440, 442], [43, 924, 925],
         * [44, 117, 125], [44, 240, 244], [44, 483, 485], [45, 60, 75], [45, 108, 117], [45, 200, 205], [45, 336, 339], [46, 528, 530], [48, 55, 73], [48, 64, 80], [48, 90, 102],
         * [48, 140, 148], [48, 189, 195], [48, 286, 290], [48, 575, 577], [49, 168, 175], [50, 120, 130], [50, 624, 626], [51, 68, 85], [51, 140, 149], [51, 432, 435], [52, 165, 173],
         * [52, 336, 340], [52, 675, 677], [54, 72, 90], [54, 240, 246], [54, 728, 730], [55, 132, 143], [55, 300, 305], [56, 90, 106], [56, 105, 119], [56, 192, 200], [56, 390, 394],
         * [56, 783, 785], [57, 76, 95], [57, 176, 185], [57, 540, 543], [58, 840, 842], [60, 63, 87], [60, 80, 100], [60, 91, 109], [60, 144, 156], [60, 175, 185], [60, 221, 229],
         * [60, 297, 303], [60, 448, 452], [60, 899, 901], [62, 960, 962], [63, 84, 105], [63, 216, 225], [63, 280, 287], [63, 660, 663], [64, 120, 136], [64, 252, 260], [64, 510, 514],
         * [65, 72, 97], [65, 156, 169], [65, 420, 425], [66, 88, 110], [66, 112, 130], [66, 360, 366], [68, 285, 293], [68, 576, 580], [69, 92, 115], [69, 260, 269], [69, 792, 795],
         * [70, 168, 182], [70, 240, 250], [72, 96, 120], [72, 135, 153], [72, 154, 170], [72, 210, 222], [72, 320, 328], [72, 429, 435], [72, 646, 650], [75, 100, 125], [75, 180, 195],
         * [75, 308, 317], [75, 560, 565], [75, 936, 939], [76, 357, 365], [76, 720, 724], [77, 264, 275], [77, 420, 427], [78, 104, 130], [78, 160, 178], [78, 504, 510], [80, 84, 116],
         * [80, 150, 170], [80, 192, 208], [80, 315, 325], [80, 396, 404], [80, 798, 802], [81, 108, 135], [81, 360, 369], [84, 112, 140], [84, 135, 159], [84, 187, 205], [84, 245, 259],
         * [84, 288, 300], [84, 437, 445], [84, 585, 591], [84, 880, 884], [85, 132, 157], [85, 204, 221], [85, 720, 725], [87, 116, 145], [87, 416, 425], [88, 105, 137], [88, 165, 187],
         * [88, 234, 250], [88, 480, 488], [88, 966, 970], [90, 120, 150], [90, 216, 234], [90, 400, 410], [90, 672, 678], [91, 312, 325], [91, 588, 595], [92, 525, 533], [93, 124, 155],
         * [93, 476, 485], [95, 168, 193], [95, 228, 247], [95, 900, 905], [96, 110, 146], [96, 128, 160], [96, 180, 204], [96, 247, 265], [96, 280, 296], [96, 378, 390], [96, 572, 580],
         * [96, 765, 771], [98, 336, 350], [99, 132, 165], [99, 168, 195], [99, 440, 451], [99, 540, 549], [100, 105, 145], [100, 240, 260], [100, 495, 505], [100, 621, 629]]
         *
         * Process finished with exit code 0
         */
    }

    @Test
    public void TestStreamFile() throws IOException {
        File file = new File("/testStreamFile.txt");
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), Charset.defaultCharset()))) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i <= 100; i++) {
                    if (i % 10 == 0) {
                        bufferedWriter.write(stringBuilder.toString());
                        bufferedWriter.newLine();
                        stringBuilder = new StringBuilder();
                    }
                    stringBuilder.append(i).append("↑");
                }
            }
        }
        try (Stream<String> linesStream = Files.lines(Paths.get("/testStreamFile.txt"), Charset.defaultCharset())) {
            linesStream.flatMap(l -> Arrays.stream(l.split("↑")))
                    .forEach(System.out::println);
        }
        file.deleteOnExit();
    }

    @Test
    public void testHanota(){
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                51, 52, 53, 54, 55, 56, 57, 58, 59, 50,
                61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94);
        List<Integer> A = new ArrayList<>(integers);
        System.out.println("开始时间为："+ DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss").format(LocalDateTime.now()));
        hanota(A,B,C);
        System.out.println(C);
        System.out.println("结束时间为："+ DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        move(A,B,C,A.size());
    }

    //第一个参数是初始，第二个是目标，第三个参数是缓存。
    public void move(List<Integer> A, List<Integer> C, List<Integer> B ,int n) {
        if(n == 1) {
            C.add(A.remove(A.size()-1));
            return;
        }
        move(A, B, C, n - 1);             //上面n-1个，从A移动到B
        C.add(A.remove(A.size() - 1));    //最底下的一个，从A移动到C
        move(B, C, A, n - 1);             //上面n-1个，从B移动到C
    }


}
