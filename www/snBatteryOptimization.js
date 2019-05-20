var exec = require('cordova/exec');

exports.disableBatteryOptimizations = function()
{
    cordova.exec(null, null, 'snBatteryOptimization', 'battery', []);
};