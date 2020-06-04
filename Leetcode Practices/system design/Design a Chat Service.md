Q:  
Design an enterprise grade chat service which supports:  
* authorized and anonymous users
* realtime message delivery
* chat history
* direct and group text messaging
* highly available
* durable  
  
A:  
Realtime messaging delivery - can use websockets.
chat history, for that it need to be an authorized user, can store the data in db and retrieve the history of whatever conversation user had .
highly available. can distribute the data by sharding. span multiple service API and route through load balancer. keep the static content of the web page if its a web client in cdn server.
still there are lot to do with this questions. but can start with the above.