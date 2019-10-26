module.exports.func = (con, req, res) => {
	res.render('index', {
		title:"Главная страница",
		isLogin:req.session.uID ? true : false,
		user: req.session
	});
};

module.exports.path = '/';