package tutorial;

import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;

/**
 * simulates writing / reading from two different clients
 */
public class MembaseTest2
{
    static int MAX = 1000;
    static String server = "localhost";
    static int port = 11211;

    public static void main(String[] args) throws Exception
    {
        MemcachedClient cache = new MemcachedClient(new InetSocketAddress(server, port));
        cache.flush(); // clear all

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++)
        {
            String s = new Integer(i).toString();
            // key : integer converted to String (keys are always string)
            // time to live : in seconds, 3600 seconds (1h), 0 means no expiration
            // value : actual integer.  This can be an object.  Our integer will be converted to 'Integer'
            //         class by 'auto boxing' proess
            Object o = cache.set(s, 0, i);
            System.out.println("cache put : " + s + " : " + i + ",  result " + o);
        }
        long t2 = System.currentTimeMillis();
        cache.shutdown(); // close the client
        System.out.println("Time for " + MAX + " puts is " + (t2 - t1) + " ms");

        // open another connection
        cache = new MemcachedClient(new InetSocketAddress(server, port));

        t1 = System.currentTimeMillis();
        int nulls = 0;
        for (int i = 0; i < MAX; i++)
        {
            String s = new Integer(i).toString();
            Object o = cache.get(s);
            System.out.println("Cache get : " + s + " : " + o);
            if (o == null)
                nulls++;
        }
        t2 = System.currentTimeMillis();
        cache.shutdown();

        System.out.println("Time for " + MAX + " gets is " + (t2 - t1) + " ms.  nulls " + nulls + "\n");
    }
}
