var exec = require('cordova/exec');

exports.disableBatteryOptimizations = function(arg0, success, error)
{
	console.log('123');
	exec(success, error, "snBatteryOptimization", "battery", []);
	console.log('1234');
	exec(success, error, "snBatteryOptimization", "dsabattery", []);
	console.log('12345');
	exec(success, error, "snBatteryOptimization", "teste", []);
	console.log('123456');

	// console.log(exec(null, null, 'snBatteryOptimization', 'battery', []));
	// console.log('1234');
 //    exec(null, null, 'snBatteryOptimization', 'battery', []);
 //    console.log('12345');
 //    console.log(exec(null, null, 'snBatteryOptimization', 'sdabattery321', []));
 //    console.log('123456');


    
};