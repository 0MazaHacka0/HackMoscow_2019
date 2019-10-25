module.export.func = (con, req, res) => {
	res.render('login', {
		title:"Авторизация",
		isLogin:req.session.refresh_token ? true : false,
		user: req.session
	});
};
module.exports.path = '/login';