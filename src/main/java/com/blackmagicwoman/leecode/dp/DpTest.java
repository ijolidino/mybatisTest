package com.blackmagicwoman.leecode.dp;

import org.junit.jupiter.api.Test;

/**
 * @program: mybatisTest
 * @description: 动态规划转移方程
 * @author: Fuwen
 * @create: 2023-09-23 23:31
 **/
public class DpTest {

    private static int[][] triangle = {
            {2, 0, 0, 0},
            {3, 4, 0, 0},
            {6, 5, 7, 0},
            {4, 1, 8, 3}
    };
    @Test
    public void testMiniPath(){
        //三角形中最小路径
        int traverse = traverse();
        System.out.println("最小路径为："+traverse);
    }

    public int traverse(){
        int ROW = 4;
        int[] mini = triangle[ROW - 1];
        // 从倒数第二行求起，因为最后一行的值本身是固定的
        for (int i = ROW - 2; i >= 0; i--) {
            for (int j = 0; j < triangle[j].length-1; j++) {
                mini[j] = triangle[i][j] + Math.min(mini[j], mini[j+1]);
            }
        }
        return mini[0];
    }
}
