import java.util.Random;
import java.util.Scanner;

public class GameOfLives {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("输入行数：");
        int rows = in.nextInt();
        System.out.print("输入列数：");
        int cols = in.nextInt();

        int[][] enviroment = new int[rows][cols];
        int[][] temp = new int[rows][cols];
        //随机初始化
        Random random = new Random();
        int jishu = 0;
        int outshu = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double rd =  Math.random();
                int num = rd < 0.5 ? 0 : 1;
                if (num == 1) {
                    jishu++;
                }else {
                    outshu++;
                }
                enviroment[i][j] = num;
            }
        }
        System.out.println("奇数："+jishu);
        System.out.println("偶数："+outshu);

        Thread t1 = new Thread(()->{
            int count = 1;
            while(true) {
                System.out.println("============= 第 "+count+" 轮 ============");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        temp[i][j] = enviroment[i][j];
                    }
                }
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int liveCount = getLiveCount(temp,i,j);
                        if (liveCount == 3) {
                            enviroment[i][j] = 1;
                        }else if (liveCount < 2 || liveCount > 3) {
                            enviroment[i][j] = 0;
                        }
                    }
                }
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        System.out.print(enviroment[i][j] + ",");
                    }
                    System.out.print("\n");
                }
                count++;

                try {
                    Thread.sleep(3 *1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"定时任务");
        t1.start();
    }

    private static int getLiveCount(int[][] environment, int x, int y) {
        int count = 0;
        count += isLive(environment,x-1,y);
        count += isLive(environment,x+1,y);
        count += isLive(environment,x,y+1);
        count += isLive(environment,x,y-1);
        count += isLive(environment,x-1,y-1);
        count += isLive(environment,x-1,y+1);
        count += isLive(environment,x+1,y-1);
        count += isLive(environment,x+1,y+1);
        System.out.println("count in ["+x+","+y+"] is :" + count);
        return count;
    }
    private static int isLive(int[][] environment,int x, int y) {
        int rows = environment.length;
        int cols = environment[0].length;

        if (x < 0 || x >= rows) {
            return 0;
        }
        if (y < 0 || y >= cols) {
            return 0;
        }
        return environment[x][y] == 1 ? 1 : 0;
    }
}
