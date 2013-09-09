define("ikj/rest/1.0.0/rest-debug", [ "$-debug" ], function(require, exports, module) {
    var $ = require("$-debug");
    function rest(url, data, callback) {
        return function(type) {
            if (callback === undefined) {
                callback = data;
                data = null;
            }
            $.ajax({
                type: type,
                url: url,
                data: data,
                cache: false,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: callback
            });
        };
    }
    $.extend({
        get: function() {
            rest.apply(this, arguments)("GET");
        },
        post: function() {
            rest.apply(this, arguments)("POST");
        },
        put: function() {
            rest.apply(this, arguments)("PUT");
        },
        del: function() {
            rest.apply(this, arguments)("DELETE");
        }
    });
});
