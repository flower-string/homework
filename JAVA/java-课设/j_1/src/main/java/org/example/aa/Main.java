package org.example.aa;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import sun.security.krb5.internal.Ticket;

import java.util.*;



public class Main {

    private static final int MaxPoints = 5;

    public static void main(String[] args) {
        int MaxTickets = 100;
        Set<Integer> soldTicketSet = new HashSet<>();

        TicketSeller[] t = new TicketSeller[5];
        t[0] = new TicketSeller("售票点"+(1),MaxTickets,soldTicketSet);
        t[0].start();

        t[1] = new TicketSeller("售票点"+(2),MaxTickets,soldTicketSet);
        t[1].start();

        t[2] = new TicketSeller("售票点"+(3),MaxTickets,soldTicketSet);
        t[2].start();

        t[3] = new TicketSeller("售票点"+(4),MaxTickets,soldTicketSet);
        t[3].start();

        t[4] = new TicketSeller("售票点"+(5),MaxTickets,soldTicketSet);
        t[4].start();

    }
    static class TicketSeller extends Thread
    {
        private  String name;
        private  int ticketCount;
        private  Set<Integer> soldTicketSet;
        public TicketSeller(String name ,int ticketCount ,Set<Integer> soldTicketSet)
        {
            this.name = name;
            this.ticketCount = ticketCount;
            this.soldTicketSet = soldTicketSet;
        }
        @Override
        public void run() {
            while(ticketCount>0)
            {
                int ticketNo = (int)(Math.random()*100)+1;
                synchronized (soldTicketSet)
                {
                    if(!soldTicketSet.contains(ticketNo))
                    {
                        soldTicketSet.add(ticketNo);
                        ticketCount--;
                        System.out.println( name+"售出了第"+ticketNo+"号火车票");
                    }
                }
            }
        }
    }
}
