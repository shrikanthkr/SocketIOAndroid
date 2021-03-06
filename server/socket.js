var server = require('http').createServer(handler);
var Firebase = require('firebase');
var rootRef = new Firebase('https://socket.firebaseio.com/');
var io = require('socket.io')(server);
var port =3000;
rootRef.off('child_added');
function handler (req, res) {
   res.writeHead(200, {'Content-Type': 'text/plain'});
      res.write('hello');
      res.write(':');
      res.end('World\n');
}

rootRef.on('child_added', function(dataSnapshot) {
    console.log("Fire base set : "+JSON.stringify(dataSnapshot.val()));
    io.sockets.emit('receive',JSON.stringify(dataSnapshot.val()));
 });

io.on('connection', function(socket){
  console.log(socket);
  rootRef.on("value", function(dataSnapshot) {
    io.sockets.emit('receive',JSON.stringify(dataSnapshot.val()));
  });
  socket.on('message', function(data){
    	 rootRef.push(data );
    });
});
 


server.listen(port);