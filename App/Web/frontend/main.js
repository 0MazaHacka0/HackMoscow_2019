var express = require('express')
	, fs = require('fs')
	, app = express()
	, http = require('http').Server(app)
	, session = require('express-session')
	, MySQLStore = require('express-mysql-session')(session)
	, io = require('socket.io').listen(http)
	;

let con = require('mysql').createConnection({user: "moscow", password: "moscow", database: "moscow", charset: "utf8mb4"});
con.on('error', (err) => {console.warn(err)});
con.connect((err) => {if (err) return console.error('error connecting: ' + err.stack); console.log('mysql for / as id ' + con.threadId);});
let util = require('mysql-utilities');
util.upgrade(con);
util.introspection(con);

con.userLib = {};
con.userLib.promise = require('./promise');
con.userLib.md5 = require('js-md5');

app.set("view engine", "ejs");
app.use(express.static('public'));

session = session({secret: "u9hf2huwh29", store: new MySQLStore({expiration: 604800000}, con), resave: false, saveUninitialized: false, name: 'sid', cookie: {maxAge: 604800000}});

io.use((socket, next) => {session(socket.request, socket.request.res, next)});
// try {require('./io')(io, con)} catch (e) {console.warn(e)}

app.use(express.urlencoded({ extended: true })).use(express.json()).use(require('cookie-parser')())
.use(session)

let gets = fs.readdirSync("./get/");

gets.filter(el => el.endsWith(".js")).forEach(file => {
	try{
		const get = require(`./get/${file}`);
		app.get(get.path, get.func.bind(null, con));
		delete require.cache[require.resolve(`./get/${file}`)];
	} catch (e) {console.warn(e)}
});

let posts = fs.readdirSync("./post/");

posts.filter(el => el.endsWith(".js")).forEach(file => {
	try {
		const post = require(`./post/${file}`);
		app.post(post.path, post.func.bind(null, con));
		delete require.cache[require.resolve(`./post/${file}`)];
	} catch (e) {console.warn(e)}
});

// Handle 404 AND 500
app.use((req, res) => {res.status(404).send('400 <a href="/">back</a>')})
.use((error, req, res, next) => {console.warn(error); res.status(500).send('500 <a href="/">back</a>');})

http.listen(100, () => console.log('Work on port :100'));