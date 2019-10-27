module.exports.func = async (con, req, res) => {

	if (!req.body) {
		res.redirect('/login');
		return;
	}

	let login = await con.userLib.promise(con, con.queryValue, 'SELECT id FROM users WHERE login = ? AND password = ?', [req.body.login, con.userLib.md5(req.body.password)]);
	login = login.res;

	if (!login) {
		req.session.logErr = 1;
		res.redirect('/login');
		return;
	}

	delete req.session.logErr
	req.session.uID = login;
	res.redirect('/map');
};

module.exports.path = '/login';