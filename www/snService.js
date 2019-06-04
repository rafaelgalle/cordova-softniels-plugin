var exec = require('cordova/exec');

exports.startService1 = function(arg0, success, error)
{
	console.log('entrou startService1');
	exec(success, error, "snService", "startService1", []);
	console.log('entrou startService2');
};
exports.stopService1 = function(arg0, success, error)
{
	console.log('entrou stopService1');
	exec(success, error, "snService", "stopService1", []);
	console.log('entrou stopService1');
};