var exec = require('cordova/exec');

exports.disableBatteryOptimizations = function(arg0, success, error)
{
	exec(success, error, "snBatteryOptimization", "battery", []);
};
exports.disableWebViewOptimizations = function(arg0, success, error)
{
	exec(success, error, "snBatteryOptimization", "webview", []);
};