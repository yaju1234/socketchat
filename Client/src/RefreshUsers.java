class RefreshUsers extends Thread
{
  ConfDraw client;
  public RefreshUsers(ConfDraw client)
  {
    this.client = client;
  }
  public void run()
  {
    while(true)
    {
      /** sending a U: to get the users online after every 15 seconds **/
      client.cp.sendData("U" + ":");
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
