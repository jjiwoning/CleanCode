package Chapter13;

public class X {
    private int lastIdUsed;

    public int getNextId() {
        ++lastIdUsed;
        System.out.println(lastIdUsed);
        return lastIdUsed;
    }

    public int getLastIdUsed() {
        return lastIdUsed;
    }

    public void setLastIdUsed(int lastIdUsed) {
        this.lastIdUsed = lastIdUsed;
    }
}


