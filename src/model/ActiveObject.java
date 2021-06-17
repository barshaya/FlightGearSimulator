package model;

public interface ActiveObject {
    public void start();
    public void stop();
    public void pause();
    public void execute(Runnable r);
    public void join();
}
