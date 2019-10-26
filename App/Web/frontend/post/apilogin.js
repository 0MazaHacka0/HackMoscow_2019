module.exports.func = async (con, req, res) => {
	console.log(req.body);

	if (!req.body) {
		res.json({error: "NoBodyData"});
		return;
	}

	let login = await con.userLib.promise(con, con.queryValue, 'SELECT id FROM users WHERE login = ? AND password = ?', [req.body.login, con.userLib.md5(req.body.password)]);
	login = login.res;

	res.json(login ? {id: login} : {error: "NoUserFound"})
};

module.exports.path = '/api/login';