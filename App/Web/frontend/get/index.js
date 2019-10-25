module.export.func = (con, req, res) => {
	res.render('index', {
		title:"Главная страница",
		isLogin:req.session.refresh_token ? true : false,
		user: req.session
	});
};
module.exports.path = '/';