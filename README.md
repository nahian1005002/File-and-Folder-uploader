# File-and-Folder-uploader

Clients will be able to upload file only according to the constraints specified by the
server. Some constraints implemented are as follows.  
-- File Type: This will allow a client to upload file only type/s specified by the
server, such as .java, .py, .c etc.  
-- Single file/ multiple files/ folder: whether client will be able to upload single or
multiple files or folders with nested subfolders.  
-- Fixed file names: Clients will only be able to upload file with pre-specified file
names such as server.java, client.java etc.  
-- Max file size: Maximum allowable file size that a client is able to upload.  

On startup, the client program will prompt the user for the server’s ‘IP Address’ and
‘Port Number’ as well as the ‘Student ID’. Then the client will initiate a connection
request with the server and send the ‘Student ID’.   

Upon receipt of the connection request from a client, the server will save the <IP
Address, Student ID> mapping. The the server will create
a folder named after the ‘Student ID’ and all the files received through this connection
will be saved under this folder. The ‘Student IDs’ treated as numbers and should
be within the range pre-configured in the server. If the ‘Student ID’ of an incoming
request is not within the list, the server should notify the requesting client accordingly
and ask him to send a valid ID.

The server program provides interface for the following configurable options:  
(i) Root Directory: Location in the file system where the all the incoming files will
be stored by the server, i.e., all the folders named after the ‘Student IDs’ of
incoming requests as mentioned above, will be stored under this directory. By
default, the root directory will be the location from which the server program is
running.  
(ii) File types: Allowable file types that a client can upload.  
(iii) Number of files and folder: Number of files a client is allowed to upload and
whether a client is able to upload folder.  
(iv) Max file size: Maximum file size that a client is allowed to upload.  
(v) Allowable Student IDs: List of Student IDs who are allowed to upload files. IDs
can be specified in range format such as 201005001-20100560 as well as comma
separated individual IDs such as 200905100, 200805119.  
