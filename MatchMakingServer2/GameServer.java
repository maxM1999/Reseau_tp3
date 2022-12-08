import java.io.*;
import java.net.*;

public class GameServer implements Runnable
{
    private int m_Port1;
    private int m_Port2;
    private ServerSocket m_ServerSocket;
    private Socket m_SocketPlayer1;
    private DataInputStream m_DataInputStreamPlayer1;
    private DataOutputStream m_DataOutputStreamPlayer1;
    private Socket m_SocketPlayer2;
    private DataInputStream m_DataInputStreamPlayer2;
    private DataOutputStream m_DataOutputStreamPlayer2;

    public GameServer(int port)
    {
        m_Port1 = port;
        m_Port2 = port + 1;
    }

    @Override
    public void run()
    {
        try
        {
            m_ServerSocket = new ServerSocket(m_Port1);
            m_SocketPlayer1 = m_ServerSocket.accept();
            System.out.println("Client connecté a la game");
            m_ServerSocket = new ServerSocket(m_Port2);
            m_SocketPlayer2 = m_ServerSocket.accept();

            System.out.println("le eux clients sont connectés");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public int GetPort(int portNumber)
    {
        if(portNumber == 1)
        {
            return m_Port1;
        }
        else if(portNumber == 2)
        {
            return m_Port2;
        }

        return -1;
    }
}
