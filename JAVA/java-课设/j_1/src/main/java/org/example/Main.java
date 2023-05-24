package org.example;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int TOTAL_TICKETS = 100;
    private static final int SELLER_NUM = 5;

    public static void main(String[] args) {
        List<Integer> ticketPool = new ArrayList<>();
        for (int i = 1; i <= TOTAL_TICKETS; i++) {
            ticketPool.add(i);
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= SELLER_NUM; i++) {
            Thread thread = new Thread(new TicketSeller(i, ticketPool));
            thread.start();
            threads.add(thread);
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有的票已经售出!!!!!!!!");
    }

    private static class TicketSeller implements Runnable {
        private final int sellerId;
        private final List<Integer> ticketPool;

        public TicketSeller(int sellerId, List<Integer> ticketPool) {
            this.sellerId = sellerId;
            this.ticketPool = ticketPool;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (ticketPool) {
                    if (ticketPool.isEmpty()) {
                        break;
                    }
                    int ticketNumber = ticketPool.remove(0);
                    System.out.println("窗口 " + sellerId + " 卖出票的编号为： " + ticketNumber);
                }
                try {
                    // 模拟售票时间
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}