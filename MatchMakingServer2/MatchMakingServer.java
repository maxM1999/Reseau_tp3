import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class MatchMakingServer implements Runnable
{
    private ServerSocket m_ServerSocket;
    private Socket m_SocketPlayer1;
    private Socket m_SocketPlayer2;
    private DataInputStream m_DataInputStreamPlayer1;
    private DataOutputStream m_DataOutputStreamPlayer1;
    private DataInputStream m_DataInputStreamPlayer2;
    private DataOutputStream m_DataOutputStreamPlayer2;

    private ArrayList<GameServer> m_Games;
    private ArrayList<Thread> m_GamesThreads;
    private int m_Port;

    public MatchMakingServer(int port)
    {
        m_Port = port;

        for(int i = 0; i < 4; i++)
        {
            int gamePort = 5000;
            GameServer game = new GameServer(5000 + i);
            Thread thread = new Thread(game);
        }
    }

    @Override
    public void run()
    {
        try
        {
            m_ServerSocket = new ServerSocket(m_Port);
            m_SocketPlayer1 = m_ServerSocket.accept();
            m_DataInputStreamPlayer1 = new DataInputStream(m_SocketPlayer1.getInputStream());
            m_DataOutputStreamPlayer1 = new DataOutputStream(m_SocketPlayer1.getOutputStream());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

