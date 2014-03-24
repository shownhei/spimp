define("ikj/rest/1.0.0/rest-debug", [ "$-debug" ], function(require, exports, module) {
    var $ = require("$-debug");
    function rest(url, data, callback) {
        return function(type) {
            if (callback === undefined) {
                callback = data;
                data = null;
            }
            var modalBblack=$('#__confirm-modal-black');
            if(modalBblack.length<=0){
                $('<div class="modal-backdrop  in" id="__confirm-modal-black" style="z-index:1052;display:none"></div>').appendTo($("body"));
            }
            if(type==='POST'||type==='PUT'){
                $('#__confirm-modal-black').show();
            }
            $.ajax({
                type: type,
                url: url,
                data: data,
                cache: false,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function(data){
                    var modalBlack=$('#__confirm-modal-black');
                    if(modalBlack.length>0){
                        modalBlack.hide();
                    }
                    callback(data);
                }
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
