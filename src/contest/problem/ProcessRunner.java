package contest.problem;

public class ProcessRunner implements Runnable {
	
	public static boolean waitForOrKill(Process self, long numberOfMillis) {
	    ProcessRunner runnable = new ProcessRunner(self);
	    Thread thread = new Thread(runnable);
	    thread.start();
	    return runnable.waitForOrKill(numberOfMillis);
	}
	
    Process process;
    private boolean finished;

    public ProcessRunner(Process process) {
        this.process = process;
    }

    public void run() {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            // Ignore
        }
        synchronized (this) {
            notifyAll();
            finished = true;
        }
    }

    public synchronized boolean waitForOrKill(long millis) {
        if (!finished) {
            try {
                wait(millis);
            } catch (InterruptedException e) {
                // Ignore
            }
            if (!finished) {
                process.destroy();
                return false;
            }
        }
        return true;
    }
}