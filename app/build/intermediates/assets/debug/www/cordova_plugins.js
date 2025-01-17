cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/org.apache.cordova.camera/www/CameraConstants.js",
        "id": "org.apache.cordova.camera.Camera",
        "clobbers": [
            "Camera"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.camera/www/CameraPopoverOptions.js",
        "id": "org.apache.cordova.camera.CameraPopoverOptions",
        "clobbers": [
            "CameraPopoverOptions"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.camera/www/Camera.js",
        "id": "org.apache.cordova.camera.camera",
        "clobbers": [
            "navigator.camera"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.camera/www/CameraPopoverHandle.js",
        "id": "org.apache.cordova.camera.CameraPopoverHandle",
        "clobbers": [
            "CameraPopoverHandle"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.dialogs/www/notification.js",
        "id": "org.apache.cordova.dialogs.notification",
        "merges": [
            "navigator.notification"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.dialogs/www/android/notification.js",
        "id": "org.apache.cordova.dialogs.notification_android",
        "merges": [
            "navigator.notification"
        ]
    },
    {
        "file": "plugins/org.apache.cordova.vibration/www/vibration.js",
        "id": "org.apache.cordova.vibration.notification",
        "merges": [
            "navigator.notification"
        ]
    },
      {
            "file": "plugins/intent.js",
            "id": "org.apache.cordova.intent",
            "merges": [
                "navigator.intent"
            ]
        },
    {
        "file": "plugins/jstocordova.js",
        "id": "org.apache.cordova.jstocordova",
        "merges": [
            "navigator.jstocordova"
        ]
    },
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.camera": "0.2.7",
    "org.apache.cordova.dialogs": "0.2.6",
    "org.apache.cordova.vibration": "0.3.7",
       "org.apache.cordova.jstocordova" :"0.0.1",
    "org.apache.cordova.intent" :"0.0.1",

}
// BOTTOM OF METADATA
});