# client/server Application

Summary: Implementation of both client and server sides of a file/folder uploader application with GUI.

Key Concepts: TCP/IP socket programming, message passing protocol, file spliting and sending, receiving in segments.

• Options for user of server side to specify in GUI type of upload :

(i) File Type: This will allow a client to upload file only type/s specified by the
server, such as .java, .py, .c etc.

(ii) Single file/ multiple files/ folder: whether client will be able to upload single or
multiple files or folders with nested subfolders.

(iii) Fixed file names: Clients will only be able to upload file with pre-specified file
names such as server.java, client.java etc.

(iv) Max file size: Maximum allowable file size that a client is able to upload.
On startup, the client program will prompt the user for the server’s ‘IP Address’ and
‘Port Number’ as well as the ‘Student ID’. Then the client will initiate a connection
request with the server and send the ‘Student ID’.   
