// let net = require('net');
// let client = new net.Socket();

// client.connect(11000,"127.0.0.1",function(){
// 	console.log('Connected!');
// 	client.write('Hello i Node.JS');
// })

module.exports = (io, con) => {

	io.on('connection', (socket) => {

		socket.on('getData', async () => {
			let data = await con.userLib.promise(con, con.query, 'SELECT * FROM points WHERE uID = ?', [socket.request.session.uID]);
			socket.emit('recData', data.res);
		});

		// socket.on('sendCsv', (data) => {client.write(data);});

	});
};