
var exec = require('cordova/exec');

// var PLUGIN_NAME = 'MyCordovaPlugin';
var PLUGIN_NAME = 'CordovaPluginScancode';

var MyCordovaPlugin = {
  requestPermission: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'requestPermission');
  },
  scan: function(data,cb) {
    exec(cb, null, PLUGIN_NAME, 'scan', [data]);
  }
};

module.exports = MyCordovaPlugin;
