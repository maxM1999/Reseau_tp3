import java.io.*;
import java.net.*;

public class MatchMakingServer
{
    private ServerSocket m_ServerSocket;
    private Socket m_SocketPlayer1;
    private Socket m_SocketPlayer2;
    private DataInputStream m_DataInputStreamPlayer1;
    private DataOutputStream m_DataOutputStreamPlayer1;
    private DataInputStream m_DataInputStreamPlayer2;
    private DataOutputStream m_DataOutputStreamPlayer2;
    private int m_Port;

    public MatchMakingServer(int port)
    {
        m_Port = port;

        while(true)
        {
            while(!m_SocketPlayer1.isConnected() && !m_SocketPlayer2.isConnected())
            {
                try
                {
                    m_ServerSocket = new ServerSocket(m_Port);
                    if(!m_SocketPlayer1.isConnected())
                    {
                        m_SocketPlayer1 = m_ServerSocket.accept();
                        m_DataInputStreamPlayer1 = new DataInputStream(m_SocketPlayer1.getInputStream());
                        m_DataOutputStreamPlayer1 = new DataOutputStream(m_SocketPlayer1.getOutputStream());
                    }
                    else
                    {
                        m_SocketPlayer2 = m_ServerSocket.accept();
                        m_DataInputStreamPlayer2 = new DataInputStream(m_SocketPlayer2.getInputStream());
                        m_DataOutputStreamPlayer2 = new DataOutputStream(m_SocketPlayer2.getOutputStream());
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            System.out.println("les deux clients sont connecté");
            break;
        }
    }
}
