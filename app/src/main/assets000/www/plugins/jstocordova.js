cordova.define("org.apache.cordova.jstocordova", function(require, exports, module) {

var exec = require('cordova/exec');



module.exports = {


    demo: function(mills) {
        exec(function(winParam){
        	null
        }, null, "Jstocordova", "jstocordova", [mills]);
    },
};

});
