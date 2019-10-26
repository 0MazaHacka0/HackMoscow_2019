module.exports.func = (con, req, res) => {

	if (req.session.uID) {
		res.redirect('/map');
		return;
	}

	res.render('login', {
		title:"Авторизация",
		isLogin:req.session.uID ? true : false,
		user: req.session
	});
};

module.exports.path = '/login';