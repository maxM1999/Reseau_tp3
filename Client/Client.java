import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client implements Runnable
{
    private Socket m_Socket;

    private DataInputStream m_In;
    private DataInputStream m_DataInputStream;
    private DataOutputStream m_DataOutputStream;

    private String m_MatchMakingId;

    private String m_ID;

    private boolean m_IsConnectedToGameServer;

    public Client(String IP, int port, String ID)
    {
        m_IsConnectedToGameServer = false;
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
        try
        {
            String m_MatchMakingId = m_DataInputStream.readUTF();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        while(true)
        {
            if(m_IsConnectedToGameServer == false)
            {
                try
                {
                    String gameConnexionInfos = "";

                    gameConnexionInfos = m_DataInputStream.readUTF();

                    if(gameConnexionInfos.equals(""))
                    {
                        continue;
                    }
                    else if(gameConnexionInfos.length() == 1)
                    {
                        m_MatchMakingId = gameConnexionInfos;
                    }
                    else
                    {
                        ArrayList<String> infos = GetConnexionInfos(gameConnexionInfos);
                        String IP = infos.get(0);
                        int port = Integer.parseInt(infos.get(1));


                        m_DataOutputStream.writeUTF("ResetConnexion" + m_MatchMakingId);

                        m_Socket = new Socket(IP, port);
                        m_DataInputStream = new DataInputStream(m_Socket.getInputStream());
                        m_DataOutputStream = new DataOutputStream(m_Socket.getOutputStream());

                        if(m_Socket != null)
                        {
                            m_IsConnectedToGameServer = true;
                        }
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }

    private ArrayList<String> GetConnexionInfos(String infos)
    {
        String IP = "";
        String port = "";

        for(int i = 0; i<infos.length(); i++)
        {
            if(infos.charAt(i) != '_')
            {
                IP += infos.charAt(i);
            }
            else
            {
                break;
            }
        }

        boolean hasReachedPort = false;
        for(int i = 0; i < infos.length(); i++)
        {
            if(!hasReachedPort)
            {
                if(infos.charAt(i) == '_')
                {
                    hasReachedPort = true;
                    continue;
                }
            }
            else
            {
                port += infos.charAt(i);
            }
        }

        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add(IP);
        toReturn.add(port);

        return toReturn;
    }
}
