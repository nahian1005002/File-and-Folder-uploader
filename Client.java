/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

/**
 *
 * @author Nahian
 */
import java.io.*;  
import java.io.FileInputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.Socket;  
import java.util.Arrays;  
import java.lang.*;  
import java.util.Scanner;  
import javax.swing.JOptionPane;



  
  
public class Client implements Runnable{
    
        public static final int BUFFER_SIZE = 512;
        ObjectInputStream ois ; 
        ObjectOutputStream oos;
        Socket socket;
        String ip="localhost";
        int port=3332;
        int studentId;
        Thread t;
        
        String[] fileTypes;
        int numberOfFile;
        boolean folderOption;
        int maxFileSize;
        String fixedFileName;
        
        Client(String ipAddress,int port,int studentId)
        {
            
            ip=ipAddress;
            this.port=port;
            this.studentId=studentId;
            t=new Thread(this);
            t.run();
        }
        public void run()
        {
           // promptIp();
            System.out.println("run");
            System.out.println("run");
          
            try{
                Socket socket = new Socket(ip,port);
                ois = new ObjectInputStream(socket.getInputStream());  
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeInt(studentId);
                oos.flush();
                String s1;
                while(ois.readInt()==0)
                {
                    s1=JOptionPane.showInputDialog(ClientGui.frame,"Your Student id is not valid. Please enter a valid student id :");
                    oos.writeInt(Integer.parseInt(s1));
                    oos.flush();
                }
                
                
           /*     sendFile(new File("E:\\wubildr"),"");
                oos.close();
                ois.close();*/
              
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            
        }
        void askRequirement() throws Exception
        {
            oos.writeInt(111);
            oos.flush();
            fileTypes= (String[])ois.readObject();
            System.out.println("entered");
            numberOfFile=ois.readInt();
            System.out.println(numberOfFile);
            folderOption=ois.readBoolean();
            System.out.println(folderOption);
            maxFileSize=ois.readInt();
            System.out.println(maxFileSize);
            fixedFileName=(String)ois.readObject();
             for(int i=0;i<fileTypes.length;i++)
                System.out.println(fileTypes[i]);
        }
        
        void promptIp()
        {
            Scanner scn=new Scanner(System.in);
            System.out.println("Enter ip:");
            ip=scn.nextLine();
            System.out.println("Enter port:");
            port=scn.nextInt();
            System.out.println("Enter ip:");
            studentId=scn.nextInt();
        }
        
        void sendFile(File file,String base) throws Exception
        {
             
            // file_name=file_name.replace("\\", "\\\\");
             System.out.println(file.getName());
            
            byte[] buffer = new byte[Client.BUFFER_SIZE];
            int bytesRead = 0;
            int startingByte=0;
            
            String ackFileName;
            int ackStartingByte;
            int ackBytes;
            File[] subFiles;
            oos.writeObject(base+"\\"+file.getName());
            if(file.isDirectory())
            {
                oos.writeInt(1);
                subFiles=file.listFiles();
                oos.writeInt(subFiles.length);
                for(int i=0;i<subFiles.length;i++)
                {
                    sendFile(subFiles[i],base+"\\"+file.getName());
                }
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                oos.writeInt(0);
                while ((bytesRead = fis.read(buffer)) > 0) {
                    oos.writeInt(1);
                    oos.writeObject(file.getName());
                    oos.writeInt(startingByte);
                    oos.writeInt(bytesRead);
                    oos.writeObject(Arrays.copyOf(buffer, buffer.length));
                    oos.flush();
                    ackFileName=(String)ois.readObject();
                    ackStartingByte=ois.readInt();
                    ackBytes=ois.readInt();
                    System.out.println("recieved:"+ackFileName+ackStartingByte+ackBytes);
                    if((ackFileName == null ? file.getName() != null : !ackFileName.equals(file.getName())) || startingByte!=ackStartingByte || ackBytes!=bytesRead)
                    {
                        System.out.println("file send error");
                        break;
                    }
                    startingByte+=bytesRead;
                }
                oos.writeInt(0);
                oos.flush();
                fis.close();
            }
            
        }
 /*   public static void main(String[] args) throws Exception {  
 
  
      //  oos.close();  
       // ois.close();  
}*/
 /*   
static class Folder
{
    File folder;
    File[] files;
    File[] subFolders;
    Folder(File f) throws Exception
    {
        folder=f;
        files= folder.listFiles();
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isDirectory()==false)
                oos.writeObject(files[i]);
            else
            {
                 new Folder(files[i]);
            }
                
        }
    }
}*/
  
}