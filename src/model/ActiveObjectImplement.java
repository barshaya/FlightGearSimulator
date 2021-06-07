package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveObjectImplement implements ActiveObject{


    Thread activeThread;
    BlockingQueue<Runnable> queue;
    volatile boolean stop;

    public ActiveObjectImplement() {
        super();
        this.queue = new LinkedBlockingQueue<Runnable>();
        this.stop = true;
    }

    @Override
    public void start() {
        if(stop==false)
            return;
        stop=false;
        activeThread = new Thread(()->this.run());
        activeThread.start();
    }

    @Override
    public void stop() {
        this.stop=true;
        this.queue = new LinkedBlockingQueue<Runnable>();
        //this.activeThread.interrupt();

    }

    @Override
    public void execute(Runnable r) {
        this.queue.add(r);
    }

    @Override
    public void join() {
        try {
            this.activeThread.join();
        } catch (InterruptedException e) {}

    }

    public void run() {
        while(!stop) {
            try {
                queue.take().run();
            }catch(InterruptedException e) {}
        }
    }

    @Override
    public void pause() {
        this.stop = true;
        this.activeThread.interrupt();
    }
    public void ClearTasks() {
        queue = new LinkedBlockingQueue<Runnable>();
    }
}
