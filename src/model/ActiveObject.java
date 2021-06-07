package model;

public interface ActiveObject {
    public void start();
    public void stop();
    public void execute(Runnable r);
    public void join();
    public void pause();
}
