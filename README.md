# File-and-Folder-uploader


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

On the client side, when a user wants to upload a file (by pressing corresponding button in the
client interface), the client will first request the server to send all the constraints
configured. After receiving the constraints list, the client will then prompt the user to
upload files accordingly and present with him a File Dialog box. If the files/folder
specified by the user mismatch with the constraints, those will be rejected and a warning
will be showed to the user.

Every file is segmented before transfer by the client where the segment size will
be maximum 512 bytes. For example, if a file is 1000 bytes, it will be segmented in two
chunks, the first one is 512 bytes and the next one is 1000-512=488 bytes. The client will
send each segment according to the following format:  
File Name::Starting Byte Number::Size of Segment::File Data  
The server, upon receiving each segment, will send an acknowledgment to the client
which will cause the client to send the next segment, i.e., each segment is
individually acknowledged.
