module.exports.func = async (req, res) => {
	console.log(req.body);

	if (!req.body) {
		res.json({error: "NoBodyData"});
		return;
	}

	let wait = await con.userLib.promise(con, con.queryValue, 'SELECT id FROM points WHERE lat = ? AND lng = ?', [req.body.location.latitude, req.body.location.longitude]);
	if (wait.res) {
		res.json({error: "HasPoint"});
		return;
	}

	con.query('INSERT INTO points (uID, lat, lng, radius) VALUES (?, ?, ?, ?)', [req.body.uID, req.body.location.latitude, req.body.location.longitude, req.body.radius], (err) => {if (err) throw err; res.json({status: "OK"});});

	// res.json({status: "OK"});
};

module.exports.path = '/api/point';