var exec = require('cordova/exec');
var cordova = require('cordova');

exports.disableBatteryOptimizations = function()
{
	console.log('123');
	console.log(cordova.exec(null, null, 'snBatteryOptimization', 'battery', []));
	console.log('1234');
    cordova.exec(null, null, 'snBatteryOptimization', 'battery', []);
    console.log('12345');
    console.log(cordova.exec(null, null, 'snBatteryOptimization', 'sdabattery321', []));
    console.log('123456');
};