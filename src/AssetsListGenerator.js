const request = require('request');
const fs = require('fs');
const rp = require('request-promise');

const baseUrl = 'https://dolphin.jump-technology.com:3472/api/v1';
const fileName = 'assets-list.json';

var options = {
    url: baseUrl + '/asset/',
    method: 'GET',
    timeout: 600000,
    headers: {
	'Authorization': 'Basic ZXBpdGFfdXNlcl8zOmRrdzNKUmVOZFptWjZXVjQ=',
	'Accept': 'application/json'
    }
};

var jsonContent1 = "";
var jsonContent2 = "";
jsonContent1 += '[';

request(options, function(err, res, body) {
    if (err) {
	throw err;
    } else {
	var assetArray = JSON.parse(body);
	var i = 0;
	var n = assetArray.length;
	var id = 0;
	var urls = [];
	for (; i < n - 1; i++) {
	    id = assetArray[i].ASSET_DATABASE_ID.value;
	    var options2 = {
		url: baseUrl + '/asset/' + id + '/quote',
		method: 'GET',
		timeout: 600000,
		headers: {
		    'Authorization': 'Basic ZXBpdGFfdXNlcl8zOmRrdzNKUmVOZFptWjZXVjQ=',
		    'Accept': 'application/json'
		}
	    };
	    urls.push(options2);
	}
	const promises = urls.map(url => rp(url));
	Promise.all(promises)
	    .then((response) => {
		var n = response.length;
		var j = 0;
		for (; j < n - 1; j++) {
		    var assetBody = JSON.parse(response[j]);
		    if (!(assetBody[0] && assetBody[0].asset && (id = assetBody[0].asset.value)))
			continue;
		    if (j < n / 2) {
			jsonContent1 += '{"id": ' + id + ', "quotes": ' + JSON.stringify(assetBody) + '},';
		    } else {
			jsonContent2 += '{"id": ' + id + ', "quotes": ' + JSON.stringify(assetBody) + '},';
		    }
		}
		var assetBody = JSON.parse(response[j]);
		if (assetBody[0] && assetBody[0].asset && (id = assetBody[0].asset.value))
		    jsonContent2 += '{"id": ' + id + ', "quotes": ' + JSON.stringify(assetBody) + '}]';
		fs.open(fileName, 'a', (err, fd) => {
		    if (err) throw err;
		    fs.appendFile(fd, jsonContent1, 'utf8', (err) => {
			if (err) throw err;
		    });
		    fs.appendFile(fd, jsonContent2, 'utf8', (err) => {
			fs.close(fd, (err) => {
			    if (err) throw err;
			});
			if (err) throw err;
		    });
		});
		console.log('Done.');
	    })
	    .catch((err) => {
		console.error(err);
	    });
    }
});
