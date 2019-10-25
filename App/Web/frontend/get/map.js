module.export.func = (con, req, res) => {
	res.render('map', {
		title:"MTS карта",
		isLogin:req.session.refresh_token ? true : false,
		user: req.session
	});
};
module.exports.path = '/';