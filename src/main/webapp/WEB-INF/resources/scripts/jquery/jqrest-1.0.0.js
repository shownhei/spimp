/**
 * jQuery RESTful插件，实现PUT和DELETE AJAX请求。
 * 
 * @author Xiong Shuhong
 */
(function() {
	function rest(url, data, callback) {
		return function(type) {
			if (callback === undefined) {
				callback = data;
				data = null;
			}

			$.ajax({
				type : type,
				url : url,
				data : data,
				cache : false,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success : callback
			});
		};
	}

	jQuery.extend({
		get : function() {
			rest.apply(this, arguments)('GET');
		},
		post : function() {
			rest.apply(this, arguments)('POST');
		},
		put : function() {
			rest.apply(this, arguments)('PUT');
		},
		del : function() {
			rest.apply(this, arguments)('DELETE');
		}
	});
}());
