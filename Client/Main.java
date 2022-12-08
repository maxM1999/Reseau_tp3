
public class Main
{
    public static void main(String[] argv)
    {
        Client c = new Client("127.0.0.1", 4000, "1");
        Client c2 = new Client("127.0.0.1", 4001, "2");
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c2);

        t1.start();
        t2.start();
    }
}
