define(function(require, exports, module) {
	var $ = require('kjquery');
	var rjhProcessInDialog=function(url){
		/**网页对话框方式打开结果展示页***/
		var rjhProcessUrl='http://' + location.hostname + ":" + location.port +url;
		WebMineSystem.WebInterface('{"URL":"'+rjhProcessUrl+'","ISWEB":1}');
		WebMineSystem.DoCommand('设置 网页 开');
	};
	window.rjhProcessInDialog=rjhProcessInDialog;
});
