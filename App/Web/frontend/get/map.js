module.exports.func = (con, req, res) => {

	if (!req.session.uID) {
		res.redirect('/');
		return;
	}

	res.render('map', {
		title:"Карта",
		isLogin:req.session.uID ? true : false,
		user: req.session
	});
	
};
module.exports.path = '/map';