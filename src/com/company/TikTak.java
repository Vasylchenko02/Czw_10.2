package com.company;

import javax.swing.*;

public class TikTak {
    String state;
    synchronized void tik(boolean running){
        if(!running){
            state="ticked";
            notify();
            return;

        }
        System.out.println("tik");
        state = "ticked";
        notify();
        try{
            while (!state.equals("tocked"))
                wait();
            Thread.sleep(0500);

        }catch (InterruptedException e ){
            System.out.println("Wątek został przerwany ");
        }
    }
    synchronized void tok (boolean running){
        if(!running){
            state="tocked";
            notify();
            return;

        }
        System.out.println("tak");
        state = "tocked";
        notify();
        try{
            while (!state.equals("ticked"))
                wait();
            Thread.sleep(0500);
        }catch (InterruptedException e ){
            System.out.println("Wątek został przerwany ");
        }
    }

}
class  MyThreadClock implements Runnable{
    Thread thread; TikTak tt0b;
    MyThreadClock(String name, TikTak tt){
        thread = new Thread(this,name);
        tt0b = tt;

    }
    public static  MyThreadClock createAndStart(String name,TikTak tt){
        MyThreadClock myThreadClock = new MyThreadClock(name,tt);
        myThreadClock.thread.start();
        return myThreadClock;
    }
    public void run() {
        if(thread.getName().compareTo("tik")==0){
            for(int i =0;i<5;i++)tt0b.tik(true);
            tt0b.tik(false);
        }else {
            for(int i =0;i<5;i++)tt0b.tok(true);
            tt0b.tok(false);
        }
    }


}
class ThreadCom{
    public static void main(String[] args) {
        TikTak tt = new TikTak();
        MyThreadClock myThreadClock1 = MyThreadClock.createAndStart("tik",tt);
        MyThreadClock myThreadClock2 = MyThreadClock.createAndStart("tak",tt);
        try{

            myThreadClock1.thread.join();
            myThreadClock2.thread.join();
        }catch (InterruptedException e ){
            System.out.println("Wątek główny  został przerwany ");
        }
    }
}