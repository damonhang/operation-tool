package com.damonhang.algorithm.leetcode;

public class NumToZeroStep {

  private int step = 0;

  public int numberOfSteps(int num) {
    toZero(num);
    return step;
  }

  private void toZero(int num) {
    if (num == 0) {
      return;
    }
    step += 1;
    if (num % 2 == 0) {
      num = num / 2;
    } else {
      num = num - 1;
    }
    toZero(num);
  }

  public static void main(String[] args) {
    System.out.println(new NumToZeroStep().numberOfSteps(5));
  }
}
