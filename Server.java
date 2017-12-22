/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;
import javax.swing.JOptionPane;

public class Server implements Runnable {

    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 512;
    static Hashtable<String, Integer> table = new Hashtable<String, Integer>();
    static String rootDirectory;
    static String[] fileTypes;
    static int numberOfFile;
    static boolean folderOption;
    static int maxFileSize;
    static TreeSet<Integer> allowedStudentId;
    static String fixedFileName=null;
    Socket socket;
    Thread thr;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    Server(Socket s) {
        socket = s;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
        thr = new Thread(this);
        thr.start();
    }

    @Override
    public void run() {
        try {
            int id;
            do{
                id=ois.readInt();
                System.out.println(id);
                if(allowedStudentId.contains(id))
                {
                    table.put(socket.getInetAddress().toString(),id);
                    oos.writeInt(1);
                }
                else
                {
                    oos.writeInt(0);
                }
                oos.flush();
            }while(allowedStudentId.contains(id)==false);
            File file=new File(rootDirectory+"\\"+id);
            file.mkdir();
            sendRequirement();
            int n=ois.readInt();
            for(int i=0;i<n;i++)
                saveFile(file.getAbsolutePath(),0);
            int a,re=0;
            while((a=ois.readInt())<2)
            {
                re++;
                if(a==0)
                {
                    n=ois.readInt();
                    for(int i=0;i<n;i++)
                        saveFile(file.getAbsolutePath(),0);
                }
                else if (a==1)
                {
                    //prob
                    boolean flag=true;
                    n=ois.readInt();
                    System.out.println(n);
                    String o;
                    for(int j=0;j<n;j++)
                    {
                        File []files=file.listFiles();
                        int i;
                        o=(String)ois.readObject();
                        System.out.println(o);
                        for(i=0;i<files.length;i++)
                            if(files[i].getName().equals(o.toString()))
                                break;
                        if(i==files.length)
                        {
                            oos.writeInt(0);
                            flag=false;
                        }
                        else
                            oos.writeInt(1);
                        oos.flush();
                    }
                    if(flag)
                    {
                       // file= new File(str+ o.toString()+re);
                        n=ois.readInt();
                        System.out.println(n);
                        for(int i=0;i<n;i++)
                            saveFile(file.getAbsolutePath(),re);
                    }
                }
                else
                {

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void sendRequirement() throws Exception
    {
       /* oos.writeInt(fileTypes.length);
        for(int i=0;i<fileTypes.length;i++)
            oos.writeObject(fileTypes[i]);
        */
        if(ois.readInt()==111)
        {
            System.out.println("entered");
            oos.writeObject(fileTypes);
            System.out.println("entered");
            oos.writeInt(numberOfFile);
            System.out.println("entered");
            oos.writeBoolean(folderOption);
            System.out.println("entered");
            oos.writeInt(maxFileSize);
            System.out.println("entered");
            oos.writeObject(fixedFileName);
            oos.flush();
            System.out.println("entered5");
         }
        
        
    }

    private void saveFile(String str,int re) throws Exception {
        //  String parentDirectory="E:\\my necessary docs\\L-3,T2\\";
        Object o = ois.readObject();
        FileOutputStream fos = null;
        String fileName;
        File file = null;
        int bytesRead = 0;
        int startingByte;
        byte[] buffer = new byte[BUFFER_SIZE];

        if (o instanceof String) {
            if(re==0)
            {
                    file = new File(str+ o.toString());
            }
            else
            {
                String temp=o.toString();
                if(temp.indexOf('.')>=0)
                    temp=temp.substring(0,temp.lastIndexOf('.'))+'('+re+')'+temp.substring(temp.lastIndexOf('.'));
              //  else
                //    temp=temp+re;
                file= new File(str+temp);
            }
            //  fos = new FileOutputStream("E:\\"+o.toString());
        } else {
            System.out.println("failed");
            throwException("Something is wrong");
        }
        if (ois.readInt() == 1) {
            System.out.println(file.mkdir());
            int n=ois.readInt();
            for(int i=0;i<n;i++)
                saveFile(str,re);
            System.out.println("Folder transfer success");
        } else {

            // 2. Read file to the end.
            fos = new FileOutputStream(file);
            while(ois.readInt()==1)
            {
                fileName = (String) ois.readObject();
                startingByte = ois.readInt();
                bytesRead = ois.readInt();
                o = ois.readObject();
                System.out.println("recieved:" + fileName + startingByte + bytesRead);
                if (!(o instanceof byte[])) {
                    throwException("Something is wrong");
                }

                buffer = (byte[]) o;

                // 3. Write data to output file.
                fos.write(buffer, 0, bytesRead);

                oos.writeObject(fileName);
                oos.writeInt(startingByte);
                oos.writeInt(bytesRead);
                oos.flush();

            }// while (bytesRead == BUFFER_SIZE);

            System.out.println("File transfer success");
            fos.close();
        }

    }

    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }

  /*  public static void main(String[] args) {

        
    }*/
}
