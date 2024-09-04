package com.blackmagicwoman.leecode.LeetCode100.backdate;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisTest
 * @description: 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 示例 1：
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 * 输入：n = 1
 * 输出：["()"]
 * 提示：
 * 1 <= n <= 8
 * @author: HeiSe
 * @create: 2024-03-13 20:25
 **/
public class CanGenerateParentheses {
    public static void main(String[] args) {
        generateParenthesis(3);
    }

    public static List<String> generateParenthesis(int n) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();
        generator(strings, tmp, 0, 0, n);
        return strings;
    }

    private static void generator(ArrayList<String> strings, ArrayList<String> tmp, int begin, int end, int sum) {
        if (tmp.size() == 2 * sum) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : tmp) {
                stringBuilder.append(s);
            }
            strings.add(stringBuilder.toString());
            return;
        }
        if (begin < sum) {
            tmp.add("(");
            generator(strings, tmp, begin + 1, end, sum);
            tmp.remove(tmp.size() - 1);
        }
        if (end < begin) {
            tmp.add(")");
            generator(strings, tmp, begin, end + 1, sum);
            tmp.remove(tmp.size() - 1);
        }
    }
}
