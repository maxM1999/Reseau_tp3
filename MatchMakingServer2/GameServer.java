import java.io.*;
import java.net.*;

public class GameServer implements Runnable
{
    private int m_Port;
    private ServerSocket m_ServerSocket;
    private Socket m_SocketPlayer1;
    private DataInputStream m_DataInputStreamPlayer1;
    private DataOutputStream m_DataOutputStreamPlayer1;
    private Socket m_SocketPlayer2;
    private DataInputStream m_DataInputStreamPlayer2;
    private DataOutputStream m_DataOutputStreamPlayer2;

    public GameServer(int port)
    {
        m_Port = port;
    }

    @Override
    public void run()
    {
        try
        {
            m_ServerSocket = new ServerSocket(m_Port);
            m_SocketPlayer1 = m_ServerSocket.accept();
            m_ServerSocket = new ServerSocket(m_Port);
            m_SocketPlayer2 = m_ServerSocket.accept();

            System.out.println("le eux clients sont connectés");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
