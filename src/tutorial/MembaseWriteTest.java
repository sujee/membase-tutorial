package tutorial;

import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class MembaseWriteTest
{
    static int MAX = 100;
    static String server = "localhost";
    static int port = 11211;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        MemcachedClient cache = new MemcachedClient(new InetSocketAddress(server, port));

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++)
        {
            String s = new Integer(i).toString();
            Object o = cache.set(s, 3600, i);
            System.out.println("cache put : " + s + " : " + i + ",  result " + o);
        }
        long t2 = System.currentTimeMillis();

        System.out.println("Time for " + MAX + " puts is " + (t2 - t1) + " ms");


        t1 = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++)
        {
            String s = new Integer(i).toString();
            Object o = cache.get(s);
            System.out.println("Cache get : " + s + " : " + o);
        }
        t2 = System.currentTimeMillis();
        cache.shutdown();

        System.out.println("Time for " + MAX + " gets is " + (t2 - t1) + " ms");
    }
}