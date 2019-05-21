var exec = require('cordova/exec');

exports.disableBatteryOptimizations = function()
{
	console.log('123');
    cordova.exec(null, null, 'snBatteryOptimization', 'battery', []);
    console.log('1234');
};