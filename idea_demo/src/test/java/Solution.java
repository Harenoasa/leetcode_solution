import org.junit.Test;

import java.util.*;

public class Solution {



    public static char[][] board = {{'5','3','.','.','7','.','.','.','.'},
                                    {'6','.','.','1','9','5','.','.','.'},
                                    {'.','9','8','.','.','.','.','6','.'},
                                    {'8','.','.','.','6','.','.','.','3'},
                                    {'4','.','.','8','.','3','.','.','1'},
                                    {'7','.','.','.','2','.','.','.','6'},
                                    {'.','6','.','.','.','.','2','8','.'},
                                    {'.','.','.','4','1','9','.','.','5'},
                                    {'.','.','.','.','8','.','.','7','9'}};

    @Test
    public void test01(){
        HashSet<Character> c  = new HashSet<>();
        c.add('c');
        System.out.println(c.contains('c'));
    }

    @Test
    public void test() {
        solveSudoku(board);
    }
    public HashSet<Character> qushu(LinkedList board) {

        HashSet<Character> set = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            set.add(String.valueOf(i).charAt(0));
        }

        board.forEach((aChar) -> {
            set.remove(aChar);
        });

        return set;
    }

    public char[][] sodokuPlay(char[][] board,HashSet<Character>[] rowBuckets
            ,HashSet<Character>[] columnBuckets,HashSet<Character>[] smallMatrixBuckets,int tempRow,int tempCol){

        for (int i = tempRow; i < 9; i++) {
            for (int j = tempCol; j < 9; j++) {
                if (board[i][j] == '.') {
                    int k;
                    for (k = 1; k < 10; k++) {
                        Character c = String.valueOf(k).charAt(0);
                        if (rowBuckets[i].contains(c) && columnBuckets[j].contains(c)
                                && smallMatrixBuckets[i / 3 * 3 + j / 3].contains(c)) {
                            rowBuckets[i].remove(c);
                            columnBuckets[j].remove(c);
                            smallMatrixBuckets[i / 3 * 3 + j / 3].remove(c);
                            board[i][j] = c;
                            //替换成c之后循环
                            char[][] chars = sodokuPlay(board, rowBuckets, columnBuckets, smallMatrixBuckets, i, j + 1);
                            if (chars != null)return chars;
                            //不能通过之后回溯
                            rowBuckets[i].add(c);
                            columnBuckets[j].add(c);
                            smallMatrixBuckets[i / 3 * 3 + j / 3].add(c);
                            board[i][j] = '.';
                        }
                    }
                    if(k == 10)return null;

                }
            }
            tempCol = 0;
        }

        return board;
    }

    public void solveSudoku(char[][] board) {
        HashSet<Character>[] rowBuckets = new HashSet[9];
        HashSet<Character>[] columnBuckets = new HashSet[9];
        HashSet<Character>[] smallMatrixBuckets = new HashSet[9];
        LinkedList<Character> rowCharacters = new LinkedList<>();

        //用来设置横桶初始值的预设集合
        List<Character> defaultCollection = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            defaultCollection.add(String.valueOf(i).charAt(0));
        }

        //创造每列初始横桶
        for (int i = 0; i < columnBuckets.length; i++) {
            columnBuckets[i] = new HashSet<>();
            smallMatrixBuckets[i] = new HashSet<>();
            columnBuckets[i].addAll(defaultCollection);
            smallMatrixBuckets[i].addAll(defaultCollection);

        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                rowCharacters.add(board[i][j]);
                columnBuckets[j].remove(board[i][j]);
                smallMatrixBuckets[i/3*3+j/3].remove(board[i][j]);
            }
            HashSet<Character> qushu = qushu(rowCharacters);
            rowBuckets[i] = qushu;
            rowCharacters = new LinkedList<>();
        }

//        for (int i = 0; i < rowBuckets.length; i++) {
//            System.out.println(smallMatrixBuckets[i]);w
//        }

        char[][] chars = sodokuPlay(board, rowBuckets,
                columnBuckets,
                smallMatrixBuckets, 0, 0);
        for (char[] aChar : chars) {
            for (char c : aChar) {
                System.out.printf(c + " ");
            }
            System.out.println();
        }

    }
}
