cordova.define("org.apache.cordova.intent", function(require, exports, module) {

var exec = require('cordova/exec');



module.exports = {


    demo: function(mills,eid) {
        exec(function(winParam){
        	null
        }, null, "Intent", "intent", [mills,eid]);
    },
    login: function(mills) {
              exec(function(winParam){
              	null
              }, null, "Intent", "login", [mills]);
          },
    logout: function(mills) {
        exec(function(winParam){
            null
        }, null, "Intent", "logout", [mills]);
    },
};

});
