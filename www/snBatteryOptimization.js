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
exports.moveToForeground2 = function(arg0, success, error)
{
	console.log('vai chamar java');
	exec(success, error, "snBatteryOptimization", "foreground2", []);
	console.log('passou vai chamar java');
};
exports.moveToForeground3 = function(arg0, success, error)
{
	console.log('vai chamar java');
	exec(success, error, "snBatteryOptimization", "foreground3", []);
	console.log('passou vai chamar java');
};
exports.moveToForeground4 = function(arg0, success, error)
{
	console.log('vai chamar java');
	exec(success, error, "snBatteryOptimization", "foreground4", []);
	console.log('passou vai chamar java');
};