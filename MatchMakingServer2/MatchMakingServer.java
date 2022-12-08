import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Dictionary;

public class MatchMakingServer implements Runnable
{
    private ServerSocket m_ServerSocket1;
    private ServerSocket m_ServerSocket2;
    private Socket m_SocketPlayer1;
    private Socket m_SocketPlayer2;
    private DataInputStream m_DataInputStreamPlayer1;
    private DataOutputStream m_DataOutputStreamPlayer1;
    private DataInputStream m_DataInputStreamPlayer2;
    private DataOutputStream m_DataOutputStreamPlayer2;

    private ArrayList<GameServer> m_Games;
    private ArrayList<Thread> m_GamesThreads;
    private int m_Port;

    private String m_GameServerIP;

    private int m_GameCount = 0;

    private ArrayList<Integer> m_GamesInfos;



    public MatchMakingServer(int port, String gameServerIP)
    {
        m_Games = new ArrayList<GameServer>();
        m_GamesThreads = new ArrayList<Thread>();
        m_GamesInfos = new ArrayList<Integer>();


        m_Port = port;
        m_GameServerIP = gameServerIP;

        for(int i = 0; i < 4; i+=2)
        {
            int gamePort = 5000;
            GameServer game = new GameServer(5000 + i);
            Thread thread = new Thread(game);
            m_Games.add(game);
        }

        System.out.println("waiting for players");

        try
        {
            m_ServerSocket1 = new ServerSocket(m_Port);
            System.out.println("Waiting for player 1 cons");
            m_SocketPlayer1 = m_ServerSocket1.accept();
            m_DataInputStreamPlayer1 = new DataInputStream(m_SocketPlayer1.getInputStream());
            m_DataOutputStreamPlayer1 = new DataOutputStream(m_SocketPlayer1.getOutputStream());
            m_DataOutputStreamPlayer1.writeUTF("1");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        try
        {
            m_ServerSocket2 = new ServerSocket(m_Port + 1);
            System.out.println("Waiting for player 2 cons");
            m_SocketPlayer2 = m_ServerSocket2.accept();
            System.out.println("les deux clients sont connecté");
            m_DataInputStreamPlayer2 = new DataInputStream(m_SocketPlayer2.getInputStream());
            m_DataOutputStreamPlayer2 = new DataOutputStream(m_SocketPlayer2.getOutputStream());
            m_DataOutputStreamPlayer2.writeUTF("2");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        while(true)
        {
            System.out.println("allo");
            /* Vérifier si on doit réinitialiser les sockets. */
            if(m_SocketPlayer1 != null)
            {
                System.out.println("1");
                try
                {
                    String playerMessage = "";
                    playerMessage = m_DataInputStreamPlayer1.readUTF();

                    if(playerMessage.equals("ResetConnexion1"))
                    {
                        m_SocketPlayer1 = null;
                    }
                    else if(playerMessage.equals("ResetConnexion2"))
                    {
                        m_SocketPlayer2 = null;
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }

            if(m_SocketPlayer2 != null)
            {
                System.out.println("2");
                try
                {
                    String playerMessage = "";
                    playerMessage = m_DataInputStreamPlayer2.readUTF();

                    if(playerMessage.equals("ResetConnexion1"))
                    {
                        m_SocketPlayer1 = null;
                    }
                    else if(playerMessage.equals("ResetConnexion2"))
                    {
                        m_SocketPlayer2 = null;
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }

            /* Rediriger les clients */
            if(m_SocketPlayer1 != null && m_SocketPlayer2 != null)
            {
                System.out.println("2");
                System.out.println("test");
                try
                {
                    if(m_GameCount < m_Games.size())
                    {
                        if (m_GamesInfos.get(m_GameCount) == 0) {
                            String gameServerPort = Integer.toString(m_Games.get(m_GameCount).GetPort(1));
                            m_DataOutputStreamPlayer1.writeUTF(m_GameServerIP + '_' + gameServerPort);

                        }
                        else if (m_GamesInfos.get(m_GameCount) == 1)
                        {
                            String gameServerPort = Integer.toString(m_Games.get(m_GameCount).GetPort(2));
                            m_DataOutputStreamPlayer1.writeUTF(m_GameServerIP + '_' + gameServerPort);
                        }

                        m_GamesInfos.set(m_GameCount, m_GamesInfos.get(m_GameCount) + 1);

                        if (m_GamesInfos.get(m_GameCount) < 2)
                        {
                            m_GamesInfos.set(m_GameCount, m_GamesInfos.get(m_GameCount) + 1);
                        }
                        else
                        {
                            m_GameCount++;
                        }
                    }
                }
                catch(Exception e2)
                {
                    System.out.println(e2);
                }

                try
                {
                    if(m_GameCount < m_Games.size())
                    {
                        if (m_GamesInfos.get(m_GameCount) == 0)
                        {
                            String gameServerPort = Integer.toString(m_Games.get(m_GameCount).GetPort(1));
                            m_DataOutputStreamPlayer2.writeUTF(m_GameServerIP + '_' + gameServerPort);

                        }
                        else if (m_GamesInfos.get(m_GameCount) == 1)
                        {
                            String gameServerPort = Integer.toString(m_Games.get(m_GameCount).GetPort(2));
                            m_DataOutputStreamPlayer2.writeUTF(m_GameServerIP + '_' + gameServerPort);
                        }

                        m_GamesInfos.set(m_GameCount, m_GamesInfos.get(m_GameCount) + 1);

                        if (m_GamesInfos.get(m_GameCount) < 2)
                        {
                            m_GamesInfos.set(m_GameCount, m_GamesInfos.get(m_GameCount) + 1);
                        }
                        else
                        {
                            m_GameCount++;
                        }
                    }
                }
                catch(Exception e2)
                {
                    System.out.println(e2);
                }
            }

            /* Si les deux joueur sont redirigé, réouvrir les connexions. */
            if(m_SocketPlayer1 == null && m_SocketPlayer2 == null)
            {
                System.out.println("2");
                try
                {
                    m_ServerSocket1 = new ServerSocket(m_Port);
                    System.out.println("Wainting for player 1");
                    m_SocketPlayer1 = m_ServerSocket1.accept();
                    m_DataInputStreamPlayer1 = new DataInputStream(m_SocketPlayer1.getInputStream());
                    m_DataOutputStreamPlayer1 = new DataOutputStream(m_SocketPlayer1.getOutputStream());
                    m_DataOutputStreamPlayer1.writeUTF("1");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }

                try
                {
                    m_ServerSocket2 = new ServerSocket(m_Port + 1);
                    System.out.println("Wainting for player 2");
                    m_SocketPlayer2 = m_ServerSocket2.accept();
                    m_DataInputStreamPlayer2 = new DataInputStream(m_SocketPlayer2.getInputStream());
                    m_DataOutputStreamPlayer2 = new DataOutputStream(m_SocketPlayer2.getOutputStream());
                    m_DataOutputStreamPlayer2.writeUTF("2");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }
}

