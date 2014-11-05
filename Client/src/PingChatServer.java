class PingChatServer extends Thread
{
    ConfDraw client;
    public PingChatServer(ConfDraw client)
    {

        this.client = client;
    }
    public void run()
    {
        while(true)
        {
            client.cp.sendData("P");
            try
            {
                this.sleep(15000);
            }
            catch(InterruptedException e)
            {
            }
        }
    }
}


