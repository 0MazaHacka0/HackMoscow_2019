module.exports.func = async (con, req, res) => {
	console.log(req.body);

	if (!req.body) {
		res.json({error: "NoBodyData"});
		return;
	}

	con.query('INSERT INTO points (uID, lat, lng, radius) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE time = time + 1', [req.body.uid, req.body.location.lat, req.body.location.lng, req.body.location.radius], (err) => {if (err) throw err; res.json({status: "OK"});});

	// res.json({status: "OK"});
};

module.exports.path = '/api/point';