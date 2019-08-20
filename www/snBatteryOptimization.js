var exec = require('cordova/exec');

exports.disableBatteryOptimizations = function(arg0, success, error)
{
	exec(success, error, "snBatteryOptimization", "battery", []);
};
exports.moveToForeground = function(arg0, success, error)
{
	console.log('vai chamar java');
	exec(success, error, "snBatteryOptimization", "foreground", []);
	console.log('passou vai chamar java');
};