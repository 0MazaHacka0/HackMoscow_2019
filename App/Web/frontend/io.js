let net = require('net');
let client = new net.Socket();

client.connect(11000,"127.0.0.1",function(){console.log('Connected!');})
client.on('close', () => {console.log('Connected closed!');});

module.exports = (io, con) => {

	io.on('connection', (socket) => {

		socket.on('getData', async () => {
			let data = await con.userLib.promise(con, con.query, 'SELECT * FROM points WHERE uID = ?', [socket.request.session.uID]);
			socket.emit('recData', data.res);
		});

		socket.on('sendCsv', (data) => {console.log('Data get'); client.write(data);});

		client.on('data', (data) => {
			console.log(data.toString());
			// console.log();
			socket.emit('recData', JSON.parse(data.toString()));
		});

	});
};