import java.io.*;
import java.net.*;

public class Client implements Runnable
{
    private Socket m_Socket;

    private DataInputStream m_In;
    private DataInputStream m_DataInputStream;
    private DataOutputStream m_DataOutputStream;

    private String m_ID;

    public Client(String IP, int port, String ID)
    {
        try
        {
            m_ID = ID;
            m_Socket = new Socket(IP, port);
            m_DataInputStream = new DataInputStream(m_Socket.getInputStream());
            m_DataOutputStream = new DataOutputStream(m_Socket.getOutputStream());
            m_In = new DataInputStream(System.in);

            if(m_Socket.isConnected())
            {
                System.out.println("client connecté.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                m_DataOutputStream.writeUTF(m_ID);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
