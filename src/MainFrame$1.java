class MainFrame$1 implements Runnable {
    MainFrame$1() {
    }

    public void run() {
        try {
            MainFrame.frame1 = new MainFrame();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
