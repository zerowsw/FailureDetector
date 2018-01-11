# FailureDetector
Instructions:
We predefined a primary introducer node which should join the group first. Then, 
other nodes can join the group through the primary introducer node.We also 
predefined four potential introducers which ensure the rejoin of the primary 
introducer. These settings are in the Introuducers.java. The first one is the IP 
of primary introducer and the others are IPs of potential introducers. You can 
change them as you like.Our project is build on maven. You can package our project 
into a executeable jar file by the command mvn assembly:assembly. We assume that 
you name the jar file "mp2.jar". Then you can arrange the jar files at mahcines 
where you want to run the program. 

cd into the folder of mp2.jar and use the command java -jar mp2.jar to run our 
failure detection program.
You will see the following lines:

You can choose the following action: 

Enter 'membership' to list membership list

Enter 'id' to list your id 

Enter 'join' to join the group 

Enter 'leave' to leave the group 

Enter 'grep' and queries to grep

You can type 'id' to get the id of the current node and type 'membership' to get 
the membership list of this node.
You can type 'join' to allow the current node join in the member group and type 
'leave' to leave.
